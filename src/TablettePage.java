import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TablettePage extends JPanel {
    private JLabel fileLabel;

    // Adresses IP des appareils
    private static final String[] DEVICE_IPS = {
        "192.168.5.1", "192.168.5.2", "192.168.5.3", "192.168.5.4", "192.168.5.5",
        "192.168.5.6", "192.168.5.7", "192.168.5.8", "192.168.5.9", "192.168.5.10",
        
    };

    public TablettePage() {
        setLayout(new BorderLayout());

        // Panneau pour la gestion des tablettes et des cibles
        JPanel controlePanel = new JPanel();
        controlePanel.setLayout(new BoxLayout(controlePanel, BoxLayout.Y_AXIS));

        // Bouton pour sélectionner le fichier APK
        JButton selectFileButton = new JButton("Sélectionner le fichier APK");
        selectFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });
        controlePanel.add(selectFileButton);

        // Étiquette pour afficher le fichier sélectionné
        fileLabel = new JLabel("Aucun fichier sélectionné");
        controlePanel.add(fileLabel);

        // Bouton pour mettre à jour les appareils
        JButton updateButton = new JButton("Mettre à jour les appareils");
        controlePanel.add(updateButton);

        // Panneau pour afficher les appareils connectés
        JPanel devicePanel = new JPanel(new GridLayout(DEVICE_IPS.length, 1, 10, 10));
        for (String ip : DEVICE_IPS) {
            devicePanel.add(new JLabel("Appareil (" + ip + ")"));
        }

        // Ajout du panneau de contrôle et des appareils
        add(controlePanel, BorderLayout.WEST);
        add(devicePanel, BorderLayout.CENTER);
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            fileLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    // Exemple de commande ADB, peut être utilisée dans la gestion des appareils
    private String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
