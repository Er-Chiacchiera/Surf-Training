package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Instructor;
import it.uniroma3.siw.model.Lesson;
import it.uniroma3.siw.repository.LessonRepository;

@Component
public class LessonValidator implements Validator {
	@Autowired
	private LessonRepository lessonRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Lesson lesson = (Lesson)o;
		if (lesson.getTitle()!=null && lesson.getCourse()!=null && lessonRepository.existsByTitleAndCourse(lesson.getTitle(), lesson.getCourse())) {
			errors.reject("lesson.duplicate");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Instructor.class.equals(aClass);
	}

}
