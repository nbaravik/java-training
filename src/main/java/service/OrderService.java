package service;

import model.Order;

import java.util.List;

public interface OrderService {

    // save operation
    Order saveOrder(Order order);

    // read operation
    List<Order> fetchOrderList();
}
