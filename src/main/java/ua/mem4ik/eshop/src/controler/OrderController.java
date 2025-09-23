package ua.mem4ik.eshop.src.controler;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mem4ik.eshop.src.domain.Item;
import ua.mem4ik.eshop.src.service.ItemService;
import ua.mem4ik.eshop.src.service.MailService;

@Controller
@RequestMapping("/eshop")
public class OrderController {

    private final ItemService itemService;
    private final MailService mailService;

    public OrderController(ItemService itemService, MailService mailService) {
        this.itemService = itemService;
        this.mailService = mailService;
    }

    @PostMapping("/order/{id}")
    public String placeOrder(@PathVariable Long id,
                             @RequestParam String buyerName,
                             @RequestParam String buyerEmail,
                             @RequestParam(required = false) String buyerMessage,
                             Model model) {
        Item item = itemService.findById(id);
        if (item == null) {
            model.addAttribute("error", "Товар не найден");
            return "error";
        }

        String message = buyerMessage != null ? buyerMessage : "";
        mailService.sendOrderNotification(buyerEmail, item.getTitle(), buyerName + ": " + message);

        model.addAttribute("success", "Сообщение успешно отправлено на почту " + buyerEmail);
        model.addAttribute("item", item);
        return "ItemPage";
    }
}

