package com.mello.mello.Controllers;


import com.mello.mello.Services.Service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class SimpleRoutesController {

    private ItemService itemService;

    @GetMapping("/")
    public String routeToIndex(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "index";
    }

}
