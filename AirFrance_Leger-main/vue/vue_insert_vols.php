<?php
// Initialisation de $leVol à null si elle n'est pas définie
if (!isset($leVol)) {
    $leVol = null;
}
?>

<h3>Ajout/Modification d'un vol</h3>
<form method="post">
    <table>
        <tr>
            <td>Numéro de vol</td>
            <td><input type="text" name="NumeroVol" value="<?= ($leVol != null) ? $leVol['NumeroVol'] : '' ?>"></td>
        </tr>
        <tr>
            <td>Date de départ</td>
            <td><input type="date" name="DateDepart" value="<?= ($leVol != null) ? $leVol['DateDepart'] : '' ?>"></td>
        </tr>
        <tr>
            <td>Heure de départ</td>
            <td><input type="time" name="HeureDepart" value="<?= ($leVol != null) ? $leVol['HeureDepart'] : '' ?>"></td>
        </tr>
        <tr>
            <td>Aéroport de départ</td>
            <td>
                <select name="AeroportDepart">
                    <?php foreach ($lesAeroports as $aeroport) : ?>
                        <option value="<?= $aeroport['ID_Aeroport'] ?>" <?= ($leVol != null && $leVol['AeroportDepart'] == $aeroport['ID_Aeroport']) ? 'selected' : '' ?>>
                            <?= $aeroport['Nom'] ?>
                        </option>
                    <?php endforeach; ?>
                </select>
            </td>
        </tr>
        <tr>
            <td>Date d'arrivée</td>
            <td><input type="date" name="DateArrivee" value="<?= ($leVol != null) ? $leVol['DateArrivee'] : '' ?>"></td>
        </tr>
        <tr>
            <td>Heure d'arrivée</td>
            <td><input type="time" name="HeureArrivee" value="<?= ($leVol != null) ? $leVol['HeureArrivee'] : '' ?>"></td>
        </tr>
        <tr>
            <td>Aéroport d'arrivée</td>
            <td>
                <select name="AeroportArrivee">
                    <?php foreach ($lesAeroports as $aeroport) : ?>
                        <option value="<?= $aeroport['ID_Aeroport'] ?>" <?= ($leVol != null && $leVol['AeroportArrivee'] == $aeroport['ID_Aeroport']) ? 'selected' : '' ?>>
                            <?= $aeroport['Nom'] ?>
                        </option>
                    <?php endforeach; ?>
                </select>
            </td>
        </tr>
        <tr>
            <td>Avion</td>
            <td>
                <select name="Avion">
                    <?php foreach ($lesAvions as $avion) : ?>
                        <option value="<?= $avion['ID_Avion'] ?>" <?= ($leVol != null && $leVol['Avion'] == $avion['ID_Avion']) ? 'selected' : '' ?>>
                            <?= $avion['Modele'] ?>
                        </option>
                    <?php endforeach; ?>
                </select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" <?= ($leVol != null) ? 'name="Modifier" value="Modifier"' : 'name="Valider" value="Valider"' ?>>
                <input type="reset" name="Annuler" value="Annuler">
            </td>
        </tr>
        <?= ($leVol != null) ? '<input type="hidden" name="ID_Vol" value="'.$leVol['ID_Vol'].'">' : '' ?>
    </table>
</form>
