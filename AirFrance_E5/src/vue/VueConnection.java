package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controleur.Admin;
import controleur.AirFrance_E5;
import controleur.Controleur;
import utils.HashUtil;
import utils.PasswordValidator;

public class VueConnection extends JFrame implements ActionListener {
	
	private JTextField txtEmail = new JTextField("newadmin@airfrance.com");
	private JPasswordField txtMdp = new JPasswordField("Admin@512");
	private JButton btAnnuler = new JButton("Annuler");
	private JButton btSeconnecter = new JButton("Se connecter");
	private JPanel panelForm = new JPanel();
	
	public VueConnection() {
		this.setTitle(" AIRFRANCE EVENT MANAGER FOR ADMIN");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(100, 100, 600, 300);
		this.setLayout(null);
		this.getContentPane().setBackground(Color.white);
		
		//inserer le logo
		ImageIcon uneImage = new ImageIcon("src/images/airfrance1.png");
		JLabel leLogo = new JLabel(uneImage);
		leLogo.setBounds(30, 40, 200, 200);
		this.add(leLogo);
		
		//construction du panelForm
		this.panelForm.setBounds(300, 40, 250, 200);
		this.panelForm.setBackground(Color.white);
		this.panelForm.setLayout(new GridLayout(3, 2));
		this.panelForm.add(new JLabel("Email : "));
		this.panelForm.add(this.txtEmail);
		this.panelForm.add(new JLabel("MDP : "));
		this.panelForm.add(this.txtMdp);
		this.panelForm.add(this.btAnnuler);
		this.panelForm.add(this.btSeconnecter);
		this.add(this.panelForm);
		
		//rendre les boutons écoutables
		this.btAnnuler.addActionListener(this);
		this.btSeconnecter.addActionListener(this);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btAnnuler) {
			this.txtEmail.setText("");
			this.txtMdp.setText("");
		} else if (e.getSource() == this.btSeconnecter) {
			String email = this.txtEmail.getText();
			String mdp = new String(this.txtMdp.getPassword());

			// Vérifier le mot de passe avant vérification
			if (!PasswordValidator.verifMdp(mdp)) {
				JOptionPane.showMessageDialog(this, "Le mot de passe doit contenir au moins 8 caractères, " +
						"3 minuscules, 1 majuscule, 2 chiffres et 1 caractère spécial.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Hacher le mot de passe avant vérification
			String hashedMdp = HashUtil.hashPassword(mdp);
			
			// Vérifier dans la base de données
			Admin unUser = Controleur.verifConnexion(email, hashedMdp);
			if (unUser != null) {
				// Ouvrir le logiciel : Vue Générale
				JOptionPane.showMessageDialog(this, "Bienvenue " + unUser.getPrenom());
				
				AirFrance_E5.rendreVisibleVueConnection(false);
				AirFrance_E5.rendreVisibleVueGenerale(true, unUser);
			} else {
				JOptionPane.showMessageDialog(this, "Veuillez vérifier vos identifiants !");
				this.txtEmail.setText("");
				this.txtMdp.setText("");
			}
		}
	}
}
