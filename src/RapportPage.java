import javax.swing.*;
import java.awt.*;

public class RapportPage extends JPanel {

    public RapportPage() {
        setLayout(new BorderLayout());

        // Panneau principal qui contient les 4 rubriques
        JPanel rubriquePanel = new JPanel();
        rubriquePanel.setLayout(new GridLayout(4, 1, 10, 10));  // 4 lignes, 1 colonne, espacement de 10px

        // Rubrique "Standards"
        JPanel standardPanel = createRubriquePanel("Standards");
        rubriquePanel.add(standardPanel);

        // Rubrique "3/7"
        JPanel troisSeptPanel = createRubriquePanel("3/7");
        rubriquePanel.add(troisSeptPanel);

        // Rubrique "3*20"
        JPanel troisVingtPanel = createRubriquePanel("3*20");
        rubriquePanel.add(troisVingtPanel);

        // Rubrique "10 m"
        JPanel dixMPanel = createRubriquePanel("10 m");
        rubriquePanel.add(dixMPanel);

        // Ajout du panneau de rubriques au centre
        add(rubriquePanel, BorderLayout.CENTER);
    }

    // Méthode pour créer une rubrique avec un titre et une zone de texte
    private JPanel createRubriquePanel(String rubriqueTitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Titre de la rubrique
        JLabel titleLabel = new JLabel(rubriqueTitle, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Zone de texte pour saisir ou afficher des informations du rapport
        JTextArea textArea = new JTextArea(5, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
