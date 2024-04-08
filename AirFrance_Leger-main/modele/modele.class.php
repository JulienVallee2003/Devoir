<?php
	class Modele {
		private $unPDO;
		public function __construct(){
			try {
				$url="mysql:host=localhost;dbname=airfrance";
				$user="root";
				$mdp="";
				$this->unPDO= new PDO ($url, $user, $mdp);
			}
			catch(PDOException $exp){
				echo "Erreur de connexion: ".$exp->getMessage();
			}
		}
		public function insertPassagers($tab){
			$requete = "INSERT INTO passagers (ID_Personne, NumPasseport) VALUES (:idPersonne, :numPasseport)";
			$donnees = array(
				":idPersonne" => $tab['ID_Personne'],
				":numPasseport" => $tab['NumPasseport']
			);
			$insert = $this->unPDO->prepare($requete);
			$insert->execute($donnees);
		}
	
		public function selectAllPassagers(){
			$requete = "SELECT * FROM passagers";
			$select = $this->unPDO->prepare($requete);
			$select->execute();
			return $select->fetchAll();
		}
	
		public function selectLikePassager($filtre){
			$requete = "SELECT * FROM passagers WHERE NumPasseport LIKE :filtre";
			$donnees = array(":filtre" => "%".$filtre."%");
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetchAll();
		}
	
		public function deletePassager($idPassager){
			$requete = "DELETE FROM passagers WHERE ID_Passager = :idPassager";
			$donnees = array(":idPassager" => $idPassager);
			$delete = $this->unPDO->prepare($requete);
			$delete->execute($donnees);
		}
	
		public function selectWherePassager($idPassager){
			$requete = "SELECT * FROM passagers WHERE ID_Passager = :idPassager";
			$donnees = array(":idPassager" => $idPassager);
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetch();
		}
	
		public function updatePassager($tab){
			$requete = "UPDATE passagers SET ID_Personne = :idPersonne, NumPasseport = :numPasseport WHERE ID_Passager = :idPassager";
			$donnees = array(
				":idPassager" => $tab['ID_Passager'],
				":idPersonne" => $tab['ID_Personne'],
				":numPasseport" => $tab['NumPasseport']
			);
			$update = $this->unPDO->prepare($requete);
			$update->execute($donnees);
		}
		public function insertAeroport($tab){
			$requete = "INSERT INTO aeroports (Nom, Localisation) VALUES (:nom, :localisation)";
			$donnees = array(
				":nom" => $tab['Nom'],
				":localisation" => $tab['Localisation']
			);
			$insert = $this->unPDO->prepare($requete);
			$insert->execute($donnees);
		}
	
		public function selectAllAeroports(){
			$requete = "SELECT * FROM aeroports";
			$select = $this->unPDO->prepare($requete);
			$select->execute();
			return $select->fetchAll();
		}
	
		public function selectLikeAeroport($filtre){
			$requete = "SELECT * FROM aeroports WHERE Nom LIKE :filtre OR Localisation LIKE :filtre";
			$donnees = array(":filtre" => "%".$filtre."%");
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetchAll();
		}
	
		public function deleteAeroport($idAeroport){
			$requete = "DELETE FROM aeroports WHERE ID_Aeroport = :idAeroport";
			$donnees = array(":idAeroport" => $idAeroport);
			$delete = $this->unPDO->prepare($requete);
			$delete->execute($donnees);
		}
	
		public function selectWhereAeroport($idAeroport){
			$requete = "SELECT * FROM aeroports WHERE ID_Aeroport = :idAeroport";
			$donnees = array(":idAeroport" => $idAeroport);
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetch();
		}
	
		public function updateAeroport($tab){
			$requete = "UPDATE aeroports SET Nom = :nom, Localisation = :localisation WHERE ID_Aeroport = :idAeroport";
			$donnees = array(
				":idAeroport" => $tab['ID_Aeroport'],
				":nom" => $tab['Nom'],
				":localisation" => $tab['Localisation']
			);
			$update = $this->unPDO->prepare($requete);
			$update->execute($donnees);
		}
		public function insertAvion($tab){
			$requete = "INSERT INTO avions (Modele, NombrePlaces) VALUES (:modele, :nombrePlaces)";
			$donnees = array(
				":modele" => $tab['Modele'],
				":nombrePlaces" => $tab['NombrePlaces']
			);
			$insert = $this->unPDO->prepare($requete);
			$insert->execute($donnees);
		}
	
		public function selectAllAvions(){
			$requete = "SELECT * FROM avions";
			$select = $this->unPDO->prepare($requete);
			$select->execute();
			return $select->fetchAll();
		}
	
		public function selectLikeAvion($filtre){
			$requete = "SELECT * FROM avions WHERE Modele LIKE :filtre";
			$donnees = array(":filtre" => "%".$filtre."%");
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetchAll();
		}
	
		public function deleteAvion($idAvion){
			$requete = "DELETE FROM avions WHERE ID_Avion = :idAvion";
			$donnees = array(":idAvion" => $idAvion);
			$delete = $this->unPDO->prepare($requete);
			$delete->execute($donnees);
		}
	
		public function selectWhereAvion($idAvion){
			$requete = "SELECT * FROM avions WHERE ID_Avion = :idAvion";
			$donnees = array(":idAvion" => $idAvion);
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetch();
		}
	
		public function updateAvion($tab){
			$requete = "UPDATE avions SET Modele = :modele, NombrePlaces = :nombrePlaces WHERE ID_Avion = :idAvion";
			$donnees = array(
				":idAvion" => $tab['ID_Avion'],
				":modele" => $tab['Modele'],
				":nombrePlaces" => $tab['NombrePlaces']
			);
			$update = $this->unPDO->prepare($requete);
			$update->execute($donnees);
		}
		public function insertMembresEquipage($tab){
			$requete = "INSERT INTO membresequipage (ID_Personne, Role, DateEmbauche, ID_Vol) VALUES (:idPersonne, :role, :dateEmbauche, :idVol)";
			$donnees = array(
				":idPersonne" => $tab['ID_Personne'],
				":role" => $tab['Role'],
				":dateEmbauche" => $tab['DateEmbauche'],
				":idVol" => $tab['ID_Vol']
			);
			$insert = $this->unPDO->prepare($requete);
			$insert->execute($donnees);
		}
	
		public function selectAllMembresEquipage(){
			$requete = "SELECT * FROM membresequipage";
			$select = $this->unPDO->prepare($requete);
			$select->execute();
			return $select->fetchAll();
		}
	
		public function selectLikeMembresEquipage($filtre){
			$requete = "SELECT * FROM membresequipage WHERE Role LIKE :filtre";
			$donnees = array(":filtre" => "%".$filtre."%");
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetchAll();
		}
	
		public function deleteMembreEquipage($idMembreEquipage){
			$requete = "DELETE FROM membresequipage WHERE ID_MembreEquipage = :idMembreEquipage";
			$donnees = array(":idMembreEquipage" => $idMembreEquipage);
			$delete = $this->unPDO->prepare($requete);
			$delete->execute($donnees);
		}
	
		public function selectWhereMembreEquipage($idMembreEquipage){
			$requete = "SELECT * FROM membresequipage WHERE ID_MembreEquipage = :idMembreEquipage";
			$donnees = array(":idMembreEquipage" => $idMembreEquipage);
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetch();
		}
	
		public function updateMembreEquipage($tab){
			$requete = "UPDATE membresequipage SET ID_Personne = :idPersonne, Role = :role, DateEmbauche = :dateEmbauche, ID_Vol = :idVol WHERE ID_MembreEquipage = :idMembreEquipage";
			$donnees = array(
				":idMembreEquipage" => $tab['ID_MembreEquipage'],
				":idPersonne" => $tab['ID_Personne'],
				":role" => $tab['Role'],
				":dateEmbauche" => $tab['DateEmbauche'],
				":idVol" => $tab['ID_Vol']
			);
			$update = $this->unPDO->prepare($requete);
			$update->execute($donnees);
		}
		public function insertReservation($tab){
			$requete = "INSERT INTO reservations (ID_Passager, ID_Vol, DateReservation, SiegeAttribue) VALUES (:idPassager, :idVol, :dateReservation, :siegeAttribue)";
			$donnees = array(
				":idPassager" => $tab['ID_Passager'],
				":idVol" => $tab['ID_Vol'],
				":dateReservation" => $tab['DateReservation'],
				":siegeAttribue" => $tab['SiegeAttribue']
			);
			$insert = $this->unPDO->prepare($requete);
			$insert->execute($donnees);
		}
	
		public function selectAllReservations(){
			$requete = "SELECT * FROM reservations";
			$select = $this->unPDO->prepare($requete);
			$select->execute();
			return $select->fetchAll();
		}
	
		public function selectLikeReservation($filtre){
			$requete = "SELECT * FROM reservations WHERE SiegeAttribue LIKE :filtre";
			$donnees = array(":filtre" => "%".$filtre."%");
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetchAll();
		}
	
		public function deleteReservation($idReservation){
			$requete = "DELETE FROM reservations WHERE ID_Reservation = :idReservation";
			$donnees = array(":idReservation" => $idReservation);
			$delete = $this->unPDO->prepare($requete);
			$delete->execute($donnees);
		}
	
		public function selectWhereReservation($idReservation){
			$requete = "SELECT * FROM reservations WHERE ID_Reservation = :idReservation";
			$donnees = array(":idReservation" => $idReservation);
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetch();
		}
	
		public function updateReservation($tab){
			$requete = "UPDATE reservations SET ID_Passager = :idPassager, ID_Vol = :idVol, DateReservation = :dateReservation, SiegeAttribue = :siegeAttribue WHERE ID_Reservation = :idReservation";
			$donnees = array(
				":idReservation" => $tab['ID_Reservation'],
				":idPassager" => $tab['ID_Passager'],
				":idVol" => $tab['ID_Vol'],
				":dateReservation" => $tab['DateReservation'],
				":siegeAttribue" => $tab['SiegeAttribue']
			);
			$update = $this->unPDO->prepare($requete);
			$update->execute($donnees);
		}
		public function insertVol($tab){
			$requete = "INSERT INTO vols (NumeroVol, DateDepart, HeureDepart, AeroportDepart, DateArrivee, HeureArrivee, AeroportArrivee, Avion) VALUES (:numeroVol, :dateDepart, :heureDepart, :aeroportDepart, :dateArrivee, :heureArrivee, :aeroportArrivee, :avion)";
			$donnees = array(
				":numeroVol" => $tab['NumeroVol'],
				":dateDepart" => $tab['DateDepart'],
				":heureDepart" => $tab['HeureDepart'],
				":aeroportDepart" => $tab['AeroportDepart'],
				":dateArrivee" => $tab['DateArrivee'],
				":heureArrivee" => $tab['HeureArrivee'],
				":aeroportArrivee" => $tab['AeroportArrivee'],
				":avion" => $tab['Avion']
			);
			$insert = $this->unPDO->prepare($requete);
			$insert->execute($donnees);
		}
	
		public function selectAllVols(){
			$requete = "SELECT * FROM vols";
			$select = $this->unPDO->prepare($requete);
			$select->execute();
			return $select->fetchAll();
		}
	
		public function selectLikeVol($filtre){
			$requete = "SELECT * FROM vols WHERE NumeroVol LIKE :filtre";
			$donnees = array(":filtre" => "%".$filtre."%");
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetchAll();
		}
	
		public function deleteVol($idVol){
			$requete = "DELETE FROM vols WHERE ID_Vol = :idVol";
			$donnees = array(":idVol" => $idVol);
			$delete = $this->unPDO->prepare($requete);
			$delete->execute($donnees);
		}
	
		public function selectWhereVol($idVol){
			$requete = "SELECT * FROM vols WHERE ID_Vol = :idVol";
			$donnees = array(":idVol" => $idVol);
			$select = $this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetch();
		}
	
		public function updateVol($tab){
			$requete = "UPDATE vols SET NumeroVol = :numeroVol, DateDepart = :dateDepart, HeureDepart = :heureDepart, AeroportDepart = :aeroportDepart, DateArrivee = :dateArrivee, HeureArrivee = :heureArrivee, AeroportArrivee = :aeroportArrivee, Avion = :avion WHERE ID_Vol = :idVol";
			$donnees = array(
				":idVol" => $tab['ID_Vol'],
				":numeroVol" => $tab['NumeroVol'],
				":dateDepart" => $tab['DateDepart'],
				":heureDepart" => $tab['HeureDepart'],
				":aeroportDepart" => $tab['AeroportDepart'],
				":dateArrivee" => $tab['DateArrivee'],
				":heureArrivee" => $tab['HeureArrivee'],
				":aeroportArrivee" => $tab['AeroportArrivee'],
				":avion" => $tab['Avion']
			);
			$update = $this->unPDO->prepare($requete);
			$update->execute($donnees);
		}
		public function count($table){
			$requete="select count(*) as nb from ".$table;
			$select=$this->unPDO->prepare($requete);
			$select->execute();
			return $select->fetch();
		}
		public function verifConnexion($email, $mdp){
			$requete="select * from admin where Email=:email and MotDePasse=:mdp ";
			$donnees=array(":email"=>$email, ":mdp"=>$mdp);
			$select=$this->unPDO->prepare($requete);
			$select->execute($donnees);
			return $select->fetch();
		}
	}
?>