package com.example.todo.todoApi.repository;

import com.example.todo.todoApi.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, String> {

}
