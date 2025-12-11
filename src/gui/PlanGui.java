package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingWorker;

import controller.PlanController;
import connection.DataAccessException;
import model.Plan;
import model.PlanItem;

public class PlanGui extends JFrame {

    private JPanel contentPane;
    private JTextField employeeField;
    private JTextArea outputArea;
    private JButton btnGeneratePlan;
    private JButton btnViewPlans;

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
        setTitle("Planlæg produktion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setContentPane(contentPane);

        employeeField = new JTextField(20);
        contentPane.add(employeeField);

        btnGeneratePlan = new JButton("Planlæg produktion");
        contentPane.add(btnGeneratePlan);

        btnViewPlans = new JButton("Se alle planer");
        contentPane.add(btnViewPlans);

        outputArea = new JTextArea(25, 60);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        contentPane.add(scrollPane);

        btnGeneratePlan.addActionListener(this::handleGeneratePlan);
        btnViewPlans.addActionListener(this::handleViewPlans);
    }

    private void handleGeneratePlan(ActionEvent e) {
        String employeeName = employeeField.getText().trim();
        if (employeeName.isEmpty()) {
            outputArea.setText("Skriv venligst et medarbejdernavn.");
            return;
        }

        btnGeneratePlan.setEnabled(false);
        outputArea.setText("Genererer plan...");

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                try {
                    PlanController controller = new PlanController();
                    Plan plan = controller.createPlannedProductionForEmployee(employeeName, 5);

                    if (plan == null || plan.getItems().isEmpty()) {
                        return "Ingen plan blev oprettet.";
                    }

                    StringBuilder sb = new StringBuilder();
                    sb.append("Plan ID: ").append(plan.getPlanID())
                      .append(", Dato: ").append(plan.getDate()).append("\n\n");

                    for (PlanItem item : plan.getItems()) {
                        sb.append("Candy: ").append(item.getCandy().getName())
                          .append(", Recipe: ").append(item.getRecipe().getName())
                          .append(", Qty: ").append(item.getQty())
                          .append(", Ingredients: ");

                        item.getRecipe().getIngridients().forEach((ing, qty) ->
                            sb.append(ing).append(" ").append(qty).append(", ")
                        );
                        sb.setLength(sb.length() - 2);
                        sb.append("\n");
                    }

                    return sb.toString();
                } catch (DataAccessException ex) {
                    ex.printStackTrace();
                    return "Fejl ved databaseadgang: " + ex.getMessage();
                }
            }

            @Override
            protected void done() {
                try {
                    outputArea.setText(get());
                } catch (Exception ex) {
                    outputArea.setText("Fejl: " + ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    btnGeneratePlan.setEnabled(true);
                }
            }
        }.execute();
    }

    private void handleViewPlans(ActionEvent e) {
        btnViewPlans.setEnabled(false);
        outputArea.setText("Henter alle planer...");

        new SwingWorker<Void, Void>() {
            List<Plan> plans;

            @Override
            protected Void doInBackground() {
                try {
                    PlanController controller = new PlanController();
                    plans = controller.getAllPlans();
                } catch (DataAccessException ex) {
                    ex.printStackTrace();
                    plans = null;
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    if (plans == null || plans.isEmpty()) {
                        outputArea.setText("Ingen planer fundet.");
                    } else {
                        ViewPlansFrame viewFrame = new ViewPlansFrame(plans);
                        viewFrame.setVisible(true);
                    }
                } finally {
                    btnViewPlans.setEnabled(true);
                }
            }
        }.execute();
    }
}
