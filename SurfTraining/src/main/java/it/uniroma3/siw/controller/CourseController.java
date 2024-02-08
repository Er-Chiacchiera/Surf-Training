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
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.CourseValidator;
import it.uniroma3.siw.model.Course;
import it.uniroma3.siw.presentation.FileStorer;
import it.uniroma3.siw.service.CourseService;
import it.uniroma3.siw.service.InstructorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/course")
public class CourseController {
	@Autowired CourseValidator courseValidator;
	@Autowired CourseService courseService;
	@Autowired InstructorService instructorService;

	public static final String COURSE_DIR = "course/";

	/*Mostra la lista di tutti gli istruttori*/
	@GetMapping("/all")
	public String getCourses(Model model) {
		model.addAttribute("courses", this.courseService.GetAllCourse());
		return  COURSE_DIR + "courseList";
	}

	/*Form per aggiungere un nuovo corso*/
	@GetMapping("/add/new/{idInstructor}")
	public String formNewInstrucotr(@PathVariable("idInstructor") Long idInstructor ,Model model) {
		model.addAttribute("course", new Course());
		model.addAttribute("idInstructor", idInstructor);
		return COURSE_DIR + "courseAdd";
	}

	/*Verifico se il nuovo istruttore rispetta i criteri e lo aggiungo al database altirmenti torno alla form*/
	@PostMapping("/add/{idInstructor}")
	public String newCourse(@Valid @ModelAttribute("course") Course course,BindingResult bindingResult , @PathVariable("idInstructor") Long idInstructor, @RequestParam("file")MultipartFile file, Model model) {
		System.out.println("Qui ci sono\n\n\n\\n\n");
		this.courseValidator.validate(course, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.instructorService.addNewCourse(idInstructor,course);
			
			if(!file.isEmpty()) {
				course.setPathImg(FileStorer.store(file,"course",course.getId()));
				this.courseService.updateCourse(course);
			}
			
			model.addAttribute("course", course);
			return "redirect:/course/all";
		}  
		else {
			return COURSE_DIR + "courseAdd";
		}
	}

	@GetMapping("/{id}")
	public String getCourse(@PathVariable("id") Long id, Model model) {
		model.addAttribute("course", this.courseService.GetCourseById(id));
		return COURSE_DIR + "courseProfile";
	}

	@GetMapping("/edit/{id}")
	public String formEditCourse(@PathVariable("id") Long id, Model model) {
		model.addAttribute("course", this.courseService.GetCourseById(id));
		return COURSE_DIR + "courseEdit";
	}

	@PostMapping("/update/{id}")
	public String updateCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult, @RequestParam("file")MultipartFile file, Model model) {
		if (bindingResult.hasErrors()) {
			return COURSE_DIR + "courseEdit";
		}
		if(!file.isEmpty()) {
			course.setPathImg(FileStorer.store(file,"course",course.getId()));
		}
		
		this.courseService.updateCourse(course); 
		model.addAttribute("course", course);
		return "redirect:/course/" + course.getId();
	}

	@PostMapping("/search")
	public String searchCourses(@RequestParam(name = "type") String type,@RequestParam(name = "attribute", defaultValue = "") String attribute,
			Model model) {
		model.addAttribute("courses",this.courseService.GetAllCourseByTypeAndAttribute(type,attribute));
		return COURSE_DIR + "courseList";
	}

	@GetMapping("/delete/{id}")
	public String deleteCourse(@PathVariable("id") Long id, Model model) {
		this.courseService.deleteById(id);
		return "redirect:/course/all";
	}


}