import javax.swing.*;
import java.awt.*;

public class MainApp {
    private JFrame frame;
    private JPanel contentPane;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Bienvenue");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Créer les panneaux pour les différentes pages
        JPanel tablettePanel = new TablettePage();
        JPanel testPanel = createTestPanel();
        JPanel rapportPanel = createRapportPanel();  // Utilisation de la page Rapport

        // Créer le panneau de contenu avec CardLayout
        contentPane = new JPanel(new CardLayout());
        contentPane.add(tablettePanel, "Tablette");
        contentPane.add(testPanel, "Test");
        contentPane.add(rapportPanel, "Rapport");

        // Créer la barre de navigation en haut
        JPanel menuPanel = createMenuPanel();

        // Ajouter le panneau de menu et le panneau de contenu à la fenêtre
        frame.add(menuPanel, BorderLayout.NORTH);
        frame.add(contentPane, BorderLayout.CENTER);

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 4));  // 4 colonnes pour 4 boutons

        // Boutons de navigation
        JButton tabletteButton = new JButton("Tablette");
        JButton testButton = new JButton("Test");
        JButton rapportButton = new JButton("Rapport de Test");  // Nouveau bouton
        JButton exitButton = new JButton("Quitter");

        // Actions des boutons
        tabletteButton.addActionListener(e -> showPanel("Tablette"));
        testButton.addActionListener(e -> showPanel("Test"));
        rapportButton.addActionListener(e -> showPanel("Rapport"));
        exitButton.addActionListener(e -> System.exit(0));

        // Ajouter les boutons au panneau de menu
        menuPanel.add(tabletteButton);
        menuPanel.add(testButton);
        menuPanel.add(rapportButton);  // Ajout du bouton Rapport
        menuPanel.add(exitButton);

        return menuPanel;
    }

    private void showPanel(String panelName) {
        CardLayout cl = (CardLayout) (contentPane.getLayout());
        cl.show(contentPane, panelName);
    }

    private JPanel createTestPanel() {
        JPanel testPanel = new JPanel();
        testPanel.add(new JLabel("Bienvenue dans l'onglet Test!"));
        return testPanel;
    }

    // Intégration de la page RapportPage dans createRapportPanel
    private JPanel createRapportPanel() {
        return new RapportPage();  // Utilisation de la classe RapportPage pour la page Rapport
    }
}
