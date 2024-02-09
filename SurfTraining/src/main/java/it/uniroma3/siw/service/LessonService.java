package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Lesson;
import it.uniroma3.siw.presentation.FileStorer;
import it.uniroma3.siw.repository.LessonRepository;
import jakarta.transaction.Transactional;



@Service
public class LessonService {
	@Autowired LessonRepository lessonRepository;
	@Autowired CourseService courseService;


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

	@Transactional
	public void updateLesson(Lesson lesson) {
		Lesson old=this.lessonRepository.findById(lesson.getId()).get();
		old.setTitle(lesson.getTitle());
		old.setDescription(lesson.getDescription());
		if(lesson.getPathVideo()!=null)
			old.setPathVideo(lesson.getPathVideo());
		this.lessonRepository.save(old);
	}

	@Transactional
	public void deleteById(Long idCourse, Long idLesson) {
		Lesson lesson=this.lessonRepository.findById(idLesson).get();
		this.courseService.removeLesson(lesson);
		FileStorer.removeImg("lesson", lesson.getPathVideo());
		this.lessonRepository.delete(lesson);
	}


	public void setCourseInLesson(Lesson lesson, Long idCourse) {
		lesson.setCourse(this.courseService.GetCourseById(idCourse));
	}
}