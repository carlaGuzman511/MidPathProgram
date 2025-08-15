package com.midpath.notesapp.service;

import com.midpath.notesapp.dto.requests.TagRequest;
import com.midpath.notesapp.dto.responses.TagResponse;
import com.midpath.notesapp.entity.Tag;
import com.midpath.notesapp.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public TagResponse createTag(TagRequest request) {
        Tag tag = Tag.builder()
                .name(request.getName())
                .build();
        Tag saved = tagRepository.save(tag);
        return mapToResponse(saved);
    }

    public List<TagResponse> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        tagRepository.delete(tag);
    }

    private TagResponse mapToResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
