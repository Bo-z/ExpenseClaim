package ca.ualberta.cs.expenseclaim.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import ca.ualberta.cs.expenseclaim.R;
import ca.ualberta.cs.expenseclaim.dao.ExpenseClaimDao;
import ca.ualberta.cs.expenseclaim.dao.ExpenseItemDao;

public class ListActivity extends Activity implements OnItemClickListener {

	private String operate;
	private String kind;
	private ListView listView;
	private List<HashMap<String, String>> dataList;
	private TextView tvTotal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		listView = (ListView) findViewById(R.id.listView);
		tvTotal=(TextView) findViewById(R.id.tv_total);
		
		Intent intent = getIntent();
		kind = intent.getStringExtra("kind");
		operate = intent.getStringExtra("operate");
		if (kind.equals("claim")) {
			if (operate.equals("check") || operate.equals("edit")
					|| operate.equals("delete")) {
				setTitle("Select Claim");
			} else if (operate.equals("list")) {
				setTitle("List Claim");
			}
		} else if (kind.equals("item")) {
			if (operate.equals("edit") || operate.equals("delete")) {
				setTitle("Select Claim Item");
			} else if (operate.equals("list")) {
				setTitle("List Claim Item");
			}
		}

		SimpleAdapter adapter;
		if (kind.equals("claim")) {
			String status = intent.getStringExtra("status");
			if (status != null) {
				dataList = ExpenseClaimDao.getInstance(this).listByStatus(
						intent.getStringExtra("status"));
			} else {
				dataList = ExpenseClaimDao.getInstance(this).list();
			}
			adapter = new SimpleAdapter(this, dataList, R.layout.view_claim,
					new String[] { "name", "description", "startDate",
							"endDate", "status", "description" }, new int[] {
							R.id.tv_name, R.id.tv_description, R.id.tv_start,
							R.id.tv_end, R.id.tv_status, R.id.tv_description });
		} else {
			int claimId = intent.getIntExtra("claimId", 0);
			dataList = ExpenseItemDao.getInstance(this).list(claimId);
			adapter = new SimpleAdapter(this, dataList, R.layout.view_item,
					new String[] { "category", "date", "description", "amount",
							"unit" }, new int[] { R.id.tv_category,
							R.id.tv_date, R.id.tv_description, R.id.tv_amount,
							R.id.tv_unit });
			Map<String,Double> total=ExpenseItemDao.getInstance(this).total(claimId);
			StringBuilder sb=new StringBuilder();
			sb.append("Total:\n");
			for(String key:total.keySet()){
				sb.append(String.format("%s: %1.2f\n", key,total.get(key)));
			}
			tvTotal.setText(sb.toString());
		}
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		HashMap<String, String> item = dataList.get(position);
		Intent data = new Intent();
		int cid = Integer.parseInt(item.get("id"));
		data.putExtra("kind", kind);
		data.putExtra("id", cid);
		if (item.containsKey("claimId")) {
			data.putExtra("claimId", Integer.parseInt(item.get("claimId")));
		}
		setResult(0, data);
		finish();
	}

}
