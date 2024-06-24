package controleur;

import java.time.LocalDate;
import java.time.LocalTime;

public class Vols {
    private int idVol;
    private String numVol;
    private String aeroportDepart, aeroportArrivee, avion;
    private LocalDate dateDepart, dateArrivee;
    private LocalTime heureDepart, heureArrivee;

    public Vols(int idVol, String numVol, LocalDate dateDepart, LocalTime heureDepart, String aeroportDepart,
                LocalDate dateArrivee, LocalTime heureArrivee, String aeroportArrivee, String avion) {
        super();
        this.idVol = idVol;
        this.numVol = numVol;
        this.dateDepart = dateDepart;
        this.heureDepart = heureDepart;
        this.aeroportDepart = aeroportDepart;
        this.dateArrivee = dateArrivee;
        this.heureArrivee = heureArrivee;
        this.aeroportArrivee = aeroportArrivee;
        this.avion = avion;
    }

    // Getters et Setters

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

    public String getAeroportDepart() {
        return aeroportDepart;
    }

    public void setAeroportDepart(String aeroportDepart) {
        this.aeroportDepart = aeroportDepart;
    }

    public String getAeroportArrivee() {
        return aeroportArrivee;
    }

    public void setAeroportArrivee(String aeroportArrivee) {
        this.aeroportArrivee = aeroportArrivee;
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

    public String getAvion() {
        return avion;
    }

    public void setAvion(String avion) {
        this.avion = avion;
    }
}
