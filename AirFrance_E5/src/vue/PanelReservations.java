package vue;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import controleur.Reservations;
import controleur.Tableau;
import controleur.Vols;
import controleur.Passager;

public class PanelReservations extends PanelPrincipal implements ActionListener {

    private JComboBox<String> txtIdPassager = new JComboBox<String>();
    private JComboBox<String> txtIdVol = new JComboBox<String>();
    private JTextField txtDateReservation = new JTextField();
    private JTextField txtSiegeAttribue = new JTextField();
    
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");
    
    private JTable tableReservations;
    private JScrollPane uneScroll;
    private Tableau unTableau;
    
    private JPanel panelForm  = new JPanel(); 
    
    public PanelReservations() {
        super("Gestion des Réservations");
        
        // Construction du panel pour ajouter une réservation
        this.panelForm.setBackground(new Color(135, 206, 235));
        this.panelForm.setBounds(70, 100, 600, 300);
        this.panelForm.setLayout(new GridLayout(5, 2));
        this.panelForm.add(new JLabel("ID Passager :"));
        this.panelForm.add(this.txtIdPassager);
        this.panelForm.add(new JLabel("ID Vol :"));
        this.panelForm.add(this.txtIdVol);
        this.panelForm.add(new JLabel("Date de réservation :"));
        this.panelForm.add(this.txtDateReservation);
        this.panelForm.add(new JLabel("Siège attribué :"));
        this.panelForm.add(this.txtSiegeAttribue);
        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btEnregistrer);
        this.add(this.panelForm);
        
        // Construction de la table des réservations
        String[] entetes = {"ID Réservation", "ID Passager", "ID Vol", "Date de réservation", "Siège attribué"};
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableReservations = new JTable (this.unTableau);
        this.uneScroll = new JScrollPane(this.tableReservations);
        this.uneScroll.setBounds(700, 100, 550, 400);
        this.add(this.uneScroll);
        
        // Remplissage des JComboBox avec les données disponibles
        remplirCBXPassagers();
        remplirCBXVols();
        
        // Ajout des ActionListener aux boutons
        this.btEnregistrer.addActionListener(this);
        this.btAnnuler.addActionListener(this);
        


