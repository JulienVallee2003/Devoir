package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.Controleur;
import controleur.Passager;
import controleur.Reservations;
import controleur.Tableau;
import controleur.Vols;

public class PanelReservations extends PanelPrincipal implements ActionListener {

    private JComboBox<String> txtNomPassager = new JComboBox<String>();
    private JComboBox<String> txtNumeroVol = new JComboBox<String>();
    private JTextField txtDateReservation = new JTextField();
    private JTextField txtSiegeAttribue = new JTextField();
    
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");
    private JButton btTriAsc = new JButton("Tri Ascendant");
    private JButton btTriDesc = new JButton("Tri Descendant");
    
    private JTable tableReservations;
    private JScrollPane uneScroll;
    private Tableau unTableau;

    private JTextField txtFilter = new JTextField();
    private JComboBox<String> cbFilterField = new JComboBox<>(new String[] {"Nom", "Prenom", "Numero de Vol", "Siege attribue"});
    private JButton btFilter = new JButton("Filtrer");
    
    private JPanel panelForm  = new JPanel(); 
    private JLabel nbReservations = new JLabel("Nombre de réservations : 0");

    public PanelReservations() {
        super("Gestion des Réservations");
        
        // Construction du panel pour ajouter une réservation
        this.panelForm.setBackground(new Color(240, 248, 255));
        this.panelForm.setBounds(1140, 170, 330, 500);
        this.panelForm.setLayout(new GridLayout(7, 2));
        this.panelForm.add(new JLabel("Nom Passager :"));
        this.panelForm.add(this.txtNomPassager);
        this.panelForm.add(new JLabel("Numéro de Vol :"));
        this.panelForm.add(this.txtNumeroVol);
        this.panelForm.add(new JLabel("Date de réservation :"));
        this.panelForm.add(this.txtDateReservation);
        this.panelForm.add(new JLabel("Siège attribué :"));
        this.panelForm.add(this.txtSiegeAttribue);
        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btEnregistrer);
        this.panelForm.add(this.btTriAsc);
        this.panelForm.add(this.btTriDesc);
        this.panelForm.add(nbReservations);  // Ajout du label pour le nombre de réservations
        this.add(this.panelForm);
        
        // Construction de la table des réservations
        String[] entetes = {"ID Réservation", "Nom Passager", "Prénom Passager", "Numéro de Vol", "Date de réservation", "Siège attribué"};
        this.unTableau = new Tableau(this.obtenirDonnees("", ""), entetes);
        this.tableReservations = new JTable(this.unTableau);
        this.uneScroll = new JScrollPane(this.tableReservations);
        this.uneScroll.setBounds(40, 170, 1000, 500);
        this.add(this.uneScroll);
        
        // Remplissage des JComboBox avec les données disponibles
        remplirCBXPassagers();
        remplirCBXVols();
        
        // Ajout des ActionListener aux boutons
        this.btEnregistrer.addActionListener(this);
        this.btAnnuler.addActionListener(this);
        this.btTriAsc.addActionListener(this);
        this.btTriDesc.addActionListener(this);
        
