package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.Controleur;
import controleur.Passager;
import controleur.Tableau;

public class PanelPassagers extends PanelPrincipal implements ActionListener {

    private JPanel panelFormAjouter = new JPanel();
    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtDateNaissance = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtTelephone = new JTextField();
    private JTextField txtNumPasseport = new JTextField();
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");

    private JTable tablePassagers;
    private JScrollPane uneScroll;
    private Tableau unTableau;

    private JPanel panelFiltre = new JPanel();
    private JButton btFiltrer = new JButton("Filtrer");
    private JTextField txtFiltre = new JTextField();

    private JLabel nbPassagers = new JLabel(); 

    public PanelPassagers() {
        super("Gestion des passagers");
        this.setLayout(null);

        // Construction du panel ajouter passager
        this.panelFormAjouter.setBackground(new Color(240, 248, 255));
        this.panelFormAjouter.setBounds(1140, 170, 350, 400);
        this.panelFormAjouter.setLayout(new GridLayout(9, 2));
        ajouterChampFormulaire("Nom Passager :", this.txtNom);
        ajouterChampFormulaire("Prenom Passager :", this.txtPrenom);
        ajouterChampFormulaire("Date de naissance :", this.txtDateNaissance);
        ajouterChampFormulaire("Adresse :", this.txtAdresse);
        ajouterChampFormulaire("Email Passager :", this.txtEmail);
        ajouterChampFormulaire("Téléphone du Passager :", this.txtTelephone);
        ajouterChampFormulaire("Numéro de passeport :", this.txtNumPasseport);
        panelFormAjouter.add(btAnnuler);
        panelFormAjouter.add(btEnregistrer);
        panelFormAjouter.add(nbPassagers);  // Ajout du label pour le nombre de passagers
        this.add(this.panelFormAjouter);

        // Construction de la table des passagers
        String[] entetes = {"ID_Passager", "Nom", "Prenom", "DateNaissance", "Adresse", "Email", "Téléphone", "Numéro de passeport"};
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
        this.tablePassagers = new JTable(this.unTableau);
        this.uneScroll = new JScrollPane(this.tablePassagers);
        this.uneScroll.setBounds(40, 170, 1000, 500);
        this.add(this.uneScroll);

        this.nbPassagers.setText("Nombre de passagers : " + this.unTableau.getRowCount());

        // Construction du panel de filtrage
        this.panelFiltre.setBackground(new Color(240, 248, 255));
        this.panelFiltre.setBounds(1150, 600, 300, 40);
        this.panelFiltre.setLayout(new GridLayout(1, 3));
        this.panelFiltre.add(new JLabel("Filtrer par :"));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);
        this.btFiltrer.addActionListener(this);

        // Ajout des listeners
        this.btAnnuler.addActionListener(this);
        this.btEnregistrer.addActionListener(this);

