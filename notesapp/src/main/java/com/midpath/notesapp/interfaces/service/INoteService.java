package com.midpath.notesapp.interfaces.service;

import com.midpath.notesapp.dto.responses.NoteResponse;
import com.midpath.notesapp.entity.User;

import java.util.List;

public interface INoteService {
    public NoteResponse archive(User currentUser, Long id);
    public NoteResponse unarchive(User currentUser, Long id);
    public List<NoteResponse> getAll(User currentUser, String title, Long tagId, Boolean archived, String content);
}