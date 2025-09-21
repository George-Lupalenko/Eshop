package ua.mem4ik.eshop.src.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.mem4ik.eshop.src.domain.Item;
import ua.mem4ik.eshop.src.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "ItemPage";
    }
}