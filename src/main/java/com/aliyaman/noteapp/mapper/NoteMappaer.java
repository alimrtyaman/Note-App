package com.aliyaman.noteapp.mapper;

import com.aliyaman.noteapp.dto.NoteDto;

import com.aliyaman.noteapp.entity.Note;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NoteMappaer {

    Note toEntity(NoteDto noteDto);

    NoteDto toDto(Note note);
}
