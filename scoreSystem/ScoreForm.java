package scoreSystem;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ScoreForm extends JFrame{
	private JFrame frame;
	private ArrayList<ScoreDTO> list; 
	private JTextField numT, nameT, korT, engT, mathT;
	private JTextField[] textFields = {numT, nameT, korT, engT, mathT};
	private DefaultTableModel model;
	private Score impl;

	public ScoreForm() {
		list = new ArrayList<ScoreDTO>();
		frame = new JFrame();
	
		// 왼쪽 - 입력	
		JPanel[] panels = new JPanel[5]; // 5개의 JPanel
		JLabel numL = null;
		JLabel nameL = null;
		JLabel korL = null;
		JLabel engL = null;
		JLabel mathL =  null;
		JLabel[] labels = {numL, nameL, korL, engL, mathL};
		String[] labelName = {"학번", "이름", "국어", "영어", "수학"};
		
		JPanel leftP = new JPanel();
		leftP.setLayout(new GridLayout(5, 1)); // 5행 1열 
		
		for(int i = 0; i < textFields.length; i++) {
			panels[i] = new JPanel(); // 5개의 panel 생성 
			labels[i] = new JLabel(labelName[i]); //label에 이름 넣기
			panels[i].add(labels[i]); // 패널안에 textField 넣기 
			
			textFields[i] = new JTextField(20); // 5개의 textField 생성
			panels[i].add(textFields[i]); // 패널 안에 textField 넣기
			leftP.add(panels[i]); // leftP에 각 패널들 넣기 
		}
		
		
		// 오른쪽 - 테이블 
		JPanel rightP = new JPanel();
		rightP.setLayout(new GridLayout(1, 1)); // 1행 1열
		String[] tableHeader = {"학번", "이름", "국어", "영어", "수학", "총점", "평균"};
		
		// JTable에서 직접 추가, 삭제 기능을 할 수 없기 때문에 
		//DefaultTableModel를 이용해 추가, 삭제 
		model = new DefaultTableModel(tableHeader, list.size()); // 행 데이터, 열 title 
		JTable table = new JTable(model);
		// setAutoCreateRowSorter를 이용해 컬럼헤드 클릭시 행을 자동으로 정렬해준다. (테이블은 정렬, 필터링 가능)
		table.setAutoCreateRowSorter(true);
		JScrollPane scroll = new JScrollPane(table);
		rightP.add(scroll);
		
		
		// 센터 - 오른쪽 왼쪽 합침
		JPanel centerP = new JPanel();
		centerP.setLayout(new GridLayout(1, 2, 5, 5));
		centerP.add(leftP);
		centerP.add(rightP);
		
		JPanel btnP = new JPanel();
		btnP.setLayout(new GridLayout(1, 6, 5, 5));
		JButton insertBtn = null;
		JButton printBtn = null;
		JButton searchBtn = null;
		JButton sortBtn = null;
		JButton saveBtn = null;
		JButton loadBtn = null;
		
		JButton[] buttons = {insertBtn, printBtn, searchBtn, sortBtn, saveBtn, loadBtn};
		String[] btnNames = {"입력", "출력", "학번검색", "순위", "파일저장", "파일열기"};
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(btnNames[i]);
			btnP.add(buttons[i]);
		}
		
		
		Container c = getContentPane();
		c.add("North", new JPanel());
		c.add("Center", centerP);
		c.add("South", btnP);
		
		
		impl = new ScoreImpl(this);
		setTitle("성적관리 시스템");
		setBounds(700, 500, 600, 300);
		setVisible(true);
		setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);
		
		
		
		// X 버튼 누를 시 (저장 여부)
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				impl.close();
			}
		});
		
		
		//버튼 이벤트 
		buttons[0].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				impl.insert();
			}
					
		});
		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				impl.print();
			}
		});
		buttons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				impl.search();
			}
		});
		buttons[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				impl.sort();
			}
		});
		buttons[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				impl.save();
			}
		});
		buttons[5].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				impl.load();
			}
		});
		
	} // Constructor 

	// getter setter
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public ArrayList<ScoreDTO> getList() {
		return list;
	}

	public void setList(ArrayList<ScoreDTO> list) {
		this.list = list;
	}

	public JTextField getNumT() {
		return numT;
	}

	public void setNumT(JTextField numT) {
		this.numT = numT;
	}

	public JTextField getNameT() {
		return nameT;
	}

	public void setNameT(JTextField nameT) {
		this.nameT = nameT;
	}

	public JTextField getKorT() {
		return korT;
	}

	public void setKorT(JTextField korT) {
		this.korT = korT;
	}

	public JTextField getEngT() {
		return engT;
	}

	public void setEngT(JTextField engT) {
		this.engT = engT;
	}

	public JTextField getMathT() {
		return mathT;
	}

	public void setMathT(JTextField mathT) {
		this.mathT = mathT;
	}

	public JTextField[] getTextFields() {
		return textFields;
	}

	public void setTextFields(JTextField[] textFields) {
		this.textFields = textFields;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

	public Score getImpl() {
		return impl;
	}

	public void setImpl(Score impl) {
		this.impl = impl;
	}
	
	// TextField Setter 

}
