package scoreSystem;

import java.text.DecimalFormat;

public class ScoreDTO implements Comparable{
	private int num;
	private String name; 
	private int kor; 
	private int eng; 
	private int math; 
	private int tot; 
	private double avg;
	
	public ScoreDTO(int num, String name, int kor, int eng, int math) {
		this.num =  num;
		this.name = name; 
		this.kor = kor;
		this.eng = eng;
		this.math = math;
	}
	// setter
	public void setNum(int num) {
		this.num = num;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public void setTot(int tot) {
		this.tot = tot;
	}
	public void setAvt(double avg) {
		this.avg = avg;
	}
	
	// getter
	
	public int getNum() {
		return num;
	}
	public String getName() {
		return name;
	}
	public int getKor() {
		return kor;
	}
	public int getEng() {
		return eng;
	}
	public int getMath() {
		return math;
	}
	public int getTot() {
		return tot;
	}
	public String getAvg() {
		return new DecimalFormat("0.###").format(avg);
	}
	
	public void calc() {
		tot = kor + eng + math;
		avg = tot/3.0;
		
	}
	@Override
	public int compareTo(Object object) {
		// 총점으로 내림차순 
		return this.tot < ((ScoreDTO)object).tot ? 1 : -1;
	}
	
}
