<h2>Gestion des réservations</h2>
<?php
	$lReservation = null;
	$lesPassagers= $unControleur->selectAllPassagers ();
	$lesVols= $unControleur->selectAllVols ();
	require_once("vue/vue_insert_reservation.php");
	if (isset($_POST['Valider'])){
		$unControleur->insertReservation($_POST);
	}
	$lesReservations= $unControleur->selectAllReservations ();
	require_once("vue/vue_select_reservation.php");
?>