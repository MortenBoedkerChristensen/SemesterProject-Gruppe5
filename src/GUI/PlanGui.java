package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JScrollPane;

import Connection.DataAccessException;
import Database.PlanDB;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlanGui extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField employeeField;
    private JTextArea outputArea;
    private JButton btnGeneratePlan; // made a field so SwingWorker can re-enable it

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

        // Text field for employee name
        employeeField = new JTextField(20);
        contentPane.add(employeeField);

        btnGeneratePlan = new JButton("Planlæg produktion");
        contentPane.add(btnGeneratePlan);

        outputArea = new JTextArea(25, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        contentPane.add(scrollPane);

        btnGeneratePlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String employeeName = employeeField.getText(); // get the typed name
                if (employeeName.isEmpty()) {
                    outputArea.setText("Skriv venligst et medarbejdernavn.");
                    return;
                }

                // disable button and run background worker
                btnGeneratePlan.setEnabled(false);
                outputArea.setText("Genererer plan...");
                new PlanSwingWorker(employeeName, 5).execute();
            }
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
		protected String doInBackground() throws Exception {
			PlanDB planDB = new PlanDB();
			// run blocking DB call off the EDT
			return planDB.createPlannedProductionForEmployee(employeeName, maxItems);
		}
		@Override
		protected void done() {
		    try {
		        String result = get();
		        outputArea.setText(result);
		    } catch (Exception e) {
		        outputArea.setText("Fejl: " + e.getMessage());
		        e.printStackTrace();
		    } finally {
		        btnGeneratePlan.setEnabled(true);
		    }
		}
}
}