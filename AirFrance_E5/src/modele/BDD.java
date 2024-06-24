package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDD {
	//IDEM QUE LA CLASSE PDO DE PHP
		private String serveur, bdd, user, mdp ;
		private Connection maConnection;
		
		public BDD(String serveur, String bdd, String user, String mdp) {
			super();
			this.serveur = serveur;
			this.bdd = bdd;
			this.user = user;
			this.mdp = mdp;
			this.maConnection = null;
		}

		
		
		public void chargerPilote() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			}catch(ClassNotFoundException exp) {
				System.out.println("Erreur chargement du pilote JDBC");
			}
		}
		
		public void seConnecter() {
			this.chargerPilote();
			String url = "jdbc:mysql://"+this.serveur+"/"+this.bdd;
			try {
				this.maConnection = DriverManager.getConnection(url, this.user, this.mdp);
				System.out.println("Connexion réussie à " + url);
			}catch(SQLException exp) {
				System.out.println("Erreur : connexion échouée : "+ url);
				exp.printStackTrace();
			}
		}
		
		public void seDeconnecter() {
			try {
			if (this.maConnection != null) {
				this.maConnection.close();
			}
			}catch(SQLException exp) {
				System.out.println("erreur de fermeture de la connexion");
			}
		}
		
		public Connection getMaConnexion() {
			return this.maConnection;
		}

       
		
		

}
