package com.aliyaman.noteapp.repository;

import com.aliyaman.noteapp.dto.NoteCreateRequest;
import com.aliyaman.noteapp.entity.Note;
import com.aliyaman.noteapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUserId(Long userId);

    Optional<Note> findByIdAndUserId(Long id, Long userId);
}
