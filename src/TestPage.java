import javax.swing.*;
import java.awt.*;

public class TestPage {
    private JPanel testPanel;

    public TestPage() {
        testPanel = new JPanel();
        testPanel.add(new JLabel("Bienvenue dans l'onglet Test!"));
    }

    public JPanel getPanel() {
        return testPanel;
    }
}
