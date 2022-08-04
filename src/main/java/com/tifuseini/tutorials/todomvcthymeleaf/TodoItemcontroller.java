package com.tifuseini.tutorials.todomvcthymeleaf;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class TodoItemcontroller {

    private final TodoItemRepository todoItemRepository;

    @GetMapping
    private String index(Model model) {
        model.addAttribute("item", new TodoItemFormData());
        model.addAttribute("totalNumberOfItems",todoItemRepository.count());
        return "index";
    }

    @PostMapping
    public String addTodoItem(@Valid @ModelAttribute("item") TodoItemFormData formData) {
        todoItemRepository.save(new TodoItem(formData.getTitle()));
        return "redirect:/";
    }
}
