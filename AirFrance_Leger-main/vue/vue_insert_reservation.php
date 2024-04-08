<h3>Ajout/Modification d'une réservation</h3>
<form method="post">
    <table>
        <tr>
            <td>Passager</td>
            <td>
                <select name="ID_Passager">
                    <?php foreach ($lesPassagers as $passager) : ?>
                        <option value="<?= $passager['ID_Passager'] ?>" <?= ($lReservation != null && $lReservation['ID_Passager'] == $passager['ID_Passager']) ? 'selected' : '' ?>>
                            <?= $passager['NumPasseport']?>
                        </option>
                    <?php endforeach; ?>
                </select>
            </td>
        </tr>
        <tr>
            <td>Vol</td>
            <td>
                <select name="ID_Vol">
                    <?php foreach ($lesVols as $vol) : ?>
                        <option value="<?= $vol['ID_Vol'] ?>" <?= ($lReservation != null && $lReservation['ID_Vol'] == $vol['ID_Vol']) ? 'selected' : '' ?>>
                            <?= $vol['NumeroVol'] ?>
                        </option>
                    <?php endforeach; ?>
                </select>
            </td>
        </tr>
        <tr>
            <td>Date de réservation</td>
            <td><input type="date" name="DateReservation" value="<?= ($lReservation != null) ? $lReservation['DateReservation'] : '' ?>"></td>
        </tr>
        <tr>
            <td>Siège attribué</td>
            <td><input type="text" name="SiegeAttribue" value="<?= ($lReservation != null) ? $lReservation['SiegeAttribue'] : '' ?>"></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" <?= ($lReservation != null) ? 'name="Modifier" value="Modifier"' : 'name="Valider" value="Valider"' ?>>
                <input type="reset" name="Annuler" value="Annuler">
            </td>
        </tr>
        <?= ($lReservation != null) ? '<input type="hidden" name="ID_Reservation" value="'.$lReservation['ID_Reservation'].'">' : '' ?>
    </table>
</form>
