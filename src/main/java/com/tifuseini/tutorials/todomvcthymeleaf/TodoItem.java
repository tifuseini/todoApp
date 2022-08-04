package com.tifuseini.tutorials.todomvcthymeleaf;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class TodoItem {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    private boolean completed;

}
