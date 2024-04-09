package controleur;

import java.time.LocalDate;
import java.util.ArrayList;

import modele.Modele;


public class Controleur {
	
	//GESTION ADMIN
	public static Admin verifConnexion(String email, String mdp) {
		return Modele.verifConnexion(email, mdp);
	}
	
//METHODES PASSAGERS	
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
	
//GESTION AVIONS
	public static ArrayList<Avions> selectAllAvions() {
		
		return Modele.selectAllAvions();
	}
	
	//GESTION DES VOLS
	public static ArrayList<Vols> selectAllVols (String filtre){
		return Modele.selectAllVols(filtre);
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

	
	//GESTION DES MEMBRES DE L'EQUIPAGE
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

	//GESTION DES RESERVATIONS
	public static ArrayList<Reservations> selectAllReservations(String filtre) {
		return Modele.selectAllReservation(filtre);
	}

	public static void deleteReservation(int idResa) {
		Modele.deleteReservation(idResa);
		
	}
	public static void insertReservation(Reservations nouvelleReservation) {
		   Modele.insertReservation(nouvelleReservation);
		}

	public static void updateReservation(Reservations nouvelleReservation) {
		Modele.updateReservation(nouvelleReservation);
		
	}
	

}
