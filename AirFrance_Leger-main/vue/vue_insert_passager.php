
<h3>Ajout/Modification d'un passager</h3>
<form method="post">
    <table>
        <tr>
            <td>ID Passager</td>
            <td><input type="text" name="ID_Passager" value="<?= ($lePassager != null) ? $lePassager['ID_Passager'] : '' ?>"></td>
        </tr>
        <tr>
            <td>ID Personne</td>
            <td><input type="text" name="ID_Personne" value="<?= ($lePassager != null) ? $lePassager['ID_Personne'] : '' ?>"></td>
        </tr>
        <tr>
            <td>Numéro de passeport</td>
            <td><input type="text" name="NumPasseport" value="<?= ($lePassager != null) ? $lePassager['NumPasseport'] : '' ?>"></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" <?= ($lePassager != null) ? 'name="Modifier" value="Modifier"' : 'name="Valider" value="Valider"' ?>>
                <input type="reset" name="Annuler" value="Annuler">
            </td>
        </tr>
        <?= ($lePassager != null) ? '<input type="hidden" name="ID_Passager" value="'.$lePassager['ID_Passager'].'">' : '' ?>
    </table>
</form>