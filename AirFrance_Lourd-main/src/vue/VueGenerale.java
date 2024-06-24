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

import controleur.Admin;
import controleur.AirFrance_E5;

public class VueGenerale extends JFrame implements ActionListener {
    private JButton btPassagers = new JButton("Passagers");
    private JButton btVols = new JButton("Vols");
    private JButton btEquipages = new JButton("Equipage");
    private JButton btReservations = new JButton("Reservations");
    private JButton btProfil = new JButton("Profil");
    private JButton btQuitter = new JButton("Quitter");
    private JPanel panelMenu = new JPanel();

    private PanelPassagers unPanelPassagers;
    private PanelVols unPanelVols;
    private PanelEquipages unPanelEquipages;
    private PanelReservations unPanelReservations;
    private PanelProfil unPanelProfil;

    public VueGenerale(Admin unUser) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        this.setTitle("AirFrance Event Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(0, 0, screenWidth, screenHeight);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.white);

        this.panelMenu.setBounds(300, 10, 1200, 30);
        this.panelMenu.setBackground(Color.white);
        this.panelMenu.setLayout(new GridLayout(1, 6));
        this.panelMenu.add(this.btPassagers);
        this.panelMenu.add(this.btVols);
        this.panelMenu.add(this.btEquipages);
        this.panelMenu.add(this.btReservations);
        this.panelMenu.add(this.btProfil);
        this.panelMenu.add(this.btQuitter);
        this.add(this.panelMenu);

        this.unPanelPassagers = new PanelPassagers();
        this.unPanelVols = new PanelVols();
        this.unPanelEquipages = new PanelEquipages();
        this.unPanelReservations = new PanelReservations();
        this.unPanelProfil = new PanelProfil(unUser);

        this.add(this.unPanelPassagers);
        this.add(this.unPanelVols);
        this.add(this.unPanelEquipages);
        this.add(this.unPanelReservations);
        this.add(this.unPanelProfil);

        this.unPanelPassagers.setVisible(false);
        this.unPanelVols.setVisible(false);
        this.unPanelEquipages.setVisible(false);
        this.unPanelReservations.setVisible(false);
        this.unPanelProfil.setVisible(false);

        this.btPassagers.addActionListener(this);
        this.btVols.addActionListener(this);
        this.btEquipages.addActionListener(this);
        this.btReservations.addActionListener(this);
        this.btProfil.addActionListener(this);
        this.btQuitter.addActionListener(this);

        this.setVisible(true);
    }

    public void afficherPanel(int choix) {
        this.unPanelPassagers.setVisible(false);
        this.unPanelVols.setVisible(false);
        this.unPanelEquipages.setVisible(false);
        this.unPanelReservations.setVisible(false);
        this.unPanelProfil.setVisible(false);
        switch (choix) {
            case 1: this.unPanelPassagers.setVisible(true); break;
            case 2: this.unPanelVols.setVisible(true); break;
            case 3: this.unPanelEquipages.setVisible(true); break;
            case 4: this.unPanelReservations.setVisible(true); break;
            case 5: this.unPanelProfil.setVisible(true); break;
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
        } else if (e.getSource() == this.btProfil) {
            this.afficherPanel(5);
        } else if (e.getSource() == this.btQuitter) {
            AirFrance_E5.rendreVisibleVueGenerale(false, null);
            AirFrance_E5.rendreVisibleVueConnection(true);
        }
    }
}
