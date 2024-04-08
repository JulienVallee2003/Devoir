<h2>Gestion des membres de l'équipage</h2>
<?php
	$lMenbre = null;
	require_once("vue/vue_insert_menbreequipage.php");
	if (isset($_POST['Valider'])){
		$unControleur->insertMembresEquipage($_POST);
	}
	$lesMenbresequipages= $unControleur->selectAllMembresEquipage ();
	require_once("vue/vue_select_menbreequipage.php");
?>