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

	@Transactional
	public void addNewLesson(Lesson lesson) {
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
		System.out.println(old.getId());
		old.setTitle(lesson.getTitle());
		old.setDescription(lesson.getDescription());
		old.setExercise(lesson.getExercise());
		this.lessonRepository.save(old);
	}

	/*public Iterable<Instructor> GetAllInstructorByTypeAndAttribute(String type, String attribute) {
		if(type.equals("name"))
			return this.instructorRepository.findByName(attribute);
		else if(type.equals("surname"))
			return this.instructorRepository.findBySurname(attribute);
		else
			return this.instructorRepository.findBySpeciality(attribute);
	}*/

	public void deleteById(Long idCourse, Long idLesson) {
		Course course= this.courseRepository.findById(idCourse).get();
		Lesson lesson=this.lessonRepository.findById(idLesson).get();
		course.getLessons().remove(lesson);
		this.courseRepository.save(course);
		this.lessonRepository.delete(lesson);
		
	}
}