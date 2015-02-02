package ca.ualberta.cs.expenseclaim.dao;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import android.content.Context;
import ca.ualberta.cs.expenseclaim.domain.ExpenseClaim;
import ca.ualberta.cs.expenseclaim.util.DateUtil;

public class ExpenseClaimDao {

	private static final String FILENAME = "claim.txt";

	private static List<ExpenseClaim> claimList = null;
	private static ExpenseClaimDao dao = null;
	private int maxId = 0;

	private ExpenseClaimDao(Context context) {
		claimList = new ArrayList<ExpenseClaim>();
		File file = new File(context.getFilesDir(), FILENAME);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				JSONObject jo = new JSONObject(scanner.nextLine());
				ExpenseClaim claim = new ExpenseClaim();
				claim.setId(jo.getInt("id"));
				claim.setName(jo.getString("name"));
				claim.setDescription(jo.getString("description"));
				claim.setStartDate(new Date(jo.getLong("startDate")));
				claim.setEndDate(new Date(jo.getLong("endDate")));
				claim.setStatus(jo.getString("status"));
				if (maxId < claim.getId()) {
					maxId = claim.getId();
				}
				claimList.add(claim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		maxId++;
	}

	public int getMaxId() {
		return maxId;
	}

	public static ExpenseClaimDao getInstance(Context context) {
		if (dao == null) {
			dao = new ExpenseClaimDao(context);
		}
		return dao;
	}

	private void save(Context context) {
		Collections.sort(claimList, new Comparator<ExpenseClaim>() {

			@Override
			public int compare(ExpenseClaim lhs, ExpenseClaim rhs) {
				return lhs.getStartDate().compareTo(rhs.getStartDate());
			}

		});
		File file = new File(context.getFilesDir(), FILENAME);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			for (ExpenseClaim c : claimList) {
				JSONObject jo = new JSONObject();
				jo.put("id", c.getId());
				jo.put("name", c.getName());
				jo.put("description", c.getDescription());
				jo.put("startDate", c.getStartDate().getTime());
				jo.put("endDate", c.getEndDate().getTime());
				jo.put("status", c.getStatus());
				writer.println(jo.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}

	public void add(Context context, ExpenseClaim claim) {
		claimList.add(claim);
		save(context);
		maxId++;
	}

	public void update(Context context, ExpenseClaim claim) {
		for (ExpenseClaim c : claimList) {
			if (c.getId() == claim.getId()) {
				c.setName(claim.getName());
				c.setDescription(claim.getDescription());
				c.setStartDate(claim.getStartDate());
				c.setEndDate(claim.getEndDate());
				c.setStatus(claim.getStatus());
				save(context);
				return;
			}
		}
	}

	public void delete(Context context, int id) {
		for (ExpenseClaim c : claimList) {
			if (c.getId() == id) {
				claimList.remove(c);
				save(context);
				return;
			}
		}
	}

	public ExpenseClaim get(int id) {
		for (ExpenseClaim c : claimList) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}

	private HashMap<String, String> claimToMap(ExpenseClaim claim) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", claim.getId() + "");
		map.put("name", claim.getName());
		map.put("description", claim.getDescription());
		map.put("startDate", DateUtil.format(claim.getStartDate()));
		map.put("endDate", DateUtil.format(claim.getEndDate()));
		map.put("status", claim.getStatus());
		return map;
	}

	public List<HashMap<String, String>> list() {
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		for (ExpenseClaim c : claimList) {
			HashMap<String, String> map = claimToMap(c);
			resultList.add(map);
		}
		return resultList;
	}

	public List<HashMap<String, String>> listByStatus(String status) {
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		for (ExpenseClaim c : claimList) {
			if (status.indexOf(c.getStatus()) >= 0) {
				HashMap<String, String> map = claimToMap(c);
				resultList.add(map);
			}
		}
		return resultList;
	}

}
