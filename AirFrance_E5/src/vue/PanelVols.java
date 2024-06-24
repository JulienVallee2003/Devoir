package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.Aeroports;
import controleur.Avions;
import controleur.Controleur;
import controleur.Tableau;
import controleur.Vols;

public class PanelVols extends PanelPrincipal implements ActionListener {

    private JPanel panelFormAjouter = new JPanel();
    private JTextField txtNumVol = new JTextField();
    private JTextField txtDateDepart = new JTextField();
    private JTextField txtDateArrivee = new JTextField();
    private JTextField txtHeureDepart = new JTextField();
    private JTextField txtHeureArrivee = new JTextField();
    private JComboBox<String> txtIdAeroportDepart = new JComboBox<String>();
    private JComboBox<String> txtIdAeroportArrive = new JComboBox<String>();
    private JComboBox<String> txtIdAvion = new JComboBox<String>();
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");

    private JTable tableVols;
    private JScrollPane uneScroll;
    private Tableau unTableau;

    private JLabel lblNombreVols = new JLabel("Nombre de vols: 0");

    private JTextField txtFiltre = new JTextField();
    private JComboBox<String> cbTri = new JComboBox<>(new String[]{"Croissant", "Décroissant"});
    private JComboBox<String> cbFiltreAeroportDepart = new JComboBox<>();
    private JComboBox<String> cbFiltreAeroportArrivee = new JComboBox<>();
    private JComboBox<String> cbFiltreAvion = new JComboBox<>();
    private JButton btFiltrer = new JButton("Filtrer");

