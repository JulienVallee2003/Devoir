package controleur;

import java.time.LocalDate;

public class Reservations {
	 private int idReservation;
	    private int idPassager;
	    private int idVol;
	    private LocalDate dateReservation;
	    private String siegeAttribue;

	    public Reservations(int idReservation, int idPassager, int idVol, LocalDate dateReservation, String siegeAttribue) {
	        this.idReservation = idReservation;
	        this.idPassager = idPassager;
	        this.idVol = idVol;
	        this.dateReservation = dateReservation;
	        this.siegeAttribue = siegeAttribue;
	    }
	    
	    public Reservations( int idPassager, int idVol, LocalDate dateReservation, String siegeAttribue) {
	        this.idPassager = idPassager;
	        this.idVol = idVol;
	        this.dateReservation = dateReservation;
	        this.siegeAttribue = siegeAttribue;
	    }
	    
	    public Reservations() {
	    	
	    }

	    public int getIdReservation() {
	        return idReservation;
	    }

	    public void setIdReservation(int idReservation) {
	        this.idReservation = idReservation;
	    }

	    public int getIdPassager() {
	        return idPassager;
	    }

	    public void setIdPassager(int idPassager) {
	        this.idPassager = idPassager;
	    }

	    public int getIdVol() {
	        return idVol;
	    }

	    public void setIdVol(int idVol) {
	        this.idVol = idVol;
	    }

	    public LocalDate getDateReservation() {
	        return dateReservation;
	    }

	    public void setDateReservation(LocalDate dateReservation) {
	        this.dateReservation = dateReservation;
	    }

	    public String getSiegeAttribue() {
	        return siegeAttribue;
	    }

	    public void setSiegeAttribute(String siegeAttribue) {
	        this.siegeAttribue = siegeAttribue;
	    }

}
