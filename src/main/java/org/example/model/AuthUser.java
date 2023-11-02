package org.example.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser {
    private Long id;
    private String username;
    private String password;
    private String role;
    private Boolean isBlocked;
    private String pictureGeneratedName;
}
