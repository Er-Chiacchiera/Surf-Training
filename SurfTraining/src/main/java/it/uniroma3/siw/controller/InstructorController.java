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

import it.uniroma3.siw.controller.validator.InstructorValidator;
import it.uniroma3.siw.model.Instructor;
import it.uniroma3.siw.presentation.FileStorer;
import it.uniroma3.siw.service.InstructorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/instructor")
public class InstructorController {
	@Autowired InstructorValidator instructorValidator;
	@Autowired InstructorService instructorService;

	public static final String INSTRUCTOR_DIR = "instructor/";

	/*Mostra la lista di tutti gli istruttori*/
	@GetMapping("/all")
	public String getInstructors(Model model) {
		model.addAttribute("instructors", this.instructorService.GetAllInstructor());
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
	public String newInstructor(@Valid @ModelAttribute("instructor") Instructor instructor,BindingResult bindingResult, @RequestParam("file") MultipartFile file , Model model) {
		this.instructorValidator.validate(instructor, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.instructorService.addNewInstructor(instructor);

			if(!file.isEmpty()) {
				instructor.setPathImg(FileStorer.store(file,"instructor",instructor.getId()));
				this.instructorService.updateInstructor(instructor);
			}
			model.addAttribute("instructor", instructor);
			return INSTRUCTOR_DIR + "instructorProfile";
		} 
		else {
			return INSTRUCTOR_DIR + "instructorAdd";
		}
	}

	@GetMapping("/{id}")
	public String getInstructor(@PathVariable("id") Long id, Model model) {
		model.addAttribute("instructor", this.instructorService.GetInstructordById(id));
		return INSTRUCTOR_DIR + "instructorProfile";
	}

	@GetMapping("/edit/{id}")
	public String formEditInstructor(@PathVariable("id") Long id, Model model) {
		model.addAttribute("instructor", this.instructorService.GetInstructordById(id));
		return INSTRUCTOR_DIR + "instructorEdit";
	}


	/*Se i dati inseriti sono corretti, viene aggiornato il profilo dell'atleta*/
	@PostMapping("/update/{id}")
	public String updateInstructor( @Valid @ModelAttribute("instructor") Instructor instructor, @RequestParam("file") MultipartFile file  ,BindingResult bindingResult, Model model) {
		System.out.println("Sono entrato nel post\n\n");
		this.instructorValidator.validate( instructor, bindingResult);
		
		if (bindingResult.hasErrors()) {
			System.out.println("Qua ci sono\n\n");
			return INSTRUCTOR_DIR + "instructorEdit";
		}
		
		System.out.println("Invece sto qua\n\n");
		if(!file.isEmpty()) {
			instructor.setPathImg(FileStorer.store(file,"instructor",instructor.getId()));
		}
		this.instructorService.updateInstructor(instructor); 
		model.addAttribute("instructor", instructor);
		return "redirect:/instructor/" + instructor.getId();
	}

	@PostMapping("/search")
	public String searchInstructors(@RequestParam(name = "type") String type,@RequestParam(name = "attribute", defaultValue = "") String attribute,
			Model model) {
		model.addAttribute("instructors",this.instructorService.GetAllInstructorByTypeAndAttribute(type,attribute));
		return INSTRUCTOR_DIR + "instructorList";
	}

	@GetMapping("/delete/{id}")
	public String deleteInstructor(@PathVariable("id") Long id, Model model) {
		this.instructorService.deleteById(id);
		return "redirect:/instructor/all";
	}


}