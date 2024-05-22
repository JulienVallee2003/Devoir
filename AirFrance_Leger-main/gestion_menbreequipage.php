<h2> Gestion des membres de l'équipage </h2><br /><br />

<?php

// Vérification des actions sur les membres d'équipage
$lMembre = null;
if(isset($_GET['action']) && isset($_GET['idmembreequipage'])){
    $idMembreEquipage = $_GET['idmembreequipage']; 
    $action = $_GET['action']; 

    switch ($action){
        case "sup" : 
            $unControleur->deleteMembreEquipage($idMembreEquipage); 
            break; 
        case "edit" : 
            $lMembre = $unControleur->selectWhereMembreEquipage($idMembreEquipage);  
            break;
        case "voir" :
            $detailsMembreEquipage = $unControleur->selectWhereMembreEquipage($idMembreEquipage);
            break;  
    }
}


require_once ("vue/vue_insert_membreequipage.php"); 


if(isset($_POST['Valider'])){
    $unControleur->insertMembresEquipage($_POST);
}

// Mise à jour d'un membre d'équipage
if (isset($_POST['Modifier'])){
    $unControleur->updateMembreEquipage($_POST); 
    header("Location: index.php?page=7");
}

if (isset($_POST['Annuler'])){
    $lAeroport = null;
    header("Location: index.php?page=7");
}

// Filtrage des membres d'équipage
if(isset($_POST['Filtrer'])){
    $filtre = $_POST['filtre']; 
    $lesMembresEquipage = $unControleur->selectLikeMembresEquipage($filtre); 
} else {
    $lesMembresEquipage = $unControleur->selectAllMembresEquipage();
}

// Affichage du nombre de membres d'équipage
$nbMembresEquipage = $unControleur->count("membresequipage")['nb']; 
echo "<br> Nombre de membres d'équipage : ".$nbMembresEquipage; 

// Inclusion de la vue pour afficher la liste des membres d'équipage
require_once("vue/vue_select_membreequipage.php");

// Affichage des détails du membre d'équipage si disponible

?>
