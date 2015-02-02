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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cs.expenseclaim.R;
import ca.ualberta.cs.expenseclaim.dao.ExpenseClaimDao;
import ca.ualberta.cs.expenseclaim.domain.ExpenseClaim;


// input or edit the information for one claim
public class EditClaimActivity extends Activity {

	private String operate;
	private int id;
	private EditText etName;
	private EditText etDescription;
	private DatePicker dpStart;
	private DatePicker dpEnd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_claim);

		etName = (EditText) findViewById(R.id.et_name);
		etDescription = (EditText) findViewById(R.id.et_description);
		dpStart = (DatePicker) findViewById(R.id.dp_start);
		dpEnd = (DatePicker) findViewById(R.id.dp_end);

		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		Intent intent = getIntent();
		operate = intent.getStringExtra("operate");
		if (operate.equals("edit")) {
			setTitle("Edit Item");
			id = intent.getIntExtra("id", 0);
			ExpenseClaim claim = ExpenseClaimDao.getInstance(this).get(id);
			etName.setText(claim.getName());
			etDescription.setText(claim.getDescription());
			calStart.setTime(claim.getStartDate());
			calEnd.setTime(claim.getEndDate());
		} else {
			setTitle("Add Item");
			id = ExpenseClaimDao.getInstance(this).getMaxId();
			calStart = Calendar.getInstance();
			calEnd = Calendar.getInstance();
		}
		dpStart.updateDate(calStart.get(Calendar.YEAR),
				calStart.get(Calendar.MONTH),
				calStart.get(Calendar.DAY_OF_MONTH));
		dpEnd.updateDate(calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH),
				calEnd.get(Calendar.DAY_OF_MONTH));
	}

	
	// save one travel claim when press save in edit_claim activity
	public void save_click(View v) {
		String name=etName.getText().toString().trim();
		String description = etDescription.getText().toString().trim();
		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.YEAR, dpStart.getYear());
		startDate.set(Calendar.MONTH, dpStart.getMonth());
		startDate.set(Calendar.DATE, dpStart.getDayOfMonth());
		Calendar endDate = Calendar.getInstance();
		endDate.set(Calendar.YEAR, dpEnd.getYear());
		endDate.set(Calendar.MONTH, dpEnd.getMonth());
		endDate.set(Calendar.DATE, dpEnd.getDayOfMonth());
		if (TextUtils.isEmpty(name)) {
			Toast.makeText(this, "please input Name!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(description)) {
			Toast.makeText(this, "please input Description!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		ExpenseClaim claim = new ExpenseClaim(id, name, description,
				startDate.getTime(), endDate.getTime(),
				ExpenseClaim.status_array[0]);
		if (operate.equals("edit")) {
			ExpenseClaimDao.getInstance(this).update(this, claim);
			Toast.makeText(this, "One expense claim is edited.",
					Toast.LENGTH_SHORT).show();
		} else {
			ExpenseClaimDao.getInstance(this).add(this, claim);
			Toast.makeText(this, "One expense claim is added.",
					Toast.LENGTH_SHORT).show();
		}
		finish();
	}
}
