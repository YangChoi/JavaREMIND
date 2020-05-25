package action;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bean.FriendDTO;
import dao.FriendDAO;

public class FriendManager extends JFrame implements ActionListener, ListSelectionListener {
	private JLabel inputL, listL, numL, numL2, nameL, phoneL, phoneL2, phoneL3, sexL, hobbyL, analysisL;
	private JTextField nameT, phoneT1, phoneT2;
	private JComboBox phoneComboBox;
	private JRadioButton maleBtn, femaleBtn;
	private JCheckBox readBox, movieBox, musicBox, gameBox, shoppingBox;
	private JButton inputBtn, editBtn, deleteBtn, removeBtn;
	private JTextArea analysisT;
	private JList<FriendDTO> list;
	private DefaultListModel<FriendDTO> model;
	private FriendDAO friendDAO = new FriendDAO();
	private FriendDTO selectedDTO;

	public FriendManager() {
		setLayout(null);

		inputL = new JLabel("개인정보입력");
		inputL.setBounds(145, 15, 80, 25);
		
		numL = new JLabel("번          호  :");
		numL.setBounds(5, 60, 110, 25);
		numL2 = new JLabel();
		numL2.setBounds(80, 60, 25, 25);
		numL2.setOpaque(true);
		numL2.setBackground(Color.WHITE);
		
		nameL = new JLabel("이          름  :");
		nameL.setBounds(5, 100, 80, 25);
		nameT = new JTextField();
		nameT.setBounds(80, 100, 130, 25);
		
		phoneL = new JLabel("전화 번호  :");
		phoneL.setBounds(5, 140, 80, 25);
		phoneComboBox = new JComboBox();
		phoneComboBox.setModel(new DefaultComboBoxModel(new String[] {"010", "019"}));
		phoneComboBox.setBounds(80, 140, 50, 25);
		phoneL2 = new JLabel("-");
		phoneL2.setBounds(135, 140, 20, 25);
		phoneT1 = new JTextField();
		phoneT1.setBounds(145, 140, 50, 25);
		phoneL3 = new JLabel("-");
		phoneL3.setBounds(200, 140, 20, 25);
		phoneT2 = new JTextField();
		phoneT2.setBounds(210, 140, 50, 25);
		
		sexL = new JLabel("성          별  :");
		sexL.setBounds(5, 180, 80, 25);
		maleBtn = new JRadioButton("남성", true);
		maleBtn.setBounds(80, 180, 60, 25);
		maleBtn.setBackground(new Color(128, 128, 192));
		femaleBtn = new JRadioButton("여성");
		femaleBtn.setBounds(140, 180, 60, 25);
		femaleBtn.setBackground(new Color(128, 128, 192));
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(maleBtn); bg.add(femaleBtn);

		hobbyL = new JLabel("취          미  :");
		hobbyL.setBounds(5, 220, 80, 25);
		readBox = new JCheckBox("독서");
		readBox.setBounds(80, 220, 55, 25);
		readBox.setBackground(new Color(128, 128, 192));
		movieBox = new JCheckBox("영화");
		movieBox.setBounds(135, 220, 55, 25);
		movieBox.setBackground(new Color(128, 128, 192));
		musicBox = new JCheckBox("음악");
		musicBox.setBounds(190, 220, 55, 25);
		musicBox.setBackground(new Color(128, 128, 192));
		gameBox = new JCheckBox("게임");
		gameBox.setBounds(245, 220, 55, 25);
		gameBox.setBackground(new Color(128, 128, 192));
		shoppingBox = new JCheckBox("쇼핑");
		shoppingBox.setBounds(300, 220, 55, 25);
		shoppingBox.setBackground(new Color(128, 128, 192));
				
		inputBtn = new JButton("등록");
		inputBtn.setBounds(50, 270, 60, 30);
		editBtn = new JButton("수정");
		editBtn.setBounds(115, 270, 60, 30);
		deleteBtn = new JButton("삭제");
		deleteBtn.setBounds(180, 270, 60, 30);
		removeBtn = new JButton("지우기");
		removeBtn.setBounds(245, 270, 75, 30);
		
		editBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
		removeBtn.setEnabled(false);
		
		listL = new JLabel("전체목록");
		listL.setBounds(450, 15, 70, 25);
		list = new JList<FriendDTO>(new DefaultListModel<FriendDTO>());
		model = (DefaultListModel<FriendDTO>) list.getModel();
		list.setBounds(370, 50, 220, 260);
		
		analysisL = new JLabel("개인정보분석");
		analysisL.setBounds(250, 325, 80, 25);
		analysisT = new JTextArea();
		analysisT.setBounds(10, 360, 580, 100);
		
		Container c = this.getContentPane();
		c.add(inputL); 
		c.add(numL); c.add(numL2);
		c.add(nameL); c.add(nameT);
		c.add(phoneL); c.add(phoneComboBox); c.add(phoneL2); c.add(phoneT1); c.add(phoneL3); c.add(phoneT2);
		c.add(sexL); c.add(maleBtn); c.add(femaleBtn);
		c.add(hobbyL); c.add(readBox); c.add(movieBox); c.add(musicBox); c.add(gameBox); c.add(shoppingBox);
		c.add(inputBtn); c.add(editBtn); c.add(deleteBtn); c.add(removeBtn);
		c.add(listL); c.add(list);
		c.add(analysisL); c.add(analysisT);
		
		c.setBackground(new Color(128, 128, 192));
		setSize(610, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        // db로딩, getConnection은 DAO의 역할
        
        // db의 모든 레코드를 꺼내서 JList에 뿌리기 
        // 레코드 한 줄을 FriendDTO에 넣기를 반복한다
        // 만약 레코드가 5개면 리턴 불가(리턴은 1개만 가능)
        // 따라서 이 FriendDTO 들을 List를 잡아 모아주어야한다. 
        // 리스트 안에 하나씩 담는다. 
        List<FriendDTO> arraylist = friendDAO.getFriendList(); // DAO에서 리스트를 가지고 온다. 
        // ArrayList의 부모인 list로 리턴값을 받는다. 
        for(FriendDTO friendDTO : arraylist	) {
        	model.addElement(friendDTO);
        }
        
        // 현재 list 안에는 string이 들어있는 것이 아니다. dto가 들어있는 것이다. 
        // 이 list 안의 dto 클릭시 dto 내용 꺼내서 왼쪽 입력창에 데이터를 뿌려준다. 
        
        
	} // constructor
	
	public void event() {
		inputBtn.addActionListener(this);
		editBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		removeBtn.addActionListener(this);
	}// event()
	
	




	@Override
	public void valueChanged(ListSelectionEvent e) {
		// 현재 list에는 문자열이 아닌 11개의 항목을 가지고 있는 dto가 들어가 있는 상태 
		FriendDTO friendDTO = list.getSelectedValue();
		selectedDTO = list.getSelectedValue(); // 선택된 dto
		
		//friendDTO로 받기 
		if(friendDTO == null) {
			return;
		}
		
		numL2.setText(friendDTO.getSeq() + "");
		nameT.setText(friendDTO.getName());
		phoneComboBox.setSelectedItem(friendDTO.getTel1());
		phoneT1.setText(friendDTO.getTel2());
		phoneT2.setText(friendDTO.getTel3());
		
		if(friendDTO.getGender() == 0) {
			maleBtn.setSelected(true);
		}else {
			femaleBtn.setSelected(false);
		}
		
		readBox.setSelected(friendDTO.getRead() == 1 ? true : false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new FriendManager();

	}

}
