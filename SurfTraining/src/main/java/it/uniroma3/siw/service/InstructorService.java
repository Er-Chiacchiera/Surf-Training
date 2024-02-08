package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Course;
import it.uniroma3.siw.model.Instructor;
import it.uniroma3.siw.presentation.FileStorer;
import it.uniroma3.siw.repository.InstructorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class InstructorService {
	@Autowired InstructorRepository instructorRepository;
	@Autowired CourseService courseService;

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
		if(instructor.getPathImg()!=null)
			old.setPathImg(instructor.getPathImg());
		
		this.instructorRepository.save(old);	
	}

	public Iterable<Instructor> GetAllInstructorByTypeAndAttribute(String type, String attribute) {
		if(type.equals("name"))
			return this.instructorRepository.findByNameContains(attribute);
		else if(type.equals("surname"))
			return this.instructorRepository.findBySurnameContains(attribute);
		else
			return this.instructorRepository.findBySpecialityContains(attribute);
	}

	public void deleteById(Long id) {
		Instructor instructor=this.GetInstructordById(id);
		for(Course course :instructor.getCourses())
			this.courseService.deleteById(course.getId());;
		if(instructor.getPathImg()!=null)
			FileStorer.removeImg("instructor",instructor.getPathImg());
			
			this.instructorRepository.deleteById(id);
	}

	public void addNewCourse(Long idInstructor, @Valid Course course) {
		// TODO Auto-generated method stub
				Instructor instructor = this.instructorRepository.findById(idInstructor).get();
				instructor.getCourses().add(course);
				course.setInstructor(instructor);;
				this.instructorRepository.save(instructor);
	}
	
	@Transactional
	public void removeCourse(Course course, Instructor instructor) {
		instructor.getCourses().remove(course);
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
