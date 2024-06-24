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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controleur.Controleur;
import controleur.MembreEquipage;
import controleur.Tableau;
import controleur.Vols;

public class PanelEquipages extends PanelPrincipal implements ActionListener {

    private JPanel panelFormAjouter = new JPanel();

    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtDateNaissance = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtTelephone = new JTextField();
    private JTextField txtRole = new JTextField();
    private JTextField txtDateEmbauche = new JTextField();
    private JComboBox<String> txtIdVol = new JComboBox<>();
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");
    private JLabel nbEquipages = new JLabel("Nombre de membres d'équipage : 0", SwingConstants.CENTER);

    private JTextField txtFilter = new JTextField();
    private JButton btFilter = new JButton("Filtrer");

    private JTable tableEquipages;
    private JScrollPane uneScroll;
    private Tableau unTableau;

    public PanelEquipages() {
        super("Gestion des équipages");
        this.setLayout(null);

        // Construction du panel ajouter membre
        this.panelFormAjouter.setBackground(new Color(240, 248, 255));
        this.panelFormAjouter.setBounds(1140, 170, 330, 500);
        this.panelFormAjouter.setLayout(new GridLayout(12, 2));
        this.panelFormAjouter.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        ajouterChampFormulaire("Nom Passager :", this.txtNom);
        ajouterChampFormulaire("Prénom Passager :", this.txtPrenom);
        ajouterChampFormulaire("Date de naissance :", this.txtDateNaissance);
        ajouterChampFormulaire("Adresse :", this.txtAdresse);
        ajouterChampFormulaire("Email Passager :", this.txtEmail);
        ajouterChampFormulaire("Téléphone du Passager :", this.txtTelephone);
        ajouterChampFormulaire("Rôle :", this.txtRole);
        ajouterChampFormulaire("Date embauche :", this.txtDateEmbauche);
        ajouterChampFormulaire("Numéro vol attribué :", this.txtIdVol);
        panelFormAjouter.add(btAnnuler);
        panelFormAjouter.add(btEnregistrer);
        panelFormAjouter.add(nbEquipages);  // Ajout du label pour le nombre de membres d'équipage
        this.add(this.panelFormAjouter);

        // Construction de la table des équipages
        String[] entetes = {"ID_MembreEquipage", "Nom", "Prénom", "DateNaissance", "Adresse", "Email", "Téléphone", "Rôle", "DateEmbauche", "idVol"};
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableEquipages = new JTable(this.unTableau);
        this.uneScroll = new JScrollPane(this.tableEquipages);
        this.uneScroll.setBounds(70, 170, 1050, 500);
        this.add(this.uneScroll);

        // Ajout des listeners
        this.btAnnuler.addActionListener(this);
        this.btEnregistrer.addActionListener(this);

        // Suppression d'un membre d'équipage sur double clic
        this.tableEquipages.addMouseListener(new MouseListener() {
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
                int numLigne, idMembreEquipage;
                if (e.getClickCount() >= 2) {
                    numLigne = tableEquipages.getSelectedRow();
                    idMembreEquipage = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
                    int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer le membre ?", "Suppression du membre", JOptionPane.YES_NO_OPTION);
                    if (reponse == 0) {
                        // Suppression en BDD
                        Controleur.deleteMembreEquipage(idMembreEquipage);
                        // Actualiser affichage
                        unTableau.setDonnees(obtenirDonnees(""));
                        mettreAJourNbEquipages();
                    }
                } else if (e.getClickCount() >= 1) {
                    numLigne = tableEquipages.getSelectedRow();
                    txtNom.setText(unTableau.getValueAt(numLigne, 1).toString());
                    txtPrenom.setText(unTableau.getValueAt(numLigne, 2).toString());
                    txtDateNaissance.setText(unTableau.getValueAt(numLigne, 3).toString());
                    txtAdresse.setText(unTableau.getValueAt(numLigne, 4).toString());
                    txtEmail.setText(unTableau.getValueAt(numLigne, 5).toString());
                    txtTelephone.setText(unTableau.getValueAt(numLigne, 6).toString());
                    txtRole.setText(unTableau.getValueAt(numLigne, 7).toString());
                    txtDateEmbauche.setText(unTableau.getValueAt(numLigne, 8).toString());
                    txtIdVol.setSelectedItem(unTableau.getValueAt(numLigne, 9).toString());
                    btEnregistrer.setText("Modifier");
                }
            }
        });
        
        // Ajout du champ de filtre et du bouton de filtre
                this.txtFilter.setBounds(70, 680, 200, 25);
                this.add(this.txtFilter);
        
                this.btFilter.setBounds(280, 680, 100, 25);
                this.add(this.btFilter);
        
