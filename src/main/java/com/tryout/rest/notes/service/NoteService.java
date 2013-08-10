package com.tryout.rest.notes.service;

import java.util.List;

import com.tryout.rest.notes.service.dto.Note;

public interface NoteService {

	public List<Note> getAllNotes();

	public Note getNoteById(Long id);

	public void addNote(Note note);

	public void delete(Note note);
}
