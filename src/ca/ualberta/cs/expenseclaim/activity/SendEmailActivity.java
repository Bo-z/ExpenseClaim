package ca.ualberta.cs.expenseclaim.activity;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.expenseclaim.R;
import ca.ualberta.cs.expenseclaim.dao.ExpenseClaimDao;
import ca.ualberta.cs.expenseclaim.dao.ExpenseItemDao;
import ca.ualberta.cs.expenseclaim.domain.ExpenseClaim;
import ca.ualberta.cs.expenseclaim.util.DateUtil;

public class SendEmailActivity extends Activity {

	private TextView tv_info;
	private EditText et_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_email);
		setTitle("Send Email");
		tv_info = (TextView) findViewById(R.id.tv_info);
		et_address = (EditText) findViewById(R.id.et_address);

		Intent intent = getIntent();
		int claimId = intent.getIntExtra("claimId", 0);
		ExpenseClaim claim = ExpenseClaimDao.getInstance(this).get(claimId);
		List<HashMap<String, String>> itemList = ExpenseItemDao.getInstance(
				this).list(claimId);
		StringBuilder sb = new StringBuilder();
		sb.append("Expense Claim:\n");
		sb.append("Name: " + claim.getName() + "\n");
		sb.append("Start Date: " + DateUtil.format(claim.getStartDate()) + "\n");
		sb.append("End Date: " + DateUtil.format(claim.getEndDate()) + "\n");
		sb.append("Description: " + claim.getDescription() + "\n");
		sb.append("\nExpense Claim Item:\n");
		for (HashMap<String, String> item : itemList) {
			sb.append("Category: " + item.get("category") + "\n");
			sb.append("Date: " + item.get("date") + "\n");
			sb.append("Amount: " + item.get("amount") + item.get("unit") + "\n");
		}
		tv_info.setText(sb.toString());
	}

	public void send_click(View v) {
		String address = et_address.getText().toString().trim();
		if (TextUtils.isEmpty(address)) {
			Toast.makeText(this, "please input address!", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { address });
		intent.putExtra(Intent.EXTRA_SUBJECT, "Expense Claim");
		intent.putExtra(Intent.EXTRA_TEXT, tv_info.getText());
		startActivity(Intent.createChooser(intent, "Sending mail..."));
	}
}
