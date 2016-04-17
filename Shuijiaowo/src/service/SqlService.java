package service;

public interface SqlService {
	
	//创建数据库
	public void CreateDatebase(String DatebaseName); 
	
	//是否存在表
	public boolean isExistsTable(String TableName);
	
	//保存Token
	public void saveToken(String Token);
	
	//得到Token
	public String getToken();
	
}
