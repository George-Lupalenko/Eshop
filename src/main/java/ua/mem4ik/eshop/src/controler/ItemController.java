package ua.mem4ik.eshop.src.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.mem4ik.eshop.src.domain.Item;
import ua.mem4ik.eshop.src.service.ItemService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "ItemPage";
    }
    @PostMapping("/create-item")
    public String createItemSubmit(
            @ModelAttribute Item item,
            @RequestParam("image") MultipartFile file
    ) {
        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String uploadDir = "images/";

                Path path = Paths.get(uploadDir + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                item.setImagePath("/" + uploadDir + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        itemService.addItem(item);
        return "redirect:/";
    }
}