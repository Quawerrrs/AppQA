import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TablettePage extends JPanel {
    private JLabel fileLabel;
    private final Map<JCheckBox, String> deviceCheckBoxes = new HashMap<>();
    private final JButton[] statusButtons = new JButton[10];  // 10 tablettes max
    private final JCheckBox[] checkBoxes = new JCheckBox[10];

    // Adresses IP des tablettes
    private static final String[] DEVICE_IPS = {
            "192.168.5.1", "192.168.5.2", "192.168.5.3", "192.168.5.4", "192.168.5.5",
            "192.168.5.6", "192.168.5.7", "192.168.5.8", "192.168.5.9", "192.168.5.10"
    };

    public TablettePage() {
        setLayout(new BorderLayout());

        // Panneau pour la gestion des tablettes et des cibles
        JPanel controlePanel = new JPanel();
        controlePanel.setLayout(new BoxLayout(controlePanel, BoxLayout.Y_AXIS));

        // Bouton pour sélectionner le fichier APK
        JButton selectFileButton = new JButton("Sélectionner le fichier APK");
        selectFileButton.addActionListener(e -> selectFile());
        controlePanel.add(selectFileButton);

        // Étiquette pour afficher le fichier sélectionné
        fileLabel = new JLabel("Aucun fichier sélectionné");
        controlePanel.add(fileLabel);

        // Bouton pour mettre à jour les appareils
        JButton updateButton = new JButton("Mettre à jour les tablettes");
        updateButton.addActionListener(e -> updateTablets());
        controlePanel.add(updateButton);

        // Bouton pour tout sélectionner / désélectionner
        JButton toggleSelectAllButton = new JButton("Tout sélectionner / désélectionner");
        toggleSelectAllButton.addActionListener(e -> toggleSelectAll());
        controlePanel.add(toggleSelectAllButton);

        // Bouton pour rafraîchir l'état de connexion
        JButton refreshButton = new JButton("Rafraîchir l'état de connexion");
        refreshButton.addActionListener(e -> updateStatus());
        controlePanel.add(refreshButton);

        // Panneau pour afficher les appareils connectés avec cases à cocher et boutons de statut
        JPanel devicePanel = new JPanel(new GridLayout(DEVICE_IPS.length, 2, 10, 10));  // 2 colonnes : case et bouton

        for (int i = 0; i < DEVICE_IPS.length; i++) {
            JCheckBox checkBox = new JCheckBox("Sélectionner");
            checkBoxes[i] = checkBox;
            devicePanel.add(checkBox);

            JButton statusButton = new JButton("Tablette " + (i + 1) + " (" + DEVICE_IPS[i] + ")");
            statusButton.setBackground(Color.RED);  // Initialement non connecté
            statusButtons[i] = statusButton;
            devicePanel.add(statusButton);
        }

        // Ajout du panneau de contrôle et des appareils
        add(controlePanel, BorderLayout.WEST);
        add(devicePanel, BorderLayout.CENTER);
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            fileLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    private void updateTablets() {
        String filePath = fileLabel.getText();
        if (filePath.equals("Aucun fichier sélectionné")) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un fichier APK.");
            return;
        }

        for (int i = 0; i < DEVICE_IPS.length; i++) {
            if (checkBoxes[i].isSelected() && checkConnection(DEVICE_IPS[i])) {
                executeCommand("adb -s " + DEVICE_IPS[i] + ":5555 install -r \"" + filePath + "\"");
            }
        }

        JOptionPane.showMessageDialog(this, "Mise à jour terminée pour toutes les tablettes connectées.");
    }

    private void toggleSelectAll() {
        boolean allSelected = true;
        for (JCheckBox checkBox : checkBoxes) {
            if (!checkBox.isSelected()) {
                allSelected = false;
                break;
            }
        }
        for (JCheckBox checkBox : checkBoxes) {
            checkBox.setSelected(!allSelected);
        }
    }

    private void updateStatus() {
        for (int i = 0; i < DEVICE_IPS.length; i++) {
            if (checkConnection(DEVICE_IPS[i])) {
                statusButtons[i].setBackground(Color.GREEN);  // Connecté
            } else {
                statusButtons[i].setBackground(Color.RED);  // Non connecté
            }
        }
    }

    private boolean checkConnection(String ip) {
        executeCommand("adb disconnect " + ip);  // Déconnexion pour éviter les erreurs de connexion existantes
        String response = executeCommand("adb connect " + ip);
        return response.contains("connected") && executeCommand("adb devices").contains(ip + ":5555\tdevice");
    }

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
