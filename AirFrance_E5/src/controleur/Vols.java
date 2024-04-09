package controleur;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Vols{
	private int idVol;
	private String numVol;
	private int idAeroportDepart, idAeroportArrive;
	private LocalDate dateDepart, dateArrivee;
	private LocalTime heureDepart, heureArrivee;
	private int idAvion;
	
	public Vols(int idVol, String numVol, LocalDate dateDepart, LocalTime heureDepart, int idAeroportDepart,
	        LocalDate dateArrivee, LocalTime heureArrivee, int idAeroportArrive, int idAvion) {
	    super();
	    this.idVol = idVol;
	    this.numVol = numVol;
	    this.dateDepart = dateDepart;
	    this.heureDepart = heureDepart;
	    this.idAeroportDepart = idAeroportDepart;
	    this.dateArrivee = dateArrivee;
	    this.heureArrivee = heureArrivee;
	    this.idAeroportArrive = idAeroportArrive;
	    this.idAvion = idAvion;
	}

	public int getIdVol() {
		return idVol;
	}

	public void setIdVol(int idVol) {
		this.idVol = idVol;
	}

	public String getNumVol() {
		return numVol;
	}

	public void setNumVol(String numVol) {
		this.numVol = numVol;
	}

	public int getIdAeroportDepart() {
		return idAeroportDepart;
	}

	public void setIdAeroportDepart(int idAeroportDepart) {
		this.idAeroportDepart = idAeroportDepart;
	}

	public int getIdAeroportArrive() {
		return idAeroportArrive;
	}

	public void setIdAeroportArrive(int idAeroportArrive) {
		this.idAeroportArrive = idAeroportArrive;
	}

	public LocalDate getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(LocalDate dateDepart) {
		this.dateDepart = dateDepart;
	}

	public LocalDate getDateArrivee() {
		return dateArrivee;
	}

	public void setDateArrivee(LocalDate dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	public LocalTime getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(LocalTime heureDepart) {
		this.heureDepart = heureDepart;
	}

	public LocalTime getHeureArrivee() {
		return heureArrivee;
	}

	public void setHeureArrivee(LocalTime heureArrivee) {
		this.heureArrivee = heureArrivee;
	}

	public int getIdAvion() {
		return idAvion;
	}

	public void setIdAvion(int idAvion) {
		this.idAvion = idAvion;
	}







	










	

	

}
