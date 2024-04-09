package controleur;

public class Avions {
	private int idAvion;
	private String modele;
	private int nbPlaces;
	
	public Avions(int idAvion, String modele, int nbPlaces) {
		this.idAvion = idAvion;
		this.modele = modele;
		this.nbPlaces = nbPlaces;
	}
	
	public Avions() {
		this.idAvion = 0;
		this.modele = "";
		this.nbPlaces = 0;
	}

	public int getIdAvion() {
		return idAvion;
	}

	public void setIdAvion(int idAvion) {
		this.idAvion = idAvion;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public int getNbPlaces() {
		return nbPlaces;
	}

	public void setNbPlaces(int nbPlaces) {
		this.nbPlaces = nbPlaces;
	}
	
}
