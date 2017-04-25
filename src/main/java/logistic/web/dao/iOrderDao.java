package logistic.web.dao;

import logistic.web.models.Order;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by bodrik on 23.04.17.
 */
@Local
public interface iOrderDao {

    void add(Order order);

    void update(Order order);

    void delete(Order order);

    Order getById(int id);

    List<Order> getAll();
}
