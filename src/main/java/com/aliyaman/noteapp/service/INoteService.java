package com.aliyaman.noteapp.service;

import com.aliyaman.noteapp.dto.NoteCreateRequest;
import com.aliyaman.noteapp.dto.NoteDto;
import com.aliyaman.noteapp.entity.User;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface INoteService {

    public List<NoteDto> getAllNotes(Long userId);

    public NoteDto saveNote(NoteCreateRequest noteCreateRequest , User user);

    public boolean deleteNote(long id , Long userId);

    public NoteDto updateNote(Long id , Long userId , NoteCreateRequest noteCreateRequest);
}
