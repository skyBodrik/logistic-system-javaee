package logistic.web.repositories;


import logistic.web.dao.CalendarDao;
import logistic.web.models.Calendar;
import logistic.web.models.User;

import javax.ejb.EJB;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarRepository {
    private static CalendarRepository instance;

    @EJB
    private CalendarDao dao = new CalendarDao();

    public static CalendarRepository getInstance() {
        if (instance == null) {
            instance = new CalendarRepository();
        }

        return instance;
    }

    public List<Calendar> getAll() {
        return this.dao.getAll();
    }

    public Calendar getById(int id) {
        return this.dao.getById(id);
    }

    public List<Calendar> getCalendarForOperator() {
        List<Calendar> calendar = this.getAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        long startDate = System.currentTimeMillis();
        String d = dateFormat.format(new java.util.Date(startDate));
        return calendar.stream().sorted((l, p) -> l.getDate().compareTo(p.getDate())).filter(p -> p.getDate().toString().compareTo(d) >= 0).collect(Collectors.toList());
        //return calendar.stream().sorted((l, p) -> l.getDate().compareTo(p.getDate())).collect(Collectors.toList());
    }

    public List<Calendar> getCalendarForCarrier(User currentUser) {
        List<Calendar> calendar = this.getAll().stream().filter(c -> c.getUserId() == currentUser.getId()).collect(Collectors.toList());
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        long startDate = System.currentTimeMillis();
        for (int i=0; i<30; i++) {
            String d = dateFormat.format(new java.util.Date(startDate));
            if (!calendar.stream().anyMatch(p -> p.getDate().toString().equals(d))) {
                calendar.add(new Calendar(d, currentUser.getId(), 0));
            }
            startDate += 86400000;
        }
        dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        startDate = System.currentTimeMillis();
        String d = dateFormat.format(new java.util.Date(startDate));
        return calendar.stream().sorted((l, p) -> l.getDate().compareTo(p.getDate())).filter(p -> p.getDate().toString().compareTo(d) >= 0).collect(Collectors.toList());
    }
}
