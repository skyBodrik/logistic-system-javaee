package logistic.web.controllers;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * Created by bodrik on 21.04.17.
 */
@WebServlet("/logout")
public class LogoutController extends MainController implements iController {
    @Override
    public boolean indexPage() throws ServletException, IOException {
        if (this.checkAuthorization()) {
            try {
                InitialContext context = new InitialContext();
                context.unbind("facade" + this.session.getId());
                this.session.invalidate();
            } catch (NamingException e) {
                e.printStackTrace();
                return false;
            }
        }
        response.sendRedirect((String) this.dataTemplate.getStorage().get("documentFolderPath") + "/sign-in");
        return true;
    }
}
