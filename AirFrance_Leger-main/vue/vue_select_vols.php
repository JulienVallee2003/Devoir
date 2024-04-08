<h3>Liste des vols</h3>
<form method="post">
    <p>Filtrer par : </p><input type="text" name="filtre">
    <input type="submit" name="filtrer" value="Filtrer">
</form>
<br>
<table border="1">
    <tr>
        <td>ID Vol</td>
        <td>Numéro de vol</td>
        <td>Date de départ</td>
        <td>Heure de départ</td>
        <td>Aéroport de départ</td>
        <td>Date d'arrivée</td>
        <td>Heure d'arrivée</td>
        <td>Aéroport d'arrivée</td>
        <td>Avion</td>
		<td>Opération</td>
    </tr>
    <?php
    if (isset($lesVols)) {
        foreach ($lesVols as $unVol) {
            echo "<tr>";
            echo "<td>".$unVol['ID_Vol']."</td>";
            echo "<td>".$unVol['NumeroVol']."</td>";
            echo "<td>".$unVol['DateDepart']."</td>";
            echo "<td>".$unVol['HeureDepart']."</td>";
            echo "<td>".$unVol['AeroportDepart']."</td>";
            echo "<td>".$unVol['DateArrivee']."</td>";
            echo "<td>".$unVol['HeureArrivee']."</td>";
            echo "<td>".$unVol['AeroportArrivee']."</td>";
            echo "<td>".$unVol['Avion']."</td>";
            echo "<td>";
            echo "<a href='index.php?page=2&action=sup&idvol=".$unVol['ID_Vol']."'><img src='image/supprimer.png' height='30' width='30'></a>";
            echo "<a href='index.php?page=2&action=edit&idvol=".$unVol['ID_Vol']."'><img src='image/editer.png' height='30' width='30'></a>";
            echo "<a href='index.php?page=2&action=voir&idvol=".$unVol['ID_Vol']."'><img src='image/voire.png' height='30' width='30'></a>";
            echo "</td>";
            echo "</tr>";
        }
    }
    ?>
</table>
