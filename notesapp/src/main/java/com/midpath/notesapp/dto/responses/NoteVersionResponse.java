package com.midpath.notesapp.dto.responses;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteVersionResponse {
    private Long id;
    private String title;
    private String content;
    private boolean archived;
    private Set<String> tags;
    private LocalDateTime createdAt;
    private String reason;
}
