<h2> Gestion des réservations </h2><br /><br />

<?php

// Vérification des actions sur les réservations
$lReservation = null;
$lesPassagers= $unControleur->selectAllPassagers ();
$lesVols= $unControleur->selectAllVols ();
if(isset($_GET['action']) && isset($_GET['idreservation'])){
    $idReservation = $_GET['idreservation']; 
    $action = $_GET['action']; 

    switch ($action){
        case "sup" : 
            $unControleur->deleteReservation($idReservation); 
            break; 
        case "edit" : 
            $lReservation = $unControleur->selectWhereReservation($idReservation);  
            break;
        case "voir" :
            $detailsReservation = $unControleur->selectWhereReservation($idReservation);
            break;  
    }
}

// Inclusion de la vue pour insérer une réservation
require_once ("vue/vue_insert_reservation.php"); 

// Insertion d'une nouvelle réservation
if(isset($_POST['Valider'])){
    $unControleur->insertReservation($_POST);
}

// Mise à jour d'une réservation
if (isset($_POST['Modifier'])){
    $unControleur->updateReservation($_POST); 
    header("Location: index.php?page=5");
}

if (isset($_POST['Annuler'])){
    $lAeroport = null;
    header("Location: index.php?page=5");
}

// Filtrage des réservations
if(isset($_POST['Filtrer'])){
    $filtre = $_POST['filtre']; 
    $lesReservations = $unControleur->selectLikeReservation($filtre); 
} else {
    $lesReservations = $unControleur->selectAllReservations();
}

// Affichage du nombre de réservations
$nbReservations = $unControleur->count("reservations")['nb']; 
echo "<br> Nombre de réservations : ".$nbReservations; 

// Inclusion de la vue pour afficher la liste des réservations
require_once("vue/vue_select_reservation.php");

// Affichage des détails de la réservation si disponible

?>
