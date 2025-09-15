package com.midpath.notesapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "notes")
@Getter
@Setter
@Data @Builder @AllArgsConstructor @NoArgsConstructor
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition="TEXT")
    private String content;

    private boolean archived = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "note_tags",
            joinColumns = @JoinColumn(name="note_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id")
    )

    private Set<Tag> tags = new HashSet<>();
}
