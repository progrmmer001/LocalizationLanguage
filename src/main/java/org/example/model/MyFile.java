package org.example.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyFile {
    private Long id;
    private String originalFileName;
    private String codeFileName;
    private String mediaType;
    private String title;
    private String overview;
    private Long sizeFile;
}
