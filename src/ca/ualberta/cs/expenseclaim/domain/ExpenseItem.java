package ca.ualberta.cs.expenseclaim.domain;

import java.util.Date;

public class ExpenseItem {

	public static final String[] category_array = { "air fare",
			"ground transport", "vehicle rental", "fuel", "parking",
			"registration", "accommodation", "meal" };

	public static final String[] unit_array = { "CAD", "USD", "EUR", "GBP" };
	private int id;
	private int claimId;
	private String category;
	private Date date;
	private String description;
	private Double amount;
	private String unit;

	
	public ExpenseItem() {
		
	}
	public ExpenseItem(int id, int claimId, String category, Date date,
			String description, Double amount, String unit) {
		this.id = id;
		this.claimId = claimId;
		this.category = category;
		this.date = date;
		this.description = description;
		this.amount = amount;
		this.unit = unit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClaimId() {
		return claimId;
	}

	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
