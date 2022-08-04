package com.tifuseini.tutorials.todomvcthymeleaf;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class TodoItemcontroller {

    private final TodoItemRepository todoItemRepository;

    @GetMapping
    private String index(Model model) {
        model.addAttribute("item", new TodoItemFormData());
        model.addAttribute("todos",getTodoItems());
        model.addAttribute("totalNumberOfItems",todoItemRepository.count());
        return "index";
    }

    private List<TodoItemDto> getTodoItems() {
        return todoItemRepository.findAll()
                .stream()
                .map(todoItem -> new TodoItemDto(todoItem.getId(),
                        todoItem.getTitle(),
                        todoItem.isCompleted())
                ).collect(Collectors.toList());
    }

    public static record TodoItemDto(long id, String title, boolean completed) {
    }

    @PostMapping
    public String addTodoItem(@Valid @ModelAttribute("item") TodoItemFormData formData) {
        todoItemRepository.save(new TodoItem(formData.getTitle()));
        return "redirect:/";
    }
}
