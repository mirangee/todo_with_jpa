package com.example.todo.todoApi.service;

import com.example.todo.todoApi.dto.reponse.TodoDetailResponseDTO;
import com.example.todo.todoApi.dto.reponse.TodoListResponseDTO;
import com.example.todo.todoApi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoApi.entity.Todo;
import com.example.todo.todoApi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoListResponseDTO create(TodoCreateRequestDTO requestDTO) throws Exception {
        todoRepository.save(requestDTO.toEntity());
        log.info("할 일 저장 완료! 제목: {}", requestDTO.getTitle());
        return retrieve();
    }

    // 할 일 목록 가져오기
    public TodoListResponseDTO retrieve() throws Exception {
        List<Todo> entityList = todoRepository.findAll();
        List<TodoDetailResponseDTO> dtoList = entityList.stream()
//                .map(entity -> new TodoDetailResponseDTO(entity))
                .map(TodoDetailResponseDTO::new)
                .collect(Collectors.toList());

        return TodoListResponseDTO.builder()
                .todos(dtoList)
                .build();
    }
}
