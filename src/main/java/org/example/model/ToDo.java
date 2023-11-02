package org.example.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ToDo {
    private Long id;
    private String title;
    private String priority;
    private LocalDateTime createdAt;
}
