package chatProgram;

import java.io.Serializable;
// 문자열이 아닌 객체 보내기 
// 모든 데이터를 String이 아닌 InfoDTO로 보내고 받는다. 
enum Info {
	// 프로토콜
	JOIN, EXIT, SEND
}

public class InfoDTO implements Serializable{
	private String nickName; 
	private String message;
	private Info command;
	
	// getter & setter
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Info getCommand() {
		return command;
	}
	public void setCommand(Info command) {
		this.command = command;
	}
}
