package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JScrollPane;

import connection.DataAccessException;
import controller.PlanController;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlanGui extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField employeeField;
    private JTextArea outputArea;
    private JButton btnGeneratePlan;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PlanGui frame = new PlanGui();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PlanGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Text field for employee name
        employeeField = new JTextField(20);
        contentPane.add(employeeField);

        btnGeneratePlan = new JButton("Planlæg produktion");
        contentPane.add(btnGeneratePlan);

        outputArea = new JTextArea(25, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        contentPane.add(scrollPane);

        btnGeneratePlan.addActionListener(e -> {
            String employeeName = employeeField.getText().trim();
            if (employeeName.isEmpty()) {
                outputArea.setText("Skriv venligst et medarbejdernavn.");
                return;
            }

            btnGeneratePlan.setEnabled(false);
            outputArea.setText("Genererer plan...");

            // Run background task via SwingWorker
            new PlanSwingWorker(employeeName, 5).execute();
        });
    }

    private class PlanSwingWorker extends SwingWorker<String, Void> {
        private final String employeeName;
        private final int maxItems;

        private PlanSwingWorker(String employeeName, int maxItems) {
            this.employeeName = employeeName;
            this.maxItems = maxItems;
        }

        @Override
        protected String doInBackground() {
            try {
                PlanController controller = new PlanController();
                return controller.createPlannedProductionForEmployee(employeeName, maxItems);
            } catch (DataAccessException e) {
                e.printStackTrace();
                return "Fejl ved databaseadgang: " + e.getMessage();
            }
        }

        @Override
        protected void done() {
            try {
                outputArea.setText(get());
            } catch (Exception e) {
                outputArea.setText("Fejl: " + e.getMessage());
                e.printStackTrace();
            } finally {
                btnGeneratePlan.setEnabled(true);
            }
        }
    }
}