      //suppression d'un client sur double click
  		this.tableReservations.addMouseListener(new MouseListener() {
  			
  			@Override
  			public void mouseReleased(MouseEvent e) {
  				// TODO Auto-generated method stub
  				
  			}
  			
  			@Override
  			public void mousePressed(MouseEvent e) {
  				
  				
  			}
  			
  			@Override
  			public void mouseExited(MouseEvent e) {
  				
  				
  			}
  			
  			@Override
  			public void mouseEntered(MouseEvent e) {
  				
  				
  			}
  			
  			@Override
  			public void mouseClicked(MouseEvent e) {
  				int numLigne, idResa;
  				if (e.getClickCount() >= 2) {
  					numLigne = tableReservations.getSelectedRow();
  					idResa = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
  					int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer la resa ?", "Suppresion de la resa", JOptionPane.YES_NO_OPTION);
  					if (reponse == 0) {
  						//suppression en BDD
  						Controleur.deleteReservation(idResa);
  						//actualiser affichage
  						unTableau.setDonnees(obtenirDonnees(""));
  					}
  				}else if (e.getClickCount() >= 1) {
  					numLigne = tableReservations.getSelectedRow();
  			        txtIdVol.setSelectedItem(unTableau.getValueAt(numLigne, 1).toString());
  			        txtIdPassager.setSelectedItem(unTableau.getValueAt(numLigne, 2).toString());
  			        txtDateReservation.setText(unTableau.getValueAt(numLigne, 3).toString());
  			        txtSiegeAttribue.setText(unTableau.getValueAt(numLigne, 4).toString());
  			        btEnregistrer.setText("Modifier");
  				}
  				
  			}
  		});
}
    
    
    // Méthode pour remplir la JComboBox des passagers avec les données disponibles en base
    public void remplirCBXPassagers() {
        this.txtIdPassager.removeAllItems(); // Vide la JComboBox des passagers
        // Récupération des passagers depuis la base de données
        // Assurez-vous d'avoir une méthode selectAllPassagers dans votre classe Controleur
        ArrayList<Passager> lesPassagers = Controleur.selectAllPassagers(""); 
        // Remplissage de la JComboBox avec les informations des passagers
        for (Passager unPassager : lesPassagers) {
            this.txtIdPassager.addItem(unPassager.getIdPassager() + "-" + unPassager.getNom() + " " + unPassager.getPrenom());
        }
    }
    
    // Méthode pour remplir la JComboBox des vols avec les données disponibles en base
    public void remplirCBXVols() {
        this.txtIdVol.removeAllItems(); // Vide la JComboBox des vols
        // Récupération des vols depuis la base de données
        // Assurez-vous d'avoir une méthode selectAllVols dans votre classe Controleur
        ArrayList<Vols> lesVols = Controleur.selectAllVols(""); 
        // Remplissage de la JComboBox avec les informations des vols
        for (Vols unVol : lesVols) {
            this.txtIdVol.addItem(unVol.getIdVol() + "-" + unVol.getNumVol());
        }
    }
    
    // Méthode pour obtenir les données des réservations à afficher dans la table
    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Reservations> lesReservations = Controleur.selectAllReservations(filtre);
        Object[][] matrice = new Object[lesReservations.size()][5];
        int i = 0;
        for (Reservations uneReservation : lesReservations) {
            matrice[i][0] = uneReservation.getIdReservation();
            matrice[i][1] = uneReservation.getIdPassager();
            matrice[i][2] = uneReservation.getIdVol();
            matrice[i][3] = uneReservation.getDateReservation();
            matrice[i][4] = uneReservation.getSiegeAttribue();
            i++;
        }
        return matrice;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Enregistrer")) {
            // Récupération des valeurs saisies par l'utilisateur
            String idPassagerString = this.txtIdPassager.getSelectedItem().toString();
            int idPassager = Integer.parseInt(idPassagerString.split("-")[0]);
            String idVolString = this.txtIdVol.getSelectedItem().toString();
            int idVol = Integer.parseInt(idVolString.split("-")[0]);
            String dateReservationText = this.txtDateReservation.getText();
            LocalDate dateReservation = LocalDate.parse(dateReservationText);
            String siegeAttribue = this.txtSiegeAttribue.getText();
            
            // Création de l'objet Réservation avec les valeurs récupérées
            Reservations nouvelleReservation = new Reservations(0, idPassager, idVol, dateReservation, siegeAttribue);
            
            // Appel de la méthode d'insertion de la réservation dans la base de données
            Controleur.insertReservation(nouvelleReservation);
            
            // Actualisation de l'affichage de la table des réservations
            this.unTableau.setDonnees(this.obtenirDonnees(""));
            
            // Affichage d'un message de confirmation
            JOptionPane.showMessageDialog(this, "Réservation enregistrée avec succès !");
            
        }else if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Modifier")) {
        	// Récupération des valeurs saisies par l'utilisateur
            String idPassagerString = this.txtIdPassager.getSelectedItem().toString();
            int idPassager = Integer.parseInt(idPassagerString.split("-")[0]);
            String idVolString = this.txtIdVol.getSelectedItem().toString();
            int idVol = Integer.parseInt(idVolString.split("-")[0]);
            String dateReservationText = this.txtDateReservation.getText();
            LocalDate dateReservation = LocalDate.parse(dateReservationText);
            String siegeAttribue = this.txtSiegeAttribue.getText();
            
         // Récupération de l'ID du vol à modifier
		    int numLigne = this.tableReservations.getSelectedRow();
		    int idReservation = Integer.parseInt(this.unTableau.getValueAt(numLigne, 0).toString());
            
            Reservations nouvelleReservation = new Reservations(idReservation, idPassager, idVol, dateReservation, siegeAttribue);
            
            Controleur.updateReservation(nouvelleReservation);

		    // Actualisation des données des vols dans le tableau
		    this.unTableau.setDonnees(this.obtenirDonnees(""));

		    // Réinitialisation des champs et du bouton d'action
		    this.txtDateReservation.setText("");
	        this.txtSiegeAttribue.setText("");
		    this.btEnregistrer.setText("Enregistrer");

		    // Affichage d'un message de confirmation
		    JOptionPane.showMessageDialog(this, "Modification du vol effectuée avec succès.");
        	
        } else if (e.getSource() == this.btAnnuler) {
            // Effacement des champs de saisie
            this.txtDateReservation.setText("");
            this.txtSiegeAttribue.setText("");
        }
    }
}
