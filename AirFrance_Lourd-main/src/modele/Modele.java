package modele;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

import controleur.Admin;
import controleur.Aeroports;
import controleur.Avions;
import controleur.MembreEquipage;
import controleur.Passager;
import controleur.Reservations;
import controleur.Vols;

public class Modele {
    // Instanciation de la connection Mysql
    public static BDD uneBDD = new BDD("localhost", "airfrance", "root", "");

    public static Admin verifConnexion(String email, String mdp) {
        Admin unUser = null;
        String requete = "SELECT * FROM admin WHERE email = '" + email + "' and MotDePasse ='" + mdp + "';";
        try {
            uneBDD.seConnecter();
            Statement unStat = uneBDD.getMaConnexion().createStatement();
            ResultSet unRes = unStat.executeQuery(requete);
            if (unRes.next()) {
                // Extraction des données de la BDD
                unUser = new Admin(unRes.getInt("ID_Admin"), unRes.getString("Prenom"), unRes.getString("Email"),
                        unRes.getString("MotDePasse"));
            }
            unStat.close();
            uneBDD.seDeconnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur de connexion à la BDD :" + requete);
            exp.printStackTrace();
        }
        return unUser;
    }

    public static Admin getAdminById(int id) {
        Admin admin = null;
        Connection connection = Modele.uneBDD.getMaConnexion();
        try {
            String query = "SELECT * FROM admin WHERE ID_Admin = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                admin = new Admin(
                    resultSet.getInt("ID_Admin"),
                    resultSet.getString("Prenom"),
                    resultSet.getString("Email"),
                    resultSet.getString("MotDePasse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public static void updateAdmin(Admin admin) {
        System.out.println("Début de updateAdmin dans Modele avec admin ID: " + admin.getIdAdmin());
        Connection connection = uneBDD.getMaConnexion();
        System.out.println("Connection: " + connection);
        if (connection == null) {
            System.out.println("La connexion est nulle.");
            return;
        }
        try {
            String query = "UPDATE admin SET Prenom = ?, Email = ?, MotDePasse = ? WHERE ID_Admin = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, admin.getPrenom());
            statement.setString(2, admin.getEmail());
            statement.setString(3, admin.getMdp());
            statement.setInt(4, admin.getIdAdmin());
    
            // Message de débogage
            System.out.println("Requête SQL : " + statement.toString());
    
            int rowsUpdated = statement.executeUpdate();
    
            // Message de débogage
            System.out.println("Nombre de lignes mises à jour : " + rowsUpdated);
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Fin de updateAdmin dans Modele");
    }
    
    

    public static Admin getAdminByEmailAndPassword(String email, String hashedPassword) {
        Admin admin = null;
        Connection connection = Modele.uneBDD.getMaConnexion();
        try {
            String query = "SELECT * FROM admin WHERE Email = ? AND MotDePasse = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                admin = new Admin(
                    resultSet.getInt("ID_Admin"),
                    resultSet.getString("Prenom"),
                    resultSet.getString("Email"),
                    resultSet.getString("MotDePasse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    // REQUETES POUR LES PASSAGERS 
    public static ArrayList<Passager> selectAllPassagers(String filtre) {
        ArrayList<Passager> lesPassagers = new ArrayList<Passager>();
        String requete;
        if (filtre.equals("")) {
            requete = "SELECT passagers.ID_Passager, personne.Nom, personne.Prenom, personne.DateNaissance, personne.adresse, personne.email, personne.telephone, passagers.NumPasseport\n"
                    + "FROM passagers\n"
                    + "INNER JOIN personne ON passagers.ID_Personne = personne.ID_Personne;";
        } else {
            requete = "SELECT passagers.ID_Passager, personne.Nom, personne.Prenom, personne.DateNaissance, personne.adresse, personne.email, personne.telephone, passagers.NumPasseport\n"
                    + "FROM passagers\n"
                    + "INNER JOIN personne ON passagers.ID_Personne = personne.ID_Personne\n"
                    + "WHERE personne.Nom LIKE ? OR personne.Prenom LIKE ?";
        }
        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);

            if (!filtre.equals("")) {
                String filterString = "" + filtre + "%";
                ps.setString(1, filterString);
                ps.setString(2, filterString);
            }

            ResultSet desRes = ps.executeQuery();

            while (desRes.next()) {
                Passager unPassager = new Passager(
                    desRes.getInt("ID_Passager"),
                    desRes.getString("Nom"),
                    desRes.getString("Prenom"),
                    desRes.getDate("DateNaissance").toLocalDate(),
                    desRes.getString("adresse"),
                    desRes.getString("email"),
                    desRes.getString("telephone"),
                    desRes.getString("NumPasseport")
                );
                lesPassagers.add(unPassager);
            }

            ps.close();
            uneBDD.seDeconnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur de connexion à la BDD :" + requete);
            exp.printStackTrace();
        }

        return lesPassagers;
    }

    public static void insertPassager(Passager unPassager) {
        String requetePersonne = "INSERT INTO personne (Nom, Prenom, DateNaissance, Adresse, Email, Telephone) VALUES (?, ?, ?, ?, ?, ?)";
        String requetePassager = "INSERT INTO passagers (ID_Personne, NumPasseport) VALUES (?, ?)";

        try {
            uneBDD.seConnecter();
            PreparedStatement pstmtPersonne = uneBDD.getMaConnexion().prepareStatement(requetePersonne, Statement.RETURN_GENERATED_KEYS);

            pstmtPersonne.setString(1, unPassager.getNom());
            pstmtPersonne.setString(2, unPassager.getPrenom());
            pstmtPersonne.setDate(3, Date.valueOf(unPassager.getDateNaissance()));
            pstmtPersonne.setString(4, unPassager.getAdresse());
            pstmtPersonne.setString(5, unPassager.getEmail());
            pstmtPersonne.setString(6, unPassager.getTelephone());

            pstmtPersonne.executeUpdate();

            ResultSet generatedKeys = pstmtPersonne.getGeneratedKeys();
            int idPersonne = -1;
            if (generatedKeys.next()) {
                idPersonne = generatedKeys.getInt(1);
            }

            // Vérification de l'ID de personne avant d'insérer dans la table passagers
            if (idPersonne != -1) {
                PreparedStatement pstmtPassager = uneBDD.getMaConnexion().prepareStatement(requetePassager);

                pstmtPassager.setInt(1, idPersonne);
                pstmtPassager.setString(2, unPassager.getNumPasseport());

                pstmtPassager.executeUpdate();

             

                pstmtPassager.close();
            }

            pstmtPersonne.close();
            uneBDD.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de l'insertion du passager : " + exp.getMessage());
            exp.printStackTrace();
        }
    }  
    
    public static void deletePassager(int idPassager) {
        String requete = "{ CALL DeletePassager(?) }";

        try {
            uneBDD.seConnecter();
            PreparedStatement pstmtDelete = uneBDD.getMaConnexion().prepareStatement(requete);
            pstmtDelete.setInt(1, idPassager);
            pstmtDelete.executeUpdate();
            pstmtDelete.close();
            uneBDD.seDeconnecter();
            System.out.println("Passager supprimé avec succès !");
        } catch (SQLException exp) {
            System.out.println("Erreur lors de la suppression du passager : " + exp.getMessage());
            exp.printStackTrace();
        }
    }


    public static void updatePassager(Passager unPassager) {
        String requete = "UPDATE personne AS p INNER JOIN passagers AS ps ON p.ID_Personne = ps.ID_Personne SET p.Nom = ?, p.Prenom = ?, p.DateNaissance = ?, p.Adresse = ?, p.Email = ?, p.Telephone = ?, ps.NumPasseport = ? WHERE ps.ID_Passager = ?";
        try {
            uneBDD.seConnecter();
            PreparedStatement pstmt = uneBDD.getMaConnexion().prepareStatement(requete);
            pstmt.setString(1, unPassager.getNom());
            pstmt.setString(2, unPassager.getPrenom());
            pstmt.setDate(3, Date.valueOf(unPassager.getDateNaissance())); // Supposons que getDateNaissance() retourne la date de naissance
            pstmt.setString(4, unPassager.getAdresse());
            pstmt.setString(5, unPassager.getEmail());
            pstmt.setString(6, unPassager.getTelephone());
            pstmt.setString(7, unPassager.getNumPasseport());
            pstmt.setInt(8, unPassager.getIdPassager()); // Supposons que getIdPassager() retourne l'ID du passager

            pstmt.executeUpdate();
            pstmt.close();

            uneBDD.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de la mise à jour du passager : " + exp.getMessage());
            exp.printStackTrace();
        }
    }
    
//GESTION DES VOLS
public static ArrayList<Vols> selectAllVols(String filtre) {
    ArrayList<Vols> lesVols = new ArrayList<Vols>();
    String requete;
    if (filtre.equals("")) {
        requete = "SELECT * FROM vue_vols;";
    } else {
        requete = "SELECT * FROM vue_vols WHERE AeroportDepart LIKE ? OR AeroportArrivee LIKE ? OR Avion LIKE ?;";
    }
    try {
        uneBDD.seConnecter();
        PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);

        if (!filtre.equals("")) {
            String filterString = "%" + filtre + "%";
            ps.setString(1, filterString);
            ps.setString(2, filterString);
            ps.setString(3, filterString);
        }

        ResultSet desRes = ps.executeQuery();

        while (desRes.next()) {
            Vols unVol = new Vols(
                desRes.getInt("ID_Vol"),
                desRes.getString("NumeroVol"),
                desRes.getDate("DateDepart").toLocalDate(),
                desRes.getTime("HeureDepart").toLocalTime(),
                desRes.getString("AeroportDepart"),
                desRes.getDate("DateArrivee").toLocalDate(),
                desRes.getTime("HeureArrivee").toLocalTime(),
                desRes.getString("AeroportArrivee"),
                desRes.getString("Avion")
            );
            lesVols.add(unVol);
        }

        ps.close();
        uneBDD.seDeconnecter();

    } catch (SQLException exp) {
        System.out.println("Erreur lors de la récupération des vols : " + exp.getMessage());
        exp.printStackTrace();
    }

    return lesVols;
}

public static ArrayList<Vols> selectAllVols(String filtre, String order) {
    ArrayList<Vols> lesVols = new ArrayList<Vols>();
    String requete;
    if (filtre.equals("")) {
        requete = "SELECT * FROM vue_vols ORDER BY DateDepart " + order + ";";
    } else {
        requete = "SELECT * FROM vue_vols WHERE AeroportDepart LIKE ? OR AeroportArrivee LIKE ? OR Avion LIKE ? ORDER BY DateDepart " + order + ";";
    }
    try {
        uneBDD.seConnecter();
        PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);

        if (!filtre.equals("")) {
            String filterString = "%" + filtre + "%";
            ps.setString(1, filterString);
            ps.setString(2, filterString);
            ps.setString(3, filterString);
        }

        ResultSet desRes = ps.executeQuery();

        while (desRes.next()) {
            Vols unVol = new Vols(
                desRes.getInt("ID_Vol"),
                desRes.getString("NumeroVol"),
                desRes.getDate("DateDepart").toLocalDate(),
                desRes.getTime("HeureDepart").toLocalTime(),
                desRes.getString("AeroportDepart"),
                desRes.getDate("DateArrivee").toLocalDate(),
                desRes.getTime("HeureArrivee").toLocalTime(),
                desRes.getString("AeroportArrivee"),
                desRes.getString("Avion")
            );
            lesVols.add(unVol);
        }

        ps.close();
        uneBDD.seDeconnecter();

    } catch (SQLException exp) {
        System.out.println("Erreur lors de la récupération des vols : " + exp.getMessage());
        exp.printStackTrace();
    }

    return lesVols;
}

public static ArrayList<Vols> selectAllVols(String filtre, String order, String aeroportDepart, String aeroportArrive, String avion) {
    ArrayList<Vols> lesVols = new ArrayList<Vols>();
    StringBuilder requete = new StringBuilder("SELECT * FROM vue_vols WHERE 1=1");

    if (!filtre.equals("")) {
        requete.append(" AND (NumeroVol LIKE ?)");
    }
    if (!aeroportDepart.equals("")) {
        requete.append(" AND AeroportDepart LIKE ?");
    }
    if (!aeroportArrive.equals("")) {
        requete.append(" AND AeroportArrivee LIKE ?");
    }
    if (!avion.equals("")) {
        requete.append(" AND Avion LIKE ?");
    }
    requete.append(" ORDER BY DateDepart ").append(order);

    try {
        uneBDD.seConnecter();
        PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete.toString());

        int paramIndex = 1;
        if (!filtre.equals("")) {
            ps.setString(paramIndex++, "%" + filtre + "%");
        }
        if (!aeroportDepart.equals("")) {
            ps.setString(paramIndex++, "%" + aeroportDepart + "%");
        }
        if (!aeroportArrive.equals("")) {
            ps.setString(paramIndex++, "%" + aeroportArrive + "%");
        }
        if (!avion.equals("")) {
            ps.setString(paramIndex++, "%" + avion + "%");
        }

        ResultSet desRes = ps.executeQuery();

        while (desRes.next()) {
            Vols unVol = new Vols(
                desRes.getInt("ID_Vol"),
                desRes.getString("NumeroVol"),
                desRes.getDate("DateDepart").toLocalDate(),
                desRes.getTime("HeureDepart").toLocalTime(),
                desRes.getString("AeroportDepart"),
                desRes.getDate("DateArrivee").toLocalDate(),
                desRes.getTime("HeureArrivee").toLocalTime(),
                desRes.getString("AeroportArrivee"),
                desRes.getString("Avion")
            );
            lesVols.add(unVol);
        }

        ps.close();
        uneBDD.seDeconnecter();

    } catch (SQLException exp) {
        System.out.println("Erreur lors de la récupération des vols : " + exp.getMessage());
        exp.printStackTrace();
    }

    return lesVols;
}


public static void insertVol(Vols unVol) {
    String requete = "INSERT INTO vols (NumeroVol, DateDepart, HeureDepart, AeroportDepart, DateArrivee, HeureArrivee, AeroportArrivee, Avion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    try {
        uneBDD.seConnecter();
        PreparedStatement pstmt = uneBDD.getMaConnexion().prepareStatement(requete);

        int idAeroportDepart = getIdAeroport(unVol.getAeroportDepart());
        int idAeroportArrivee = getIdAeroport(unVol.getAeroportArrivee());
        int idAvion = getIdAvion(unVol.getAvion());

        pstmt.setString(1, unVol.getNumVol());
        pstmt.setDate(2, Date.valueOf(unVol.getDateDepart()));
        pstmt.setTime(3, Time.valueOf(unVol.getHeureDepart()));
        pstmt.setInt(4, idAeroportDepart);
        pstmt.setDate(5, Date.valueOf(unVol.getDateArrivee()));
        pstmt.setTime(6, Time.valueOf(unVol.getHeureArrivee()));
        pstmt.setInt(7, idAeroportArrivee);
        pstmt.setInt(8, idAvion);

        pstmt.executeUpdate();
        pstmt.close();
        uneBDD.seDeconnecter();
    } catch (SQLException exp) {
        System.out.println("Erreur lors de l'insertion du vol : " + exp.getMessage());
        exp.printStackTrace();
    }
}

public static void updateVol(Vols unVol) {
    String requete = "UPDATE vols SET NumeroVol = ?, DateDepart = ?, HeureDepart = ?, AeroportDepart = ?, DateArrivee = ?, HeureArrivee = ?, AeroportArrivee = ?, Avion = ? WHERE ID_Vol = ?";
    try {
        uneBDD.seConnecter();
        PreparedStatement pstmt = uneBDD.getMaConnexion().prepareStatement(requete);

        int idAeroportDepart = getIdAeroport(unVol.getAeroportDepart());
        int idAeroportArrivee = getIdAeroport(unVol.getAeroportArrivee());
        int idAvion = getIdAvion(unVol.getAvion());

        pstmt.setString(1, unVol.getNumVol());
        pstmt.setDate(2, Date.valueOf(unVol.getDateDepart()));
        pstmt.setTime(3, Time.valueOf(unVol.getHeureDepart()));
        pstmt.setInt(4, idAeroportDepart);
        pstmt.setDate(5, Date.valueOf(unVol.getDateArrivee()));
        pstmt.setTime(6, Time.valueOf(unVol.getHeureArrivee()));
        pstmt.setInt(7, idAeroportArrivee);
        pstmt.setInt(8, idAvion);
        pstmt.setInt(9, unVol.getIdVol());

        pstmt.executeUpdate();
        pstmt.close();
        uneBDD.seDeconnecter();
    } catch (SQLException exp) {
        System.out.println("Erreur lors de la mise à jour du vol : " + exp.getMessage());
        exp.printStackTrace();
    }
}

private static int getIdAeroport(String nomAeroport) throws SQLException {
    String requete = "SELECT ID_Aeroport FROM aeroports WHERE Nom = ?";
    PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
    ps.setString(1, nomAeroport);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return rs.getInt("ID_Aeroport");
    }
    return -1; // Ou gérer l'erreur autrement
}

