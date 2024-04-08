<?php
	session_start();
	require_once("controleur/controleur.class.php");
	$unControleur= new Controleur; 
?>
<!DOCTYPE html>
<html>
<head>
	<title>Air France</title>
	<meta charset="utf-8">
</head>
<body>
	<center>
		<?php
		if(!isset($_SESSION['email'])){
			require_once ("vue/vue_connexion.php");
		}
		if (isset($_POST['seConnecter'])){
			$email = $_POST['email'];
			$mdp = $_POST['mdp'];
			$unUser = $unControleur->verifConnexion($email, $mdp);
	
			if ($unUser !=null){
				$_SESSION['prenom'] = $unUser['Prenom'];
				$_SESSION['email'] = $unUser['Email'];
				//header("Location: index.php?page=1");

			} else {
				echo "<br>Votre identifiant ou mot de passe est incorrect";
			}
		}

		if  (isset($_SESSION['email'])){
			echo '
				<h1>Site de gestion de Air France</h1>
				<a href="index.php?page=1">
					<img src="image/accueil.png" height="100" width="100" alt="">
				</a>
				<a href="index.php?page=2">
					<img src="image/aeroport.jpg" height="100" width="100" alt="Gestion des passagers">
				</a>
				<a href="index.php?page=3">
					<img src="image/avion.png" height="100" width="100" alt="Gestion des produits">
				</a>
				<a href="index.php?page=4">
					<img src="image/vols.png" height="100" width="100" alt="Gestion des techniciens">
				</a>
				<a href="index.php?page=5">
					<img src="image/reserve.png" height="100" width="100" alt="Gestion des interventions">
				</a>
				<a href="index.php?page=6">
					<img src="image/passager.png" height="100" width="100" alt="Gestion des techniciens">
				</a>
				<a href="index.php?page=7">
					<img src="image/menbre.png" height="100" width="100" alt="Gestion des interventions">
				</a>
				<a href="index.php?page=8">
					<img src="image/deconnexion.png" height="100" width="100" alt="Déconnexion">
				</a>
				<h2>Bienvenue chez Air France</h2>
				</center>';
				echo "<p style='text-align:center;'> Bonjour ". $_SESSION['prenom'] . ".</p>";
		}
		if(isset($_GET['page'])){
			$page= $_GET['page'];
		} else {
			$page = 1; //Page par défaut= index.php
		}
		switch ($page){
			case 1 : require_once ("index.php"); break;
			case 2 : require_once ("gestion_aeroport.php"); break;
			case 3 : require_once ("gestion_avion.php"); break;
			case 4 : require_once ("gestion_vols.php"); break;
			case 5 : require_once ("gestion_reservation.php"); break;
			case 6 : require_once ("gestion_passager.php"); break;
			case 7 : require_once ("gestion_menbreequipage.php"); break;
			case 8 : session_destroy();
			unset($_SESSION['email']);
			header("Location: index.php?page=1");
			break;
		}
		?>
	</center>
</body>
</html>
