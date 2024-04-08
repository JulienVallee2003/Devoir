<h3>Liste des passagers</h3>
<form method="post">
    <p>Filtrer par : </p><input type="text" name="filtre">
    <input type="submit" name="filtrer" value="Filtrer">
</form>
<br>
<table border="1">
    <tr>
        <td>Id Passager</td>
        <td>Numéro de Passeport</td>
        <td>ID Personne</td>
		<td>Opération</td>
    </tr>
    <?php
    if (isset($lesPassagers)) {
        foreach ($lesPassagers as $passager) {
            echo "<tr>";
            echo "<td>".$passager['ID_Passager']."</td>";
            echo "<td>".$passager['NumPasseport']."</td>";
            echo "<td>".$passager['ID_Personne']."</td>";
            if (isset($_SESSION['role']) && $_SESSION['role'] == 'admin') {
                echo "<td>";
                echo "<a href='index.php?page=2&action=sup&idPassager=".$passager['ID_Passager']."'><img src='image/supprimer.png' height='30' width='30'></a>";
                echo "<a href='index.php?page=2&action=edit&idPassager=".$passager['ID_Passager']."'><img src='image/editer.png' height='30' width='30'></a>";
                echo "<a href='index.php?page=2&action=voir&idPassager=".$passager['ID_Passager']."'><img src='image/voire.png' height='30' width='30'></a>";
                echo "</td>";
            }
            echo "</tr>";
        }
    }
    ?>
</table>
