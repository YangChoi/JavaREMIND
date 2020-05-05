package scoreManageSystem;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class ScoreImpl implements Score{
	private List<ScoreDTO> list = new ArrayList<ScoreDTO>();
	// list��� �θ� �ؿ� �ڽ� �� �ϳ��� Arrayist�� �����Ѵ�. 
	// 1 : 1 ���ٴ� 1 : �� ���·�, �θ� ArrayList ���� �ٸ� �͵� ������ �� �ְ� ����� ���յ��� �����ش�. 
	
	// ���⿡�� �޴� ������ score �������̽����� ����Ѵ�. 
	public void insert(ScoreDTO dto) {
		// form���� �� �� �ޱ� 
		list.add(dto); // list�� ���� dto �� �߰� 
		// ��ϿϷ� �޽��� 
	}
	public void print(DefaultTableModel model) {
		// model ���� ���´�. 
		model.setRowCount(0); // ���̺��� clear�� ���̺� ���� �������� �ʰ� �����. 
		
		for(ScoreDTO dto : list) {
			// list ���� �ϳ��� �����Ѵ�(list�� ���� � �ִ��� �𸣱� ������ Ȯ���� for��)
			// ���ʹ� �׸� ����� �������Ѵ�.
			Vector<String> v = new Vector<String>();
			
			v.add(dto.getNum());
			v.add(dto.getName());
			v.add(dto.getKor() + "");
			v.add(dto.getEng() + "");
			v.add(dto.getMath() + "");
			v.add(dto.getTot() + "");
			v.add(dto.getAvg());
			
			model.addRow(v); // model�� ���͸� �ִ´� > table�� �� �߰��� 
			
		}
	}
	public int search(DefaultTableModel model, String num) {
		model.setRowCount(0); // ���̺��� ���� ã�� ���� �ѷ��ش�. 
		int sw = 0; // ã���� 1�� �ȴ�. 
		
		// ã���� �ϴ� ���� num�� model�� �Բ� form���� �޴´�. 
		for(ScoreDTO dto : list) {
			if(num.equals(dto.getNum())) {
				// ã�� ������ vector > model ���� �ѷ��ش�. 
				Vector<String> v = new Vector<String>();
				v.add(dto.getNum());
				v.add(dto.getName());
				v.add(dto.getKor() + "");
				v.add(dto.getEng() + "");
				v.add(dto.getMath()+ "");
				v.add(dto.getTot() + "");
				v.add(dto.getAvg());
				
				model.addRow(v);
				sw = 1;
			}
		}// for
		
		// ã���� �ϴ� ������ ���� dialog�� form���� ����ش�. 
		return sw; // ã�Ҵ��� ã�� ���ߴ��� �˷��ִ� sw�� return �� 
		// form�� �˷��ش� (�׿� ���� dialog�� ������ϱ� ����)
	}
	
	public int delete(ScoreDTO dto, String num) {
		int sw = 0; // ������ 1�� �ȴ�. 
		
		// ������ �� ��ȣ �˾Ƴ��� 
		Iterator<ScoreDTO> it = list.iterator();
		
		while(it.hasNext()) {
			dto = it.next();
				if(num.equals(dto.getNum())) {
					it.remove();
					
					sw = 1;
								
				}	
		}
		return sw;
	}
	public void sort() {
		// ��ü���� ��������
		// Comparable
		// Comparator : �͸��̳� 
		Collections.sort(list);
	}
	public void save() {
		// ���� ���̾�α� 
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showSaveDialog(null);
		File file = null;
		if(result == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		}
		if(file == null) return; // ���� ���� ���� �� ������ 
		// ��ü�� �����Ƿ� ObjectOutputStream
		// Buffer���� ���Ϸ� ������ ���� FileOutputStream
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void load() {
		// ���� ���̾�α� 
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showOpenDialog(null);
		File file = null;
		if(result == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		}
		
		if(file == null) return;
		
		list.clear(); // ����Ʈ ���� 
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			while(true) {
				try {
					ScoreDTO dto = (ScoreDTO)ois.readObject();
					// readObject�� ������  Object Ÿ������ �о�´�. 
					// DTO�� ����ȯ �ؼ� �Ѱ��ش�. 
					list.add(dto); // ����Ʈ�� �ҷ��� ���� ��´�. 
					
				}catch(EOFException e) {
					break;
				}
			}
			ois.close();
		}catch(IOException e) {
			// ���� ���� �����ϸ� ������ 
			// ���ʿ� ������ ũ�⸦ ������ �� ���� ������ 
			// ������ byte ������ �Ǿ� �ֱ� ����.
			// ��� �������� �ϳ����� �� �� ���� (byte ���� ���ڼ��̱� ������)
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
		
