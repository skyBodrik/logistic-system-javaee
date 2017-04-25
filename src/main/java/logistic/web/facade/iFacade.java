package logistic.web.facade;

import logistic.web.models.Calendar;
import logistic.web.models.User;

import java.util.List;

/**
 * Created by bodrik on 21.04.17.
 */
public interface iFacade {
    int getCounter();
    void setCounter(int counter);

    User getLoggedUser();

    void setLoggedUser(User user);

    User getById(int id);

    User login(String email, String password);

    User updateLoggedUser(String name, String email, String phone, double weight, double length, double width, double height);

    User updateLoggedUser(String name, String email, String phone);

    public List<Calendar> getCalendar();
}
