<h2>Gestion des passagers</h2>

<?php
	if (isset($_SESSION['role']) && $_SESSION['role']=='admin'){
		$lePassager=null;
		if(isset($_GET['action']) && isset($_GET['idpassager'])){
			$idpassager = $_GET['idpassager'];
			$action= $_GET['action'];
			switch($action){
				case "sup" : $unControleur->deletePassager($idpassager); 
				break;
				case "edit" : 
				$lePassager=$unControleur->selectWherePassager($idpassager);
				break;
				case "voir" :
				// Traitement pour voir les détails du passager si nécessaire
				break;
			}
		}
		require_once("vue/vue_insert_passager.php");
		if (isset($_POST['Valider'])){
			$unControleur->insertPassager($_POST);
		}
		if (isset($_POST['Modifier'])){
			$unControleur->updatePassager($_POST);
			header("Location: index.php?page=2");
		}
	}

	if (isset($_POST['filtrer'])){
		$filtre = $_POST['filtrer'];
		$lesPassagers=$unControleur->selectLikePassager($filtre);
		require_once("vue/vue_select_passager.php");
	} else {
		
		$lesPassagers= $unControleur->selectAllPassagers ();
		require_once("vue/vue_select_passager.php");
	}
	$nb=$unControleur->count("passagers")['nb'];
	echo "<br>Nombre de passager(s): ".$nb;
?>
