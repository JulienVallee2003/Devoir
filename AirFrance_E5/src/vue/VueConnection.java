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

import controleur.Controleur;
import controleur.AirFrance_E5;
import controleur.Admin;

public class VueConnection extends JFrame implements ActionListener {
	
	private JTextField txtEmail = new JTextField();
	private JPasswordField txtMdp = new JPasswordField();
	private JButton btAnnuler = new JButton("Annuler");
	private JButton btSeconnecter = new JButton("Se connecter");
	private JPanel panelForm = new JPanel();
	
	public VueConnection() {
		this.setTitle("Admin AIRFRANCE");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(100, 100, 600, 300);
		this.setLayout(null);
		this.getContentPane().setBackground(Color.gray);
		
		//inserer le logo
		ImageIcon uneImage = new ImageIcon("src/images/airfrance1.png");
		JLabel leLogo = new JLabel(uneImage);
		leLogo.setBounds(30, 40, 200, 200);
		this.add(leLogo);
		
		//construction du panelForm
		this.panelForm.setBounds(300, 40, 250, 200);
		this.panelForm.setBackground(Color.gray);
		this.panelForm.setLayout(new GridLayout(3,2));
		this.panelForm.add(new JLabel("Email : "));
		this.panelForm.add(this.txtEmail);
		this.panelForm.add(new JLabel("MDP : "));
		this.panelForm.add(this.txtMdp);
		this.panelForm.add(this.btAnnuler);
		this.panelForm.add(this.btSeconnecter);
		this.add(this.panelForm);
		
		//rendre les boutons ecoutables
		this.btAnnuler.addActionListener(this);
		this.btSeconnecter.addActionListener(this);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btAnnuler) {
			this.txtEmail.setText("");
			this.txtMdp.setText("");
		}
		
		else if (e.getSource() == this.btSeconnecter) {
			String email = this.txtEmail.getText();
			String mdp = new String (this.txtMdp.getPassword());
			
			//on vérifie les donnéees (sécurité)
			
			//on vérifie dans la base de données
			Admin unUser = Controleur.verifConnexion(email, mdp);
			if (unUser != null) {
				
			
			
			//on ouvre le logiciel : Vue Générale
			JOptionPane.showMessageDialog(this, "Bienvenue M/MM" + unUser.getPrenom());
			
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

