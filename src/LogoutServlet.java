import java.io.IOException;  
import java.io.PrintWriter;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet; 
import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.*;
import javax.servlet.http.*; 
  
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {  

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)  
    throws ServletException, IOException {  
  
    response.setContentType("text/html");  
    PrintWriter out = response.getWriter();  
          
    HttpSession session=request.getSession(false);  
    session.invalidate();
    out.println("You are successfully Logged out !!! Thank You !!. ");
    out.println("<a href='index.html'>Index</a>");
    
    } 
  
}