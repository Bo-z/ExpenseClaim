package ca.ualberta.cs.expenseclaim.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import ca.ualberta.cs.expenseclaim.R;
import ca.ualberta.cs.expenseclaim.dao.ExpenseClaimDao;
import ca.ualberta.cs.expenseclaim.dao.ExpenseItemDao;
import ca.ualberta.cs.expenseclaim.domain.ExpenseClaim;

public class MainActivity extends Activity {

	public static final int REQUEST_CODE_ADD_CLAIM = 1;
	public static final int REQUEST_CODE_ADD_ITEM = 2;
	public static final int REQUEST_CODE_EDIT_CLAIM =3;
	public static final int REQUEST_CODE_EDIT_ITEM = 4;
	public static final int REQUEST_CODE_DELETE_CLAIM = 5;
	public static final int REQUEST_CODE_DELETE_ITEM = 6;
	public static final int REQUEST_CODE_LIST_CLAIM = 7;
	public static final int REQUEST_CODE_LIST_ITEM = 8;
	public static final int REQUEST_CODE_CHECK_SUBMIT = 9;
	public static final int REQUEST_CODE_CHECK_RETURN = 10;
	public static final int REQUEST_CODE_CHECK_APPROVE = 11;
	public static final int REQUEST_CODE_SEND_EMAIL = 12;

