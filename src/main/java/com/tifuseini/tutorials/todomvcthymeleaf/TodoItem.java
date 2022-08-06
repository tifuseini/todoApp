package com.tifuseini.tutorials.todomvcthymeleaf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoItem {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    private boolean completed;

    public TodoItem(String title) {
        this.title = title;
    }


}