private static int getIdAvion(String modeleAvion) throws SQLException {
    String requete = "SELECT ID_Avion FROM avions WHERE Modele = ?";
    PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
    ps.setString(1, modeleAvion);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return rs.getInt("ID_Avion");
    }
    return -1; // Ou gérer l'erreur autrement
}



    
   
    
    //GESTION DES RESERVATIONS
    /*public static void insertReservation(Reservations nouvelleReservation) {
    	
    	
        String requeteReservation = "INSERT INTO reservations (ID_Passager, ID_Vol, DateReservation, SiegeAttribue) VALUES (?, ?, ?, ?)";
        try {
            uneBDD.seConnecter();
            PreparedStatement pstmt = uneBDD.getMaConnexion().prepareStatement(requeteReservation);
            pstmt.setInt(1, nouvelleReservation.getIdPassager());
            pstmt.setInt(2, nouvelleReservation.getIdVol());
            pstmt.setDate(3, Date.valueOf(nouvelleReservation.getDateReservation()));
            pstmt.setString(4, nouvelleReservation.getSiegeAttribute());
            pstmt.executeUpdate();
            pstmt.close();
            uneBDD.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de l'insertion de la réservation : " + exp.getMessage());
            exp.printStackTrace();
        }
    }*/


//gestion des avions
    public static ArrayList<Avions> selectAllAvions() {
        ArrayList<Avions> lesAvions = new ArrayList<Avions>();
        String requete = "SELECT * FROM avions"; // Requête SQL pour récupérer tous les avions

        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);

            ResultSet desRes = ps.executeQuery();

            // Parcourir les résultats et créer des objets Avion
            while (desRes.next()) {
                Avions unAvion = new Avions(
                    desRes.getInt("id_Avion"),
                    desRes.getString("Modele"),
                    desRes.getInt("nombrePlaces")
                );
                lesAvions.add(unAvion);
            }

            ps.close();
            uneBDD.seDeconnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur lors de la récupération des avions : " + exp.getMessage());
            exp.printStackTrace();
        }

        return lesAvions;
    }
    
    public static ArrayList<Aeroports> selectAllAeroports() {
        ArrayList<Aeroports> lesAeroports = new ArrayList<Aeroports>();
        String requete = "SELECT * FROM aeroports"; // Requête SQL pour récupérer tous les avions

        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);

            ResultSet desRes = ps.executeQuery();

            // Parcourir les résultats et créer des objets Avion
            while (desRes.next()) {
                Aeroports unAeroport = new Aeroports(
                    desRes.getInt("id_Aeroport"),
                    desRes.getString("Nom"),
                    desRes.getString("Localisation")
                );
                lesAeroports.add(unAeroport);
            }

            ps.close();
            uneBDD.seDeconnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur lors de la récupération des avions : " + exp.getMessage());
            exp.printStackTrace();
        }

        return lesAeroports;
    }


