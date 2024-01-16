package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Instructor;
import it.uniroma3.siw.repository.InstructorRepository;

@Component
public class InstructorValidator implements Validator {
	@Autowired
	private InstructorRepository instructorRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Instructor instrucotr = (Instructor)o;
		if (instrucotr.getName()!=null && instrucotr.getSurname()!=null && instructorRepository.existsByNameAndSurname(instrucotr.getName(), instrucotr.getSurname())) {
			errors.reject("instructor.duplicate");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Instructor.class.equals(aClass);
	}

}
