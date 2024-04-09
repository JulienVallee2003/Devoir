package modele;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

import controleur.Admin;
import controleur.Avions;
import controleur.MembreEquipage;
import controleur.Passager;
import controleur.Reservations;
import controleur.Vols;
import controleur.Aeroports;

public class Modele {
    // Instanciation de la connection Mysql
    private static BDD uneBDD = new BDD("localhost", "airfrance", "root", "");

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
                String filterString = "%" + filtre + "%";
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
        String requete = "DELETE passagers, personne FROM passagers INNER JOIN personne ON passagers.ID_Personne = personne.ID_Personne WHERE passagers.ID_Passager = ?";

        try {
            uneBDD.seConnecter();
            PreparedStatement pstmtDelete = uneBDD.getMaConnexion().prepareStatement(requete);
            pstmtDelete.setInt(1, idPassager);
            pstmtDelete.executeUpdate();
            pstmtDelete.close();
            uneBDD.seDeconnecter();
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
            requete = "SELECT * FROM vols;";
        } else {
            requete = "SELECT * FROM vols WHERE AeroportDepart LIKE ? OR AeroportArrivee LIKE ? OR Avion LIKE ?;";
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
                        desRes.getInt("AeroportDepart"),
                        desRes.getDate("DateArrivee").toLocalDate(),
                        desRes.getTime("HeureArrivee").toLocalTime(),
                        desRes.getInt("AeroportArrivee"),
                        desRes.getInt("Avion")
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
        String requete = "DELETE FROM vols WHERE ID_Vol = ?";

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



    public static void insertVol(Vols unVol) {
        String requete = "INSERT INTO vols (NumeroVol, DateDepart, HeureDepart, AeroportDepart, DateArrivee, HeureArrivee, AeroportArrivee, Avion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            uneBDD.seConnecter();
            PreparedStatement pstmt = uneBDD.getMaConnexion().prepareStatement(requete);

            pstmt.setString(1, unVol.getNumVol());
            pstmt.setDate(2, Date.valueOf(unVol.getDateDepart()));
            pstmt.setTime(3, Time.valueOf(unVol.getHeureDepart()));
            pstmt.setInt(4, unVol.getIdAeroportDepart());
            pstmt.setDate(5, Date.valueOf(unVol.getDateArrivee()));
            pstmt.setTime(6, Time.valueOf(unVol.getHeureArrivee()));
            pstmt.setInt(7, unVol.getIdAeroportArrive());
            pstmt.setInt(8, unVol.getIdAvion());

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
            
            pstmt.setString(1, unVol.getNumVol());
            pstmt.setDate(2, Date.valueOf(unVol.getDateDepart()));
            pstmt.setTime(3, Time.valueOf(unVol.getHeureDepart()));
            pstmt.setInt(4, unVol.getIdAeroportDepart());
            pstmt.setDate(5, Date.valueOf(unVol.getDateArrivee()));
            pstmt.setTime(6, Time.valueOf(unVol.getHeureArrivee()));
            pstmt.setInt(7, unVol.getIdAeroportArrive());
            pstmt.setInt(8, unVol.getIdAvion());
            pstmt.setInt(9, unVol.getIdVol());
            
            pstmt.executeUpdate();
            pstmt.close();
            uneBDD.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de la mise à jour du vol : " + exp.getMessage());
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
                String filterString = "%" + filtre + "%";
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
    public static void insertReservation(Reservations nouvelleReservation) {

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
            uneBDD.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur lors de l'insertion de la réservation : " + exp.getMessage());
            exp.printStackTrace();
        }
    }


    public static ArrayList<Reservations> selectAllReservation(String filtre) {
        ArrayList<Reservations> lesReservations = new ArrayList<Reservations>();
        String requete;
        if (filtre.equals("")) {
            requete = "SELECT * FROM reservations";
        } else {
            requete = "SELECT * FROM reservations WHERE colonne1 LIKE ? OR colonne2 LIKE ?";
        }
        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);

            if (!filtre.equals("")) {
                String filterString = "%" + filtre + "%";
                ps.setString(1, filterString);
                ps.setString(2, filterString);
            }

            ResultSet desRes = ps.executeQuery();

            while (desRes.next()) {
                // Récupération des attributs de la réservation
                int idReservation = desRes.getInt("ID_Reservation");
                int idPassager = desRes.getInt("ID_Passager");
                int idVol = desRes.getInt("ID_Vol");
                LocalDate dateReservation = desRes.getDate("DateReservation").toLocalDate();
                String siegeAttribue = desRes.getString("SiegeAttribue");

                // Création de l'objet Reservations en utilisant les attributs récupérés
                Reservations uneReservation = new Reservations(idReservation, idPassager, idVol, dateReservation, siegeAttribue);

                // Ajout de la réservation à la liste
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



    public static void updateReservation(Reservations nouvelleReservation) {
        // Construction de la requête SQL
        String requete = "UPDATE reservations SET ID_Passager = ?, ID_Vol = ?, DateReservation = ?, SiegeAttribue = ? WHERE ID_Reservation = ?";

        try {
            // Connexion à la base de données
            uneBDD.seConnecter();

            // Préparation de la requête SQL
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);

            // Attribution des valeurs aux paramètres de la requête
            ps.setInt(1, nouvelleReservation.getIdPassager());
            ps.setInt(2, nouvelleReservation.getIdVol());
            ps.setDate(3, java.sql.Date.valueOf(nouvelleReservation.getDateReservation()));
            ps.setString(4, nouvelleReservation.getSiegeAttribue());
            ps.setInt(5, nouvelleReservation.getIdReservation());

            // Exécution de la requête
            ps.executeUpdate();

            // Fermeture de la connexion
            ps.close();
            uneBDD.seDeconnecter();

        } catch (SQLException exp) {
            // Gestion des exceptions
            System.out.println("Erreur lors de la mise à jour de la réservation : " + exp.getMessage());
            exp.printStackTrace();
        }
    }


    



}

    
    





    




