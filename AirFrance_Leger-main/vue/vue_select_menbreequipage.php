<h3>Liste des membres d'équipage</h3>
<form method="post">
    <p>Filtrer par : </p><input type="text" name="filtre">
    <input type="submit" name="filtrer" value="Filtrer">
</form>
<br>
<table border="1">
    <tr>
        <td>ID Membre Equipage</td>
        <td>ID Personne</td>
        <td>Rôle</td>
        <td>Date d'embauche</td>
        <td>ID Vol</td>
        <td>Opération</td>
    </tr>
    <?php
    if (isset($lesMembresEquipage)) {
        foreach ($lesMembresEquipage as $unMembreEquipage) {
            echo "<tr>";
            echo "<td>".$unMembreEquipage['ID_MembreEquipage']."</td>";
            echo "<td>".$unMembreEquipage['ID_Personne']."</td>";
            echo "<td>".$unMembreEquipage['Role']."</td>";
            echo "<td>".$unMembreEquipage['DateEmbauche']."</td>";
            echo "<td>".$unMembreEquipage['ID_Vol']."</td>";
            echo "<td>";
            echo "<a href='index.php?page=2&action=sup&idmembreequipage=".$unMembreEquipage['ID_MembreEquipage']."'><img src='image/supprimer.png' height='30' width='30'></a>";
            echo "<a href='index.php?page=2&action=edit&idmembreequipage=".$unMembreEquipage['ID_MembreEquipage']."'><img src='image/editer.png' height='30' width='30'></a>";
            echo "<a href='index.php?page=2&action=voir&idmembreequipage=".$unMembreEquipage['ID_MembreEquipage']."'><img src='image/voire.png' height='30' width='30'></a>";
            echo "</td>";
            echo "</tr>";
        }
    }
    ?>
</table>
