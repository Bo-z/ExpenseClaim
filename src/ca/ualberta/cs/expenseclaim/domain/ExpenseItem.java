/********************************
  The licenses for most software are designed to take away your
freedom to share and change it.  By contrast, the GNU General Public
License is intended to guarantee your freedom to share and change free
software--to make sure the software is free for all its users.  This
General Public License applies to most of the Free Software
Foundation's software and to any other program whose authors commit to
using it.  (Some other Free Software Foundation software is covered by
the GNU Lesser General Public License instead.)  You can apply it to
your programs, too.

 ************************************************************/

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
