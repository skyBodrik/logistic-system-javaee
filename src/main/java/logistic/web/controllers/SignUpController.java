package logistic.web.controllers;

import logistic.web.facade.UserFacade;
import logistic.web.models.User;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by bodrik on 22.04.17.
 */
@WebServlet("/sign-up")
public class SignUpController extends MainController implements iController {
    @Override
    public boolean indexPage() throws ServletException, IOException {
        String action = Optional.ofNullable(this.request.getParameter("action")).orElse("");
        String name = Optional.ofNullable(this.request.getParameter("name")).orElse("");
        String email = Optional.ofNullable(this.request.getParameter("email")).orElse("");
        String phone = Optional.ofNullable(this.request.getParameter("phone")).orElse("");
        String password = Optional.ofNullable(this.request.getParameter("password")).orElse("");
        int role = Integer.parseInt(Optional.ofNullable(this.request.getParameter("role")).orElse("0"));
/*        if (this.checkAuthorization()) {
            response.sendRedirect((String) this.dataTemplate.getStorage().get("documentFolderPath") + "/sign-in");
            return false;
        }*/
        if (action.equals("create_user")) {
            User user = null;
            switch (role) {
                case User.TYPE_CLIENT:
                    user = UserFacade.createClient(name, email, password, phone);
                    break;
                case User.TYPE_OPERATOR:
                    user = UserFacade.createOperator(name, email, password, phone);
                    break;
                case User.TYPE_CARRIER:
                    user = UserFacade.createCarrier(name, email, password, phone);
                    break;
            }
            if (user == null) {
                this.dataTemplate.getStorage().put("statusMessage", "Упс... неудача!");
            } else {
                this.dataTemplate.getStorage().put("statusMessage", "Пользователь успешно создан!");
            }
        }

        renderTemplate("/sign-up.html");
        return true;
    }
}
