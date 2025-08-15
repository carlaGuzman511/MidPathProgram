package com.midpath.notesapp.controller;

import com.midpath.notesapp.dto.requests.NoteRequest;
import com.midpath.notesapp.dto.responses.NoteResponse;
import com.midpath.notesapp.entity.User;
import com.midpath.notesapp.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody NoteRequest request
    ) {
        return ResponseEntity.ok(noteService.createNote(currentUser, request));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getNotes(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long tagId,
            @RequestParam(required = false) Boolean archived
    ) {
        return ResponseEntity.ok(noteService.getNotes(currentUser, title, tagId, archived));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(noteService.getNoteById(currentUser, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id,
            @Valid @RequestBody NoteRequest request
    ) {
        return ResponseEntity.ok(noteService.updateNote(currentUser, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id
    ) {
        noteService.deleteNote(currentUser, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<NoteResponse> archiveNote(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(noteService.archiveNote(currentUser, id));
    }

    @PatchMapping("/{id}/unarchive")
    public ResponseEntity<NoteResponse> unarchiveNote(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(noteService.unarchiveNote(currentUser, id));
    }
}
