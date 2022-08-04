package com.tifuseini.tutorials.todomvcthymeleaf;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TodoItemFormData {

    @NotBlank
    private String title;
}