                this.btFilter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String filter = txtFilter.getText();
                        unTableau.setDonnees(obtenirDonnees(filter));
                        mettreAJourNbEquipages();
                    }
                });

        remplirCBXVol();
        mettreAJourNbEquipages();  // Initialisation du nombre de membres d'équipage
    }

    private void ajouterChampFormulaire(String label, JTextField textField) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        panelFormAjouter.add(lbl);
        panelFormAjouter.add(textField);
    }

    private void ajouterChampFormulaire(String label, JComboBox<String> comboBox) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        panelFormAjouter.add(lbl);
        panelFormAjouter.add(comboBox);
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<MembreEquipage> lesMembresEquipages = Controleur.selectAllMembreEquipage(filtre);
        Object[][] matrice = new Object[lesMembresEquipages.size()][10];
        int i = 0;
        for (MembreEquipage unMembreEquipage : lesMembresEquipages) {
            matrice[i][0] = unMembreEquipage.getIdEquipage();
            matrice[i][1] = unMembreEquipage.getNom();
            matrice[i][2] = unMembreEquipage.getPrenom();
            matrice[i][3] = unMembreEquipage.getDateNaissance();
            matrice[i][4] = unMembreEquipage.getAdresse();
            matrice[i][5] = unMembreEquipage.getEmail();
            matrice[i][6] = unMembreEquipage.getTelephone();
            matrice[i][7] = unMembreEquipage.getRole();
            matrice[i][8] = unMembreEquipage.getDateEmbauche();
            matrice[i][9] = unMembreEquipage.getIdVol();
            i++;
        }
        return matrice;
    }

    public void remplirCBXVol() {
        this.txtIdVol.removeAllItems();
        ArrayList<Vols> lesVols = Controleur.selectAllVols("");
        for (Vols unVol : lesVols) {
            this.txtIdVol.addItem(unVol.getIdVol() + "-" + unVol.getNumVol());
        }
    }

    private void mettreAJourNbEquipages() {
        this.nbEquipages.setText("Nombre de membres : " + this.unTableau.getRowCount());
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
            String role = this.txtRole.getText();
            String dateEmbaucheText = this.txtDateEmbauche.getText();
            LocalDate dateEmbauche = LocalDate.parse(dateEmbaucheText);
            String chaine = this.txtIdVol.getSelectedItem().toString();
            String[] tab = chaine.split("-");
            int idVol = Integer.parseInt(tab[0]);

            MembreEquipage nouveauEquipage = new MembreEquipage(0, nom, prenom, dateNaissance, adresse, email, telephone, role, dateEmbauche, idVol);

            Controleur.insertEquipage(nouveauEquipage);

            this.unTableau.setDonnees(this.obtenirDonnees(""));
            mettreAJourNbEquipages(); // Mise à jour du nombre de membres d'équipage
            JOptionPane.showMessageDialog(this, "Insertion effectuée");
        } else if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Modifier")) {
            String nom = this.txtNom.getText();
            String prenom = this.txtPrenom.getText();
            String dateNaissanceText = this.txtDateNaissance.getText();
            LocalDate dateNaissance = LocalDate.parse(dateNaissanceText);
            String adresse = this.txtAdresse.getText();
            String email = this.txtEmail.getText();
            String telephone = this.txtTelephone.getText();
            String role = this.txtRole.getText();
            String dateEmbaucheText = this.txtDateEmbauche.getText();
            LocalDate dateEmbauche = LocalDate.parse(dateEmbaucheText);
            String chaine = this.txtIdVol.getSelectedItem().toString();
            String[] tab = chaine.split("-");
            int idVol = Integer.parseInt(tab[0]);

            int numLigne = this.tableEquipages.getSelectedRow();
            int idMembreEquipage = Integer.parseInt(this.unTableau.getValueAt(numLigne, 0).toString());

            MembreEquipage unEquipage = new MembreEquipage(idMembreEquipage, nom, prenom, dateNaissance, adresse, email, telephone, role, dateEmbauche, idVol);

            Controleur.updateEquipage(unEquipage);

            this.unTableau.setDonnees(this.obtenirDonnees(""));
            mettreAJourNbEquipages(); // Mise à jour du nombre de membres d'équipage

            this.txtNom.setText("");
            this.txtPrenom.setText("");
            this.txtDateNaissance.setText("");
            this.txtAdresse.setText("");
            this.txtEmail.setText("");
            this.txtTelephone.setText("");
            this.txtRole.setText("");
            this.txtDateEmbauche.setText("");
            this.btEnregistrer.setText("Enregistrer");
            JOptionPane.showMessageDialog(this, "Modification réussie du Membre.");
        } else if (e.getSource() == this.btAnnuler) {
            this.txtNom.setText("");
            this.txtPrenom.setText("");
            this.txtDateNaissance.setText("");
            this.txtAdresse.setText("");
            this.txtEmail.setText("");
            this.txtTelephone.setText("");
            this.txtRole.setText("");
            this.txtDateEmbauche.setText("");
            this.btEnregistrer.setText("Enregistrer");
        }
    }
}

