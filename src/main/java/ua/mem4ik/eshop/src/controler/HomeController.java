package ua.mem4ik.eshop.src.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.mem4ik.eshop.src.domain.Item;
import ua.mem4ik.eshop.src.service.ItemService;

@Controller
public class HomeController {

    private ItemService itemService;


    public HomeController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "GreetingPage";
    }
    @GetMapping("/category/{name}")
    public String category(@PathVariable String name, Model model) {
        model.addAttribute("items", itemService.findByCategory(name));
        return "GreetingPage";
    }
    @GetMapping("/create-item")
    public String createItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "CreateItem";
    }

    @PostMapping("/create-item")
    public String createItemSubmit(@ModelAttribute Item item) {
        itemService.addItem(item);
        return "redirect:/";
    }

}
