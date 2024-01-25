package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Course;
import it.uniroma3.siw.model.Instructor;
import it.uniroma3.siw.repository.InstructorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class InstructorService {
	@Autowired InstructorRepository instructorRepository;

	@Transactional
	public void addNewInstructor(Instructor instructor) {
		this.instructorRepository.save(instructor);
	}

	public Iterable<Instructor> GetAllInstructor() {
		return this.instructorRepository.findAll();
	}

	public Instructor GetInstructordById(Long id) {
		return this.instructorRepository.findById(id).get();
	}

	public void updateInstructor(Instructor instructor) {
		Instructor old=this.instructorRepository.findById(instructor.getId()).get();
		old.setName(instructor.getName());
		old.setSurname(instructor.getSurname());
		old.setSpeciality(instructor.getSpeciality());
		old.setDateOfBirth(instructor.getDateOfBirth());
		old.setDescrizione(instructor.getDescrizione());
		old.setInstagramUrl(instructor.getInstagramUrl());
		old.setEmail(instructor.getEmail());
		
		this.instructorRepository.save(old);	
	}

	public Iterable<Instructor> GetAllInstructorByTypeAndAttribute(String type, String attribute) {
		if(type.equals("name"))
			return this.instructorRepository.findByName(attribute);
		else if(type.equals("surname"))
			return this.instructorRepository.findBySurname(attribute);
		else
			return this.instructorRepository.findBySpeciality(attribute);
	}

	public void deleteById(Long id) {
		this.instructorRepository.deleteById(id);
	}

	public void addNewCourse(Long idInstructor, @Valid Course course) {
		// TODO Auto-generated method stub
				Instructor instructor = this.instructorRepository.findById(idInstructor).get();
				instructor.getCourses().add(course);
				course.setInstructor(instructor);;
				this.instructorRepository.save(instructor);
	}
}



/*	@Transactional
	public void addDirector(Long idMovie, Long idArtist) {
		Movie movie = this.movieRepository.findById(idMovie).get();
		Artist artist = this.artistRepository.findById(idArtist).get();
		movie.setDirector(artist);
		artist.setListDirector(movie);
		this.movieRepository.save(movie);
		this.artistRepository.save(artist);
	}*/