//GESTION DES VOLS
public static void deleteVol(int idVol) {
    String requete = "{ CALL DeleteVol(?) }";

    try {
        uneBDD.seConnecter();
        PreparedStatement pstmtDelete = uneBDD.getMaConnexion().prepareStatement(requete);
        pstmtDelete.setInt(1, idVol);
        pstmtDelete.executeUpdate();
        pstmtDelete.close();
        uneBDD.seDeconnecter();
        System.out.println("Vol supprimé avec succès !");
    } catch (SQLException exp) {
        System.out.println("Erreur lors de la suppression du vol : " + exp.getMessage());
        exp.printStackTrace();
    }
}




//GESTION DES EQUIPAGES 
    public static ArrayList<MembreEquipage> selectAllMembreEquipage(String filtre) {
        ArrayList<MembreEquipage> lesMembresEquipage = new ArrayList<MembreEquipage>();
        String requete;
        if (filtre.equals("")) {
            requete = "SELECT m.ID_MembreEquipage, p.*, m.Role, m.DateEmbauche, m.ID_Vol " +
                      "FROM membresequipage m " +
                      "INNER JOIN personne p ON m.ID_Personne = p.ID_Personne;";
        } else {
            requete = "SELECT m.ID_MembreEquipage, p.*, m.Role, m.DateEmbauche, m.ID_Vol " +
                      "FROM membresequipage m " +
                      "INNER JOIN personne p ON m.ID_Personne = p.ID_Personne " +
                      "WHERE p.Nom LIKE ? OR p.Prenom LIKE ? OR m.Role LIKE ?";
        }
        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);

            if (!filtre.equals("")) {
                String filterString = "" + filtre + "%";
                ps.setString(1, filterString);
                ps.setString(2, filterString);
                ps.setString(3, filterString);
            }

            ResultSet desRes = ps.executeQuery();

            while (desRes.next()) {
                // Récupération des attributs spécifiques au membre d'équipage
                int idMembreEquipage = desRes.getInt("ID_MembreEquipage");
                String nom = desRes.getString("Nom");
                String prenom = desRes.getString("Prenom");
                LocalDate dateNaissance = desRes.getDate("DateNaissance").toLocalDate();
                String adresse = desRes.getString("adresse");
                String email = desRes.getString("email");
                String telephone = desRes.getString("telephone");
                String role = desRes.getString("Role");
                LocalDate dateEmbauche = desRes.getDate("DateEmbauche").toLocalDate();
                int idVol = desRes.getInt("ID_Vol");

                // Création de l'objet MembreEquipage en utilisant les attributs récupérés
                MembreEquipage unMembreEquipage = new MembreEquipage(idMembreEquipage, nom, prenom, dateNaissance, adresse, email, telephone, role, dateEmbauche, idVol);

                // Ajout du membre d'équipage à la liste
                lesMembresEquipage.add(unMembreEquipage);
            }

            ps.close();
            uneBDD.seDeconnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur lors de la récupération des membres de l'équipage : " + exp.getMessage());
            exp.printStackTrace();
        }

        return lesMembresEquipage;
    }

    public static void insertEquipage(MembreEquipage membre) {
        String requete = "INSERT INTO personne (Nom, Prenom, DateNaissance, adresse, email, telephone) VALUES (?, ?, ?, ?, ?, ?)";
        String requete2 = "INSERT INTO membresequipage (ID_Personne, Role, DateEmbauche, ID_Vol) VALUES (?, ?, ?, ?)";

        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, membre.getNom());
            ps.setString(2, membre.getPrenom());
            ps.setDate(3, Date.valueOf(membre.getDateNaissance()));
            ps.setString(4, membre.getAdresse());
            ps.setString(5, membre.getEmail());
            ps.setString(6, membre.getTelephone());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idPersonne = generatedKeys.getInt(1);

                PreparedStatement ps2 = uneBDD.getMaConnexion().prepareStatement(requete2);
                ps2.setInt(1, idPersonne);
                ps2.setString(2, membre.getRole());
                ps2.setDate(3, Date.valueOf(membre.getDateEmbauche()));
                ps2.setInt(4, membre.getIdVol());
                ps2.executeUpdate();
                ps2.close();
            }
            ps.close();
            uneBDD.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de l'insertion de l'équipage : " + exp.getMessage());
            exp.printStackTrace();
        }
    }


    public static void deleteMembreEquipage(int idMembreEquipage) {
        String requete = "DELETE FROM membresequipage WHERE ID_MembreEquipage = ?";
        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
            ps.setInt(1, idMembreEquipage);

            int nombreLignesSupprimees = ps.executeUpdate();
            if (nombreLignesSupprimees > 0) {
                System.out.println("Le membre d'équipage avec l'ID " + idMembreEquipage + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun membre d'équipage trouvé avec l'ID " + idMembreEquipage + ".");
            }

            ps.close();
            uneBDD.seDeconnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur lors de la suppression du membre d'équipage : " + exp.getMessage());
            exp.printStackTrace();
        }
    }

    public static void updateEquipage(MembreEquipage unEquipage) {
        String requete = "UPDATE membresequipage m " +
                         "INNER JOIN personne p ON m.ID_Personne = p.ID_Personne " +
                         "SET p.Nom = ?, p.Prenom = ?, p.DateNaissance = ?, p.Adresse = ?, " +
                         "p.Email = ?, p.Telephone = ?, m.Role = ?, m.DateEmbauche = ?, " +
                         "m.ID_Vol = ? WHERE m.ID_MembreEquipage = ?";
        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
            // Remplissage des paramètres de la requête
            ps.setString(1, unEquipage.getNom());
            ps.setString(2, unEquipage.getPrenom());
            ps.setDate(3, java.sql.Date.valueOf(unEquipage.getDateNaissance()));
            ps.setString(4, unEquipage.getAdresse());
            ps.setString(5, unEquipage.getEmail());
            ps.setString(6, unEquipage.getTelephone());
            ps.setString(7, unEquipage.getRole());
            ps.setDate(8, java.sql.Date.valueOf(unEquipage.getDateEmbauche()));
            ps.setInt(9, unEquipage.getIdVol());
            ps.setInt(10, unEquipage.getIdEquipage());

            // Exécution de la requête
            int nbLignes = ps.executeUpdate();

            // Vérification de la réussite de l'opération
            if (nbLignes > 0) {
                System.out.println("Mise à jour de l'équipage effectuée avec succès.");
            } else {
                System.out.println("Aucune mise à jour effectuée.");
            }

            ps.close();
            uneBDD.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de la mise à jour de l'équipage : " + exp.getMessage());
            exp.printStackTrace();
        }
    }

 // GESTION DES RESERVATIONS
 public static boolean isReservationDateValid(int idVol, LocalDate dateReservation) {
    boolean isValid = false;
    uneBDD.seConnecter();
    Connection connection = uneBDD.getMaConnexion();
    try {
        String query = "SELECT DateDepart FROM vols WHERE ID_Vol = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idVol);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            LocalDate dateDepart = resultSet.getDate("DateDepart").toLocalDate();
            System.out.println("Date de départ du vol : " + dateDepart);
            System.out.println("Date de réservation : " + dateReservation);
            isValid = dateReservation.isBefore(dateDepart);
        }
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        uneBDD.seDeconnecter();
    }
    return isValid;
}

