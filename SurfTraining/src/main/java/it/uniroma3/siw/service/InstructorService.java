package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Instructor;
import it.uniroma3.siw.repository.InstructorRepository;
import jakarta.transaction.Transactional;

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
		this.instructorRepository.save(instructor);	
	}

	public Iterable<Instructor> GetAllInstructorByTypeAndAttribute(String type, String attribute) {
		if(type.equals("name"))
			return this.instructorRepository.findByName(attribute);
		else if(type.equals("surname"))
			return this.instructorRepository.findBySurname(attribute);
		else
			return this.instructorRepository.findBySpeciality(attribute);
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
