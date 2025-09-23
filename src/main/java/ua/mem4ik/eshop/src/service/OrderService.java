package ua.mem4ik.eshop.src.service;

import org.springframework.stereotype.Service;
import ua.mem4ik.eshop.src.domain.Order;
import ua.mem4ik.eshop.src.repository.OrderRepository;

@Service
public class OrderService {
    private final  OrderRepository orderRepository;
    private final MailService mailService;

    public OrderService(OrderRepository orderRepository, MailService mailService) {
        this.orderRepository = orderRepository;
        this.mailService = mailService;
    }

    private void sendOrderEmail(Order order) {
        String to = order.getItem().getAuthor().getEmail();
        String itemTitle = order.getItem().getTitle();
        String buyerName = order.getBuyer().getEmail();
        mailService.sendOrderNotification(to, itemTitle, buyerName);
    }
}
