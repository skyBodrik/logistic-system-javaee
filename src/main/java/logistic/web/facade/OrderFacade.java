package logistic.web.facade;

import logistic.web.dao.OrderDao;
import logistic.web.models.Order;
import logistic.web.repositories.OrdersRepository;
import logistic.web.services.Acquiring;

/**
 * Created by bodrik on 25.04.17.
 */
public class OrderFacade {
    public static String[] getStatusNames() {
        return Order.statusNames;
    }

    public static OrdersRepository getRepository() {
        return OrdersRepository.getInstance();
    }

    public static void saveOrder(Order order) {
        OrderDao dao = new OrderDao();
        dao.update(order);
    }

    public static boolean toPay(Order order) {
        Acquiring ac = new Acquiring();
        if (ac.toPay()) {
            order.setStatus(Order.STATUS_PAID_WAITING_TO_DISPATCH);
            OrderFacade.saveOrder(order);
            return true;
        }
        return false;
    }
}
