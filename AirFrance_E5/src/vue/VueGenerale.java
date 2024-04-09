package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controleur.AirFrance_E5;
import controleur.Admin;

public class VueGenerale extends JFrame implements ActionListener {
	
	private JButton btPassagers = new JButton("Passagers");
	private JButton btVols = new JButton("Vols");
	private JButton btEquipages = new JButton("Equipage");
	private JButton btReservations = new JButton("Reservations");
	private JButton btQuitter = new JButton("Quitter");
	private JPanel panelMenu = new JPanel();

	// Instanciation des panels
	private PanelPassagers unPanelPassagers = new PanelPassagers();
	private PanelVols unPanelVols = new PanelVols();
	private PanelEquipages unPanelEquipages = new PanelEquipages();
	private PanelReservations unPanelReservations = new PanelReservations();
	
	public VueGenerale(Admin unUser) {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        
		this.setTitle("AirFrance Event Manager");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(0, 0, screenWidth, screenHeight);
		this.setLayout(null);
		this.getContentPane().setBackground(Color.gray);
		
		// Construction du panelMenu
		this.panelMenu.setBounds(20, 10, 800, 30);
		this.panelMenu.setBackground(Color.gray);
		this.panelMenu.setLayout(new GridLayout(1,5));
		this.panelMenu.add(this.btPassagers);
		this.panelMenu.add(this.btVols);
		this.panelMenu.add(this.btEquipages);
		this.panelMenu.add(this.btReservations);
		this.panelMenu.add(this.btQuitter);
		this.add(this.panelMenu);

		// Insertion des panels dans la fenêtre
		this.add(this.unPanelPassagers);
		this.add(this.unPanelVols);
		this.add(this.unPanelEquipages);
		this.add(this.unPanelReservations);
				
		// Rendre les boutons écoutables
		this.btPassagers.addActionListener(this);
		this.btVols.addActionListener(this);
		this.btEquipages.addActionListener(this);
		this.btReservations.addActionListener(this);
		this.btQuitter.addActionListener(this);

		this.setVisible(true);
	}

	public void afficherPanel(int choix) {
		this.unPanelPassagers.setVisible(false);
		this.unPanelVols.setVisible(false);
		this.unPanelEquipages.setVisible(false);
		this.unPanelReservations.setVisible(false);
		switch (choix) {
			case 1: this.unPanelPassagers.setVisible(true); break;
			case 2: this.unPanelVols.setVisible(true); break;
			case 3: this.unPanelEquipages.setVisible(true); break;
			case 4: this.unPanelReservations.setVisible(true); break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btPassagers) {
			this.afficherPanel(1);
		} else if (e.getSource() == this.btVols) {
			this.afficherPanel(2);
		} else if (e.getSource() == this.btEquipages) {
			this.afficherPanel(3);
		} else if (e.getSource() == this.btReservations) {
			this.afficherPanel(4);
		} else if (e.getSource() == this.btQuitter) {
			AirFrance_E5.rendreVisibleVueGenerale(false, null);
			AirFrance_E5.rendreVisibleVueConnection(true);
		}
	}
}

