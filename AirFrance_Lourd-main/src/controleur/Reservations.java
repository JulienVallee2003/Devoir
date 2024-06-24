package controleur;

import java.time.LocalDate;

public class Reservations {
    private int idReservation;
    private int idPassager;
    private int idVol;
    private LocalDate dateReservation;
    private String siegeAttribue;
    private String nomPassager;
    private String prenomPassager;
    private String numeroVol;

    public Reservations(int idReservation, int idPassager, int idVol, LocalDate dateReservation, String siegeAttribue) {
        this.idReservation = idReservation;
        this.idPassager = idPassager;
        this.idVol = idVol;
        this.dateReservation = dateReservation;
        this.siegeAttribue = siegeAttribue;
    }

    public Reservations(int idReservation, String nomPassager, String prenomPassager, String numeroVol, LocalDate dateReservation, String siegeAttribue) {
        this.idReservation = idReservation;
        this.nomPassager = nomPassager;
        this.prenomPassager = prenomPassager;
        this.numeroVol = numeroVol;
        this.dateReservation = dateReservation;
        this.siegeAttribue = siegeAttribue;
    }

    public Reservations(int idPassager, int idVol, LocalDate dateReservation, String siegeAttribue) {
        this.idPassager = idPassager;
        this.idVol = idVol;
        this.dateReservation = dateReservation;
        this.siegeAttribue = siegeAttribue;
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

    public void setSiegeAttribue(String siegeAttribue) {
        this.siegeAttribue = siegeAttribue;
    }

    public String getNomPassager() {
        return nomPassager;
    }

    public void setNomPassager(String nomPassager) {
        this.nomPassager = nomPassager;
    }

    public String getPrenomPassager() {
        return prenomPassager;
    }

    public void setPrenomPassager(String prenomPassager) {
        this.prenomPassager = prenomPassager;
    }

    public String getNumeroVol() {
        return numeroVol;
    }

    public void setNumeroVol(String numeroVol) {
        this.numeroVol = numeroVol;
    }
}
