package ua.mem4ik.eshop.src.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOrderNotification(String to, String itemTitle, String buyerName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Новый заказ на ваш товар!");
        message.setText("Ваш товар \"" + itemTitle + "\" заказал " + buyerName + ". Пожалуйста, отправьте посылку.");
        mailSender.send(message);
    }
}

