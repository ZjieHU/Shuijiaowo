package service;

import model.User;

public interface SqlService {
	
	//创建数据库
	public void CreateDatebase(String DatebaseName); 
	
	//是否存在表
	public boolean isExistsTable(String TableName);
	
	//保存Token
	public void saveUSer(User user);
	
	//得到Token
	public User getUser();
	
}
