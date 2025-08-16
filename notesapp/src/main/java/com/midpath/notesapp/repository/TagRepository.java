package com.midpath.notesapp.repository;

import com.midpath.notesapp.entity.Tag;
import com.midpath.notesapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {
    Optional<Tag> findByNameAndOwner(String tagName, User user);
}
