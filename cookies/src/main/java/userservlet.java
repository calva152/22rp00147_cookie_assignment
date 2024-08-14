import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/store")
public class userservlet extends HttpServlet {

    private static final String COOKIE_NAME = "userInput";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the existing cookie, if any
        Cookie[] cookies = request.getCookies();
        String storedValue = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    storedValue = cookie.getValue();
                    break;
                }
            }
        }
        
        // Send HTML response with pre-filled input
        response.setContentType("text/html");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html lang='en'>");
        response.getWriter().println("<head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>User Input Form</title></head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<h1>Enter Your Username or Email</h1>");
        response.getWriter().println("<form action='store' method='post'>");
        response.getWriter().println("<label for='userInput'>Username or Email:</label>");
        response.getWriter().println("<input type='text' id='userInput' name='userInput' value='" + storedValue + "'>");
        response.getWriter().println("<input type='submit' value='Submit'>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the value from the form
        String userInput = request.getParameter("userInput");
        
        // Store the value in a cookie
        Cookie cookie = new Cookie(COOKIE_NAME, userInput);
        cookie.setMaxAge(60 * 60 * 24 * 7); // 1 week
        response.addCookie(cookie);
        
        // Redirect to GET to display the form with the stored value
        response.sendRedirect("store");
    }
}
