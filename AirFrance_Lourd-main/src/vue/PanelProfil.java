package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controleur.Admin;
import controleur.Controleur;
import modele.Modele;
import utils.HashUtil;
import utils.PasswordValidator;

public class PanelProfil extends PanelPrincipal implements ActionListener {
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton updateButton;

    private Admin admin;
    private JPanel panelFormProfil;

    public PanelProfil(Admin admin) {
        super(); // Appel explicite au constructeur de la classe parente
        this.admin = admin;

        // Configuration du panel de formulaire de profil
        panelFormProfil = new JPanel();
        panelFormProfil.setBackground(new Color(240, 248, 255));
        panelFormProfil.setBounds(640, 170, 350, 400);
        panelFormProfil.setLayout(new GridLayout(10, 1, 10, 10)); // 10 rows, 1 column, 10px horizontal and vertical gaps
        panelFormProfil.setBorder(new EmptyBorder(10, 10, 10, 10)); // Adding padding around the panel

        JLabel titleLabel = new JLabel("Profil", JLabel.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f)); // Increase font size for the title
        panelFormProfil.add(titleLabel);

        panelFormProfil.add(new JLabel("Prénom:"));
        prenomField = new JTextField(admin.getPrenom());
        panelFormProfil.add(prenomField);

        panelFormProfil.add(new JLabel("Email:"));
        emailField = new JTextField(admin.getEmail());
        panelFormProfil.add(emailField);

        panelFormProfil.add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        panelFormProfil.add(passwordField);

        panelFormProfil.add(new JLabel("Confirmer mot de passe:"));
        confirmPasswordField = new JPasswordField();
        panelFormProfil.add(confirmPasswordField);

        updateButton = new JButton("Mettre à jour");
        updateButton.addActionListener(this);
        panelFormProfil.add(updateButton);

        this.setLayout(null); // Utiliser un layout nul pour des positions absolues
        this.add(panelFormProfil); // Ajouter le panel de formulaire de profil à ce panel
    }

    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == updateButton) {
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!PasswordValidator.verifMdp(password)) {
            JOptionPane.showMessageDialog(null, "Le mot de passe doit contenir au moins 8 caractères, " +
                    "3 minuscules, 1 majuscule, 2 chiffres et 1 caractère spécial.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        admin.setPrenom(prenomField.getText());
        admin.setEmail(emailField.getText());
        admin.setMdp(HashUtil.hashPassword(password)); // Hachage du mot de passe

        // Message de débogage
        System.out.println("Avant appel de Controleur.updateAdmin avec admin ID: " + admin.getIdAdmin());

        // Assurez-vous que la connexion est établie
        Modele.uneBDD.seConnecter();

        Controleur.updateAdmin(admin);
        JOptionPane.showMessageDialog(null, "Informations mises à jour avec succès");
        refreshForm(); // Rafraîchir le formulaire après mise à jour
    }
}




    private void refreshForm() {
        // Rafraîchir les champs avec les valeurs actuelles de l'objet admin
        prenomField.setText(admin.getPrenom());
        emailField.setText(admin.getEmail());
        passwordField.setText("");
        confirmPasswordField.setText("");
    }
}
