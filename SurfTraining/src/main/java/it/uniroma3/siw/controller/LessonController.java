package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.controller.validator.LessonValidator;
import it.uniroma3.siw.model.Lesson;
import it.uniroma3.siw.service.CourseService;
import it.uniroma3.siw.service.InstructorService;
import it.uniroma3.siw.service.LessonService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/lesson")
public class LessonController {
	@Autowired
	LessonValidator lessonValidator;
	@Autowired
	LessonService lessonService;
	@Autowired
	InstructorService instructorService;
	@Autowired
	CourseService courseService;

	public static final String LESSON_DIR = "lesson/";
	public static final String COURSE_DIR = "course/";

	/* Mostra la lista di tutti gli istruttori */
	@GetMapping("/all")
	public String getLessons(Model model) {
		model.addAttribute("lessons", this.lessonService.GetAllLessons());
		return LESSON_DIR + "lessonList";
	}

	/* Form per aggiungere un nuovo corso */
	@GetMapping("/add/new/{idCourse}")
	public String formNewLesson(@PathVariable("idCourse") Long idCourse, Model model) {
		model.addAttribute("lesson", new Lesson());
		model.addAttribute("idCourse", idCourse);
		return LESSON_DIR + "lessonAdd";
	}

	/* Se non ci sono erroi salvo la lezione altirmenti torno alla form */
	@PostMapping("/add/{idCourse}")
	public String newLesson(@Valid @ModelAttribute("lesson") Lesson lesson, BindingResult bindingResult,
			@PathVariable("idCourse") Long idCourse, Model model) {
		this.lessonValidator.validate(lesson, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.courseService.addNewLesson(idCourse, lesson);
			model.addAttribute("lesson", lesson);
			return "redirect:/course/" + idCourse;
		} else {
			return LESSON_DIR + "lessonAdd";
		}
	}

	@GetMapping("/edit/{id}")
	public String formEditLesson(@PathVariable("id") Long id, Model model) {
		model.addAttribute("lesson", this.lessonService.GetLessonById(id));
		return LESSON_DIR + "lessonEdit";
	}

	@PostMapping("/update/{id}")
	public String updateLesson(@Valid @ModelAttribute("lesson") Lesson lesson, @PathVariable("id") Long id,
			BindingResult bindingResult, Model model) {
		this.lessonValidator.validate(lesson, bindingResult);
		if (bindingResult.hasErrors()) {
			return LESSON_DIR + "lessonEdit";
		}
		this.lessonService.updateLesson(lesson);
		Long courseId = this.courseService.findCourseByLesson(lesson).getId();

		return "redirect:/course/" + courseId;
	}

	@GetMapping("/delete/{idCourse}/{idLesson}")
	public String deleteLesson(@PathVariable("idCourse") Long idCourse, @PathVariable("idLesson") Long idLesson,
			Model model) {
		this.lessonService.deleteById(idCourse, idLesson);
		return "redirect:/course/{idCourse}";
	}

}