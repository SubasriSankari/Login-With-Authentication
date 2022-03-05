import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.lang.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;


@WebServlet("/SetUpController")
public class SetUpController extends HttpServlet {


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
  		String username = request.getParameter("username");
    		String secretKey = GoogleAuthenticator.generateSecretKey();
  		request.getSession().setAttribute( "secretKey", secretKey );
  
  		String s = "otpauth://totp/"+username+"?secret="+secretKey;

  		ByteArrayOutputStream outs = QRCode.from(s).to(ImageType.PNG).stream();

  		response.setContentType("image/png");
  		response.setContentLength(outs.size());
  
  		OutputStream outStream = response.getOutputStream();
  		outStream.write(outs.toByteArray());
   
  		outStream.flush();
		outStream.close();
  
		try{
  			TimeUnit.MINUTES.sleep(3);
  		}catch(InterruptedException e){
			System.out.print(e);
		}
		request.getRequestDispatcher("/auth.html").forward(request, response);

 	}

}