package service;

import model.User;

public interface SqlService {
	
	//�������ݿ�
	public void CreateDatebase(String DatebaseName); 
	
	//�Ƿ���ڱ�
	public boolean isExistsTable(String TableName);
	
	//����Token
	public void saveUSer(User user);
	
	//�õ�Token
	public User getUser();
	
}
