package com.tryout.rest.notes.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NotesList implements Serializable {
	@XmlElement
	private List<Note> notes = new ArrayList<Note>();

	public NotesList() {
	}

	public NotesList(final List<Note> notes) {
		this.notes.addAll(notes);
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(final List<Note> notes) {
		this.notes.clear();
		this.notes.addAll(notes);
	}
}
