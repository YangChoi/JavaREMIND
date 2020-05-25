package bean;

public class FriendDTO {
	private int seq; 
	private String name; 
	private String tel1; 
	private String tel2;
	private String tel3;
	private int gender;
	private int read;
	private int movie;
	private int music; 
	private int game;
	private int shopping;
	
	// getter & setter
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getMovie() {
		return movie;
	}
	public void setMovie(int movie) {
		this.movie = movie;
	}
	public int getMusic() {
		return music;
	}
	public void setMusic(int music) {
		this.music = music;
	}
	public int getGame() {
		return game;
	}
	public void setGame(int game) {
		this.game = game;
	}
	public int getShopping() {
		return shopping;
	}
	public void setShopping(int shopping) {
		this.shopping = shopping;
	}
	
	// list에 주소값이 뜨는 toString 메서드를
	// 시퀀스 넘버와 이름이 뜨도록 오버라이드 해준다. 
	@Override
	public String toString() {
		return seq +"," +name;
	}
	
	
}
