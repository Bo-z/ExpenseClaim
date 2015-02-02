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

public class ExpenseClaim {

	public static final String[] status_array = { "progress", "submitted",
			"returned", "approved" };

	private int id;
	private String name;
	// destination and reason for travel
	private String description;

	// a date range of A travel claim
	private Date startDate;
	private Date endDate;

	private String status;

	public ExpenseClaim() {
	}

	public ExpenseClaim(int id, String name,String description, Date startDate,
			Date endDate,String status) {
		this.id = id;
		this.name=name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
