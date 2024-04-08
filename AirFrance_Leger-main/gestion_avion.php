<h2>Gestion des avions</h2>

<?php
$lAvion = null;
	require_once("vue/vue_insert_avions.php");
	if (isset($_POST['Valider'])){
		$unControleur->insertAvion($_POST);
	}
	$lesAvions= $unControleur->selectAllAvions ();
	require_once("vue/vue_select_avion.php");
?>
