package ua.mem4ik.eshop.src.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

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
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}

