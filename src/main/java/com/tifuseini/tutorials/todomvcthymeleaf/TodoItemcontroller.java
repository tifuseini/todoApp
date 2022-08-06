package com.tifuseini.tutorials.todomvcthymeleaf;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("numberOfActiveItems", todoItemRepository.countByCompleted(false));
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

    @PutMapping("/{id}/toggle")
    public String toggleSelection(@PathVariable("id") long id) {
        TodoItem todoItem = todoItemRepository.findById(id)
                        .orElseThrow(() -> new TodoItemNotFoundException(id));
        todoItem.setCompleted(!todoItem.isCompleted());
        todoItemRepository.save(todoItem);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteTodoItem(@PathVariable("id") long id) {
        todoItemRepository.deleteById(id);
        return "redirect:/";
    }
}