public static void insertReservation(Reservations nouvelleReservation) {
    if (!isReservationDateValid(nouvelleReservation.getIdVol(), nouvelleReservation.getDateReservation())) {
        System.out.println("Erreur : La date de réservation doit être antérieure à la date de départ du vol.");
        return;
    }

    String requeteReservation = "INSERT INTO reservations (ID_Passager, ID_Vol, DateReservation, SiegeAttribue) VALUES (?, ?, ?, ?)";
    try {
        uneBDD.seConnecter();
        PreparedStatement pstmt = uneBDD.getMaConnexion().prepareStatement(requeteReservation);
        pstmt.setInt(1, nouvelleReservation.getIdPassager());
        pstmt.setInt(2, nouvelleReservation.getIdVol());
        pstmt.setDate(3, Date.valueOf(nouvelleReservation.getDateReservation()));
        pstmt.setString(4, nouvelleReservation.getSiegeAttribue());
        pstmt.executeUpdate();
        pstmt.close();
    } catch (SQLException exp) {
        System.out.println("Erreur lors de l'insertion de la réservation : " + exp.getMessage());
        exp.printStackTrace();
    } finally {
        uneBDD.seDeconnecter();
    }
}

public static void updateReservation(Reservations nouvelleReservation) {
    if (!isReservationDateValid(nouvelleReservation.getIdVol(), nouvelleReservation.getDateReservation())) {
        System.out.println("Erreur : La date de réservation doit être antérieure à la date de départ du vol.");
        return;
    }

    String requete = "UPDATE reservations SET ID_Passager = ?, ID_Vol = ?, DateReservation = ?, SiegeAttribue = ? WHERE ID_Reservation = ?";
    try {
        uneBDD.seConnecter();
        PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
        ps.setInt(1, nouvelleReservation.getIdPassager());
        ps.setInt(2, nouvelleReservation.getIdVol());
        ps.setDate(3, java.sql.Date.valueOf(nouvelleReservation.getDateReservation()));
        ps.setString(4, nouvelleReservation.getSiegeAttribue());
        ps.setInt(5, nouvelleReservation.getIdReservation());
        ps.executeUpdate();
        ps.close();
    } catch (SQLException exp) {
        System.out.println("Erreur lors de la mise à jour de la réservation : " + exp.getMessage());
        exp.printStackTrace();
    } finally {
        uneBDD.seDeconnecter();
    }
}


