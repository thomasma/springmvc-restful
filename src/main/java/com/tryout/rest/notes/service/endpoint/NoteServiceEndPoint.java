package com.tryout.rest.notes.service.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tryout.rest.exception.ApplicationException;
import com.tryout.rest.exception.ErrorBean;
import com.tryout.rest.exception.ErrorsWrapper;
import com.tryout.rest.notes.service.NoteService;
import com.tryout.rest.notes.service.NoteValidator;
import com.tryout.rest.notes.service.dto.Note;
import com.tryout.rest.notes.service.dto.NotesList;

@Controller
@RequestMapping(value = "/note")
public class NoteServiceEndPoint {
	protected final static Logger LOGGER = LoggerFactory
			.getLogger(NoteServiceEndPoint.class);

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/notes", method = RequestMethod.GET)
	@ResponseBody
	public NotesList getAllNotes() {
		return new NotesList(noteService.getAllNotes());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Note getNoteById(@PathVariable final Long id) {
		return noteService.getNoteById(id);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Note addNewPerson(@RequestBody Note note, BindingResult result) {
		NoteValidator noteValidator = new NoteValidator();
		noteValidator.validate(note, result);

		if (result.hasErrors()) {
			throw new ApplicationException(result);
		} else {
			noteService.addNote(note);
			LOGGER.info("Added new note with id {}", note.getId());
		}
		return note;
	}

	@RequestMapping(value = "/error1", method = RequestMethod.GET)
	public Note simulateSimpleError() {
		// some underlying logic throws an error
		throw new ApplicationException("error.msg1");
		//
	}

	@RequestMapping(value = "/error2", method = RequestMethod.GET)
	public Note simulateMultipleErrors() {
		// some underlying logic throws an error
		ErrorsWrapper errors = new ErrorsWrapper();
		errors.addError(new ErrorBean("invalid.card.number"));
		errors.addError(new ErrorBean("invalid.card.exp.date="));
		throw new ApplicationException(errors.getErrors());
		//
	}
}
