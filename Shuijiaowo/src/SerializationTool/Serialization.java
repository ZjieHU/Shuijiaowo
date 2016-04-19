package SerializationTool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.util.Log;

public class Serialization {
	
	public  byte[] getSerialization(Object obj) {
		 ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		 try {
			 ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
			 objectOutputStream.writeObject(obj); 
			 objectOutputStream.flush();
			 byte[] data = arrayOutputStream.toByteArray();
			 objectOutputStream.close();  
	         arrayOutputStream.close(); 
			 return data;
		 }catch(Exception e) {
			 Log.v("myTag", e.toString());
		 }
		return null;
	}
	
	public Object getObject(byte[] data) {
		Object obj;
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
		try {
			ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
			obj = inputStream.readObject();
			inputStream.close();
			arrayInputStream.close();
			return obj;
		}catch(Exception e) {
			Log.v("myTag", e.toString());
		}
		return null;
	}
}
