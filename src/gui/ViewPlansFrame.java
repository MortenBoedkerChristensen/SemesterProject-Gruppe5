package gui;

import javax.swing.*;
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
        if (plans == null || plans.isEmpty()) {
            outputArea.setText("Ingen planer fundet.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (Plan plan : plans) {
            sb.append("Plan ID: ").append(plan.getPlanID())
              .append(", Dato: ").append(plan.getDate()).append("\n");

            for (PlanItem item : plan.getItems()) {
                sb.append("  Candy: ").append(item.getCandy().getName())
                  .append(", Recipe: ").append(item.getRecipe().getName())
                  .append(", Qty: ").append(item.getQty())
                  .append(", Ingredients: ");

                item.getRecipe().getIngridients().forEach((ing, qty) ->
                    sb.append(ing).append(" ").append(qty).append(", ")
                );
                sb.setLength(sb.length() - 2); // remove trailing comma
                sb.append("\n");
            }

            sb.append("\n");
        }

        outputArea.setText(sb.toString());
    }
}
