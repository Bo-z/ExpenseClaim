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
import ca.ualberta.cs.expenseclaim.domain.ExpenseItem;
import ca.ualberta.cs.expenseclaim.util.DateUtil;

public class ExpenseItemDao {

	private static final String FILENAME = "item.txt";

	private static List<ExpenseItem> itemList = null;
	private static ExpenseItemDao dao = null;
	private int maxId = 0;

	private ExpenseItemDao(Context context) {
		itemList = new ArrayList<ExpenseItem>();
		File file = new File(context.getFilesDir(), FILENAME);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				JSONObject jo = new JSONObject(scanner.nextLine());
				ExpenseItem item = new ExpenseItem();
				item.setId(jo.getInt("id"));
				item.setClaimId(jo.getInt("claimId"));
				item.setCategory(jo.getString("category"));
				item.setDescription(jo.getString("description"));
				item.setDate(new Date(jo.getLong("date")));
				item.setAmount(jo.getDouble("amount"));
				item.setUnit(jo.getString("unit"));
				if (maxId < item.getId()) {
					maxId = item.getId();
				}
				itemList.add(item);
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

	public static ExpenseItemDao getInstance(Context context) {
		if (dao == null) {
			dao = new ExpenseItemDao(context);
		}
		return dao;
	}

	private void save(Context context) {
		Collections.sort(itemList,new Comparator<ExpenseItem>(){

			@Override
			public int compare(ExpenseItem lhs, ExpenseItem rhs) {
				return lhs.getDate().compareTo(rhs.getDate());
			}
			
		});
		File file = new File(context.getFilesDir(), FILENAME);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			for (ExpenseItem i : itemList) {
				JSONObject jo = new JSONObject();
				jo.put("id", i.getId());
				jo.put("claimId", i.getClaimId());
				jo.put("category", i.getCategory());
				jo.put("description", i.getDescription());
				jo.put("date", i.getDate().getTime());
				jo.put("amount", i.getAmount());
				jo.put("unit", i.getUnit());
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

	public void add(Context context, ExpenseItem item) {
		itemList.add(item);
		save(context);
		maxId++;
	}

	public void update(Context context, ExpenseItem item) {
		for (ExpenseItem i : itemList) {
			if (i.getId() == item.getId()) {
				i.setClaimId(item.getClaimId());
				i.setCategory(item.getCategory());
				i.setDescription(item.getDescription());
				i.setDate(item.getDate());
				i.setAmount(item.getAmount());
				i.setUnit(item.getUnit());
				save(context);
				return;
			}
		}
	}

	public void delete(Context context, int id, int claimId) {
		for (ExpenseItem i : itemList) {
			if (i.getId() == id && i.getClaimId() == claimId) {
				itemList.remove(i);
				save(context);
				return;
			}
		}
	}

	public ExpenseItem get(int id) {
		for (ExpenseItem i : itemList) {
			if (i.getId() == id) {
				return i;
			}
		}
		return null;
	}

	public List<HashMap<String, String>> list(int claimId) {
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		for (ExpenseItem i : itemList) {
			if (i.getClaimId() == claimId) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", i.getId() + "");
				map.put("claimId", i.getClaimId() + "");
				map.put("category", i.getCategory());
				map.put("description", i.getDescription());
				map.put("date", DateUtil.format(i.getDate()));
				map.put("amount", i.getAmount() + "");
				map.put("unit", i.getUnit());
				resultList.add(map);
			}
		}
		return resultList;
	}

	public HashMap<String, Double> total(int claimId) {
		HashMap<String, Double> map = new HashMap<String, Double>();
		for (ExpenseItem i : itemList) {
			if (i.getClaimId() == claimId) {
				String unit=i.getUnit();
				Double amount;
				if(map.containsKey(unit)){
					amount=map.get(unit);
				}else{
					amount=0.0;
				}
				amount+=i.getAmount();
				map.put(unit, amount);
			}
		}
		return map;
	}

}
