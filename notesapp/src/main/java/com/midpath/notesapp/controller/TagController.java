package com.midpath.notesapp.controller;

import com.midpath.notesapp.dto.requests.NoteRequest;
import com.midpath.notesapp.dto.requests.TagRequest;
import com.midpath.notesapp.dto.responses.NoteResponse;
import com.midpath.notesapp.dto.responses.TagResponse;
import com.midpath.notesapp.entity.User;
import com.midpath.notesapp.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponse> createTag(@Valid @RequestBody TagRequest request) {
        return ResponseEntity.ok(tagService.createTag(request));
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateNote(
            @PathVariable Long id,
            @Valid @RequestBody TagRequest request
    ) {
        return ResponseEntity.ok(tagService.updateTag(id, request));
    }
}