public static ArrayList<Reservations> selectAllReservation(String filtre, String filterField) {
    ArrayList<Reservations> lesReservations = new ArrayList<Reservations>();
    String requete;
    if (filtre.equals("")) {
        requete = "SELECT * FROM vue_reservations";
    } else {
        String champFiltre;
        switch (filterField.toLowerCase()) {
            case "nom":
                champFiltre = "nom";
                break;
            case "prenom":
                champFiltre = "prenom";
                break;
            case "numero de vol":
                champFiltre = "NumeroVol";
                break;
            case "siege attribue":
                champFiltre = "SiegeAttribue";
                break;
            default:
                champFiltre = "nom";
                break;
        }
        requete = "SELECT * FROM vue_reservations WHERE " + champFiltre + " LIKE ?";
    }
    try {
        uneBDD.seConnecter();
        PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);

        if (!filtre.equals("")) {
            String filterString = "%" + filtre + "%";
            ps.setString(1, filterString);
        }

        ResultSet desRes = ps.executeQuery();

        while (desRes.next()) {
            Reservations uneReservation = new Reservations(
                desRes.getInt("ID_Reservation"),
                desRes.getString("nom"),
                desRes.getString("prenom"),
                desRes.getString("NumeroVol"),
                desRes.getDate("DateReservation").toLocalDate(),
                desRes.getString("SiegeAttribue")
            );

            lesReservations.add(uneReservation);
        }

        ps.close();
        uneBDD.seDeconnecter();

    } catch (SQLException exp) {
        System.out.println("Erreur lors de la récupération des réservations : " + exp.getMessage());
        exp.printStackTrace();
    }

    return lesReservations;
}

