package com.midpath.notesapp.controller;

import com.midpath.notesapp.dto.requests.TagRequest;
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
    public ResponseEntity<TagResponse> createTag(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody TagRequest request) {
        return ResponseEntity.ok(tagService.create(currentUser, request));
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(tagService.getAll(currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id) {
        tagService.remove(currentUser, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateNote(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id,
            @Valid @RequestBody TagRequest request
    ) {
        return ResponseEntity.ok(tagService.update(currentUser, id, request));
    }
}
