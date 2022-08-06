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
    public String index(Model model) {
        addAttributesForIndex(model, ListFilter.ALL);
        return "index";
    }

    @GetMapping("/active")
    public String indexActive(Model model) {
        addAttributesForIndex(model, ListFilter.ACTIVE);
        return "index";
    }

    @GetMapping("/completed")
    public String indexCompleted(Model model) {
        addAttributesForIndex(model, ListFilter.COMPLETED);
        return "index";
    }

    private void addAttributesForIndex(Model model, ListFilter listFilter) {
        model.addAttribute("item", new TodoItemFormData());
        model.addAttribute("filter", listFilter);
        model.addAttribute("todos",getTodoItems(listFilter));
        model.addAttribute("totalNumberOfItems",todoItemRepository.count());
        model.addAttribute("numberOfActiveItems", getNumberOfActiveItems());
        model.addAttribute("numberOfCompletedItems", getNumberOfCompletedItems());
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


    @PutMapping("/toggle-all")
    public String toggleAll() {
        List<TodoItem> todoItems = todoItemRepository.findAll();
        for (TodoItem todoItem : todoItems) {
            todoItem.setCompleted(!todoItem.isCompleted());
            todoItemRepository.save(todoItem);
        }
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteTodoItem(@PathVariable("id") long id) {
        todoItemRepository.deleteById(id);
        return "redirect:/";
    }

    @DeleteMapping("/completed")
    public String deleteCompletedItems() {
        List<TodoItem> items = todoItemRepository.findAllByCompleted(true);
        for (TodoItem item : items) {
            todoItemRepository.deleteById(item.getId());
        }
        return "redirect:/";
    }


    private List<TodoItemDto> getTodoItems(ListFilter filter) {
        return switch (filter) {
            case ALL -> convertToDto(todoItemRepository.findAll());
            case ACTIVE -> convertToDto(todoItemRepository.findAllByCompleted(false));
            case COMPLETED -> convertToDto(todoItemRepository.findAllByCompleted(true));
        };
    }

    private List<TodoItemDto> convertToDto(List<TodoItem> todoItems) {
        return todoItems
                .stream()
                .map(todoItem -> new TodoItemDto(todoItem.getId(),
                        todoItem.getTitle(),
                        todoItem.isCompleted()))
                .collect(Collectors.toList());
    }

    private int getNumberOfActiveItems() {
        return todoItemRepository.countAllByCompleted(false);
    }

    private int getNumberOfCompletedItems() {
        return todoItemRepository.countAllByCompleted(true);
    }

    public static record TodoItemDto(long id, String title, boolean completed) {
    }

    public enum ListFilter {
        ALL,
        ACTIVE,
        COMPLETED
    }
}
