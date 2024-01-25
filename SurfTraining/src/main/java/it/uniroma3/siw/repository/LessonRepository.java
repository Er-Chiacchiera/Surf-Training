package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Course;
import it.uniroma3.siw.model.Lesson;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
	
	public boolean existsByTitleAndCourse(String title, Course course);
	
	public List<Lesson> findByTitle(String lesson);

	public List<Lesson> findByCourse(Course course);

}
