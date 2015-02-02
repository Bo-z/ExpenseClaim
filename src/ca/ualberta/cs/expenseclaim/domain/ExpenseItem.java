// Copyright (C) 2015 Bo Zhou bzhou2@ualberta.ca
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.


package ca.ualberta.cs.expenseclaim.domain;

import java.util.Date;


// each ExpenseItem has category currency amount date and description
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
