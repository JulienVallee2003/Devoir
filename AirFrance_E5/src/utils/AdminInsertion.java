/* package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import controleur.Admin;
import modele.BDD;

public class AdminInsertion {
    public static void main(String[] args) {
        // Créer un nouvel administrateur
        String prenom = "NewAdmin";
        String email = "newadmin@airfrance.com";
        String password = "Admin@123"; // Mot de passe à hacher

        // Hacher le mot de passe
        String hashedPassword = HashUtil.hashPassword(password);

        // Créer un objet Admin
        Admin newAdmin = new Admin(0, prenom, email, hashedPassword);

        // Insérer l'administrateur dans la base de données
        insertAdmin(newAdmin);
    }

    public static void insertAdmin(Admin admin) {
        BDD bdd = new BDD("localhost", "airfrance", "root", "");
        bdd.seConnecter();
        Connection connection = bdd.getMaConnexion();

        try {
            String query = "INSERT INTO admin (Prenom, Email, MotDePasse) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, admin.getPrenom());
            statement.setString(2, admin.getEmail());
            statement.setString(3, admin.getMdp()); // Mot de passe haché
            statement.executeUpdate();
            System.out.println("Nouvel administrateur inséré avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            bdd.seDeconnecter();
        }
    }
}
     */