    public PanelVols() {
        super("Gestion des Vols");

        // Construction du panel ajouter vol
        this.panelFormAjouter.setBackground(new Color(240, 248, 255));
        this.panelFormAjouter.setBounds(1140, 170, 330, 500);
        this.panelFormAjouter.setLayout(new GridLayout(10, 2));
        this.panelFormAjouter.add(new JLabel("Numero de vol :"));
        this.panelFormAjouter.add(this.txtNumVol);
        this.panelFormAjouter.add(new JLabel("Date de départ :"));
        this.panelFormAjouter.add(this.txtDateDepart);
        this.panelFormAjouter.add(new JLabel("Date d'arrivée :"));
        this.panelFormAjouter.add(this.txtDateArrivee);
        this.panelFormAjouter.add(new JLabel("Heure de départ :"));
        this.panelFormAjouter.add(this.txtHeureDepart);
        this.panelFormAjouter.add(new JLabel("Heure d'arrivée :"));
        this.panelFormAjouter.add(this.txtHeureArrivee);
        this.panelFormAjouter.add(new JLabel("Aeroport de départ :"));
        this.panelFormAjouter.add(this.txtIdAeroportDepart);
        this.panelFormAjouter.add(new JLabel("Aeroport d'arrivée :"));
        this.panelFormAjouter.add(this.txtIdAeroportArrive);
        this.panelFormAjouter.add(new JLabel("Avion :"));
        this.panelFormAjouter.add(this.txtIdAvion);
        this.panelFormAjouter.add(this.btAnnuler);
        this.panelFormAjouter.add(this.btEnregistrer);
        this.panelFormAjouter.add(this.lblNombreVols);
        this.add(this.panelFormAjouter);

        // Remplir les comboBox
        this.remplirCBXAeroportDepart();
        this.remplirCBXAeroportArrive();
        this.remplirCBXAvion();

        // Construction de la table des vols
        String[] entetes = {"ID_Vol", "N° Vol", "Date_Depart", "Heure_Depart", "Aeroport_Depart", "Date_Arrivee", "Heure_Arrivee", "Aeroport_Arrive", "Avion"};
        this.unTableau = new Tableau(this.obtenirDonnees("", "", "", "", ""), entetes);
        this.tableVols = new JTable(this.unTableau);
        this.uneScroll = new JScrollPane(this.tableVols);
        this.uneScroll.setBounds(40, 170, 1000, 500);
        this.add(this.uneScroll);

        // Ajout des champs de filtre en bas du tableau
        this.txtFiltre.setBounds(40, 680, 200, 25);
        this.add(this.txtFiltre);
        this.cbTri.setBounds(250, 680, 100, 25);
        this.add(this.cbTri);
        this.cbFiltreAeroportDepart.setBounds(360, 680, 150, 25);
        this.add(this.cbFiltreAeroportDepart);
        this.cbFiltreAeroportArrivee.setBounds(520, 680, 150, 25);
        this.add(this.cbFiltreAeroportArrivee);
        this.cbFiltreAvion.setBounds(680, 680, 150, 25);
        this.add(this.cbFiltreAvion);
        this.btFiltrer.setBounds(840, 680, 100, 25);
        this.add(this.btFiltrer);
        this.btFiltrer.addActionListener(this);

        // Ajout des listeners
        this.btAnnuler.addActionListener(this);
        this.btEnregistrer.addActionListener(this);
        this.cbTri.addActionListener(this);

        // Suppression d'un vol sur double clic
        this.tableVols.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne, idVol;
                if (e.getClickCount() >= 2) {
                    numLigne = tableVols.getSelectedRow();
                    idVol = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
                    int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer le Vol ?", "Suppression du Vol", JOptionPane.YES_NO_OPTION);
                    if (reponse == 0) {
                        // Suppression en BDD
                        Controleur.deleteVol(idVol);
                        // Actualiser affichage
                        unTableau.setDonnees(obtenirDonnees("", "", "", "", ""));
                    }
                } else if (e.getClickCount() >= 1) {
                    numLigne = tableVols.getSelectedRow();
                    txtNumVol.setText(unTableau.getValueAt(numLigne, 1).toString());
                    txtDateDepart.setText(unTableau.getValueAt(numLigne, 2).toString());
                    txtHeureDepart.setText(unTableau.getValueAt(numLigne, 3).toString());
                    txtIdAeroportDepart.setSelectedItem(unTableau.getValueAt(numLigne, 4).toString());
                    txtDateArrivee.setText(unTableau.getValueAt(numLigne, 5).toString());
                    txtHeureArrivee.setText(unTableau.getValueAt(numLigne, 6).toString());
                    txtIdAeroportArrive.setSelectedItem(unTableau.getValueAt(numLigne, 7).toString());
                    txtIdAvion.setSelectedItem(unTableau.getValueAt(numLigne, 8).toString());
                    btEnregistrer.setText("Modifier");
                }
            }
        });

        mettreAJourNombreVols();  // Initialisation du nombre de vols
    }

    public Object[][] obtenirDonnees(String filtre, String order, String aeroportDepart, String aeroportArrive, String avion) {
        ArrayList<Vols> lesVols = Controleur.selectAllVols(filtre, order, aeroportDepart, aeroportArrive, avion);
        Object[][] matrice = new Object[lesVols.size()][9];
        int i = 0;
        for (Vols unVol : lesVols) {
            matrice[i][0] = unVol.getIdVol();
            matrice[i][1] = unVol.getNumVol();
            matrice[i][2] = unVol.getDateDepart();
            matrice[i][3] = unVol.getHeureDepart();
            matrice[i][4] = unVol.getAeroportDepart();
            matrice[i][5] = unVol.getDateArrivee();
            matrice[i][6] = unVol.getHeureArrivee();
            matrice[i][7] = unVol.getAeroportArrivee();
            matrice[i][8] = unVol.getAvion();
            i++;
        }

        lblNombreVols.setText("Nombre de vols: " + lesVols.size());
        return matrice;
    }

    // Méthodes pour remplir les JComboBox avec les noms des aéroports et des avions
    public void remplirCBXAeroportDepart() {
        this.txtIdAeroportDepart.removeAllItems();
        this.cbFiltreAeroportDepart.removeAllItems();
        ArrayList<Aeroports> lesAeroports = Controleur.selectAllAeroports();
        this.cbFiltreAeroportDepart.addItem("Tous");
        for (Aeroports unAeroport : lesAeroports) {
            this.txtIdAeroportDepart.addItem(unAeroport.getNomAeroport());
            this.cbFiltreAeroportDepart.addItem(unAeroport.getNomAeroport());
        }
    }

    public void remplirCBXAeroportArrive() {
        this.txtIdAeroportArrive.removeAllItems();
        this.cbFiltreAeroportArrivee.removeAllItems();
        ArrayList<Aeroports> lesAeroports = Controleur.selectAllAeroports();
        this.cbFiltreAeroportArrivee.addItem("Tous");
        for (Aeroports unAeroport : lesAeroports) {
            this.txtIdAeroportArrive.addItem(unAeroport.getNomAeroport());
            this.cbFiltreAeroportArrivee.addItem(unAeroport.getNomAeroport());
        }
    }

    public void remplirCBXAvion() {
        this.txtIdAvion.removeAllItems();
        this.cbFiltreAvion.removeAllItems();
        ArrayList<Avions> lesAvions = Controleur.selectAllAvions();
        this.cbFiltreAvion.addItem("Tous");
        for (Avions unAvion : lesAvions) {
            this.txtIdAvion.addItem(unAvion.getModele());
            this.cbFiltreAvion.addItem(unAvion.getModele());
        }
    }

    private void mettreAJourNombreVols() {
        this.lblNombreVols.setText("Nombre de vols: " + this.unTableau.getRowCount());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Enregistrer")) {
            String numVol = this.txtNumVol.getText();
            LocalDate dateDepart = LocalDate.parse(this.txtDateDepart.getText());
            LocalDate dateArrivee = LocalDate.parse(this.txtDateArrivee.getText());
            LocalTime heureDepart = LocalTime.parse(this.txtHeureDepart.getText());
            LocalTime heureArrivee = LocalTime.parse(this.txtHeureArrivee.getText());

            String aeroportDepart = this.txtIdAeroportDepart.getSelectedItem().toString();
            String aeroportArrivee = this.txtIdAeroportArrive.getSelectedItem().toString();

            if (aeroportDepart.equals(aeroportArrivee)) {
                JOptionPane.showMessageDialog(null, "L'aéroport d'arrivée doit être différent de l'aéroport de départ", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String avion = this.txtIdAvion.getSelectedItem().toString();

            Vols nouveauVol = new Vols(0, numVol, dateDepart, heureDepart, aeroportDepart, dateArrivee, heureArrivee, aeroportArrivee, avion);

            Controleur.insertVol(nouveauVol);

            // Actualiser affichage après insertion
            this.unTableau.setDonnees(this.obtenirDonnees("", "", "", "", ""));
            mettreAJourNombreVols();

            // Affichage d'un message de confirmation
            JOptionPane.showMessageDialog(this, "Insertion effectuée");

        } else if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Modifier")) {
            String numVol = this.txtNumVol.getText();
            LocalDate dateDepart = LocalDate.parse(this.txtDateDepart.getText());
            LocalDate dateArrivee = LocalDate.parse(this.txtDateArrivee.getText());
            LocalTime heureDepart = LocalTime.parse(this.txtHeureDepart.getText());
            LocalTime heureArrivee = LocalTime.parse(this.txtHeureArrivee.getText());

            String aeroportDepart = this.txtIdAeroportDepart.getSelectedItem().toString();
            String aeroportArrivee = this.txtIdAeroportArrive.getSelectedItem().toString();

            if (aeroportDepart.equals(aeroportArrivee)) {
                JOptionPane.showMessageDialog(null, "L'aéroport d'arrivée doit être différent de l'aéroport de départ", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String avion = this.txtIdAvion.getSelectedItem().toString();

            // Récupération de l'ID du vol à modifier
            int numLigne = this.tableVols.getSelectedRow();
            int idVol = Integer.parseInt(this.unTableau.getValueAt(numLigne, 0).toString());

            // Instanciation du vol à modifier
            Vols volModifie = new Vols(idVol, numVol, dateDepart, heureDepart, aeroportDepart, dateArrivee, heureArrivee, aeroportArrivee, avion);

            // Appel de la méthode de modification dans le contrôleur
            Controleur.updateVol(volModifie);

            // Actualisation des données des vols dans le tableau
            this.unTableau.setDonnees(this.obtenirDonnees("", "", "", "", ""));
            mettreAJourNombreVols();

            // Réinitialisation des champs et du bouton d'action
            this.txtNumVol.setText("");
            this.txtDateDepart.setText("");
            this.txtDateArrivee.setText("");
            this.txtHeureDepart.setText("");
            this.txtHeureArrivee.setText("");
            this.txtIdAeroportDepart.setSelectedIndex(0);
            this.txtIdAeroportArrive.setSelectedIndex(0);
            this.txtIdAvion.setSelectedIndex(0);
            this.btEnregistrer.setText("Enregistrer");

            // Affichage d'un message de confirmation
            JOptionPane.showMessageDialog(this, "Modification du vol effectuée avec succès.");
        } else if (e.getSource() == this.btAnnuler) {
            this.txtNumVol.setText("");
            this.txtDateDepart.setText("");
            this.txtDateArrivee.setText("");
            this.txtHeureDepart.setText("");
            this.txtHeureArrivee.setText("");
            this.txtIdAeroportDepart.setSelectedIndex(0);
            this.txtIdAeroportArrive.setSelectedIndex(0);
            this.txtIdAvion.setSelectedIndex(0);
            this.btEnregistrer.setText("Enregistrer");
        } else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            String order = this.cbTri.getSelectedItem().toString().equals("Croissant") ? "ASC" : "DESC";
            String aeroportDepart = this.cbFiltreAeroportDepart.getSelectedItem().toString().equals("Tous") ? "" : this.cbFiltreAeroportDepart.getSelectedItem().toString();
            String aeroportArrivee = this.cbFiltreAeroportArrivee.getSelectedItem().toString().equals("Tous") ? "" : this.cbFiltreAeroportArrivee.getSelectedItem().toString();
            String avion = this.cbFiltreAvion.getSelectedItem().toString().equals("Tous") ? "" : this.cbFiltreAvion.getSelectedItem().toString();
            this.unTableau.setDonnees(this.obtenirDonnees(filtre, order, aeroportDepart, aeroportArrivee, avion));
            mettreAJourNombreVols();
        } else if (e.getSource() == this.cbTri) {
            String filtre = this.txtFiltre.getText();
            String order = this.cbTri.getSelectedItem().toString().equals("Croissant") ? "ASC" : "DESC";
            String aeroportDepart = this.cbFiltreAeroportDepart.getSelectedItem().toString().equals("Tous") ? "" : this.cbFiltreAeroportDepart.getSelectedItem().toString();
            String aeroportArrivee = this.cbFiltreAeroportArrivee.getSelectedItem().toString().equals("Tous") ? "" : this.cbFiltreAeroportArrivee.getSelectedItem().toString();
            String avion = this.cbFiltreAvion.getSelectedItem().toString().equals("Tous") ? "" : this.cbFiltreAvion.getSelectedItem().toString();
            this.unTableau.setDonnees(this.obtenirDonnees(filtre, order, aeroportDepart, aeroportArrivee, avion));
            mettreAJourNombreVols();
        }
    }
}
