package com.midpath.notesapp.repository;

import com.midpath.notesapp.entity.Note;
import com.midpath.notesapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
    Optional<Note> findByIdAndOwner(Long id, User owner);
}