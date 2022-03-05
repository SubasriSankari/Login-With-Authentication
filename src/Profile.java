 
import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.http.*;

@WebServlet("/Profile")
public class Profile extends HttpServlet {
 private static final long serialVersionUID = 1L;

 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 }

 
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  response.setContentType("text/html");  
  PrintWriter out = response.getWriter();
  HttpSession session=request.getSession(false);
  String n=(String)session.getAttribute("uname");
  out.print("Welcome " + n + " !!! " + "Happy to see you again!!!  ");
  out.print("<a href='index.html'> Index</a>");

 }

}