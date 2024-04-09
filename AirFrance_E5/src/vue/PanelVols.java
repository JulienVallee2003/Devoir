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

import controleur.Controleur;
import controleur.Tableau;
import controleur.Vols;
import controleur.Avions;
import controleur.Aeroports;



public class PanelVols extends PanelPrincipal implements ActionListener{
	
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
    
    private JPanel panelFiltre = new JPanel();
	private JButton btFiltrer = new JButton("Filtrer");
	private JTextField txtFiltre = new JTextField();
	
	

	public PanelVols() {
		 super("Gestion des Vols");
		 
		
	        
	     // Construction du panel ajouter passager
	        this.panelFormAjouter.setBackground(new Color(135, 206, 235));
	        this.panelFormAjouter.setBounds(1140, 250, 350, 300);
	        this.panelFormAjouter.setLayout(new GridLayout(9, 4));
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
	        this.panelFormAjouter.add(new JLabel("Aeroport d'arrivé :"));
	        this.panelFormAjouter.add(this.txtIdAeroportArrive);
	        this.panelFormAjouter.add(new JLabel("Avion :"));
	        this.panelFormAjouter.add(this.txtIdAvion);
		
	        this.panelFormAjouter.add(this.btAnnuler);
	        this.panelFormAjouter.add(this.btEnregistrer);
	        this.add(this.panelFormAjouter);
	        
	      //remplir le comboBox 
			this.remplirCBXAeroportDepart();
			this.remplirCBXAeroportArrive();
			this.remplirCBXAvion();
			
			// Construction de la table des passagers
			String[] entetes = {"ID_Vol", "N° Vol", "Date_Depart", "Heure_Depart", "Aeroport_Depart", "Date_Arrivee", "Heure_Arrivee", "Aeroport_Arrive", "Avion"};
		    this.unTableau = new Tableau(this.obtenirDonnees(), entetes); // Initialisation de unTableau
		    this.tableVols = new JTable(this.unTableau);
		    this.uneScroll = new JScrollPane(this.tableVols);
		    this.uneScroll.setBounds(110, 100, 1000, 500);
		    this.add(this.uneScroll);
	        
		 // Ajout des listeners
	        this.btAnnuler.addActionListener(this);
	        this.btEnregistrer.addActionListener(this);
	        
	        
	      //suppression d'un client sur double click
      		this.tableVols.addMouseListener(new MouseListener() {
      			
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
      				int numLigne, idVol;
      				if (e.getClickCount() >= 2) {
      					numLigne = tableVols.getSelectedRow();
      					idVol = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
      					int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer le Vol ?", "Suppresion du Vol", JOptionPane.YES_NO_OPTION);
      					if (reponse == 0) {
      						//suppression en BDD
      						Controleur.deleteVol(idVol);
      						//actualiser affichage
      						unTableau.setDonnees(obtenirDonnees());
      					}
      				}else if (e.getClickCount() >= 1) {
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
    }

	
	
	

	
	public Object[][] obtenirDonnees() {
        ArrayList<Vols> lesVols = Controleur.selectAllVols("");
        Object[][] matrice = new Object[lesVols.size()][9];
        int i = 0;
        for (Vols unVol : lesVols) {
            matrice[i][0] = unVol.getIdVol();
            matrice[i][1] = unVol.getNumVol();
            matrice[i][2] = unVol.getDateDepart();
            matrice[i][3] = unVol.getHeureDepart();
            matrice[i][4] = unVol.getIdAeroportDepart();
            matrice[i][5] = unVol.getDateArrivee();
            matrice[i][6] = unVol.getHeureArrivee();
            matrice[i][7] = unVol.getIdAeroportArrive();
            matrice[i][8] = unVol.getIdAvion();
            i++;
        }
        return matrice;
    }
	
	public void remplirCBXAeroportDepart() 
	{
		//vider combox des clients
		this.txtIdAeroportDepart.removeAllItems();
		
		//remplir avec les clients de la BDD ; id-nom
		ArrayList<Aeroports> lesAeroports = Controleur.selectAllAeroports();
		
		for (Aeroports unAeroport : lesAeroports) {
			this.txtIdAeroportDepart.addItem(unAeroport.getIdAeroport()+"-" + unAeroport.getNomAeroport());
		}
	}
	
	public void remplirCBXAeroportArrive() 
	{
		//vider combox des clients
			this.txtIdAeroportArrive.removeAllItems();
				
				//remplir avec les clients de la BDD ; id-nom
			ArrayList<Aeroports> lesAeroports = Controleur.selectAllAeroports();
				
			for (Aeroports unAeroport : lesAeroports) {
				this.txtIdAeroportArrive.addItem(unAeroport.getIdAeroport()+"-" + unAeroport.getNomAeroport());
			}
	}
	
	public void remplirCBXAvion() {
	    // Vider la combobox des avions
	    this.txtIdAvion.removeAllItems();
	    
	    // Récupérer les avions depuis la base de données
	    ArrayList<Avions> lesAvions = Controleur.selectAllAvions(); // Assurez-vous d'avoir une méthode selectAllAvions dans votre classe Modele
	    
	    // Remplir la combobox avec les informations des avions
	    for (Avions unAvion : lesAvions) {
	        this.txtIdAvion.addItem(unAvion.getIdAvion() + "-" + unAvion.getModele());
	    }
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Enregistrer")) {
			String numVol = this.txtNumVol.getText();
			String dateDepartText = this.txtDateDepart.getText();
			LocalDate dateDepart = LocalDate.parse(dateDepartText);
			String dateArriveeText = this.txtDateArrivee.getText();
			LocalDate dateArrivee = LocalDate.parse(dateArriveeText);
			String heureDepartText = this.txtHeureDepart.getText();
			LocalTime heureDepart = LocalTime.parse(heureDepartText);
			String heureArriveeText = this.txtHeureArrivee.getText();
			LocalTime heureArrivee = LocalTime.parse(heureArriveeText);
			
			String chaineDepart = this.txtIdAeroportDepart.getSelectedItem().toString();
			String tabDepart[]= chaineDepart.split("-");
			int idAeroportDepart = Integer.parseInt(tabDepart[0]);
			String chaineArrive = this.txtIdAeroportArrive.getSelectedItem().toString();
			String tabArrive[]= chaineArrive.split("-");
			int idAeroportArrive = Integer.parseInt(tabArrive[0]);
			String chaineAvion = this.txtIdAvion.getSelectedItem().toString();
			String tabAvion[]= chaineAvion.split("-");
			int idAvion = Integer.parseInt(tabAvion[0]);
			


    
                
			Vols nouveauVol = new Vols(0, numVol, dateDepart, heureDepart, idAeroportDepart, dateArrivee, heureArrivee, idAeroportArrive, idAvion);

                
                Controleur.insertVol(nouveauVol);
                
              //on actualise affichage apres insertion
    			this.unTableau.setDonnees(this.obtenirDonnees());
                

                // Affichage d'un message de confirmation
                JOptionPane.showMessageDialog(this, "Insertion effectuée");
            
        }
		
		else if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Modifier")) {
		    String numVol = this.txtNumVol.getText();
		    String dateDepartText = this.txtDateDepart.getText();
		    LocalDate dateDepart = LocalDate.parse(dateDepartText);
		    String dateArriveeText = this.txtDateArrivee.getText();
		    LocalDate dateArrivee = LocalDate.parse(dateArriveeText);
		    String heureDepartText = this.txtHeureDepart.getText();
		    LocalTime heureDepart = LocalTime.parse(heureDepartText);
		    String heureArriveeText = this.txtHeureArrivee.getText();
		    LocalTime heureArrivee = LocalTime.parse(heureArriveeText);

		    String chaineDepart = this.txtIdAeroportDepart.getSelectedItem().toString();
		    String tabDepart[]= chaineDepart.split("-");
		    int idAeroportDepart = Integer.parseInt(tabDepart[0]);
		    String chaineArrive = this.txtIdAeroportArrive.getSelectedItem().toString();
		    String tabArrive[]= chaineArrive.split("-");
		    int idAeroportArrive = Integer.parseInt(tabArrive[0]);
		    String chaineAvion = this.txtIdAvion.getSelectedItem().toString();
		    String tabAvion[]= chaineAvion.split("-");
		    int idAvion = Integer.parseInt(tabAvion[0]);

		    // Récupération de l'ID du vol à modifier
		    int numLigne = this.tableVols.getSelectedRow();
		    int idVol = Integer.parseInt(this.unTableau.getValueAt(numLigne, 0).toString());

		    // Instanciation du vol à modifier
		    Vols volModifie = new Vols(idVol, numVol, dateDepart, heureDepart, idAeroportDepart, dateArrivee, heureArrivee, idAeroportArrive, idAvion);

		    // Appel de la méthode de modification dans le contrôleur
		    Controleur.updateVol(volModifie);

		    // Actualisation des données des vols dans le tableau
		    this.unTableau.setDonnees(this.obtenirDonnees());

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
		}

		
	}

}
