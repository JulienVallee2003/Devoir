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
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/styles.css">
	<style>
		.container {
			margin-top: 30px;
		}
		.card {
			margin-bottom: 20px;
		}
		.card-img-top {
			width: 100px;
			height: 100px;
			object-fit: cover;
		}
		.card-title {
			text-align: center;
			margin-top: 10px;
		}
		.footer {
			
			
			width: 100%;
			background-color: #f8f9fa;
			padding: 10px 0;
			text-align: center;
		}
	</style>
</head>
<body>

	<div class="container">
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
			
			echo "<div class='container'>
				<div class='row'>
					<div class='col-md-9 offset-md-2 text-center'>
					
						<h1>Bienvenue sur le portail de gestion Air France</h1>
						<p class='lead'>Bonjour ". $_SESSION['prenom'] . " accéder aux fonctionnalités de gestion des vols, des passagers, des aéroports et plus encore.</p>
					</div>
				</div>
			  </div>";
			
			echo '
				
				<div class="row justify-content-center">
					<div class="col-md-3">
						<div class="card text-center">
							<div class="card-body">
								<a href="index.php?page=1" title="Accueil">
									<img src="image/accueil.png" class="card-img-top" alt="Accueil">
								</a>
								<h5 class="card-title">Accueil</h5>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="card text-center">
							<div class="card-body">
								<a href="index.php?page=2" title="Gestion des aéroports">
									<img src="image/aeroport.jpg" class="card-img-top" alt="Gestion des aéroports">
								</a>
								<h5 class="card-title">Gestion des aéroports</h5>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="card text-center">
							<div class="card-body">
								<a href="index.php?page=3" title="Gestion des avions">
									<img src="image/avion.png" class="card-img-top" alt="Gestion des avions">
								</a>
								<h5 class="card-title">Gestion des avions</h5>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="card text-center">
							<div class="card-body">
								<a href="index.php?page=4" title="Gestion des vols">
									<img src="image/vols.png" class="card-img-top" alt="Gestion des vols">
								</a>
								<h5 class="card-title">Gestion des vols</h5>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="card text-center">
							<div class="card-body">
								<a href="index.php?page=5" title="Réservations">
									<img src="image/reserve.png" class="card-img-top" alt="Réservations">
								</a>
								<h5 class="card-title">Réservations</h5>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="card text-center">
							<div class="card-body">
								<a href="index.php?page=6" title="Gestion des passagers">
									<img src="image/passager.png" class="card-img-top" alt="Gestion des passagers">
								</a>
								<h5 class="card-title">Gestion des passagers</h5>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="card text-center">
							<div class="card-body">
								<a href="index.php?page=7" title="Gestion des membres équipage">
									<img src="image/menbre.png" class="card-img-top" alt="Gestion des membres équipage">
								</a>
								<h5 class="card-title">Gestion des membres équipage</h5>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="card text-center">
							<div class="card-body">
								<a href="index.php?page=8" title="Déconnexion">
									<img src="image/deconnexion.png" class="card-img-top" alt="Déconnexion">
								</a>
								<h5 class="card-title">Déconnexion</h5>
							</div>
						</div>
					</div>
				</div>
				';
				
		
			
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
echo '
	</div><br /><br />
	<footer class="footer">
		<div class="container">
			<span class="text-muted">© 2024 Air France. Tous droits réservés.</span>
		</div>
	</footer>
</body>
</html>'; 
?>