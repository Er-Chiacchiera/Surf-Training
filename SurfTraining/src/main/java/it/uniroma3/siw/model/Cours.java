package it.uniroma3.siw.model;



import java.util.LinkedList;
import java.util.List;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Cours {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@ManyToOne
	private Instructor instructor;
	
	List<String> lezioni;
	
	public Cours() {
		this.lezioni=new LinkedList<>();
	}

}
