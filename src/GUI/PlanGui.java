package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import Connection.DataAccessException;
import Database.PlanDB;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlanGui extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PlanGui frame = new PlanGui();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

        JButton btnGeneratePlan = new JButton("Planlæg produktion");
        contentPane.add(btnGeneratePlan);

        JTextArea outputArea = new JTextArea(25, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        contentPane.add(scrollPane);

        btnGeneratePlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PlanDB planDB = new PlanDB();
                    // Generate plans for e.g., max 5 candies
                    String planOutput = planDB.createPlannedProduction(5);
                    outputArea.setText(planOutput);
                } catch (DataAccessException ex) {
                    outputArea.setText("Fejl: " + ex.getMessage());
                }
            }
        });
    }
}
