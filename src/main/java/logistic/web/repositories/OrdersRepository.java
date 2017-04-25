package logistic.web.repositories;


import logistic.web.dao.OrderDao;
import logistic.web.models.Order;
import logistic.web.models.User;

import javax.ejb.EJB;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersRepository {
    private static OrdersRepository instance;

    @EJB
    private OrderDao dao = new OrderDao();

    public static OrdersRepository getInstance() {
        if (instance == null) {
            instance = new OrdersRepository();
        }

        return instance;
    }

    public List<Order> getAll() {
        return this.dao.getAll();
    }

    public List<Order> getAllByClient(User user) {
        return this.dao.getAll().stream().filter(order -> order.getClientId() == user.getId()).collect(Collectors.toList());
    }

    public List<Order> getAllByCarrier(User user) {
        return this.dao.getAll().stream().filter(order -> order.getCarrierId() == user.getId()).collect(Collectors.toList());
    }

    public Order createOrder(double weight, double width, double height, double length, int fromCityId, String fromAddress, int toCityId, String toAddress, String recipientName, String recipientPhone, int clientId) {
        Order order = new Order(weight, width, height, length, fromCityId, fromAddress, toCityId, toAddress, recipientName, recipientPhone, clientId);
        this.dao.add(order);
        return order;
    }

    public Order getById(int id) {
        return this.dao.getById(id);
    }
}
