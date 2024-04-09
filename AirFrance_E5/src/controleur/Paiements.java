package controleur;

import java.time.LocalDate;

public class Paiements {
	private int idPaiement;
	private int idReservation;
	private int montantPaye;
	private LocalDate datePaiement;
	private String statutPaiement;
	
	public Paiements(int idPaiement, int idReservation, int montantPaye, LocalDate datePaiement, String statutPaiement) {
		super();
		this.idPaiement = idPaiement;
		this.idReservation = idReservation;
		this.montantPaye = montantPaye;
		this.datePaiement = datePaiement;
		this.statutPaiement = statutPaiement;
	}
	
	public Paiements(int idReservation, int montantPaye, LocalDate datePaiement, String statutPaiement) {
		super();
		this.idReservation = idReservation;
		this.montantPaye = montantPaye;
		this.datePaiement = datePaiement;
		this.statutPaiement = statutPaiement;
	}

	public int getIdPaiement() {
		return idPaiement;
	}

	public void setIdPaiement(int idPaiement) {
		this.idPaiement = idPaiement;
	}

	public int getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}

	public int getMontantPaye() {
		return montantPaye;
	}

	public void setMontantPaye(int montantPaye) {
		this.montantPaye = montantPaye;
	}

	public LocalDate getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(LocalDate datePaiement) {
		this.datePaiement = datePaiement;
	}

	public String getStatutPaiement() {
		return statutPaiement;
	}

	public void setStatutPaiement(String statutPaiement) {
		this.statutPaiement = statutPaiement;
	}
	

}
