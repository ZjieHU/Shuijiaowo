package service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqlServiceImpl implements SqlService {

	private static Context context;
	
	private static SqlServiceImpl sqlServiceImpl;
	private SQLiteDatabase sqliteDatebase;
	
	public SqlServiceImpl getSqlServiceImpl(Context c) {
		if(sqlServiceImpl == null) {
			sqlServiceImpl = new SqlServiceImpl();
			sqlServiceImpl.context = c;
		}
		return sqlServiceImpl;
	}
	
	@Override
	public void CreateDatebase(String DatebaseName) {
		sqliteDatebase = context.openOrCreateDatabase(DatebaseName, 0, null);
	}

	@Override
	public boolean isExistsTable(String TableName) {
		Cursor cursor = sqliteDatebase.rawQuery("SELECT count(*) FROM"
				+ " sqlite_master WHERE type='table' AND name='" + TableName +"'"
				, null);
		if(cursor.moveToNext()) {
			if(cursor.getInt(0) == 0) {
				sqliteDatebase.execSQL("CREATE TABLE " + TableName
						+ "Token VARCHAR");
			}
		}
		cursor.close();
		return false;
	}

	@Override
	public void saveToken(String Token) {
		isExistsTable("Token");
		Cursor cursor = sqliteDatebase.rawQuery("SELECT * FROM Token"
				, null);
		if(cursor.moveToNext()) {
			ContentValues contentValue = new ContentValues();
			contentValue.put("Token", Token);
			sqliteDatebase.update("Token", contentValue, null, null);
		}else {
			sqliteDatebase.rawQuery("INSERT INTO TOKEN VALUES (?", new String[] {Token});
		}
		cursor.close();
	}

	@Override
	public String getToken() {
		String Token = new String();
		Cursor cursor = sqliteDatebase.rawQuery("SELECT * FROM Token", null);
		if(cursor.moveToNext()) {
			return cursor.getString(cursor.getColumnIndex("Token"));
		}
		cursor.close();
		return null;
	}
}
