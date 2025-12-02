package Model;

import java.sql.Date;
import java.util.List;

public class Plan {
	
	private int planID;
	private Date date;
	private String status;
	private List<Recipes> recipe;
	
	public Plan(List<Recipes> recipe) {
		this.recipe = recipe;

	}
	
}
	
