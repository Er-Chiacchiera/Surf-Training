package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;



import jakarta.persistence.Column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;





@Entity
public class Instructor {
/*Dotato di Nome, Cognome, data di nascita, Specialità, email, profilo instagramUrl*/
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Column(nullable = false)
	@NotBlank
	private String name;
	
	@Column(nullable = false)
	@NotBlank
	private String surname;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Past
	private LocalDate dateOfBirth;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String speciality;
	
	private String instagramUrl;
	
	@OneToMany(mappedBy = "instructor")
	private List<Cours> corso;
	
	public Instructor() {
		this.corso=new LinkedList<>();
	}
	
	
/***************** Get e Set metod *****************/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getInstagramUrl() {
		return instagramUrl;
	}

	public void setInstagramUrl(String instagramUrl) {
		this.instagramUrl = instagramUrl;
	}

	public List<Cours> getcorso() {
		return corso;
	}

	public void setcorso(List<Cours> corso) {
		this.corso = corso;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
/***************** hashCode e equals metod *****************/
	@Override
	public int hashCode() {
		return Objects.hash(dateOfBirth, name, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instructor other = (Instructor) obj;
		return Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(name, other.name)
				&& Objects.equals(surname, other.surname);
	}
}