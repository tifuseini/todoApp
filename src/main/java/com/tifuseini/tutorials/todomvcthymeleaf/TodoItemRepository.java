package com.tifuseini.tutorials.todomvcthymeleaf;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    int countByCompleted(boolean completed);

}

