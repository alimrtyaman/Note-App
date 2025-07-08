package com.aliyaman.noteapp.controller;

import com.aliyaman.noteapp.dto.NoteCreateRequest;
import com.aliyaman.noteapp.dto.NoteDto;
import com.aliyaman.noteapp.entity.CustomUserDetails;
import com.aliyaman.noteapp.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface INoteController {




    List<NoteDto> getAllNotes(Long userId);

    public NoteDto saveNote(NoteCreateRequest noteCreateRequest, User user);

    public boolean deleteNote(long id, long userId);

    public NoteDto updateNote(Long id , Long userId , NoteCreateRequest noteCreateRequest);
}
