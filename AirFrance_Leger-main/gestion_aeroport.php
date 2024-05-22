<?php
echo '<h2> Gestion des aéroports </h2><br /><br />';
// Vérification du rôle de l'utilisateur

	$lAeroport = null;
    // Vérification des actions sur les aéroports
    if(isset($_GET['action']) && isset($_GET['idAeroport'])){
        $idAeroport = $_GET['idAeroport']; 
        $action = $_GET['action']; 

        switch ($action){
            case "sup" : 
                
                $unControleur->deleteAeroport($idAeroport); 
                
                break; 
            case "edit" : 
                
                $lAeroport = $unControleur->selectWhereAeroport($idAeroport);  
                break;
            case "voir" :
                
                $detailsAeroport = $unControleur->selectWhereAeroport($idAeroport);
                break;  
        }
    }

    
    require_once ("vue/vue_insert_aeroport.php"); 

    
    if(isset($_POST['Valider'])){
        $unControleur->insertAeroport($_POST);
    }

    
    if (isset($_POST['Modifier'])){
        $unControleur->updateAeroport($_POST); 
        header("Location: index.php?page=2");
    }

    if (isset($_POST['Annuler'])){
        $lAeroport = null;
        header("Location: index.php?page=2");
    }

// Filtrage des aéroports
if(isset($_POST['Filtrer'])){
    $filtre = $_POST['filtre']; 
    $lesAeroports = $unControleur->selectLikeAeroport($filtre); 
} else {
    $lesAeroports = $unControleur->selectAllAeroports();
}

// Affichage du nombre d'aéroports
$nb = $unControleur->count("aeroports")['nb']; 
echo "<br> Nombre d'aéroports : ".$nb; 

// Inclusion de la vue pour afficher la liste des aéroports
require_once("vue/vue_select_aeroports.php");
?>
