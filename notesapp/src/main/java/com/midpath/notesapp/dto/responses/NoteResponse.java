package com.midpath.notesapp.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class NoteResponse {
    private Long id;
    private String title;
    private String content;
    private boolean archived;
    private Set<String> tags;
}
