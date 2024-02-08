package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Course;
import it.uniroma3.siw.model.Lesson;
import it.uniroma3.siw.repository.CourseRepository;
import it.uniroma3.siw.repository.LessonRepository;
import jakarta.transaction.Transactional;



@Service
public class LessonService {
	@Autowired LessonRepository lessonRepository;
	@Autowired CourseRepository courseRepository;
	@Autowired CourseService courseService;

	private static final char CHAR_TARGET = '\n'; // Carattere da sostituire
	private static final String STRING_REPLACMENT = "<br>";

	@Transactional
	public void addNewLesson(Lesson lesson) {
		lesson.setExercise(this.replaceCharWithString(lesson.getExercise()));
		this.lessonRepository.save(lesson);
	}

	public Iterable<Lesson> GetAllLessons() {
		return this.lessonRepository.findAll();
	}

	public Lesson GetLessonById(Long id) {
		return this.lessonRepository.findById(id).get();
	}

	public void updateLesson(Lesson lesson) {
		Lesson old=this.lessonRepository.findById(lesson.getId()).get();
		old.setTitle(lesson.getTitle());
		old.setDescription(lesson.getDescription());
		old.setExercise(this.replaceCharWithString(lesson.getExercise()));
		this.lessonRepository.save(old);
	}


	public void deleteById(Long idCourse, Long idLesson) {
		Course course= this.courseRepository.findById(idCourse).get();
		Lesson lesson=this.lessonRepository.findById(idLesson).get();
		course.getLessons().remove(lesson);
		this.courseRepository.save(course);
		this.lessonRepository.delete(lesson);
		
	}

	public String replaceCharWithString(String input) {
        // Utilizza il metodo replace di String per sostituire il carattere con la stringa di sostituzione
		input=input.replace(Character.toString(CHAR_TARGET), STRING_REPLACMENT); // sostituisci tutte le occorrenze
		return input;
    }
	
	public String restoreReplacement(String input) {
	    // Utilizza il metodo replace di String per invertire la sostituzione
	    input = input.replace(STRING_REPLACMENT, Character.toString(CHAR_TARGET));
	    return input;
	}

	public void setCourseInLesson(Lesson lesson, Long idCourse) {
		lesson.setCourse(this.courseService.GetCourseById(idCourse));
	}
}