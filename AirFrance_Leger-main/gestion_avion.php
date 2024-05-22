<h2> Gestion des avions </h2><br /><br />

<?php


$lAvion = null;
if(isset($_GET['action']) && isset($_GET['idAvion'])){
    $idAvion = $_GET['idAvion']; 
    $action = $_GET['action']; 

    switch ($action){
        case "sup" : 
            $unControleur->deleteAvion($idAvion); 
            break; 
        case "edit" : 
            $lAvion = $unControleur->selectWhereAvion($idAvion);  
            break;
        case "voir" :
            $detailsAvion = $unControleur->selectWhereAvion($idAvion);
            break;  
    }
}


require_once ("vue/vue_insert_avions.php"); 


if(isset($_POST['Valider'])){
    $unControleur->insertAvion($_POST);
}


if (isset($_POST['Modifier'])){
    $unControleur->updateAvion($_POST); 
    header("Location: index.php?page=3");
}

if (isset($_POST['Annuler'])){
    $lAeroport = null;
    header("Location: index.php?page=3");
}

// Filtrage des avions
if(isset($_POST['Filtrer'])){
    $filtre = $_POST['filtre']; 
    $lesAvions = $unControleur->selectLikeAvion($filtre); 
} else {
    $lesAvions = $unControleur->selectAllAvions();
}

// Affichage du nombre d'avions
$nbAvions = $unControleur->count("avions")['nb']; 
echo "<br> Nombre d'avions : ".$nbAvions; 

// Inclusion de la vue pour afficher la liste des avions
require_once("vue/vue_select_avion.php");

// Affichage des détails de l'avion si disponible

?>
