import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;

public class GoogleAuthenticator {
  
	final static int secretSize = 10;
	final static int numOfScratchCodes = 5;
 	final static int scratchCodeSize = 8;
 
 	int window_size =3;  

 	public void setWindowSize(int s) {
  		if( s >= 1 && s <= 17 )
   		window_size = s;
 	}

 	public static String generateSecretKey() {
  		byte[] buffer = new byte[secretSize + numOfScratchCodes* scratchCodeSize];

  		new Random().nextBytes(buffer);

  		Base32 codec = new Base32();
  		byte[] secretKey = Arrays.copyOf(buffer, secretSize);
  		byte[] bEncodedKey = codec.encode(secretKey);
  		String encodedKey = new String(bEncodedKey);
  		return encodedKey;
 	}

 	public static String getQRBarcodeURL(String user, String host, String secret) {
  		String format = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s";
  		return String.format(format, user, host, secret);
 	}

 	public boolean check_code(String secret, long code, long timeMsec) {
  		Base32 codec = new Base32();
  		byte[] decodedKey = codec.decode(secret);

  		long t = (timeMsec / 1000L) / 30L;
  
  		for (int i = -window_size; i <= window_size; ++i) {
   			long hash;
   			try {
    				hash = verify_code(decodedKey, t + i);
   			} catch (Exception e) {
    				e.printStackTrace();
    				throw new RuntimeException(e.getMessage());
   			} 
   			if (hash == code) {
    				return true;
   			}
  		}
  		return false;
 	}

 
 	private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
  		byte[] data = new byte[8];
  		long value = t ;
  		for (int i = 8; i-- > 0; value >>>= 8) {
   			data[i] = (byte) value;
  		}

  		SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
  		Mac mac = Mac.getInstance("HmacSHA1");
  		mac.init(signKey);
  		byte[] hash = mac.doFinal(data);

  		int offset = hash[19] & 0xF;

  		long truncatedHash = 0;
  		for (int i = 0; i < 4; ++i) {
   			truncatedHash <<= 8;

   			truncatedHash |= (hash[offset + i] & 0xFF);
  		}

  		truncatedHash &= 0x7FFFFFFF;
  		truncatedHash %= 1000000;

  		return (int) truncatedHash;
 	}
}