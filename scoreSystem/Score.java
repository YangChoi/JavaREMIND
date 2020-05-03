package scoreSystem;

interface Score {
	// 추상메서드 
	// 여기서 이걸 구현하겠다는 것을 미리 알림 
	public void insert();
	public void print();
	public void search();
	public void sort();
	public void save();
	public void load();
	public void close();
}
