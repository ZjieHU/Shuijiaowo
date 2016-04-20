package encodeTool;

import java.sql.Timestamp;

import android.util.Log;

public class ConverDate {
	
	public Timestamp StrConvertDate(String Time ) {
		try {
			Timestamp ts = Timestamp.valueOf(Time);
			return ts;
		}catch(Exception e) {
			Log.v("myTag", e.toString());
		}
		return null;
	}
	
}
