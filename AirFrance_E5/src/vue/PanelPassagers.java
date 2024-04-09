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


    public PanelPassagers() {
        super("Gestion des passagers");
        this.setLayout(null);

        this.panelFiltre.setBackground(new Color(135, 206, 235));
        this.panelFiltre.setBounds(1150, 110, 300, 40);
        this.panelFiltre.setLayout(new GridLayout(1,3));
		this.panelFiltre.add(new JLabel("Filtrer par :"));
		this.panelFiltre.add(this.txtFiltre);
		this.panelFiltre.add(this.btFiltrer);
		this.add(this.panelFiltre);
		this.btFiltrer.addActionListener(this);
		
		
        // Construction du panel ajouter passager
        this.panelFormAjouter.setBackground(new Color(135, 206, 235));
        this.panelFormAjouter.setBounds(1140, 250, 350, 300);
        this.panelFormAjouter.setLayout(new GridLayout(8, 2));
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
        this.panelFormAjouter.add(new JLabel("Numéro de passeport :"));
        this.panelFormAjouter.add(this.txtNumPasseport);

        this.panelFormAjouter.add(this.btAnnuler);
        this.panelFormAjouter.add(this.btEnregistrer);
        this.add(this.panelFormAjouter);

        
        
		
		 

       

        // Construction de la table des passagers
        String[] entetes = {"ID_Passager", "Nom", "Prenom", "DateNaissance", "Adresse", "Email", "Téléphone",
                "Numéro de passeport"};
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
		this.tablePassagers = new JTable (this.unTableau);
        this.uneScroll = new JScrollPane(this.tablePassagers);
        this.uneScroll.setBounds(110, 100, 1000, 500);
        this.add(this.uneScroll);

        // Ajout des listeners
        this.btAnnuler.addActionListener(this);
        this.btEnregistrer.addActionListener(this);
        
        
      //suppression d'un client sur double click
      		this.tablePassagers.addMouseListener(new MouseListener() {
      			
      			@Override
      			public void mouseReleased(MouseEvent e) {
      		
      				
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
      				int numLigne, idPassager;
      				if (e.getClickCount() >= 2) {
      					numLigne = tablePassagers.getSelectedRow();
      					idPassager = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
      					int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer le Passager ?", "Suppresion du passager", JOptionPane.YES_NO_OPTION);
      					if (reponse == 0) {
      						//suppression en BDD
      						Controleur.deletePassager(idPassager);
      						//actualiser affichage
      						unTableau.setDonnees(obtenirDonnees(""));
      					}
      				}else if (e.getClickCount() >= 1) {
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
                
              //on actualise affichage apres insertion
    			this.unTableau.setDonnees(this.obtenirDonnees(""));
                

                // Affichage d'un message de confirmation
                JOptionPane.showMessageDialog(this, "Insertion effectuée");
            
        } else if (e.getSource() == this.btFiltrer) {
			String filtre = this.txtFiltre.getText();
			
			//actualiser la matrice des donnees
			this.unTableau.setDonnees(this.obtenirDonnees(filtre));
        }
 
    	 else if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Modifier")) {
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
 			
 			//instanciation client
 			Passager unPassager = new Passager(idPassager, nom, prenom, dateNaissance, adresse, email, telephone, numPasseport);
 			
 			Controleur.updatePassager(unPassager);
 			
 			//actualisation des données du client
 			this.unTableau.setDonnees(this.obtenirDonnees(""));
 			
 			//on vide les champs et on remet Enregistrer
 			this.txtNom.setText("");
 			this.txtPrenom.setText("");
 			this.txtDateNaissance.setText("");
 			this.txtEmail.setText("");
 			this.txtAdresse.setText("");
 			this.txtTelephone.setText("");
 			this.txtNumPasseport.setText("");
 			this.btEnregistrer.setText("Enregistrer");
 			JOptionPane.showMessageDialog(this, "Modification réussie du Passager.");
 			
 		}
    	    

        }

    }


