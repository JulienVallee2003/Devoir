<h3>Ajout/Modification d'un membre d'équipage</h3>
<form method="post">
    <table>
        <tr>
            <td>ID Membre d'équipage</td>
            <td><input type="text" name="ID_MembreEquipage" value="<?= ($lMenbre != null) ? $lMenbre['ID_MembreEquipage'] : '' ?>"></td>
        </tr>
        <tr>
            <td>ID Personne</td>
            <td><input type="text" name="ID_Personne" value="<?= ($lMenbre != null) ? $lMenbre['ID_Personne'] : '' ?>"></td>
        </tr>
        <tr>
            <td>Rôle</td>
            <td>
                <select name="Role">
                    <option value="Pilote" <?= ($lMenbre != null && $lMenbre['Role'] == 'Pilote') ? 'selected' : '' ?>>Pilote</option>
                    <option value="Copilote" <?= ($lMenbre != null && $lMenbre['Role'] == 'Copilote') ? 'selected' : '' ?>>Copilote</option>
                    <option value="Hôtesse de l'air" <?= ($lMenbre != null && $lMenbre['Role'] == 'Hôtesse de l\'air') ? 'selected' : '' ?>>Hôtesse de l'air</option>
                    <option value="Steward" <?= ($lMenbre != null && $lMenbre['Role'] == 'Steward') ? 'selected' : '' ?>>Steward</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Date d'embauche</td>
            <td><input type="date" name="DateEmbauche" value="<?= ($lMenbre != null) ? $lMenbre['DateEmbauche'] : '' ?>"></td>
        </tr>
        <tr>
            <td>ID Vol</td>
            <td><input type="text" name="ID_Vol" value="<?= ($lMenbre != null) ? $lMenbre['ID_Vol'] : '' ?>"></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" <?= ($lMenbre != null) ? 'name="Modifier" value="Modifier"' : 'name="Valider" value="Valider"' ?>>
                <input type="reset" name="Annuler" value="Annuler">
            </td>
        </tr>
        <?= ($lMenbre != null) ? '<input type="hidden" name="ID_MembreEquipage" value="'.$lMenbre['ID_MembreEquipage'].'">' : '' ?>
    </table>
</form>
