package com.midpath.notesapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "note_versions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Lob
    private String content;

    private boolean archived;

    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "note_version_tags", joinColumns = @JoinColumn(name = "version_id"))
    @Column(name = "tag_name")
    private Set<String> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    private String reason;
}