import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MorpionGUI extends JFrame implements ActionListener {
    private JButton[][] boutons;
    private char[][] grille;
    private final char joueurHumain = 'X';
    private final char joueurOrdinateur = 'O';
    private final char vide = ' ';
    private final int taille = 3;
    private boolean tourJoueurHumain;
    private JLabel labelInfo;
    private boolean modeOrdinateur;

    public MorpionGUI(boolean modeOrdinateur) {
        this.modeOrdinateur = modeOrdinateur;
        grille = new char[taille][taille];
        boutons = new JButton[taille][taille];
        tourJoueurHumain = true;

        setTitle("Morpion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 350);
        setLayout(new BorderLayout());

        JPanel panelGrille = new JPanel();
        panelGrille.setLayout(new GridLayout(taille, taille));

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                boutons[i][j] = new JButton();
                boutons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                boutons[i][j].addActionListener(this);
                panelGrille.add(boutons[i][j]);
            }
        }

        JPanel panelButtons = new JPanel();
        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MorpionAccueil();
            }
        });
        panelButtons.add(retourButton);

        labelInfo = new JLabel("Tour du joueur 1 (X)");
        add(panelGrille, BorderLayout.CENTER);
        add(labelInfo, BorderLayout.SOUTH);
        add(panelButtons, BorderLayout.NORTH);

        initialiserGrille();

        setVisible(true);
    }

    private void initialiserGrille() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = vide;
                boutons[i][j].setText("");
            }
        }
    }

    private void afficherGrille() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                boutons[i][j].setText(Character.toString(grille[i][j]));
            }
        }
    }

    private boolean estGagnant(char joueur) {
        for (int i = 0; i < taille; i++) {
            if (grille[i][0] == joueur && grille[i][1] == joueur && grille[i][2] == joueur) {
                return true; // lignes
            }
            if (grille[0][i] == joueur && grille[1][i] == joueur && grille[2][i] == joueur) {
                return true; // colonnes
            }
        }
        if (grille[0][0] == joueur && grille[1][1] == joueur && grille[2][2] == joueur) {
            return true; // diagonale principale
        }
        if (grille[0][2] == joueur && grille[1][1] == joueur && grille[2][0] == joueur) {
            return true; // diagonale secondaire
        }
        return false;
    }

    private boolean estGrillePleine() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (grille[i][j] == vide) {
                    return false;
                }
            }
        }
        return true;
    }

    private void jouerCoup(int ligne, int colonne) {
        if (grille[ligne][colonne] == vide) {
            if (tourJoueurHumain) {
                grille[ligne][colonne] = joueurHumain;
                labelInfo.setText("Tour du joueur 2 (O)");
            } else {
                grille[ligne][colonne] = joueurOrdinateur;
                labelInfo.setText("Tour du joueur 1 (X)");
            }
            tourJoueurHumain = !tourJoueurHumain;
            afficherGrille();

            if (estGagnant(joueurHumain)) {
                JOptionPane.showMessageDialog(this, "Le joueur 1 a gagné !");
                initialiserGrille();
            } else if (estGagnant(joueurOrdinateur)) {
                if (modeOrdinateur)
                    JOptionPane.showMessageDialog(this, "Dommage ! L'ordinateur a gagné !");
                else
                    JOptionPane.showMessageDialog(this, "Bravo ! Joueur 2 a gagné !");

                initialiserGrille();
            } else if (estGrillePleine()) {
                JOptionPane.showMessageDialog(this, "Match nul !");
                initialiserGrille();
            } else if (!tourJoueurHumain && modeOrdinateur) {
                jouerCoupOrdinateur();
            }
        }
    }

    private void jouerCoupOrdinateur() {
        Random rand = new Random();
        int ligne, colonne;
        do {
            ligne = rand.nextInt(taille);
            colonne = rand.nextInt(taille);
        } while (grille[ligne][colonne] != vide);

        jouerCoup(ligne, colonne);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (e.getSource() == boutons[i][j]) {
                    jouerCoup(i, j);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        new MorpionAccueil();
    }
}
