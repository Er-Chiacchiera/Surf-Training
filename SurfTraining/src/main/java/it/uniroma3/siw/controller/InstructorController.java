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

import it.uniroma3.siw.controller.validator.InstructorValidator;
import it.uniroma3.siw.model.Instructor;
import it.uniroma3.siw.repository.InstructorRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/instructor")
public class InstructorController {
	@Autowired InstructorRepository instructorRepository;
	@Autowired InstructorValidator instructorValidator;
	public static final String INSTRUCTOR_DIR = "instructor/";

	/*Mostra la lista di tutti gli istruttori*/
	@GetMapping("/all")
	public String getInstructors(Model model) {
		model.addAttribute("instructors", this.instructorRepository.findAll());
		return  INSTRUCTOR_DIR + "instructorList";
	}
	
	/*Form per aggiungere un nuovo Istruttore*/
	@GetMapping("/add/new")
	public String formNewInstrucotr(Model model) {
		model.addAttribute("instructor", new Instructor());
		return INSTRUCTOR_DIR + "instructorAdd";
	}
	
	/*Verifico se il nuovo istruttore rispetta i criteri e lo aggiungo al database altirmenti torno alla form*/
	@PostMapping("/add")
	public String newInstructor(@Valid @ModelAttribute("instructor") Instructor instructor,BindingResult bindingResult , Model model) {
	this.instructorValidator.validate(instructor, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.instructorRepository.save(instructor); 
			model.addAttribute("instructor", instructor);
			return INSTRUCTOR_DIR + "instructorProfile";
		} 
		else {
			return INSTRUCTOR_DIR + "instructorAdd";
		}
	}
	
	@GetMapping("/{id}")
	public String getInstructor(@PathVariable("id") Long id, Model model) {
		model.addAttribute("instructor", this.instructorRepository.findById(id).get());
		return INSTRUCTOR_DIR + "instructorProfile";
	}
	
	@GetMapping("/edit/{id}")
	public String formEditInstructor(@PathVariable("id") Long id, Model model) {
		model.addAttribute("instructor", this.instructorRepository.findById(id).get());
		return INSTRUCTOR_DIR + "instructorEdit";
	}
	
    @PostMapping("/update/{id}")
    public String updateInstructor(@Valid @ModelAttribute("instructor") Instructor instructor, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Gestisci gli errori di validazione, se necessario
        	return INSTRUCTOR_DIR + "instructorEdit";
        }
        this.instructorRepository.save(instructor); 
		model.addAttribute("instructor", instructor);
		return INSTRUCTOR_DIR + "instructorProfile";
    }
}