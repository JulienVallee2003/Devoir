package controleur;

import java.time.LocalDate;

public class Passager extends Personne{
private int idPassager;
private String numPasseport;

	public Passager(int idPassager, String numPasseport) {
		super();
		this.idPassager = idPassager;
		this.numPasseport = numPasseport;
	}
	
	// Supposons que vous ayez un constructeur dans la classe Passager prenant en charge uniquement le numéro de passeport
	public Passager(String numeroPasseport) {
	   // super(); // Appel du constructeur de la classe Personne
	    this.numPasseport = numeroPasseport;
	}
	
	public Passager(int id, String nom, String prenom,LocalDate dateNaissance, String adresse, String email, String telephone, String numPasseport) {
        super(id, nom, prenom, dateNaissance, adresse, email, telephone);
        this.idPassager = id;
        this.numPasseport = numPasseport;
    }
	
	



	public int getIdPassager() {
		return idPassager;
	}

	public void setIdPassager(int idPassager) {
		this.idPassager = idPassager;
	}

	public String getNumPasseport() {
		return numPasseport;
	}

	public void setNumPasseport(String numPasseport) {
		this.numPasseport = numPasseport;
	}
	
}
