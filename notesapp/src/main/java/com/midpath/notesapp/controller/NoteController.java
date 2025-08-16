package com.midpath.notesapp.controller;

import com.midpath.notesapp.dto.requests.NoteRequest;
import com.midpath.notesapp.dto.responses.NoteResponse;
import com.midpath.notesapp.dto.responses.NoteVersionResponse;
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
        return ResponseEntity.ok(noteService.create(currentUser, request));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getNotes(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long tagId,
            @RequestParam(required = false) Boolean archived,
            @RequestParam(required = false) String content
    ) {
        return ResponseEntity.ok(noteService.getAll(currentUser, title, tagId, archived, content));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(noteService.getById(currentUser, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id,
            @Valid @RequestBody NoteRequest request
    ) {
        return ResponseEntity.ok(noteService.update(currentUser, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id
    ) {
        noteService.remove(currentUser, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<NoteResponse> archiveNote(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(noteService.archive(currentUser, id));
    }

    @PatchMapping("/{id}/unarchive")
    public ResponseEntity<NoteResponse> unarchiveNote(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(noteService.unarchive(currentUser, id));
    }

    @GetMapping("/{noteId}/versions")
    public List<NoteVersionResponse> versions(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long noteId) {
        return noteService.getVersions(currentUser, noteId);
    }

    @PostMapping("/{noteId}/versions/{versionId}/revert")
    public NoteResponse revert(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long noteId,
            @PathVariable Long versionId) {
        return noteService.revertToVersion(currentUser, noteId, versionId);
    }
}