	private RadioGroup rgKind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rgKind = (RadioGroup) findViewById(R.id.rg_kind);
	}

	public void add_click(View v) {
		Intent intent;
		if (rgKind.getCheckedRadioButtonId() == R.id.rb_claim) {
			intent = new Intent(this, EditClaimActivity.class);
			intent.putExtra("operate", "add");
			intent.putExtra("kind", "claim");
			startActivity(intent);
		} else {
			intent = new Intent(this, ListActivity.class);
			intent.putExtra("operate", "add");
			intent.putExtra("kind", "claim");
			intent.putExtra("status", "progress,returned");
			startActivityForResult(intent, REQUEST_CODE_ADD_ITEM);
		}
	}

	public void edit_click(View v) {
		Intent intent;
		if (rgKind.getCheckedRadioButtonId() == R.id.rb_claim) {
			intent = new Intent(this, ListActivity.class);
			intent.putExtra("operate", "edit");
			intent.putExtra("kind", "claim");
			intent.putExtra("status", "progress,returned");
			startActivityForResult(intent, REQUEST_CODE_EDIT_CLAIM);
		} else {
			intent = new Intent(this, ListActivity.class);
			intent.putExtra("operate", "edit");
			intent.putExtra("kind", "claim");
			startActivityForResult(intent, REQUEST_CODE_EDIT_ITEM);
		}
	}

	public void delete_click(View v) {
		Intent intent;
		if (rgKind.getCheckedRadioButtonId() == R.id.rb_claim) {
			intent = new Intent(this, ListActivity.class);
			intent.putExtra("kind", "claim");
			intent.putExtra("operate", "delete");
			intent.putExtra("status", "progress,returned");
			startActivityForResult(intent, REQUEST_CODE_DELETE_CLAIM);
		}else{
			intent = new Intent(this, ListActivity.class);
			intent.putExtra("kind", "claim");
			intent.putExtra("operate", "delete");
			startActivityForResult(intent, REQUEST_CODE_DELETE_ITEM);			
		}
	}

	public void list_click(View v) {
		Intent intent = new Intent(this, ListActivity.class);
		if (rgKind.getCheckedRadioButtonId() == R.id.rb_claim) {
			intent = new Intent(this, ListActivity.class);
			intent.putExtra("operate", "list");
			intent.putExtra("kind", "claim");
			intent.putExtra("status", "progress,submitted,returned,approved");
			startActivity(intent);
		} else {
			intent = new Intent(this, ListActivity.class);
			intent.putExtra("operate", "list");
			intent.putExtra("kind", "claim");
			startActivityForResult(intent, REQUEST_CODE_LIST_ITEM);
		}
	}

	public void submit_click(View v){
		Intent intent = new Intent(this, ListActivity.class);
		intent.putExtra("operate", "check");
		intent.putExtra("status", "progress,returned");
		intent.putExtra("kind", "claim");
		startActivityForResult(intent, REQUEST_CODE_CHECK_SUBMIT);		
	}
	
	public void return_click(View v){
		Intent intent = new Intent(this, ListActivity.class);
		intent.putExtra("operate", "check");
		intent.putExtra("status", "submitted");
		intent.putExtra("kind", "claim");
		startActivityForResult(intent, REQUEST_CODE_CHECK_RETURN);				
	}
	
	public void approve_click(View v){
		Intent intent = new Intent(this, ListActivity.class);
		intent.putExtra("operate", "check");
		intent.putExtra("status", "submitted");
		intent.putExtra("kind", "claim");
		startActivityForResult(intent, REQUEST_CODE_CHECK_APPROVE);				
	}
	
	public void send_click(View v){
		Intent intent = new Intent(this, ListActivity.class);
		intent.putExtra("operate", "send");
		intent.putExtra("kind", "claim");
		startActivityForResult(intent, REQUEST_CODE_SEND_EMAIL);						
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null)
			return;
		Intent intent;
		if (requestCode == REQUEST_CODE_ADD_ITEM) {
			String kind = data.getStringExtra("kind");
			int id = data.getIntExtra("id", 0);
			if(kind.equals("claim")){
				intent = new Intent(this, EditItemActivity.class);
				intent.putExtra("operate", "add");
				intent.putExtra("claimId", id);
				startActivity(intent);
			}
		} else if (requestCode == REQUEST_CODE_EDIT_CLAIM) {
			String kind = data.getStringExtra("kind");
			int id = data.getIntExtra("id", 0);
			if (kind.equals("claim")) {
				intent = new Intent(this, EditClaimActivity.class);
				intent.putExtra("operate", "edit");
				intent.putExtra("id", id);
				startActivity(intent);
			}
		} else if (requestCode == REQUEST_CODE_EDIT_ITEM) {
			String kind = data.getStringExtra("kind");
			int id = data.getIntExtra("id", 0);
			int claimId = data.getIntExtra("claimId", 0);
			if (kind.equals("claim")) {
				intent = new Intent(this, ListActivity.class);
				intent.putExtra("operate", "edit");
				intent.putExtra("kind", "item");
				intent.putExtra("claimId", id);
				startActivityForResult(intent, REQUEST_CODE_EDIT_ITEM);
			}else if (kind.equals("item")) {
				intent = new Intent(this, EditItemActivity.class);
				intent.putExtra("operate", "edit");
				intent.putExtra("id", id);
				intent.putExtra("claimId", claimId);
				startActivity(intent);
			}
		} else if (requestCode == REQUEST_CODE_DELETE_CLAIM) {
			int id = data.getIntExtra("id", 0);
			ExpenseClaimDao.getInstance(this).delete(this, id);
			Toast.makeText(this, "Delete the item!", Toast.LENGTH_SHORT).show();
		} else if (requestCode == REQUEST_CODE_DELETE_ITEM) {
			String kind = data.getStringExtra("kind");
			int id = data.getIntExtra("id", 0);
			int claimId = data.getIntExtra("claimId", 0);
			if (kind.equals("claim")) {
				intent = new Intent(this, ListActivity.class);
				intent.putExtra("operate", "list");
				intent.putExtra("kind", "item");
				intent.putExtra("claimId", id);
				startActivityForResult(intent, REQUEST_CODE_DELETE_ITEM);
			}else if (kind.equals("item")) {
				ExpenseItemDao.getInstance(this).delete(this, id,claimId);
				Toast.makeText(this, "Delete the Claim Item!", Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == REQUEST_CODE_LIST_ITEM) {
			String kind = data.getStringExtra("kind");
			int id = data.getIntExtra("id", 0);
			if (kind.equals("claim")) {
				intent = new Intent(this, ListActivity.class);
				intent.putExtra("operate", "list");
				intent.putExtra("kind", "item");
				intent.putExtra("claimId", id);
				startActivity(intent);
			}
		} else if (requestCode == REQUEST_CODE_CHECK_SUBMIT) {
			int id = data.getIntExtra("id", 0);
			ExpenseClaim claim=ExpenseClaimDao.getInstance(this).get(id);
			claim.setStatus("submitted");
			ExpenseClaimDao.getInstance(this).update(this, claim);
			Toast.makeText(this, "The expense claim is submitted!", Toast.LENGTH_SHORT).show();
		} else if (requestCode == REQUEST_CODE_CHECK_RETURN) {
			int id = data.getIntExtra("id", 0);
			ExpenseClaim claim=ExpenseClaimDao.getInstance(this).get(id);
			claim.setStatus("returned");
			ExpenseClaimDao.getInstance(this).update(this, claim);
			Toast.makeText(this, "The expense claim is returned!", Toast.LENGTH_SHORT).show();
		} else if (requestCode == REQUEST_CODE_CHECK_APPROVE) {
			int id = data.getIntExtra("id", 0);
			ExpenseClaim claim=ExpenseClaimDao.getInstance(this).get(id);
			claim.setStatus("approved");
			ExpenseClaimDao.getInstance(this).update(this, claim);
			Toast.makeText(this, "The expense claim is approved!", Toast.LENGTH_SHORT).show();
		} else if (requestCode == REQUEST_CODE_SEND_EMAIL) {
			int id = data.getIntExtra("id", 0);
			intent=new Intent(this,SendEmailActivity.class);
			intent.putExtra("claimId", id);
			startActivity(intent);
		}
	}
}
