<h3>Ajout/Modification d'un membre d'équipage</h3>
<form method="post">
    <table>
        <tr>
            <td>ID Membre d'équipage</td>
            <td><input type="text" name="ID_MembreEquipage" value="<?= ($lMembre != null) ? $lMembre['ID_MembreEquipage'] : '' ?>"></td>
        </tr>
        <tr>
            <td>ID Personne</td>
            <td><input type="text" name="ID_Personne" value="<?= ($lMembre != null) ? $lMembre['ID_Personne'] : '' ?>"></td>
        </tr>
        <tr>
            <td>Rôle</td>
            <td>
                <select name="Role">
                    <option value="Pilote" <?= ($lMembre != null && $lMembre['Role'] == 'Pilote') ? 'selected' : '' ?>>Pilote</option>
                    <option value="Copilote" <?= ($lMembre != null && $lMembre['Role'] == 'Copilote') ? 'selected' : '' ?>>Copilote</option>
                    <option value="Hôtesse de l'air" <?= ($lMembre != null && $lMembre['Role'] == 'Hôtesse de l\'air') ? 'selected' : '' ?>>Hôtesse de l'air</option>
                    <option value="Steward" <?= ($lMembre != null && $lMembre['Role'] == 'Steward') ? 'selected' : '' ?>>Steward</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Date d'embauche</td>
            <td><input type="date" name="DateEmbauche" value="<?= ($lMembre != null) ? $lMembre['DateEmbauche'] : '' ?>"></td>
        </tr>
        <tr>
            <td>ID Vol</td>
            <td><input type="text" name="ID_Vol" value="<?= ($lMembre != null) ? $lMembre['ID_Vol'] : '' ?>"></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" <?= ($lMembre != null) ? 'name="Modifier" value="Modifier"' : 'name="Valider" value="Valider"' ?>>
                <input type="reset" name="Annuler" value="Annuler">
            </td>
        </tr>
        <?= ($lMembre != null) ? '<input type="hidden" name="ID_MembreEquipage" value="'.$lMembre['ID_MembreEquipage'].'">' : '' ?>
    </table>
</form>
