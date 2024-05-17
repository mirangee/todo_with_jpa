package com.example.todo.todoApi.dto.reponse;

import com.example.todo.todoApi.entity.Todo;
import lombok.*;

@Getter @Setter
@ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TodoDetailResponseDTO {
    private String id;
    private String title;
    private boolean done;

    // 엔터티를 DTO로 변경하는 생성자
    public TodoDetailResponseDTO(Todo todo) {
        this.id = todo.getTodoId();
        this.title = todo.getTitle();
        this.done = todo.isDone();
    }
}
