import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {
    private JFrame frame;
    private JPanel contentPane;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainPage().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Bienvenue");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Créer les panneaux pour les différentes pages
        JPanel tablettePanel = new TablettePage();
        JPanel testPanel = createTestPanel();

        // Créer le panneau de contenu avec CardLayout
        contentPane = new JPanel(new CardLayout());
        contentPane.add(tablettePanel, "Tablette");
        contentPane.add(testPanel, "Test");

        // Créer la barre de menu
        JPanel menuPanel = createMenuPanel();

        // Ajouter le panneau de menu et le panneau de contenu à la fenêtre
        frame.add(menuPanel, BorderLayout.NORTH);
        frame.add(contentPane, BorderLayout.CENTER);

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 3));

        JButton tabletteButton = new JButton("Tablette");
        JButton testButton = new JButton("Test");
        JButton exitButton = new JButton("Quitter");

        tabletteButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (contentPane.getLayout());
            cl.show(contentPane, "Tablette");
        });

        testButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (contentPane.getLayout());
            cl.show(contentPane, "Test");
        });

        exitButton.addActionListener(e -> System.exit(0));

        menuPanel.add(tabletteButton);
        menuPanel.add(testButton);
        menuPanel.add(exitButton);

        return menuPanel;
    }

    private JPanel createTestPanel() {
        JPanel testPanel = new JPanel();
        testPanel.add(new JLabel("Bienvenue dans l'onglet Test!"));
        return testPanel;
    }
}
