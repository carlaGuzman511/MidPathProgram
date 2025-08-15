package com.midpath.notesapp.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class NoteRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Set<Long> tagIds;
}
