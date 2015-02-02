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




package ca.ualberta.cs.expenseclaim.activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import ca.ualberta.cs.expenseclaim.R;
import ca.ualberta.cs.expenseclaim.dao.ExpenseItemDao;
import ca.ualberta.cs.expenseclaim.domain.ExpenseItem;
import ca.ualberta.cs.expenseclaim.util.ArrayUtil;

public class EditItemActivity extends Activity {

	private String operate;
	private int id;
	private int claimId;
	private EditText etDescription;
	private EditText etAmount;
	private DatePicker dpDate;
	private Spinner spCategory;
	private Spinner spUnit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);

		spCategory = (Spinner) findViewById(R.id.sp_category);
		spUnit = (Spinner) findViewById(R.id.sp_unit);
		etDescription = (EditText) findViewById(R.id.et_description);
		etAmount = (EditText) findViewById(R.id.et_amount);
		dpDate = (DatePicker) findViewById(R.id.dp_date);

		spCategory.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				ExpenseItem.category_array));
		spUnit.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ExpenseItem.unit_array));

		Calendar calDate = Calendar.getInstance();
		Intent intent = getIntent();
		claimId = intent.getIntExtra("claimId", 0);
		operate = intent.getStringExtra("operate");
		if (operate.equals("edit")) {
			setTitle("Edit Item");
			id = intent.getIntExtra("id", 0);
			ExpenseItem item = ExpenseItemDao.getInstance(this).get(id);
			spCategory.setSelection(ArrayUtil.getIndex(
					ExpenseItem.category_array, item.getCategory()));
			etDescription.setText(item.getDescription());
			etAmount.setText(item.getAmount() + "");
			spUnit.setSelection(ArrayUtil.getIndex(ExpenseItem.unit_array,
					item.getUnit()));
			calDate.setTime(item.getDate());
		} else {
			setTitle("Add Item");
			id = ExpenseItemDao.getInstance(this).getMaxId();
			calDate = Calendar.getInstance();
		}
		dpDate.updateDate(calDate.get(Calendar.YEAR),
				calDate.get(Calendar.MONTH), calDate.get(Calendar.DAY_OF_MONTH));
	}

	
	// save information for one expense item
	public void save_click(View v) {
		String category = spCategory.getSelectedItem().toString();
		String unit = spUnit.getSelectedItem().toString();
		String description = etDescription.getText().toString().trim();
		String amount = etAmount.getText().toString().trim();
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, dpDate.getYear());
		date.set(Calendar.MONTH, dpDate.getMonth());
		date.set(Calendar.DATE, dpDate.getDayOfMonth());
		if (TextUtils.isEmpty(category)) {
			Toast.makeText(this, "please select Category!", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (TextUtils.isEmpty(description)) {
			Toast.makeText(this, "please input Description!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(amount)) {
			Toast.makeText(this, "please input Amount!", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (TextUtils.isEmpty(unit)) {
			Toast.makeText(this, "please select Unit!", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		ExpenseItem item = new ExpenseItem(id, claimId, category,
				date.getTime(), description, Double.parseDouble(amount), unit);
		if (operate.equals("edit")) {
			ExpenseItemDao.getInstance(this).update(this, item);
			Toast.makeText(this, "One expense claim item is edited.",
					Toast.LENGTH_SHORT).show();
		} else {
			ExpenseItemDao.getInstance(this).add(this, item);
			Toast.makeText(this, "One expense claim item is added.",
					Toast.LENGTH_SHORT).show();
		}
		finish();
	}
}
