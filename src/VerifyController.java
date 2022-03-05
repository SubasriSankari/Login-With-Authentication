import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.http.*; 

@WebServlet("/VerifyController")
public class VerifyController extends HttpServlet {

 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
  		response.setContentType("text/html");
  		String codestr = request.getParameter("code");
  		long code = Long.parseLong(codestr);
  
  		long t = System.currentTimeMillis();
  		GoogleAuthenticator ga = new GoogleAuthenticator();
  		ga.setWindowSize(5);  //should give 5 * 30 seconds of grace...
  
  		String savedSecret = (String)request.getSession().getAttribute("secretKey");

  		boolean r = ga.check_code(savedSecret, code, t);
  
  		PrintWriter pw = response.getWriter();
  		HttpSession session = request.getSession(false);
  		String n=(String)session.getAttribute("uname");

  		if(r){
  			pw.write("Congratulations!!! "+ n + "You are logged in Successfully !! It's glad to meet you again ");
  			pw.print("<a href='LogoutServlet'>click here to logout</a>");
  		}else{
  			pw.write("Failed to login");
  		}
 	}
}