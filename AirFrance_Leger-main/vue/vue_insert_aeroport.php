<h3>Ajout/Modification d'un aéroport</h3>
<form method="post">
    <table>
        <tr>
            <td>Nom de l'aéroport</td>
            <td><input type="text" name="Nom" value="<?= ($lAeroport != null) ? $lAeroport['Nom'] : '' ?>"></td>
        </tr>
        <tr>
            <td>Localisation de l'aéroport</td>
            <td><input type="text" name="Localisation" value="<?= ($lAeroport != null) ? $lAeroport['Localisation'] : '' ?>"></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" <?= ($lAeroport != null) ? 'name="Modifier" value="Modifier"' : 'name="Valider" value="Valider"' ?>>
                <input type="reset" name="Annuler" value="Annuler">
            </td>
        </tr>
        <?= ($lAeroport != null) ? '<input type="hidden" name="ID_Aeroport" value="'.$lAeroport['ID_Aeroport'].'">' : '' ?>
    </table>
</form>
