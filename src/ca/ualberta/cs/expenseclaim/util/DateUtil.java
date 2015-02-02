package ca.ualberta.cs.expenseclaim.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	public static final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
	
	public static String format(Date date){
		return dateFormat.format(date);
	}
}
