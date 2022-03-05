 
import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.http.*;

@WebServlet("/AuthController")
public class AuthController extends HttpServlet {
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    		String username = request.getParameter("username");
  		String userpass = request.getParameter("userpass");
  		boolean is2faSetup  = true;
  		HttpSession session = request.getSession();
  		session.setAttribute("uname",username);

  		if(request.getParameter("setup") == null){
     			is2faSetup = false;  
  		} 
  
  		if (username != null && userpass != null) {
   
     			request.setAttribute("username", username);
       			if(is2faSetup){
         			request.getRequestDispatcher("/SetUpController").forward(request,response);
     			}/*else{  
         			request.getRequestDispatcher("/auth.html").forward(request,response);
     			}*/
       
  		} else {
     			request.getRequestDispatcher("/Login.html").forward(request, response);
  		}	 
 	}
}