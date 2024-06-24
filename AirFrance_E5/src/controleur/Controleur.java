package controleur;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import modele.Modele;
import utils.HashUtil;

public class Controleur {
    
    // GESTION ADMIN
    public static Admin verifConnexion(String email, String mdp) {
        return Modele.verifConnexion(email, mdp);
    }

    public static int getIdPassager(String nom, String prenom) throws SQLException {
    return Modele.getIdPassager(nom, prenom);
}

public static int getIdVol(String numeroVol) throws SQLException {
    return Modele.getIdVol(numeroVol);
}
    
    public static Admin getAdminById(int id) {
        return Modele.getAdminById(id);
    }

    public static void updateAdmin(Admin admin) {
		System.out.println("Appel de updateAdmin dans Controleur avec admin ID: " + admin.getIdAdmin());
		Modele.updateAdmin(admin);
	}

    public static Admin login(String email, String password) {
        String hashedPassword = HashUtil.hashPassword(password);
        return Modele.getAdminByEmailAndPassword(email, hashedPassword);
    }
    
    // METHODES PASSAGERS   
    public static ArrayList<Passager> selectAllPassagers (String filtre){
        return Modele.selectAllPassagers(filtre);
    }
    
    public static void insertPassager(Passager unPassager) {
        Modele.insertPassager(unPassager);
    }
    
    public static void deletePassager(int idPassager) {
        Modele.deletePassager(idPassager);
    }
    
    public static void updatePassager(Passager unPassager) {
        Modele.updatePassager(unPassager);
    }
    
    // GESTION AVIONS
    public static ArrayList<Avions> selectAllAvions() {
        return Modele.selectAllAvions();
    }
    
    // GESTION DES VOLS
    public static ArrayList<Vols> selectAllVols(String filtre) {
        return Modele.selectAllVols(filtre);
    }
    
    public static ArrayList<Vols> selectAllVols(String filtre, String order) {
        return Modele.selectAllVols(filtre, order);
    }

    public static ArrayList<Vols> selectAllVols(String filtre, String order, String aeroportDepart, String aeroportArrive, String avion) {
        return Modele.selectAllVols(filtre, order, aeroportDepart, aeroportArrive, avion);
    }
    
    public static ArrayList<Aeroports> selectAllAeroports(){
        return Modele.selectAllAeroports();
    }

    public static void deleteVol(int idVol) {
        Modele.deleteVol(idVol);
    }

    public static void insertVol(Vols unVol) {
        Modele.insertVol(unVol);
    }

    public static void updateVol(Vols unVol) {
        Modele.updateVol(unVol);
    }

    // GESTION DES MEMBRES DE L'EQUIPAGE
    public static ArrayList<MembreEquipage> selectAllMembreEquipage(String filtre) {
        return Modele.selectAllMembreEquipage(filtre);
    }

    public static void deleteMembreEquipage(int idMembreEquipage) {
        Modele.deleteMembreEquipage(idMembreEquipage);
    }
    
    public static void updateEquipage(MembreEquipage unEquipage) {
        Modele.updateEquipage(unEquipage);
    }
    
    public static void insertEquipage(MembreEquipage unEquipage) {
        Modele.insertEquipage(unEquipage);
    }

    // GESTION DES RESERVATIONS
    public static ArrayList<Reservations> selectAllReservations(String filtre, String filterField) {
        return Modele.selectAllReservation(filtre, filterField);
    }
    
    public static ArrayList<Reservations> selectAllReservationsTri(String order) {
        return Modele.selectAllReservationsTri(order);
    }

    public static void deleteReservation(int idResa) {
        Modele.deleteReservation(idResa);
    }
    
    public static boolean isReservationDateValid(int idVol, LocalDate dateReservation) {
        return Modele.isReservationDateValid(idVol, dateReservation);
    }

    public static void insertReservation(Reservations nouvelleReservation) {
        Modele.insertReservation(nouvelleReservation);
    }

    public static void updateReservation(Reservations nouvelleReservation) {
        Modele.updateReservation(nouvelleReservation);
    }

    public static void insertMembreEquipage(String nom, String prenom, LocalDate dateNaissance, String adresse,
            String email, String telephone, String role, LocalDate dateEmbauche, String idVol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertMembreEquipage'");
    }
}
