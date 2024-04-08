import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MorpionAccueil extends JFrame implements ActionListener {
    private JButton btnJoueurHumain, btnOrdinateur;

    public MorpionAccueil() {
        setTitle("Bienvenue au Morpion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(2, 1, 0, 20));
        getContentPane().setBackground(Color.WHITE);

        btnJoueurHumain = new JButton("Joueur contre Joueur");
        btnJoueurHumain.setFont(new Font("Arial", Font.BOLD, 16));
        btnJoueurHumain.setFocusPainted(false);
        btnJoueurHumain.addActionListener(this);
        add(btnJoueurHumain);

        btnOrdinateur = new JButton("Joueur contre Ordinateur");
        btnOrdinateur.setFont(new Font("Arial", Font.BOLD, 16));
        btnOrdinateur.setFocusPainted(false);
        btnOrdinateur.addActionListener(this);
        add(btnOrdinateur);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnJoueurHumain) {
            new MorpionGUI(false);
        } else if (e.getSource() == btnOrdinateur) {
            new MorpionGUI(true);
        }
        dispose();
    }

    public static void main(String[] args) {
        new MorpionAccueil();
    }
}
