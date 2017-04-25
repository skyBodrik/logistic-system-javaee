package logistic.web.controllers;

import logistic.web.dao.OrderDao;
import logistic.web.dao.UserDao;
import logistic.web.facade.CityFacade;
import logistic.web.facade.OrderFacade;
import logistic.web.facade.UserFacade;
import logistic.web.models.Order;
import logistic.web.models.User;
import logistic.web.repositories.OrdersRepository;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * Created by bodrik on 22.04.17.
 */
public class ClientController extends MainController implements iController {
    @Override
    protected boolean checkAuthorization() throws ServletException, IOException {
        if (super.checkAuthorization() && this.userFacade.getLoggedUser().getType() == User.TYPE_CLIENT) {
            return true;
        }
        return false;
    }

    @Override
    public boolean indexPage() throws ServletException, IOException {
        if (!this.checkAuthorization()) {
            response.sendRedirect((String) this.dataTemplate.getStorage().get("documentFolderPath") + "/sign-in");
            return false;
        }

        String action = Optional.ofNullable(this.request.getParameter("action")).orElse("");
        int orderId = Integer.parseInt(Optional.ofNullable(this.request.getParameter("orderId")).orElse("0"));

        if (action.equals("pay")) {
            if (OrderFacade.toPay(OrderFacade.getRepository().getById(orderId))) {
                this.dataTemplate.getStorage().put("statusCode", 0);
                this.dataTemplate.getStorage().put("statusMessage", "Заказ успешно оплачен");
            } else {
                this.dataTemplate.getStorage().put("statusCode", 1);
                this.dataTemplate.getStorage().put("statusMessage", "Ошибка оплаты");
            }
            this.request.getRequestURI();
            this.response.setContentType("text/html;charset=utf-8");

            PrintWriter pw = this.response.getWriter();
            pw.println("{ \"code\": " + this.dataTemplate.getStorage().get("statusCode") + ", \"message\": \"" + this.dataTemplate.getStorage().get("statusMessage") + "\"}");
            return true;
        }

        this.dataTemplate.getStorage().put("orders", OrderFacade.getRepository().getInstance().getAllByClient(this.userFacade.getLoggedUser()));
        renderTemplate("/client/orders.html");
        return true;
    }

    public boolean newOrderPage() throws ServletException, IOException {
        if (!this.checkAuthorization()) {
            response.sendRedirect((String) this.dataTemplate.getStorage().get("documentFolderPath") + "/sign-in");
            return true;
        }

        try {
            String action = Optional.ofNullable(this.request.getParameter("action")).orElse("");
            double postLength = Double.parseDouble(Optional.ofNullable(this.request.getParameter("postLength")).orElse("0"));;
            double postWidth = Double.parseDouble(Optional.ofNullable(this.request.getParameter("postWidth")).orElse("0"));;
            double postHeight = Double.parseDouble(Optional.ofNullable(this.request.getParameter("postHeight")).orElse("0"));;
            double postWeight = Double.parseDouble(Optional.ofNullable(this.request.getParameter("postWeight")).orElse("0"));;
            int fromCity = Integer.parseInt(Optional.ofNullable(this.request.getParameter("fromCity")).orElse("0"));
            int toCity = Integer.parseInt(Optional.ofNullable(this.request.getParameter("toCity")).orElse("0"));
            String fromAddress = Optional.ofNullable(this.request.getParameter("fromAddress")).orElse("");
            String toAddress = Optional.ofNullable(this.request.getParameter("toAddress")).orElse("");
            String recipientName = Optional.ofNullable(this.request.getParameter("recipientName")).orElse("");
            String recipientPhone = Optional.ofNullable(this.request.getParameter("recipientPhone")).orElse("");
            if (action.equals("create_order")) {
                Order order = OrdersRepository.getInstance().createOrder(postWeight, postWidth, postHeight, postLength, fromCity, fromAddress, toCity, toAddress, recipientName, recipientPhone, this.userFacade.getLoggedUser().getId());
                response.sendRedirect((String) this.dataTemplate.getStorage().get("documentFolderPath") + "/client");
                return true;
            }
        } catch (Exception e) {
            this.dataTemplate.getStorage().put("statusMessage", "Упс... неудача!");
        }

        this.dataTemplate.getStorage().put("cities", CityFacade.getCities());
        renderTemplate("/client/new-order.html");
        return true;
    }

    public boolean profilePage() throws ServletException, IOException {
        if (!this.checkAuthorization()) {
            response.sendRedirect((String) this.dataTemplate.getStorage().get("documentFolderPath") + "/sign-in");
            return true;
        }

        try {
            String action = Optional.ofNullable(this.request.getParameter("action")).orElse("");
            double postLength = Double.parseDouble(Optional.ofNullable(this.request.getParameter("postLength")).orElse("0"));;
            double postWidth = Double.parseDouble(Optional.ofNullable(this.request.getParameter("postWidth")).orElse("0"));;
            double postHeight = Double.parseDouble(Optional.ofNullable(this.request.getParameter("postHeight")).orElse("0"));;
            double postWeight = Double.parseDouble(Optional.ofNullable(this.request.getParameter("postWeight")).orElse("0"));;
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
}
