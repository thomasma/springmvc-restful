package com.tryout.rest.notes.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tryout.rest.notes.service.dto.Note;

@Service
public class NoteServiceImpl implements NoteService {
	protected final static Logger LOGGER = LoggerFactory
			.getLogger(NoteServiceImpl.class);

	// THIS IS ONLY FOR TEST. NEVER DO THIS :) (EXCEPT IN THIS DEMO)
	// IN REAL CODE YOU WILL HAVE A DAO MANAGE THIS IN A DATABASE
	private Map<Long, Note> notes = new HashMap<Long, Note>();

	private Random random = new Random();
	// end : never do this

	@PostConstruct
	public void init() {
		Note note = new Note(random.nextLong(), "Default message.");
		notes.put(note.getId(), note);
	}

	//
	// /***/
	// @Override
	// public List<Note> getBusinessException() {
	// throw new PublicRelayException("testService.simple.error");
	// }
	//
	// /***/
	// @Override
	// public List<Note> returnMultipleValidationMessages() {
	// // assume u run your business logic here and it produced errors which u
	// // want to return to user
	// // PLACE HOLDER: call business logic HERE
	//
	// // ok lets return the error messages
	// List<ErrorBean> errors = new ArrayList<ErrorBean>();
	// errors.add(new ErrorBean("testService.invalid.ssn"));
	// errors.add(new ErrorBean("testService.invalid.cardnumber"));
	// errors.add(new ErrorBean("testService.invalid.firstname"));
	// throw new PublicRelayException(errors);
	// }

	@PreAuthorize("hasRole('customer')")
	public List<Note> getAllNotes() {
		List<Note> notelist = new ArrayList<Note>();
		notelist.addAll(notes.values());
		return notelist;
	}

	@PreAuthorize("hasRole('customer')")
	public Note getNoteById(Long id) {
		return notes.get(id);
	}

	@PreAuthorize("hasRole('customer')")
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNote(Note note) {
		note.setId(random.nextLong());
		notes.put(note.getId(), note);
	}

	@PreAuthorize("hasRole('admin')")
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Note note) {
		notes.remove(note.getId());
	}
}
