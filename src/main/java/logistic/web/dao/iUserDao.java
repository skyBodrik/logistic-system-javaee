package logistic.web.dao;

import logistic.web.models.User;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by bodrik on 23.04.17.
 */
@Local
public interface iUserDao {

    void add(User user);

    void update(User user);

    void delete(User user);

    User getById(int id);

    List<User> getAll();
}
