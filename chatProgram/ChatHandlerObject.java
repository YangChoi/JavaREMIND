package chatProgram;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ChatHandlerObject extends Thread{
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	private Socket socket;
	private List<ChatHandlerObject> list;
	// 클라이언트와 직접 교류를 하는 handler
	
	public ChatHandlerObject(Socket socket, List<ChatHandlerObject> list) throws IOException{
		this.socket = socket;
		this.list = list;
		
		// 클라이언트에서 먼저 데이터를 보내주므로 
		// 서버에서는 클라이언트 데이터 받기를 먼저 하기 위해 writer를 먼저 생성해준다 (출력 스트림 먼저 생성)
		writer = new ObjectOutputStream(socket.getOutputStream());
		reader = new ObjectInputStream(socket.getInputStream());
		
	}
	
	@Override
	public void run() {
		InfoDTO infoDTO = null;
		String nickName = null;
		// 메시지 보내기, 퇴장 기능에 닉네임 필요하기 때문에 따로 선언해준다. 
		
			while(true) {
				try {
					infoDTO = (InfoDTO)reader.readObject(); // dto를 가져온다.
					
					if(infoDTO.getCommand() == Info.JOIN){
						nickName = infoDTO.getNickName(); // 닉네임 꺼내 따로 보관
						
						// 나외의 모든 클라이언트들에게도 입장 메시지를 보낸다. 
						InfoDTO sendDTO = new InfoDTO();
						sendDTO.setCommand(Info.SEND);
						sendDTO.setMessage(nickName + "님이 입장하였습니다");
						broadcast(sendDTO);
					}else if(infoDTO.getCommand() == Info.EXIT) {
						InfoDTO sendDTO = new InfoDTO();
						
						// 1. 나가려고 EXIT 보낸 클라이언트에 대한 답장
						sendDTO.setCommand(Info.EXIT);
						writer.writeObject(sendDTO);
						writer.flush();
						
						reader.close();
						writer.close();
						socket.close();
						// 나에게만 보내는 것이므로 braodcast 하지 않는다. 
						
						// 2. 남아있는 클라이언트들에게 EXIT 메시지 보내기
						list.remove(this); // 리스트에서 나 자신을 지운다. 
						
						sendDTO.setCommand(Info.SEND);
						sendDTO.setMessage(nickName + "님이 퇴장하였습니다");
						
						broadcast(sendDTO);
						break;
					}else if(infoDTO.getCommand() == Info.SEND) {
						InfoDTO sendDTO = new InfoDTO();
						sendDTO.setCommand(Info.SEND);
						sendDTO.setMessage("[" +nickName+ "]" + infoDTO.getMessage());
						broadcast(sendDTO);
					}
				}catch(IOException e) {
					e.printStackTrace();
				}catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}// while
			
	}
	public void broadcast(InfoDTO sendDTO) {
		for(ChatHandlerObject handler : list) {
			try {
				handler.writer.writeObject(sendDTO); // 현재 메시지를 보낸다. 
				handler.writer.flush(); // 버퍼 비워주기 
			}catch(IOException e) {
				e.printStackTrace();
			}
		}// for
		
	}

	public static void main(String[] args) {
		

	}

}
