package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Course;
import it.uniroma3.siw.model.Lesson;

public interface CourseRepository extends CrudRepository<Course, Long> {
	
	public boolean existsByTitleAndTypology(String title, String typology);
	
	public List<Course> findBytitleContaining(String title);

	public List<Course> findByTypologyContaining(String typology);
	
	public Course findByLessons(Lesson lesson);

}
