package controleur;

public class Aeroports {

	private int idAeroport;
	private String nomAeroport;
	private String localisation;
	
	public Aeroports(int idAeroport, String nomAeroport, String localisation) {
		this.idAeroport = idAeroport;
		this.nomAeroport = nomAeroport;
		this.localisation = localisation;
	}

	public int getIdAeroport() {
		return idAeroport;
	}

	public void setIdAeroport(int idAeroport) {
		this.idAeroport = idAeroport;
	}

	public String getNomAeroport() {
		return nomAeroport;
	}

	public void setNomAeroport(String nomAeroport) {
		this.nomAeroport = nomAeroport;
	}

	public String getLocalisation() {
		return localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}
	
}

