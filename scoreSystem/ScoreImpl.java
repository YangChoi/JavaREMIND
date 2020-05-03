package scoreSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ScoreImpl implements Score {
	private ScoreForm form;
	private File file;
	// 메서드는 impl에서 구현

	public ScoreImpl(ScoreForm form) {
		this.form = form;
	}

	public void insert() {
		// 학번 중복 있을 시 다시 입력
		if (form.getList() != null || form.getList().size() != 0) {
			for (int i = 0; i < form.getList().size(); i++) {
				if (Integer.parseInt(form.getTextFields()[0].getText()) == form.getList().get(i).getNum()) {
					// 학번 중복시
					JOptionPane.showMessageDialog(form.getFrame(), "중복된 학번 입니다");
					return;
				}

			}
		}
		// 미입력 있을 시 다시 입력
		if (form.getTextFields()[0].getText() == null || form.getTextFields()[1].getText() == null
				|| form.getTextFields()[2].getText().length() == 0 || form.getTextFields()[3].getText().length() == 0
				|| form.getTextFields()[4].getText().length() == 0) {
			JOptionPane.showMessageDialog(form.getFrame(), "전부 입력해 주세요.");
			return;
		}

		int num = Integer.parseInt(form.getTextFields()[0].getText());
		String name = form.getTextFields()[1].getText();
		int kor = Integer.parseInt(form.getTextFields()[2].getText());
		int eng = Integer.parseInt(form.getTextFields()[3].getText());
		int math = Integer.parseInt(form.getTextFields()[4].getText());

		ScoreDTO dto = new ScoreDTO(num, name, kor, eng, math);
		dto.calc();
		form.getList().add(dto);

		JOptionPane.showMessageDialog(form.getFrame(), "입력 완료 ");
		form.getTextFields()[0].setText("");
		form.getTextFields()[1].setText("");
		form.getTextFields()[2].setText("");
		form.getTextFields()[3].setText("");
		form.getTextFields()[4].setText("");

	}

	public void print() {
		// 입력 정보 없을 시
		if (form.getList() == null || form.getList().size() == 0) {
			JOptionPane.showMessageDialog(form.getFrame(), "입력된 정보가 없습니다.");
			return;
		}
		// row가 0이 되서 처음부터 다시 뿌려준다.
		form.getModel().setNumRows(0);
		// 0으로 해주지 않으면 누적되어서 입력되기 때문
		// 테이블에 추가
		for (int i = 0; i < form.getList().size(); i++) {
			Object[] object = { form.getList().get(i).getNum(), form.getList().get(i).getName(),
					form.getList().get(i).getKor(), form.getList().get(i).getEng(), form.getList().get(i).getMath(),
					form.getList().get(i).getTot(), form.getList().get(i).getAvg() };
			form.getModel().addRow(object);
		}

	}

	public void search() {
		if (form.getList() == null || form.getList().size() == 0) {
			JOptionPane.showMessageDialog(form.getFrame(), "입력된 정보가 없습니다. ");
			return;
		}
		String searchNum = JOptionPane.showInputDialog(form.getFrame(), "검색할 학번을 입력하세요");
		int sw = 0;
		for (int i = 0; i < form.getList().size(); i++) {
			if (Integer.parseInt(searchNum) == form.getList().get(i).getNum()) {
				// row가 0이 되서 처음부터 다시 뿌려준다.
				form.getModel().setNumRows(0);
				Object[] object = { form.getList().get(i).getNum(), form.getList().get(i).getName(),
						form.getList().get(i).getKor(), form.getList().get(i).getEng(), form.getList().get(i).getMath(),
						form.getList().get(i).getTot(), form.getList().get(i).getAvg() };

				form.getModel().addRow(object);
				sw = 1;
			}
		}
		if (sw == 0) {
			JOptionPane.showMessageDialog(form.getFrame(), "찾는 학번이 없습니다.");
			return;
		}
	}

	public void sort() {
		if (form.getList() == null || form.getList().size() == 0) {
			JOptionPane.showMessageDialog(form.getFrame(), "입력된 정보가 없습니다.");
			return;
		}
		form.getModel().setNumRows(0);
		for (int i = 0; i < form.getList().size(); i++) {
			Object[] object = { form.getList().get(i).getNum(), form.getList().get(i).getName(),
					form.getList().get(i).getKor(), form.getList().get(i).getEng(), form.getList().get(i).getMath(),
					form.getList().get(i).getTot(), form.getList().get(i).getAvg() };

			form.getModel().addRow(object);

		}
		Collections.sort(form.getList());
	}

	public void save() {
		if (form.getList() == null || form.getList().size() == 0) {
			JOptionPane.showMessageDialog(form.getFrame(), "저장할 정보가 없습니다. ");
			return;
		}
		JFileChooser chooser = new JFileChooser();
		int select = chooser.showSaveDialog(form.getFrame());
		file = chooser.getSelectedFile();

		if (select == chooser.APPROVE_OPTION) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write("학번\t이름\t국어\t영어\t수학\t총점\t평균\n");
				bw.write("==============================================================\n");
				for (int i = 0; i < form.getList().size(); i++) {
					bw.write(form.getList().get(i).getNum() + "\t");
					bw.write(form.getList().get(i).getName() + "\t");
					bw.write(form.getList().get(i).getKor() + "\t");
					bw.write(form.getList().get(i).getEng() + "\t");
					bw.write(form.getList().get(i).getMath() + "\t");
					bw.write(form.getList().get(i).getTot() + "\t");
					bw.write(form.getList().get(i).getAvg() + "\t\n");

				}
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(form.getFrame(), "저장완료");
		} else {
			return;
		}
	}

	public void load() {

		JFileChooser chooser = new JFileChooser();
		int result = chooser.showOpenDialog(form.getFrame());
		// frame 안에 띄운다.
		if (file == null)
			return;

		if (result == chooser.APPROVE_OPTION) {
			// APPROVE_OPTION : 정상적으로 열림
			try {
				file = chooser.getSelectedFile();
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = br.readLine()) != null) { // 읽을 거리가 없을 때까지
					String[] str = line.split("\t"); // 줄바꿈 기준으로 나누기

					// 잘 모르겠다
					if (str[0].equals("학번")) {

					} else {
						int num = Integer.parseInt(str[0]);
						String name = str[1];
						int kor = Integer.parseInt(str[2]);
						int eng = Integer.parseInt(str[3]);
						int math = Integer.parseInt(str[4]);
						ScoreDTO dto = new ScoreDTO(num, name, kor, eng, math);
						form.getList().add(dto);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			form.getModel().setRowCount(0);
			// 기존 테이블 깨끗히 비우고
			// 다시 출력
			for (int i = 0; i < form.getList().size(); i++) {
				Object[] object = { form.getList().get(i).getNum(), form.getList().get(i).getName(),
						form.getList().get(i).getKor(), form.getList().get(i).getEng(), form.getList().get(i).getMath(),
						form.getList().get(i).getTot(), form.getList().get(i).getAvg() };

				form.getModel().addRow(object);

			}
		} else {
			return;
		}

	}

	public void close() {
		int result = JOptionPane.showConfirmDialog(form.getFrame(), "저장하시겠습니까?");
		// 비어있으면 그냥 묻지 않고 닫기
		if (form.getList() == null && form.getList().size() == 0) {
			System.exit(0);
		} else if(form.getList() != null || form.getList().size() != 0){

			switch (result) {
			case JOptionPane.YES_OPTION:
				if (form.getList() == null || form.getList().size() == 0) {
					JOptionPane.showMessageDialog(form.getFrame(), "저장할 정보가 없습니다.");
					return;
				}
				JFileChooser chooser = new JFileChooser();
				chooser.showSaveDialog(form.getFrame());
				file = chooser.getSelectedFile();

				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					bw.write("학번\t이름\t국어\t영어\t수학\t총점\t평균\n");
					bw.write("==============================================================\n");
					for (int i = 0; i < form.getList().size(); i++) {
						bw.write(form.getList().get(i).getNum() + "\t");
						bw.write(form.getList().get(i).getName() + "\t");
						bw.write(form.getList().get(i).getKor() + "\t");
						bw.write(form.getList().get(i).getEng() + "\t");
						bw.write(form.getList().get(i).getMath() + "\t");
						bw.write(form.getList().get(i).getTot() + "\t");
						bw.write(form.getList().get(i).getAvg() + "\t\n");

					}
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(form.getFrame(), "저장 완료");
				System.exit(0);
			case JOptionPane.NO_OPTION:
				System.exit(0);
			case JOptionPane.CANCEL_OPTION:
				return;
			}
		}
	}
}
