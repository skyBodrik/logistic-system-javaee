package logistic.web.controllers;

import logistic.web.facade.CityFacade;
import logistic.web.facade.OrderFacade;
import logistic.web.facade.UserFacade;
import logistic.web.models.Order;
import logistic.web.models.User;
import logistic.web.repositories.OrdersRepository;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Created by bodrik on 22.04.17.
 */
public class OperatorController extends MainController implements iController {
    @Override
    public boolean indexPage() throws ServletException, IOException {
        if (!this.checkAuthorization()) {
            response.sendRedirect((String) this.dataTemplate.getStorage().get("documentFolderPath") + "/sign-in");
            return false;
        }

        try {
            String action = Optional.ofNullable(this.request.getParameter("action")).orElse("");
            String[] ids = this.request.getParameterValues("orderId");
            String[] statuses = this.request.getParameterValues("status");
            String[] carriers = this.request.getParameterValues("carrier");
            String[] costs = this.request.getParameterValues("cost");
            if (action.equals("save_orders")) {
                for (int i = 0; i < ids.length; i++) {
                    Order order = OrderFacade.getRepository().getById(Integer.parseInt(ids[i]));
                    order.setStatus(Integer.parseInt(statuses[i]));
                    if (!carriers[i].isEmpty()) {
                        order.setCarrierId(Integer.parseInt(carriers[i]));
                    }
                    order.setCost(Double.parseDouble(costs[i]));
                    OrderFacade.saveOrder(order);
                }
                this.dataTemplate.getStorage().put("statusMessage", "Сохранение выполнено успешно");
            }
        } catch (Exception e) {
            this.dataTemplate.getStorage().put("statusMessage", "Упс... неудача!");
        }

        this.dataTemplate.getStorage().put("statuses", OrderFacade.getStatusNames());
        this.dataTemplate.getStorage().put("orders", OrderFacade.getRepository().getAll());
        this.dataTemplate.getStorage().put("carriers", UserFacade.getCarriers());
        renderTemplate("/operator/orders.html");
        return true;
    }

    public boolean profilePage() throws ServletException, IOException {
        if (!this.checkAuthorization()) {
            response.sendRedirect((String) this.dataTemplate.getStorage().get("documentFolderPath") + "/sign-in");
            return true;
        }

        try {
            String action = Optional.ofNullable(this.request.getParameter("action")).orElse("");
            String name = Optional.ofNullable(this.request.getParameter("name")).orElse("");
            String email = Optional.ofNullable(this.request.getParameter("email")).orElse("");
            String phone = Optional.ofNullable(this.request.getParameter("phone")).orElse("");

            if (action.equals("save_profile")) {
                User user = this.userFacade.updateLoggedUser(name, email, phone);
                if (user != null) {
                    this.dataTemplate.getStorage().put("statusMessage", "Ваш профиль успешно сохранён");
                    this.dataTemplate.getStorage().replace("currentUser", user);
                }
            }
        } catch (Exception e) {
            this.dataTemplate.getStorage().put("statusMessage", "Упс... неудача!");
        }

        renderTemplate("/profile.html");
        return true;
    }

    public boolean calendarPage() throws ServletException, IOException {
        if (!this.checkAuthorization()) {
            response.sendRedirect((String) this.dataTemplate.getStorage().get("documentFolderPath") + "/sign-in");
            return true;
        }

        this.dataTemplate.getStorage().put("calendar", this.userFacade.getCalendar());
        renderTemplate("/operator/calendar.html");
        return true;
    }
}
