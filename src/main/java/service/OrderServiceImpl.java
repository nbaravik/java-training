package service;

import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import repository.OrderRepository;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order saveOrder(Order order) {
        // TODO add validation!!!
        return orderRepository.save(order);
    }

    @Override
    public List<Order> fetchOrderList() {
        return (List<Order>) orderRepository.findAll();
    }
}
