package com.midpath.notesapp.repository;

import com.midpath.notesapp.entity.NoteVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteVersionRepository extends JpaRepository<NoteVersion, Long> {

    List<NoteVersion> findByNoteIdAndOwnerIdOrderByCreatedAtDesc(Long noteId, Long ownerId);

    Optional<NoteVersion> findByIdAndOwnerId(Long versionId, Long ownerId);
}