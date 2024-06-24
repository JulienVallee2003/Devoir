package controleur;

import java.time.LocalDate;

public class MembreEquipage extends Personne {
	private int idEquipage;
	private String role;
	private LocalDate dateEmbauche;
	 private int idVol;

	public MembreEquipage(int idEquipage,String nom, String prenom, LocalDate dateNaissance, String adresse,
			String email, String telephone, String role, LocalDate dateEmbauche, int idVol ) {
		super(nom, prenom, dateNaissance, adresse, email, telephone);
		this.idEquipage = idEquipage;
		this.role = role;
		this.dateEmbauche = dateEmbauche;
		this.setIdVol(idVol);
		
		
	}


	public int getIdEquipage() {
		return idEquipage;
	}

	public void setIdEquipage(int idEquipage) {
		this.idEquipage = idEquipage;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDate getDateEmbauche() {
		return dateEmbauche;
	}

	public void setDateEmbauche(LocalDate dateEmbauche) {
		this.dateEmbauche = dateEmbauche;
	}

	public int getIdVol() {
		return idVol;
	}

	public void setIdVol(int idVol) {
		this.idVol = idVol;
	}
	
	
}