        // Ajout du MouseListener à la table des réservations
        this.tableReservations.addMouseListener(new MouseListener() {
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
                int numLigne, idResa;
                if (e.getClickCount() >= 2) {
                    numLigne = tableReservations.getSelectedRow();
                    idResa = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
                    int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer la réservation ?", "Suppression de la réservation", JOptionPane.YES_NO_OPTION);
                    if (reponse == 0) {
                        // Suppression en BDD
                        Controleur.deleteReservation(idResa);
                        // Actualisation de l'affichage
                        unTableau.setDonnees(obtenirDonnees("", ""));
                        mettreAJourNbReservations();
                    }
                } else if (e.getClickCount() >= 1) {
                    numLigne = tableReservations.getSelectedRow();
                    txtNomPassager.setSelectedItem(unTableau.getValueAt(numLigne, 1).toString() + " " + unTableau.getValueAt(numLigne, 2).toString());
                    txtNumeroVol.setSelectedItem(unTableau.getValueAt(numLigne, 3).toString());
                    txtDateReservation.setText(unTableau.getValueAt(numLigne, 4).toString());
                    txtSiegeAttribue.setText(unTableau.getValueAt(numLigne, 5).toString());
                    btEnregistrer.setText("Modifier");
                }
            }
        });
        // Ajout du champ de filtre et du bouton de filtre
        this.txtFilter.setBounds(40, 680, 200, 25);
        this.add(this.txtFilter);
        this.cbFilterField.setBounds(250, 680, 150, 25);
        this.add(this.cbFilterField);

        this.btFilter.setBounds(410, 680, 100, 25);
        this.add(this.btFilter);

        this.btFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filter = txtFilter.getText();
                String filterField = cbFilterField.getSelectedItem().toString();
                unTableau.setDonnees(obtenirDonnees(filter, filterField));
                mettreAJourNbReservations();
            }
        });

        mettreAJourNbReservations(); // Initialisation du nombre de réservations
    }

    public void remplirCBXPassagers() {
        this.txtNomPassager.removeAllItems();
        ArrayList<Passager> lesPassagers = Controleur.selectAllPassagers(""); 
        for (Passager unPassager : lesPassagers) {
            this.txtNomPassager.addItem(unPassager.getNom() + " " + unPassager.getPrenom());
        }
    }

    public void remplirCBXVols() {
        this.txtNumeroVol.removeAllItems(); 
        ArrayList<Vols> lesVols = Controleur.selectAllVols(""); 
        for (Vols unVol : lesVols) {
            this.txtNumeroVol.addItem(unVol.getNumVol());
        }
    }

    public Object[][] obtenirDonnees(String filtre, String filterField) {
        ArrayList<Reservations> lesReservations = Controleur.selectAllReservations(filtre, filterField);
        Object[][] matrice = new Object[lesReservations.size()][6];
        int i = 0;
        for (Reservations uneReservation : lesReservations) {
            matrice[i][0] = uneReservation.getIdReservation();
            matrice[i][1] = uneReservation.getNomPassager();
            matrice[i][2] = uneReservation.getPrenomPassager();
            matrice[i][3] = uneReservation.getNumeroVol();
            matrice[i][4] = uneReservation.getDateReservation();
            matrice[i][5] = uneReservation.getSiegeAttribue();
            i++;
        }
        return matrice;
    }

    private void mettreAJourNbReservations() {
        this.nbReservations.setText("Nombre de réservations : " + this.unTableau.getRowCount());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Enregistrer")) {
            String nomPrenomPassager = this.txtNomPassager.getSelectedItem().toString();
            String[] splitNomPrenom = nomPrenomPassager.split(" ");
            String nomPassager = splitNomPrenom[0];
            String prenomPassager = splitNomPrenom[1];
            String numeroVol = this.txtNumeroVol.getSelectedItem().toString();
            LocalDate dateReservation = LocalDate.parse(this.txtDateReservation.getText());
            String siegeAttribue = this.txtSiegeAttribue.getText();

            // Vérification de la validité de la date de réservation
            if (!Controleur.isReservationDateValid(getIdVol(numeroVol), dateReservation)) {
                JOptionPane.showMessageDialog(this, "Erreur : La date de réservation doit être antérieure à la date de départ du vol.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Reservations nouvelleReservation = new Reservations(0, getIdPassager(nomPassager, prenomPassager), getIdVol(numeroVol), dateReservation, siegeAttribue);
            Controleur.insertReservation(nouvelleReservation);

            this.unTableau.setDonnees(this.obtenirDonnees("", ""));
            mettreAJourNbReservations();
            JOptionPane.showMessageDialog(this, "Réservation enregistrée avec succès !");

        } else if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Modifier")) {
            String nomPrenomPassager = this.txtNomPassager.getSelectedItem().toString();
            String[] splitNomPrenom = nomPrenomPassager.split(" ");
            String nomPassager = splitNomPrenom[0];
            String prenomPassager = splitNomPrenom[1];
            String numeroVol = this.txtNumeroVol.getSelectedItem().toString();
            LocalDate dateReservation = LocalDate.parse(this.txtDateReservation.getText());
            String siegeAttribue = this.txtSiegeAttribue.getText();

            // Vérification de la validité de la date de réservation
            if (!Controleur.isReservationDateValid(getIdVol(numeroVol), dateReservation)) {
                JOptionPane.showMessageDialog(this, "Erreur : La date de réservation doit être antérieure à la date de départ du vol.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numLigne = this.tableReservations.getSelectedRow();
            int idReservation = Integer.parseInt(this.unTableau.getValueAt(numLigne, 0).toString());

            Reservations nouvelleReservation = new Reservations(idReservation, getIdPassager(nomPassager, prenomPassager), getIdVol(numeroVol), dateReservation, siegeAttribue);
            Controleur.updateReservation(nouvelleReservation);

            this.unTableau.setDonnees(this.obtenirDonnees("", ""));
            mettreAJourNbReservations();

            this.txtDateReservation.setText("");
            this.txtSiegeAttribue.setText("");
            this.btEnregistrer.setText("Enregistrer");
            JOptionPane.showMessageDialog(this, "Modification de la réservation effectuée avec succès.");

        } else if (e.getSource() == this.btAnnuler) {
            this.txtDateReservation.setText("");
            this.txtSiegeAttribue.setText("");
            this.btEnregistrer.setText("Enregistrer");
        } else if (e.getSource() == this.btTriAsc) {
            this.unTableau.setDonnees(obtenirDonneesTri("ASC"));
            mettreAJourNbReservations();
        } else if (e.getSource() == this.btTriDesc) {
            this.unTableau.setDonnees(obtenirDonneesTri("DESC"));
            mettreAJourNbReservations();
        }
    }

    private Object[][] obtenirDonneesTri(String order) {
        ArrayList<Reservations> lesReservations = Controleur.selectAllReservationsTri(order);
        Object[][] matrice = new Object[lesReservations.size()][6];
        int i = 0;
        for (Reservations uneReservation : lesReservations) {
            matrice[i][0] = uneReservation.getIdReservation();
            matrice[i][1] = uneReservation.getNomPassager();
            matrice[i][2] = uneReservation.getPrenomPassager();
            matrice[i][3] = uneReservation.getNumeroVol();
            matrice[i][4] = uneReservation.getDateReservation();
            matrice[i][5] = uneReservation.getSiegeAttribue();
            i++;
        }
        return matrice;
    }

    private int getIdPassager(String nom, String prenom) {
        try {
            return Controleur.getIdPassager(nom, prenom);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int getIdVol(String numeroVol) {
        try {
            return Controleur.getIdVol(numeroVol);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
