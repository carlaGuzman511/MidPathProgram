package com.midpath.notesapp.service;

import com.midpath.notesapp.dto.requests.NoteRequest;
import com.midpath.notesapp.dto.responses.NoteResponse;
import com.midpath.notesapp.entity.Note;
import com.midpath.notesapp.entity.Tag;
import com.midpath.notesapp.entity.User;
import com.midpath.notesapp.interfaces.service.INoteService;
import com.midpath.notesapp.interfaces.service.generics.*;
import com.midpath.notesapp.repository.NoteRepository;
import com.midpath.notesapp.repository.TagRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService implements INoteService,
        IRead<User, NoteResponse, Long>,
        ICreate<User, NoteResponse, NoteRequest>,
        IRemove<User, Long>,
        IUpdate<User, NoteRequest, NoteResponse, Long>
{
    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;

    @Override
    public NoteResponse create(User currentUser, NoteRequest request) {
        Set<Tag> tags = new HashSet<>();

        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            tags.addAll(tagRepository.findAllById(request.getTagIds()));
        }

        Note note = Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .archived(false)
                .owner(currentUser)
                .tags(tags)
                .build();

        Note saved = noteRepository.save(note);
        return mapToResponse(saved);
    }

    @Override
    public List<NoteResponse> getAll(User currentUser, String title, Long tagId, Boolean archived, String content) {
        Specification<Note> spec = Specification.allOf(ownerIs(currentUser));

        if (title != null && !title.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (content != null && !content.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("content")), "%" + content.toLowerCase() + "%"));
        }

        if (archived != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("archived"), archived));
        }

        if (tagId != null) {
            spec = spec.and((root, query, cb) -> cb.isMember(tagRepository.getReferenceById(tagId), root.get("tags")));
        }

        return noteRepository.findAll(spec).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NoteResponse getById(User currentUser, Long id) {
        Note note = noteRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        return mapToResponse(note);
    }

    @Override
    public NoteResponse update(User currentUser, Long id, NoteRequest request) {
        Note note = noteRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        if (request.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
            note.setTags(tags);
        }

        Note updated = noteRepository.save(note);
        return mapToResponse(updated);
    }

    @Override
    public void remove(User currentUser, Long id) {
        Note note = noteRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        noteRepository.delete(note);
    }

    @Override
    public NoteResponse archive(User currentUser, Long id) {
        Note note = noteRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        note.setArchived(true);
        return mapToResponse(noteRepository.save(note));
    }

    @Override
    public NoteResponse unarchive(User currentUser, Long id) {
        Note note = noteRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        note.setArchived(false);
        return mapToResponse(noteRepository.save(note));
    }

    private Specification<Note> ownerIs(User user) {
        return (root, query, cb) -> cb.equal(root.get("owner"), user);
    }

    private NoteResponse mapToResponse(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .archived(note.isArchived())
                .tags(note.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
                .build();
    }
}
