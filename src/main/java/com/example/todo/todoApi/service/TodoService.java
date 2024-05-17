package com.example.todo.todoApi.service;

import com.example.todo.todoApi.dto.reponse.TodoDetailResponseDTO;
import com.example.todo.todoApi.dto.reponse.TodoListResponseDTO;
import com.example.todo.todoApi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoApi.dto.request.TodoModifyRequestDTO;
import com.example.todo.todoApi.entity.Todo;
import com.example.todo.todoApi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    
    // 할 일 삭제하기
    public TodoListResponseDTO delete(final String todoId) throws Exception { // 컨트롤러에서 전달한 변수를 수정하지 못하도록 매개변수에 final 선언 가능
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> {
                    log.error("존재하지 않는 ID이기에 삭제에 실패했습니다. -ID: {}", todoId);
                    throw new RuntimeException("id가 존재하지 않아 삭제에 실패했습니다");
                }
        );
        todoRepository.deleteById(todoId);

        return retrieve();
    }

    // 할 일 체크하기
    public TodoListResponseDTO update(final TodoModifyRequestDTO requestDTO) throws Exception {
        Optional<Todo> targetEntity = todoRepository.findById(requestDTO.getId());

        targetEntity.ifPresent(todo -> {
            todo.setDone(requestDTO.isDone());

            todoRepository.save(todo);
        });

        return retrieve();
    }
}
