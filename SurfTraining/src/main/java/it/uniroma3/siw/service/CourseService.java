package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Course;
import it.uniroma3.siw.model.Lesson;
import it.uniroma3.siw.repository.CourseRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class CourseService {
	@Autowired CourseRepository courseRepository;

	@Transactional
	public void addNewCourse(Course course) {
		this.courseRepository.save(course);
	}

	public Iterable<Course> GetAllCourse() {
		return this.courseRepository.findAll();
	}

	public Course GetCourseById(Long id) {
		return this.courseRepository.findById(id).get();
	}

	public void updateCourse(Course course) {
		Course old=this.courseRepository.findById(course.getId()).get();
		old.setTitle(course.getTitle());
		old.setDescription(course.getDescription());
		old.setTypology(course.getTypology());
		this.courseRepository.save(old);	
	}

	public Iterable<Course> GetAllCourseByTypeAndAttribute(String type, String attribute) {
		if(type.equals("title"))
			return this.courseRepository.findBytitleContaining(attribute);
		else
			return this.courseRepository.findByTypologyContaining(attribute);
	}

	public void deleteById(Long id) {
		this.courseRepository.deleteById(id);
	}

	public boolean alreadyExist(Course course) {
		
		return this.courseRepository.existsByTitleAndTypology(course.getTitle(), course.getTypology());
		
	}

	public void addNewLesson(Long idCourse, @Valid Lesson lesson) {
		Course course = this.courseRepository.findById(idCourse).get();
		course.getLessons().add(lesson);
		lesson.setCourse(course);;;
		this.courseRepository.save(course);
		
	}

	public Course findCourseByLesson(@Valid Lesson lesson) {
		return this.courseRepository.findByLessons(lesson);
	}
}
