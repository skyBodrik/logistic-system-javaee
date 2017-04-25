package logistic.web.dao;

import logistic.web.models.Order;
import logistic.web.repositories.UsersRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by bodrik on 23.04.17.
 */
@Stateless
public class OrderDao extends Dao<Order> implements iOrderDao {

    @Override
    public Order getById(int id) {
        return (Order)em.createNativeQuery("SELECT `id`, `weight`, `width`, `height`, `length`, `from_city_id`, `from_address`, `to_city_id`, `to_address`, `recipient_name`, `recipient_phone`, `cost`, `status`, `client_id`, `carrier_id`, `date_create` FROM `orders` WHERE `id` = :id", Order.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Order> getAll() {
        return (List<Order>)em.createNativeQuery("SELECT `id`, `weight`, `width`, `height`, `length`, `from_city_id`, `from_address`, `to_city_id`, `to_address`, `recipient_name`, `recipient_phone`, `cost`, `status`, `client_id`, `carrier_id`, `date_create` FROM `orders`", Order.class).getResultList();
    }
}
