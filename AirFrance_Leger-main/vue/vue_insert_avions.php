<h3>Ajout/Modification d'un avion</h3>
<form method="post">
    <table>
        <tr>
            <td>Modèle de l'avion</td>
            <td><input type="text" name="Modele" value="<?= ($lAvion != null) ? $lAvion['Modele'] : '' ?>"></td>
        </tr>
        <tr>
            <td>Nombre de places</td>
            <td><input type="number" name="NombrePlaces" value="<?= ($lAvion != null) ? $lAvion['NombrePlaces'] : '' ?>"></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" <?= ($lAvion != null) ? 'name="Modifier" value="Modifier"' : 'name="Valider" value="Valider"' ?>>
                <input type="reset" name="Annuler" value="Annuler">
            </td>
        </tr>
        <?= ($lAvion != null) ? '<input type="hidden" name="ID_Avion" value="'.$lAvion['ID_Avion'].'">' : '' ?>
    </table>
</form>

