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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.LessonValidator;
import it.uniroma3.siw.model.Course;
import it.uniroma3.siw.model.Lesson;
import it.uniroma3.siw.service.LessonService;
import it.uniroma3.siw.service.CourseService;
import it.uniroma3.siw.service.InstructorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/lesson")
public class LessonController {
	@Autowired LessonValidator lessonValidator;
	@Autowired LessonService lessonService;
	@Autowired InstructorService instructorService;
	@Autowired CourseService courseService;

	public static final String COURSE_DIR = "lesson/";

	/*Mostra la lista di tutti gli istruttori*/
	@GetMapping("/all")
	public String getLessons(Model model) {
		model.addAttribute("lessons", this.lessonService.GetAllLessons());
		return  COURSE_DIR + "lessonList";
	}

	/*Form per aggiungere un nuovo corso*/
	@GetMapping("/add/new/{idCourse}")
	public String formNewLesson(@PathVariable("idCourse") Long idCourse ,Model model) {
		model.addAttribute("lesson", new Lesson());
		model.addAttribute("idCourse", idCourse);
		return COURSE_DIR + "lessonAdd";
	}

	/*Verifico se il nuovo istruttore rispetta i criteri e lo aggiungo al database altirmenti torno alla form*/
	@PostMapping("/add/{idCourse}")
	public String newLesson(@Valid @ModelAttribute("lesson") Lesson lesson,BindingResult bindingResult , @PathVariable("idCourse") Long idCourse, Model model) {
		this.lessonValidator.validate(lesson, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.courseService.addNewLesson(idCourse,lesson);
			model.addAttribute("lesson", lesson);
			return "redirect:/course/" + idCourse;
		}  
		else {
			return COURSE_DIR + "lessonAdd";
		}
	}

	@GetMapping("/{id}")
	public String getLesson(@PathVariable("id") Long id, Model model) {
		model.addAttribute("lesson", this.lessonService.GetLessonById(id));
		return COURSE_DIR + "lessonProfile";
	}

	@GetMapping("/edit/{id}")
	public String formEditLesson(@PathVariable("id") Long id, Model model) {
		model.addAttribute("lesson", this.lessonService.GetLessonById(id));
		return COURSE_DIR + "lessonEdit";
	}

	@PostMapping("/update/{id}")
	public String updateLesson(@Valid @ModelAttribute("lesson") Lesson lesson, BindingResult bindingResult, Model model) {
		this.lessonValidator.validate(lesson, bindingResult);
		if (bindingResult.hasErrors()) {
			return COURSE_DIR + "lessonEdit";
		}
		this.lessonService.updateLesson(lesson); 
		model.addAttribute("lesson", lesson);
		return "redirect:/lesson/" + lesson.getId();
	}

	@PostMapping("/search")
	public String searchLessons(@RequestParam(name = "type") String type,@RequestParam(name = "attribute", defaultValue = "") String attribute,
			Model model) {
		model.addAttribute("lessons",this.lessonService.GetAllLessonByTypeAndAttribute(type,attribute));
		return COURSE_DIR + "lessonList";
	}

	@GetMapping("/delete/{id}")
	public String deleteLesson(@PathVariable("id") Long id, Model model) {
		this.lessonService.deleteById(id);
		return "redirect:/lesson/all";
	}


}