        // Suppression d'un passager sur double clic
        this.tablePassagers.addMouseListener(new MouseListener() {
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
                int numLigne, idPassager;
                if (e.getClickCount() >= 2) {
                    numLigne = tablePassagers.getSelectedRow();
                    idPassager = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
                    int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer le Passager ?", "Suppression du passager", JOptionPane.YES_NO_OPTION);
                    if (reponse == 0) {
                        // Suppression en BDD
                        Controleur.deletePassager(idPassager);
                        // Actualiser affichage
                        unTableau.setDonnees(obtenirDonnees(""));
                        nbPassagers.setText("Nombre de passagers : " + unTableau.getRowCount());
                        viderChamps();
                    }
                } else if (e.getClickCount() >= 1) {
                    numLigne = tablePassagers.getSelectedRow();
                    txtNom.setText(unTableau.getValueAt(numLigne, 1).toString());
                    txtPrenom.setText(unTableau.getValueAt(numLigne, 2).toString());
                    txtDateNaissance.setText(unTableau.getValueAt(numLigne, 3).toString());
                    txtAdresse.setText(unTableau.getValueAt(numLigne, 4).toString());
                    txtEmail.setText(unTableau.getValueAt(numLigne, 5).toString());
                    txtTelephone.setText(unTableau.getValueAt(numLigne, 6).toString());
                    txtNumPasseport.setText(unTableau.getValueAt(numLigne, 7).toString());
                    btEnregistrer.setText("Modifier");
                }
            }
        });
    }

    private void ajouterChampFormulaire(String label, JTextField textField) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        panelFormAjouter.add(lbl);
        panelFormAjouter.add(textField);
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Passager> lesPassagers = Controleur.selectAllPassagers(filtre);
        Object[][] matrice = new Object[lesPassagers.size()][8];
        int i = 0;
        for (Passager unPassager : lesPassagers) {
            matrice[i][0] = unPassager.getIdPassager();
            matrice[i][1] = unPassager.getNom();
            matrice[i][2] = unPassager.getPrenom();
            matrice[i][3] = unPassager.getDateNaissance();
            matrice[i][4] = unPassager.getAdresse();
            matrice[i][5] = unPassager.getEmail();
            matrice[i][6] = unPassager.getTelephone();
            matrice[i][7] = unPassager.getNumPasseport();
                       i++;
        }
        return matrice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Enregistrer")) {
            String nom = this.txtNom.getText();
            String prenom = this.txtPrenom.getText();
            String dateNaissanceText = this.txtDateNaissance.getText();
            LocalDate dateNaissance = LocalDate.parse(dateNaissanceText);
            String adresse = this.txtAdresse.getText();
            String email = this.txtEmail.getText();
            String telephone = this.txtTelephone.getText();
            String numPasseport = this.txtNumPasseport.getText();

            Passager nouveauPassager = new Passager(0, nom, prenom, dateNaissance, adresse, email, telephone, numPasseport);

            Controleur.insertPassager(nouveauPassager);

            // On actualise l'affichage après insertion
            this.unTableau.setDonnees(this.obtenirDonnees(""));
            this.nbPassagers.setText("Nombre de passagers : " + this.unTableau.getRowCount());

            // Affichage d'un message de confirmation
            JOptionPane.showMessageDialog(this, "Insertion effectuée");
            this.viderChamps();
        } else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();

            // Actualiser la matrice des données
            this.unTableau.setDonnees(this.obtenirDonnees(filtre));
            this.nbPassagers.setText("Nombre de passagers : " + this.unTableau.getRowCount());
        } else if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Modifier")) {
            String nom = this.txtNom.getText();
            String prenom = this.txtPrenom.getText();
            String dateNaissanceText = this.txtDateNaissance.getText();
            LocalDate dateNaissance = LocalDate.parse(dateNaissanceText);
            String adresse = this.txtAdresse.getText();
            String email = this.txtEmail.getText();
            String telephone = this.txtTelephone.getText();
            String numPasseport = this.txtNumPasseport.getText();

            int numLigne = this.tablePassagers.getSelectedRow();
            int idPassager = Integer.parseInt(this.unTableau.getValueAt(numLigne, 0).toString());

            // Instanciation du passager
            Passager unPassager = new Passager(idPassager, nom, prenom, dateNaissance, adresse, email, telephone, numPasseport);

            Controleur.updatePassager(unPassager);

            // Actualisation des données du passager
            this.unTableau.setDonnees(this.obtenirDonnees(""));
            this.nbPassagers.setText("Nombre de passagers : " + this.unTableau.getRowCount());

            // On vide les champs et on remet Enregistrer
            this.viderChamps();

            JOptionPane.showMessageDialog(this, "Modification réussie du Passager.");
        } else if (e.getSource() == this.btAnnuler) {
            // Vider les champs et remettre le texte du bouton à "Enregistrer"
            this.viderChamps();
        }
    }

    public void viderChamps() {
        // On vide les champs et on remet Enregistrer
        this.txtNom.setText("");
        this.txtPrenom.setText("");
        this.txtDateNaissance.setText("");
        this.txtEmail.setText("");
        this.txtAdresse.setText("");
        this.txtTelephone.setText("");
        this.txtNumPasseport.setText("");
        this.btEnregistrer.setText("Enregistrer");
    }
}

