package GUI;

import java.awt.EventQueue;
import Database.RecipeDB;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Model.*;

import Connection.DataAccessException;
import Database.RecipeDB;
import Model.Recipes;
import Test.TryMe;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlanGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public PlanGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton = new JButton("Planlæg produktion");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openNewWindow();
			}
		});
		contentPane.add(btnNewButton);

	}
	
	private void openNewWindow() {
	    try {
	        int candyId = 1; // test
	        RecipeDB rDB = new RecipeDB();
	        Recipes recipe = rDB.getRecipeByCandyId(candyId);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(0, 1)); // dynamic rows

	        if (recipe == null) {
	            panel.add(new JLabel("Ingen opskrift fundet for CandyID " + candyId));
	        } else {
	            panel.add(new JLabel("Navn: " + recipe.getName()));
	            panel.add(new JLabel("Sværhedsgrad: " + recipe.getDifficulty()));
	            
	            panel.add(new JLabel("Ingredienser:" + recipe.getIngridients().toString()));
	            


	            // Example button (you can add more)
	            JButton btnPlan = new JButton("Afslut produktion");
	            panel.add(btnPlan);

	            btnPlan.addActionListener(e -> {
	                System.out.println("Produktion afsluttet");
	                panel.setVisible(false);
	                contentPane.setVisible(false);
	            });
	        }

	        // Replace GUI content
	        setContentPane(panel);
	        revalidate();
	        repaint();

	    } catch (DataAccessException e) {
	        e.printStackTrace();
	    }
	}
}
