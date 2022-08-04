package com.tifuseini.tutorials.todomvcthymeleaf;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class TodoItemcontroller {

    private final TodoItemRepository todoItemRepository;

    @GetMapping
    private String index(Model model) {
        model.addAttribute("totalNumberOfItems",todoItemRepository.count());
        return "index";
    }
}
