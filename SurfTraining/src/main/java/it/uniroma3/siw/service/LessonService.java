package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Lesson;
import it.uniroma3.siw.repository.LessonRepository;
import jakarta.transaction.Transactional;

@Service
public class LessonService {
	@Autowired LessonRepository lessonRepository;

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
		this.lessonRepository.save(lesson);	
	}

	/*public Iterable<Instructor> GetAllInstructorByTypeAndAttribute(String type, String attribute) {
		if(type.equals("name"))
			return this.instructorRepository.findByName(attribute);
		else if(type.equals("surname"))
			return this.instructorRepository.findBySurname(attribute);
		else
			return this.instructorRepository.findBySpeciality(attribute);
	}*/

	public void deleteById(Long id) {
		this.lessonRepository.deleteById(id);
	}
}