<h3>Liste des réservations</h3>
<form method="post">
    <p>Filtrer par : </p><input type="text" name="filtre">
    <input type="submit" name="filtrer" value="Filtrer">
</form>
<br>
<table border="1">
    <tr>
        <td>ID Réservation</td>
        <td>ID Passager</td>
        <td>ID Vol</td>
        <td>Date de réservation</td>
        <td>Siège attribué</td>
		<td>Opération</td>
    </tr>
    <?php
    if (isset($lesReservations)) {
        foreach ($lesReservations as $uneReservation) {
            echo "<tr>";
            echo "<td>".$uneReservation['ID_Reservation']."</td>";
            echo "<td>".$uneReservation['ID_Passager']."</td>";
            echo "<td>".$uneReservation['ID_Vol']."</td>";
            echo "<td>".$uneReservation['DateReservation']."</td>";
            echo "<td>".$uneReservation['SiegeAttribue']."</td>";
            echo "<td>";
            echo "<a href='index.php?page=2&action=sup&idreservation=".$uneReservation['ID_Reservation']."'><img src='image/supprimer.png' height='30' width='30'></a>";
            echo "<a href='index.php?page=2&action=edit&idreservation=".$uneReservation['ID_Reservation']."'><img src='image/editer.png' height='30' width='30'></a>";
            echo "<a href='index.php?page=2&action=voir&idreservation=".$uneReservation['ID_Reservation']."'><img src='image/voire.png' height='30' width='30'></a>";
            echo "</td>";
            echo "</tr>";
        }
    }
    ?>
</table>
