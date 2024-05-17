package com.example.todo.todoApi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoModifyRequestDTO {
    @NotBlank
    private String id;

    private boolean done;
}
