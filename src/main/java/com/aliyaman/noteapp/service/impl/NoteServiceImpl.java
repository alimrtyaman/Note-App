package com.aliyaman.noteapp.service.impl;

import com.aliyaman.noteapp.dto.NoteCreateRequest;
import com.aliyaman.noteapp.dto.NoteDto;
import com.aliyaman.noteapp.entity.Note;
import com.aliyaman.noteapp.entity.User;
import com.aliyaman.noteapp.mapper.NoteMappaer;
import com.aliyaman.noteapp.repository.NoteRepository;
import com.aliyaman.noteapp.repository.UserRepository;
import com.aliyaman.noteapp.service.INoteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements INoteService {

    private final NoteRepository noteRepository;

    private final NoteMappaer noteMappaer;

    private final UserRepository userRepository;

    @Override
    public List<NoteDto> getAllNotes(Long userId) {
      return   noteRepository.findAllByUserId( userId)
                .stream()
                .map(noteMappaer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoteDto saveNote(NoteCreateRequest noteCreateRequest, User user) {
        Note note = new Note();
        note.setTitle(noteCreateRequest.getTitle());
        note.setContent(noteCreateRequest.getContent());
        note.setUser(user);
        Note noteSaved = noteRepository.save(note);
        return noteMappaer.toDto(noteSaved);
    }

    @Override
    public boolean deleteNote(long id, Long userId) {

          Optional<Note> note = noteRepository.findByIdAndUserId(id , userId);
          if (note.isPresent()){
              noteRepository.delete(note.get());
          }else {

          }
        return true;
    }

    public NoteDto updateNote(Long id , Long userId , NoteCreateRequest noteCreateRequest){
      Optional<Note> optional =  noteRepository.findByIdAndUserId(id , userId);
      if (optional.isEmpty()){
          throw new RuntimeException("NOTE NOT FOUND");
      }
      Note note = optional.get();
      note.setTitle(noteCreateRequest.getTitle());
      note.setContent(noteCreateRequest.getContent());
      noteRepository.save(note);
      return noteMappaer.toDto(note);
    }

}
