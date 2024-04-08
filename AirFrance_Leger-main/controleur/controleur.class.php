<?php
	require_once("modele/modele.class.php");
	class Controleur{
		private $unModele;
		public function __construct(){
			$this->unModele= new Modele();
		}
		public function insertPassagers ($tab){
			$this->unModele->insertPassagers ($tab);
		}
		public function selectAllPassagers (){
			return $this->unModele->selectAllPassagers();
		}
		public function selectLikePassager ($filtre){
			return $this->unModele->selectLikePassager($filtre);
		}
		public function deletePassager($idpassager){
			$this->unModele->deletePassager($idpassager);
		}
		public function selectWherePassager($idpassager){
			return $this->unModele->selectWherePassager ($idpassager);
		}
		public function insertAvion($tab){
			$this->unModele->insertAvion($tab);
		}
		public function selectAllAvions(){
			return $this->unModele->selectAllAvions();
		}
		public function selectLikeAvion($filtre){
			return $this->unModele->selectLikeAvion($filtre);
		}
		public function deleteAvion($idAvion){
			$this->unModele->deleteAvion($idAvion);
		}
		public function selectWhereAvion($idAvion){
			return $this->unModele->selectWhereAvion($idAvion);
		}
		public function updateAvion($tab){
			$this->unModele->updateAvion($tab);
		}
		public function insertAeroport($tab){
			$this->unModele->insertAeroport($tab);
		}
		public function selectAllAeroports(){
			return $this->unModele->selectAllAeroports();
		}
		public function selectLikeAeroport($filtre){
			return $this->unModele->selectLikeAeroport($filtre);
		}
		public function deleteAeroport($idAeroport){
			$this->unModele->deleteAeroport($idAeroport);
		}
		public function selectWhereAeroport($idAeroport){
			return $this->unModele->selectWhereAeroport($idAeroport);
		}
		public function updateAeroport($tab){
			$this->unModele->updateAeroport($tab);
		}
		public function insertReservation($tab){
			$this->unModele->insertReservation($tab);
		}
		public function selectAllReservations(){
			return $this->unModele->selectAllReservations();
		}
		public function selectLikeReservation($filtre){
			return $this->unModele->selectLikeReservation($filtre);
		}
		public function deleteReservation($idReservation){
			$this->unModele->deleteReservation($idReservation);
		}
		public function selectWhereReservation($idReservation){
			return $this->unModele->selectWhereReservation($idReservation);
		}
		public function updateReservation($tab){
			$this->unModele->updateReservation($tab);
		}
		public function insertVol($tab){
			$this->unModele->insertVol($tab);
		}
		public function selectAllVols(){
			return $this->unModele->selectAllVols();
		}
		public function selectLikeVol($filtre){
			return $this->unModele->selectLikeVol($filtre);
		}
		public function deleteVol($idVol){
			$this->unModele->deleteVol($idVol);
		}
		public function selectWhereVol($idVol){
			return $this->unModele->selectWhereVol($idVol);
		}
		public function updateVol($tab){
			$this->unModele->updateVol($tab);
		}
		public function insertMembresEquipage($tab){
			$this->unModele->insertMembresEquipage($tab);
		}
		public function selectAllMembresEquipage(){
			return $this->unModele->selectAllMembresEquipage();
		}
		public function selectLikeMembresEquipage($filtre){
			return $this->unModele->selectLikeMembresEquipage($filtre);
		}
		public function deleteMembreEquipage($idMembreEquipage){
			$this->unModele->deleteMembreEquipage($idMembreEquipage);
		}
		public function selectWhereMembreEquipage($idMembreEquipage){
			return $this->unModele->selectWhereMembreEquipage($idMembreEquipage);
		}
		public function updateMembreEquipage($tab){
			$this->unModele->updateMembreEquipage($tab);
		}
		public function count ($table){
			return $this->unModele->count($table);
		}
		public function verifConnexion ($email, $mdp){
			return $this->unModele->verifConnexion($email, $mdp);
		}
	}
?>