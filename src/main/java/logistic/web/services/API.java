package logistic.web.services;

import logistic.web.models.Order;
import logistic.web.repositories.OrdersRepository;

import java.util.Optional;

import static spark.Spark.*;

public class API {
    public static void start() {
        get("/get-status-order/:orderId", (req, res) -> {
               int orderId = Integer.parseInt(req.params(":orderId"));
                try{
                    Optional <Order> order = OrdersRepository.getInstance().getAll().stream().filter(p -> p.getId() == orderId).findFirst();

                    return order.get().getStatusOrderJson();
                } catch (Exception e) {
                    res.status(400);
                    return "Not found order with id " + orderId + "";
                }
            }
        );

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(e.getLocalizedMessage());
        });
    }
}
