package gui;

import javax.swing.*;

import connection.DataAccessException;
import controller.PlanController;

import java.awt.*;
import java.util.List;

import model.Plan;
import model.PlanItem;

public class ViewPlansFrame extends JFrame {

    private JTextArea outputArea;

    public ViewPlansFrame(List<Plan> plans) {
        setTitle("Alle planer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(150, 150, 700, 500);

        outputArea = new JTextArea(25, 60);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        displayPlans(plans);
    }

    private void displayPlans(List<Plan> plans) {
        try {
            PlanController controller = new PlanController();
            String formattedText = controller.formatPlans(plans);
            outputArea.setText(formattedText);
        } catch (DataAccessException e) {
            outputArea.setText("Fejl ved databaseadgang: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
