package vue;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class PanelPrincipal extends JPanel{

	public PanelPrincipal(String message) {
		super();
		this.setBounds(10,10,1920,1080);
		this.setLayout(null);
		this.setBackground(Color.white);
		JLabel lbTitre = new JLabel(message);
		lbTitre.setBounds(450,70,400,30);
		this.add(lbTitre);
		
		Font unePolice = new Font("Arial", Font.ITALIC, 30);
		lbTitre.setFont(unePolice);
		
		//inserer le logo
		ImageIcon uneImage = new ImageIcon("src/images/airfrance1.png");
		JLabel leLogo = new JLabel(uneImage);
		leLogo.setBounds(4, 4, 140, 140);
		this.add(leLogo);
				
		this.setVisible(false);
		
	
	}

	public PanelPrincipal() {
		//TODO Auto-generated constructor stub
		super();
		this.setBounds(10,10,1920,1080);
		this.setLayout(null);
		this.setBackground(Color.white);
		JLabel lbTitre = new JLabel();
		lbTitre.setBounds(450,70,400,30);
		this.add(lbTitre);
		
		Font unePolice = new Font("Arial", Font.ITALIC, 30);
		lbTitre.setFont(unePolice);
		
		//inserer le logo
		ImageIcon uneImage = new ImageIcon("src/images/airfrance1.png");
		JLabel leLogo = new JLabel(uneImage);
		leLogo.setBounds(4, 4, 140, 140);
		this.add(leLogo);
				
		this.setVisible(false);
		
	}
}

