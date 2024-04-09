package controleur;

import java.time.LocalDate;

public abstract class Personne {
private int id;
private String nom, prenom, adresse, email, telephone;
private LocalDate dateNaissance;

public Personne(int id, String nom, String prenom, LocalDate dateNaissance, String adresse, String email, String telephone) 
{
	this.id = id;
	this.nom = nom;
    this.prenom = prenom;
    this.dateNaissance = dateNaissance;
    this.adresse = adresse;
    this.email = email;
    this.telephone = telephone;
}

public Personne(String nom, String prenom, LocalDate dateNaissance, String adresse, String email, String telephone) 
{
	this.id = id;
	this.nom = nom;
    this.prenom = prenom;
    this.dateNaissance = dateNaissance;
    this.adresse = adresse;
    this.email = email;
    this.telephone = telephone;
}

public Personne() 
{
	this.nom = "";
    this.prenom = "";
    this.dateNaissance = null;
    this.adresse = "";
    this.email = "";
    this.telephone = "";
	
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getNom() {
	return nom;
}

public void setNom(String nom) {
	this.nom = nom;
}

public String getPrenom() {
	return prenom;
}

public void setPrenom(String prenom) {
	this.prenom = prenom;
}

public String getAdresse() {
	return adresse;
}

public void setAdresse(String adresse) {
	this.adresse = adresse;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getTelephone() {
	return telephone;
}

public void setTelephone(String telephone) {
	this.telephone = telephone;
}

public LocalDate getDateNaissance() {
	return dateNaissance;
}

public void setDateNaissance(LocalDate dateNaissance) {
	this.dateNaissance = dateNaissance;
}



}
