package com.aliyaman.noteapp.controller.impl;

import com.aliyaman.noteapp.controller.INoteController;
import com.aliyaman.noteapp.dto.NoteCreateRequest;
import com.aliyaman.noteapp.dto.NoteDto;
import com.aliyaman.noteapp.entity.CustomUserDetails;
import com.aliyaman.noteapp.entity.User;
import com.aliyaman.noteapp.service.INoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteControllerImpl implements INoteController {

    private final INoteService iNoteService;



    @GetMapping
    @Override
    public List<NoteDto> getAllNotes(@AuthenticationPrincipal(expression = "user.id") Long userId) {
        return iNoteService.getAllNotes(userId);
    }




    @PostMapping
    @Override
    public NoteDto saveNote(@RequestBody NoteCreateRequest noteCreateRequest,
                            @AuthenticationPrincipal(expression = "user") User user) {
        return iNoteService.saveNote(noteCreateRequest , user);
    }

    @DeleteMapping("/{id}")
    @Override
    public boolean deleteNote(@PathVariable(name = "id") long id,
                           @AuthenticationPrincipal(expression = "user.id" )long userId){

          return   iNoteService.deleteNote(id , userId);

    }

    @PutMapping("/{id}")
    @Override
    public NoteDto updateNote(@PathVariable Long id,
                              @AuthenticationPrincipal(expression = "user.id") Long userId
            ,@RequestBody NoteCreateRequest noteCreateRequest) {
        return iNoteService.updateNote(id , userId ,noteCreateRequest);
    }


}
