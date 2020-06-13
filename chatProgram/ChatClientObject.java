package chatProgram;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class ChatClientObject extends JFrame implements ActionListener, Runnable{
	private JTextArea output;
	private JTextField input;
	private JButton sendBtn;
	private Socket socket;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	
	public ChatClientObject() {
		output = new JTextArea();
		output.setFont(new Font("Serif", Font.BOLD, 16));
		output.setEditable(false); // textarea 수정 못하게 

		JScrollPane scroll = new JScrollPane(output);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // 스크롤바 항상보이기 
		
		JPanel sendP = new JPanel();
		sendP.setLayout(new BorderLayout());
		input = new JTextField();
		sendBtn = new JButton("보내기");
		sendP.add("Center", input);
		sendP.add("East", sendBtn);

		Container c = getContentPane();

		c.add("Center", scroll);
		c.add("South", sendP);

		setTitle("채팅");
		setBounds(700, 200, 400, 500);
		setVisible(true);
		
		
		// 창닫을 시 
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				InfoDTO infoDTO = new InfoDTO();
				infoDTO.setCommand(Info.EXIT);
				try {
					writer.writeObject(infoDTO);
					writer.flush();
				}catch(IOException io) {
					io.printStackTrace();
				}
			}
		});
		
	} // constructor
	
	public void service() {
		
		String serverIP = JOptionPane.showInputDialog(this, "서버 IP를 입력하세요", "192.168.28");
		
		// 서버 ip가 입력되지 않았을 시 
		if(serverIP == null || serverIP.length() == 0) {
			System.out.println("서버 IP가 입력되지 않았습니다. ");
			System.exit(0);
			
		}
		String nickName = JOptionPane.showInputDialog(this, "닉네임을 입력하세요", "닉네임", JOptionPane.INFORMATION_MESSAGE);
		
		// 닉네임 입력 없을 시 
		if(nickName == null || nickName.length() == 0) {
			nickName = "guest";
		}
		
		try {
			socket = new Socket(serverIP, 9500); // 서버 IP가 고정적이면 안된다. 
			reader = new ObjectInputStream(socket.getInputStream());
			writer = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 입력받은 닉네임 서버로 보내기 
		InfoDTO infoDTO = new InfoDTO();
		
		// 가장 무엇을 수행할지 COMMAND를 넘겨준다 
		infoDTO.setCommand(Info.JOIN); // 입장 
		infoDTO.setNickName(nickName); // 닉네임보내기 
		
		try {
			writer.writeObject(infoDTO);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 대화는 스레드로 이루어진다. 
		Thread t = new Thread(this);
		t.start();
		
		input.addActionListener(this); // 엔터기능 추가용
		sendBtn.addActionListener(this); // 보내기 버튼 엑션
		
	}
	
	@Override
	public void run() {
		// 서버로부터 응답 받기 
		InfoDTO infoDTO = null;
		
		// 대화가 이어지는 동안은 계속 실행 
		while(true) {
			try {
				// Object로 보내는 것을 infoDTO가 받아야하므로 형변환
				infoDTO = (InfoDTO)reader.readObject(); // Object로 읽어온다
				if(infoDTO.getCommand() == Info.EXIT) {
					reader.close();
					writer.close();
					socket.close();
					
					System.exit(0);
				}else if(infoDTO.getCommand() == Info.SEND) {
					output.append(infoDTO.getMessage() + "\n");
					int pos = output.getText().length();
					output.setCaretPosition(pos);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		} // while
		
	}
	
	// 버튼 액션
	@Override
	public void actionPerformed(ActionEvent e) {
		String data = input.getText();
		InfoDTO infoDTO = new InfoDTO();
		
		// data의 내용에 따라 명령이 달라진다(메시지일수도 있고 info일수도 있다)
		if(data.equals("exit")) {
			infoDTO.setCommand(Info.EXIT);
		}else {
			infoDTO.setCommand(Info.SEND);
			infoDTO.setMessage(data);
		}
		try {
			writer.writeObject(infoDTO);
		} catch (IOException io) {
			io.printStackTrace();
		}
		input.setText(""); // 보낸 후엔 input을 비워준다 
	}
	
	public static void main(String[] args) {
		new ChatClientObject().service();
	}
}
