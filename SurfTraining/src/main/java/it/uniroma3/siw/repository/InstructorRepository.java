package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Instructor;

public interface InstructorRepository extends CrudRepository<Instructor, Long> {
	
	public boolean existsByNameAndSurname(String Name, String surname);
	
	public List<Instructor> findBySurname(String surname);
	
	public List<Instructor> findByName(String name);

	public List<Instructor> findBySpeciality(String speciality);

}
