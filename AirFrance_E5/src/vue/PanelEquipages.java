package vue;
import java.awt.Color;
import java.awt.GridLayout;
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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controleur.Controleur;
import controleur.MembreEquipage;
import controleur.Passager;
import controleur.Tableau;
import controleur.Vols;
import controleur.Admin;
import controleur.Avions;

public class PanelEquipages extends PanelPrincipal implements ActionListener{
	
	private JPanel panelFormAjouter = new JPanel();
	
	private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtDateNaissance = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtTelephone = new JTextField();
    private JTextField txtRole = new JTextField();
    private JTextField txtDateEmbauche = new JTextField();
    private JComboBox<String> txtIdVol = new JComboBox<String>();
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");

    private JTable tableEquipages;
    private JScrollPane uneScroll;
    private Tableau unTableau;

	public PanelEquipages() {
		 super("Gestion des equipages");	
	
		 
		// Construction du panel ajouter membre
	        this.panelFormAjouter.setBackground(new Color(135, 206, 235));
	        this.panelFormAjouter.setBounds(1140, 250, 350, 300);
	        this.panelFormAjouter.setLayout(new GridLayout(10, 2));
	        this.panelFormAjouter.add(new JLabel("Nom Passager :"));
	        this.panelFormAjouter.add(this.txtNom);
	        this.panelFormAjouter.add(new JLabel("Prenom Passager :"));
	        this.panelFormAjouter.add(this.txtPrenom);
	        this.panelFormAjouter.add(new JLabel("Date de naissance :"));
	        this.panelFormAjouter.add(this.txtDateNaissance);
	        this.panelFormAjouter.add(new JLabel("adresse :"));
	        this.panelFormAjouter.add(this.txtAdresse);
	        this.panelFormAjouter.add(new JLabel("Email Passager :"));
	        this.panelFormAjouter.add(this.txtEmail);
	        this.panelFormAjouter.add(new JLabel("Téléphone du Passager :"));
	        this.panelFormAjouter.add(this.txtTelephone);
	        this.panelFormAjouter.add(new JLabel("Role :"));
	        this.panelFormAjouter.add(this.txtRole);
	        this.panelFormAjouter.add(new JLabel("Date embauche :"));
	        this.panelFormAjouter.add(this.txtDateEmbauche);
	        this.panelFormAjouter.add(new JLabel("Numero vol attribué :"));
	        this.panelFormAjouter.add(this.txtIdVol);
	        
	        this.panelFormAjouter.add(this.btAnnuler);
	        this.panelFormAjouter.add(this.btEnregistrer);
	        this.add(this.panelFormAjouter);

	        
	     // Construction de la table des passagers
	        String[] entetes = {"ID_MembreEquipage", "Nom", "Prenom", "DateNaissance", "Adresse", "Email", "Téléphone",
	                "Role","DateEmbauche","idVol"};
	        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
			this.tableEquipages = new JTable (this.unTableau);
	        this.uneScroll = new JScrollPane(this.tableEquipages);
	        this.uneScroll.setBounds(70, 100, 1050, 500);
	        this.add(this.uneScroll);
	        
	        remplirCBXVol();
	        
	     // Ajout des listeners
	        this.btAnnuler.addActionListener(this);
	        this.btEnregistrer.addActionListener(this);
	        
	      //suppression d'un client sur double click
      		this.tableEquipages.addMouseListener(new MouseListener() {
      			
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
      				int numLigne, idMembreEquipage;
      				if (e.getClickCount() >= 2) {
      					numLigne = tableEquipages.getSelectedRow();
      					idMembreEquipage = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
      					int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer le membre ?", "Suppresion du membre", JOptionPane.YES_NO_OPTION);
      					if (reponse == 0) {
      						//suppression en BDD
      						Controleur.deleteMembreEquipage(idMembreEquipage);
      						//actualiser affichage
      						unTableau.setDonnees(obtenirDonnees(""));
      					}
      				}else if (e.getClickCount() >= 1) {
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
	}
	
	
	
	public Object[][] obtenirDonnees(String filtre) {
        ArrayList<MembreEquipage> lesMembresEquipages = Controleur.selectAllMembreEquipage(filtre);
        Object[][] matrice = new Object[lesMembresEquipages.size()][10];
        int i = 0;
        for ( MembreEquipage unMembreEquipage : lesMembresEquipages) {
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
	    // Vider la combobox des avions
	    this.txtIdVol.removeAllItems();
	    
	    // Récupérer les avions depuis la base de données
	    ArrayList<Vols> lesVols = Controleur.selectAllVols(""); // Assurez-vous d'avoir une méthode selectAllAvions dans votre classe Modele
	    
	    // Remplir la combobox avec les informations des avions
	    for (Vols unVol : lesVols) {
	        this.txtIdVol.addItem(unVol.getIdVol() + "-" + unVol.getNumVol());
	    }
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
			String tab[]= chaine.split("-");
			int idVol = Integer.parseInt(tab[0]);
            
    
                
                MembreEquipage nouveauEquipage = new MembreEquipage(0, nom, prenom, dateNaissance, adresse, email, telephone, role, dateEmbauche, idVol);
                
                Controleur.insertEquipage(nouveauEquipage);
                
              //on actualise affichage apres insertion
    			this.unTableau.setDonnees(this.obtenirDonnees(""));
                

                // Affichage d'un message de confirmation
                JOptionPane.showMessageDialog(this, "Insertion effectuée");
            
        }
		else if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Modifier")) {
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
			String tab[]= chaine.split("-");
			int idVol = Integer.parseInt(tab[0]);
			
			int numLigne = this.tableEquipages.getSelectedRow();
			int idMembreEquipage = Integer.parseInt(this.unTableau.getValueAt(numLigne, 0).toString());
			
			//instanciation client
            MembreEquipage unEquipage = new MembreEquipage(idMembreEquipage, nom, prenom, dateNaissance, adresse, email, telephone, role, dateEmbauche, idVol);
			
			Controleur.updateEquipage(unEquipage);
			
			//actualisation des données du client
			this.unTableau.setDonnees(this.obtenirDonnees(""));
			
			//on vide les champs et on remet Enregistrer
			this.txtNom.setText("");
			this.txtPrenom.setText("");
			this.txtDateNaissance.setText("");
			this.txtEmail.setText("");
			this.txtAdresse.setText("");
			this.txtTelephone.setText("");
			this.txtRole.setText("");
			this.txtDateEmbauche.setText("");
			this.txtIdVol.setToolTipText("");
			this.btEnregistrer.setText("Enregistrer");
			JOptionPane.showMessageDialog(this, "Modification réussie du Membre.");
			
		}
		
	}

}
