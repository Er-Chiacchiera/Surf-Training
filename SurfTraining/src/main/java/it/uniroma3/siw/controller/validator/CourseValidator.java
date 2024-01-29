package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Course;
import it.uniroma3.siw.service.CourseService;

@Component
public class CourseValidator implements Validator {
	
	@Autowired
	private CourseService courseService;

	@Override
	public void validate(Object o, Errors errors) {
		Course course = (Course)o;
		if (this.courseService.alreadyExist(course)) {
			errors.reject("course.duplicate");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Course.class.equals(aClass);
	}

}
