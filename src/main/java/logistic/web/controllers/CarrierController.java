package logistic.web.controllers;

import logistic.web.facade.CalendarFacade;
import logistic.web.facade.CityFacade;
import logistic.web.facade.OrderFacade;
import logistic.web.models.Calendar;
import logistic.web.models.Order;
import logistic.web.models.User;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * Created by bodrik on 22.04.17.
 */
public class CarrierController extends MainController implements iController {
    @Override
    protected boolean checkAuthorization() throws ServletException, IOException {
        if (super.checkAuthorization() && this.userFacade.getLoggedUser().getType() == User.TYPE_CARRIER) {
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

        if (action.equals("changeStatusToDelivered")) {
            Order order = OrderFacade.getRepository().getById(orderId);
            if (order.getStatus() == Order.STATUS_IN_TRANSIT) {
                order.setStatus(Order.STATUS_DELIVERED);
                OrderFacade.saveOrder(order);
                this.dataTemplate.getStorage().put("statusCode", 0);
                this.dataTemplate.getStorage().put("statusMessage", "Статус установлен");
            } else {
                this.dataTemplate.getStorage().put("statusCode", 1);
                this.dataTemplate.getStorage().put("statusMessage", "Ошибка");
            }
            this.request.getRequestURI();
            this.response.setContentType("text/html;charset=utf-8");

            PrintWriter pw = this.response.getWriter();
            pw.println("{ \"code\": " + this.dataTemplate.getStorage().get("statusCode") + ", \"message\": \"" + this.dataTemplate.getStorage().get("statusMessage") + "\"}");
            return true;
        }

        this.dataTemplate.getStorage().put("orders", OrderFacade.getRepository().getAllByCarrier(this.userFacade.getLoggedUser()));
        renderTemplate("/carrier/orders.html");
        return true;
    }

    public boolean calendarPage() throws ServletException, IOException {
        if (!this.checkAuthorization()) {
            response.sendRedirect((String) this.dataTemplate.getStorage().get("documentFolderPath") + "/sign-in");
            return true;
        }

        try {
            String action = Optional.ofNullable(this.request.getParameter("action")).orElse("");
            String[] ids = this.request.getParameterValues("calendarId");
            String[] cities = this.request.getParameterValues("city");
            String[] dates = this.request.getParameterValues("date");
            if (action.equals("save_calendar")) {
                for (int i = 0; i < ids.length; i++) {
                    Calendar calendar;
                    int cityId = 0;
                    if (!cities[i].isEmpty()) {
                        cityId = Integer.parseInt(cities[i]);
                    } else {
                        continue;
                    }
                    if (ids[i].equals("0")) {
                        calendar = new Calendar(dates[i], this.userFacade.getLoggedUser().getId(), cityId);
                        CalendarFacade.createCalendar(calendar);
                    } else {
                        calendar = CalendarFacade.getRepository().getById(Integer.parseInt(ids[i]));
                        calendar.setCityId(cityId);
                        CalendarFacade.saveCalendar(calendar);
                    }
                }
                this.dataTemplate.getStorage().put("statusMessage", "Сохранение выполнено успешно");
            }
        } catch (Exception e) {
            this.dataTemplate.getStorage().put("statusMessage", "Упс... неудача!");
        }

        this.dataTemplate.getStorage().put("cities", CityFacade.getCities());
        this.dataTemplate.getStorage().put("calendar", this.userFacade.getCalendar());
        renderTemplate("/carrier/calendar.html");
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
                User user = this.userFacade.updateLoggedUser(name, email, phone, postWeight, postLength, postWidth, postHeight);
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
