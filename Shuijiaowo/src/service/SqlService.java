package service;

public interface SqlService {
	
	//�������ݿ�
	public void CreateDatebase(String DatebaseName); 
	
	//�Ƿ���ڱ�
	public boolean isExistsTable(String TableName);
	
	//����Token
	public void saveToken(String Token);
	
	//�õ�Token
	public String getToken();
	
}
