package logistic.web.facade;


import logistic.web.models.Calendar;
import logistic.web.models.User;
import logistic.web.repositories.CalendarRepository;
import logistic.web.repositories.UsersRepository;

import javax.ejb.*;
import javax.enterprise.context.*;
import java.io.Serializable;
import java.util.List;

@Stateful
@SessionScoped
public class UserFacade implements iFacade, Serializable {

    private int loggedUserId = -1;

    private int counter;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public User getLoggedUser() {
        if (this.loggedUserId > 0) {
            return this.getById(this.loggedUserId);
        }
        return null;
    }

    public void setLoggedUser(User user) {
        this.loggedUserId = user.getId();
    }

    public User getById(int id) {
        return UsersRepository.getInstance().getById(id);
    }

    public User login(String email, String password) {
        UsersRepository repo = UsersRepository.getInstance();
        User user = repo.getByEmailAndPassword(email, password);
        if (user != null) {
            this.loggedUserId = user.getId();
        }
        return user;
    }

    public User updateLoggedUser(String name, String email, String phone, double weight, double length, double width, double height) {
        return UsersRepository.getInstance().updateUser(this.loggedUserId, name, email, phone, weight, length, width, height);
    }

    public User updateLoggedUser(String name, String email, String phone) {
        return UsersRepository.getInstance().updateUser(this.loggedUserId, name, email, phone, 0, 0, 0, 0);
    }

    public List<Calendar> getCalendar() {
        User currentUser = this.getLoggedUser();
        if (currentUser.getType() == User.TYPE_OPERATOR) {
            return CalendarRepository.getInstance().getCalendarForOperator();
        } else if (currentUser.getType() == User.TYPE_CARRIER) {
            return CalendarRepository.getInstance().getCalendarForCarrier(currentUser);
        } else {
            return null;
        }
    }

    public static User createClient(String name, String email, String password, String phone) {
        UsersRepository rep = UsersRepository.getInstance();
        return rep.createClient(name, email, password, phone);
    }

    public static User createCarrier(String name, String email, String password, String phone) {
        UsersRepository rep = UsersRepository.getInstance();
        return rep.createCarrier(name, email, password, phone);
    }

    public static User createOperator(String name, String email, String password, String phone) {
        UsersRepository rep = UsersRepository.getInstance();
        return rep.createOperator(name, email, password, phone);
    }

    public static List<User> getCarriers() {
        return UsersRepository.getInstance().getAll(User.TYPE_CARRIER);
    }
}
