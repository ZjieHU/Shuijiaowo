package service;

import java.util.ArrayList;
import java.util.TreeMap;

import model.Audio;
import model.Clock;
import model.User;
import FileOperation.BitmapOperation;
import SerializationTool.Serialization;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

public class SqlServiceImpl implements SqlService {

	private static Context context;
	
	private SQLiteDatabase sqliteDatebase;
	
	private Serialization serialization;
	
	private TreeMap<String,Bitmap> PictureMap = new TreeMap<String,Bitmap>(); //Í¼Æ¬Ê÷
	
	private BitmapOperation bitmapOperation;
	
	public SqlServiceImpl(Context c) {
		serialization = new Serialization();
		this.context = c;
		bitmapOperation = new BitmapOperation(context);
	}
	
	@Override
	public void CreateDatebase(String DatebaseName) {
		sqliteDatebase = context.openOrCreateDatabase(DatebaseName, 0, null);
	}

	@Override
	public boolean isExistsTable(String TableName) {
		CreateDatebase("TOKEN");
//		sqliteDatebase.execSQL("DROP TABLE "+TableName);
		Cursor cursor = sqliteDatebase.rawQuery("SELECT count(*) FROM"
				+ " sqlite_master WHERE type='table' AND name='" + TableName +"'"
				, null);
		if(cursor.moveToNext()) {
			if(cursor.getInt(0) == 0) {
				sqliteDatebase.execSQL("CREATE TABLE " + TableName
						+ "(id INTEGER PRIMARY KEY AUTOINCREMENT , Token VARCHAR ,"
						+ " ClockList TEXT, AudioList Text"
						+ ", Version VARCHAR)");
			}
		}
		cursor.close();
		return false;
	}

	@Override
	public void saveUSer(User user) {
		isExistsTable("Token");
		//±£´æÍ¼Æ¬
		bitmapOperation.saveBitmap(user.getPictureMap().get("Welcome"), "PictureWel");
		bitmapOperation.saveBitmap(user.getPictureMap().get("Banner"), "PictureBan");
		
		Cursor cursor = sqliteDatebase.rawQuery("SELECT * FROM Token", null);
		byte[] clock = serialization.getSerialization(user.getClockList());
//		Log.v("myTag", user.getClockList().size()+"length");
		byte[] audio = serialization.getSerialization(user.getAudioMeList());
		ContentValues contentValues = new ContentValues();
		contentValues.put("Token", user.getToken()); 
		contentValues.put("ClockList", clock);
		contentValues.put("AudioList", audio);
		contentValues.put("Version", user.getVersion());
		if(cursor.moveToNext()) { 
			sqliteDatebase.update("TOKEN", contentValues, null, null);
		}else {
			String sql = "INSERT INTO TOKEN VALUES (NULL, ? , ? , ? , ?)";
			sqliteDatebase.execSQL(sql, new Object[]{user.getToken(), clock,
					audio, user.getVersion()});
		}
	}

	@Override
	public User getUser() {
		isExistsTable("Token");
		//µÃµ½Í¼Æ¬
		PictureMap.put("Welcome",bitmapOperation.getBitmap("PictureWel"));
		PictureMap.put("Banner",bitmapOperation.getBitmap("PictureBan"));
		
		Cursor cursor = sqliteDatebase.rawQuery("SELECT * FROM Token", null);
		User user = new User();
		if(cursor.moveToNext()) {
			byte[] clockData = cursor.getBlob(cursor.getColumnIndex("ClockList"));
			user.setClockList((ArrayList<Clock>) serialization.getObject(clockData));
			byte[] audioDate = cursor.getBlob(cursor.getColumnIndex("AudioList"));
			user.setAudioMeList((ArrayList<Audio>) serialization.getObject(audioDate));
			user.setToken(cursor.getString(cursor.getColumnIndex("Token")));
			user.setVersion(cursor.getString(cursor.getColumnIndex("Version")));
			user.setPictureMap(PictureMap);
		}
		return user;
	}
}
