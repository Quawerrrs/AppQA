import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TablettePage extends JPanel {
    private JFXTextField fileLabel;
    private final Map<JFXCheckBox, String> deviceCheckBoxes = new HashMap<>();

    // Adresses IP des appareils
    private static final String[] DEVICE_IPS = {
        "192.168.5.1", "192.168.5.2", "192.168.5.3", "192.168.5.4", "192.168.5.5",
        "192.168.0.1", "192.168.0.2", "192.168.0.3", "192.168.0.4", "192.168.0.5"
    };

    public TablettePage() {
        setLayout(new BorderLayout());

        // Panneau pour la gestion des tablettes et des cibles
        JPanel controlePanel = new JPanel();
        controlePanel.setLayout(new BoxLayout(controlePanel, BoxLayout.Y_AXIS));
        controlePanel.setBackground(new Color(30, 30, 30)); // Couleur de fond sombre pour un effet futuriste

        // Bouton pour sélectionner le fichier APK
        JFXButton selectFileButton = new JFXButton("Sélectionner le fichier APK");
        selectFileButton.setStyle("-fx-background-color: #1E88E5; -fx-text-fill: white;"); // Style de bouton
        selectFileButton.setOnAction(e -> selectFile());
        controlePanel.add(selectFileButton);

        // Étiquette pour afficher le fichier sélectionné
        fileLabel = new JFXTextField();
        fileLabel.setEditable(false);
        fileLabel.setPromptText("Aucun fichier sélectionné");
        fileLabel.setStyle("-fx-background-color: #424242; -fx-text-fill: white;"); // Style de texte
        controlePanel.add(fileLabel);

        // Bouton pour mettre à jour les appareils
        JFXButton updateButton = new JFXButton("Mettre à jour les appareils");
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Style de bouton
        updateButton.setOnAction(e -> updateDevices());
        controlePanel.add(updateButton);

        // Panneau pour afficher les appareils connectés avec cases à cocher
        JPanel devicePanel = new JPanel(new GridLayout(DEVICE_IPS.length, 1, 10, 10));
        devicePanel.setBackground(new Color(40, 40, 40)); // Couleur de fond sombre
        for (String ip : DEVICE_IPS) {
            JFXCheckBox checkBox = new JFXCheckBox("Tablette (" + ip + ")");
            checkBox.setStyle("-fx-text-fill: white;"); // Style de texte des cases à cocher
            devicePanel.add(checkBox);
            deviceCheckBoxes.put(checkBox, ip);
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

    private void updateDevices() {
        String filePath = fileLabel.getText();
        if (filePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un fichier APK.");
            return;
        }

        for (Map.Entry<JFXCheckBox, String> entry : deviceCheckBoxes.entrySet()) {
            JFXCheckBox checkBox = entry.getKey();
            String ip = entry.getValue();
            if (checkBox.isSelected() && checkConnection(ip)) {
                executeCommand("adb -s " + ip + ":5555 install -r \"" + filePath + "\"");
            }
        }

        JOptionPane.showMessageDialog(this, "Mise à jour terminée.");
    }

    private boolean checkConnection(String ip) {
        String command = "adb connect " + ip;
        String output = executeCommand(command);
        return output.contains("connected");
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
