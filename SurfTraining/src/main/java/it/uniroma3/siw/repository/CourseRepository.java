package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {
	
	public boolean existsByTitleAndTypology(String title, String typology);
	
	public List<Course> findBytitle(String title);

	public List<Course> findByTypology(String typology);

}
