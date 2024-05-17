package com.example.todo.todoApi.dto.request;

import com.example.todo.todoApi.entity.Todo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TodoCreateRequestDTO {

    @NotBlank
    @Size(min = 2, max = 30)
    private String title;

    // dto를 entity로 변환
    public Todo toEntity() {
        return Todo.builder().title(title).build();
    }
}
