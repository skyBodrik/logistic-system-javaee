package logistic.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInController extends MainController implements iController {
    @Override
    public void run() throws ServletException, IOException {
/*        this.request.getRequestURI();
        this.response.setContentType("text/html;charset=utf-8");

        PrintWriter pw = this.response.getWriter();
        pw.println("url of page: " + this.request.getParameter("name"));*/


        dataTemplate.put("name", "igor");
        renderTemplate("/sign-up.html");
    }
}
