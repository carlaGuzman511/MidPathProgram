package com.midpath.notesapp.service;

import com.midpath.notesapp.dto.requests.TagRequest;
import com.midpath.notesapp.dto.responses.TagResponse;
import com.midpath.notesapp.entity.Tag;
import com.midpath.notesapp.entity.User;
import com.midpath.notesapp.interfaces.service.generics.*;
import com.midpath.notesapp.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService implements
        IReadAll<User, TagResponse>,
        ICreate<User, TagResponse, TagRequest>,
        IRemove<User, Long>,
        IUpdate<User, TagRequest, TagResponse, Long> {

    private final TagRepository tagRepository;

    @Override
    public TagResponse create(User currentUser, TagRequest request) {
        Tag tag = Tag.builder()
                .name(request.getName())
                .owner(currentUser)
                .build();
        Tag saved = tagRepository.save(tag);
        return mapToResponse(saved);
    }

    public List<TagResponse> getAll(User currentUser) {
        Specification<Tag> spec = Specification.allOf(ownerIs(currentUser));

        return tagRepository.findAll(spec)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(User user, Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        tagRepository.delete(tag);
    }

    @Override
    public TagResponse update(User user, Long id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));

        tag.setName(request.getName());

        Tag updated = tagRepository.save(tag);
        return mapToResponse(updated);
    }

    private Specification<Tag> ownerIs(User user) {
        return (root, query, cb) -> cb.equal(root.get("owner"), user);
    }

    private TagResponse mapToResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