public static ArrayList<Reservations> selectAllReservationsTri(String order) {
    ArrayList<Reservations> lesReservations = new ArrayList<Reservations>();
    String requete = "SELECT * FROM vue_reservations ORDER BY DateReservation " + order;
    try {
        uneBDD.seConnecter();
        PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
        ResultSet desRes = ps.executeQuery();

        while (desRes.next()) {
            Reservations uneReservation = new Reservations(
                desRes.getInt("ID_Reservation"),
                desRes.getString("nom"),
                desRes.getString("prenom"),
                desRes.getString("NumeroVol"),
                desRes.getDate("DateReservation").toLocalDate(),
                desRes.getString("SiegeAttribue")
            );

            lesReservations.add(uneReservation);
        }

        ps.close();
        uneBDD.seDeconnecter();

    } catch (SQLException exp) {
        System.out.println("Erreur lors de la récupération des réservations : " + exp.getMessage());
        exp.printStackTrace();
    }

    return lesReservations;
}




    public static void deleteReservation(int idResa) {
        try {
            String requete = "DELETE FROM reservations WHERE ID_Reservation = ?";
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
            ps.setInt(1, idResa);
            ps.executeUpdate();
            ps.close();
            uneBDD.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de la suppression de la réservation : " + exp.getMessage());
            exp.printStackTrace();
        }
    }

    public static int getIdPassager(String nom, String prenom) throws SQLException {
        uneBDD.seConnecter();
        Connection connection = uneBDD.getMaConnexion();
        int idPassager = -1;
        try {
            String query = "SELECT ID_Passager FROM passagers INNER JOIN personne ON passagers.ID_Personne = personne.ID_Personne WHERE personne.Nom = ? AND personne.Prenom = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idPassager = resultSet.getInt("ID_Passager");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            uneBDD.seDeconnecter();
        }
        return idPassager;
    }
    
    public static int getIdVol(String numeroVol) throws SQLException {
        uneBDD.seConnecter();
        Connection connection = uneBDD.getMaConnexion();
        int idVol = -1;
        try {
            String query = "SELECT ID_Vol FROM vols WHERE NumeroVol = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, numeroVol);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idVol = resultSet.getInt("ID_Vol");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            uneBDD.seDeconnecter();
        }
        return idVol;
    }

    

    


    



}

    
    





    




