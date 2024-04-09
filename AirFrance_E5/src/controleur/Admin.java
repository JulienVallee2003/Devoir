package controleur;

public class Admin {
	private int idAdmin;
	private String prenom, email, mdp;
	
	public Admin(int idAdmin, String prenom, String email, String mdp) {
		this.idAdmin = idAdmin;
		this.prenom = prenom;
		this.email = email;
		this.mdp = mdp;
	}

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

}
