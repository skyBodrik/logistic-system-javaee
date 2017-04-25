package logistic.web.controllers;

import logistic.web.repositories.OrdersRepository;

import java.io.IOException;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/sign-in")
public class SignInController extends MainController implements iController {
    @Override
    public boolean indexPage() throws ServletException, IOException {
/*        this.request.getRequestURI();
        this.response.setContentType("text/html;charset=utf-8");

        PrintWriter pw = this.response.getWriter();
        pw.println("url of page: " + this.request.getParameter("name"));*/

        renderTemplate("/sign-in.html");
        return true;
    }
}
