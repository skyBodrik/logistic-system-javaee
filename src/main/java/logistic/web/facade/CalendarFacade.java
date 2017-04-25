package logistic.web.facade;

import logistic.web.dao.CalendarDao;
import logistic.web.models.Calendar;
import logistic.web.repositories.CalendarRepository;

/**
 * Created by bodrik on 25.04.17.
 */
public class CalendarFacade {

    public static CalendarRepository getRepository() {
        return CalendarRepository.getInstance();
    }

    public static void saveCalendar(Calendar calendar) {
        CalendarDao dao = new CalendarDao();
        dao.update(calendar);
    }

    public static void createCalendar(Calendar calendar) {
        CalendarDao dao = new CalendarDao();
        dao.add(calendar);
    }
}
