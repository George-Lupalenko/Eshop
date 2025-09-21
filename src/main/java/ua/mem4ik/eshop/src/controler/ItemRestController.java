package ua.mem4ik.eshop.src.controler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mem4ik.eshop.src.domain.Item;
import ua.mem4ik.eshop.src.service.ItemService;
import java.util.List;

@RestController
@RequestMapping("/eshop/api/item")
public class ItemRestController {

    private final ItemService itemService;

    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    @PostMapping("/")
    public Item createItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }
    @GetMapping("/item/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "ItemPage";
    }

}

