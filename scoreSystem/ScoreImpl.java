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
	// �޼���� impl���� ����

	public ScoreImpl(ScoreForm form) {
		this.form = form;
	}

	public void insert() {
		// �й� �ߺ� ���� �� �ٽ� �Է�
		if (form.getList() != null || form.getList().size() != 0) {
			for (int i = 0; i < form.getList().size(); i++) {
				if (Integer.parseInt(form.getTextFields()[0].getText()) == form.getList().get(i).getNum()) {
					// �й� �ߺ���
					JOptionPane.showMessageDialog(form.getFrame(), "�ߺ��� �й� �Դϴ�");
					return;
				}

			}
		}
		// ���Է� ���� �� �ٽ� �Է�
		if (form.getTextFields()[0].getText() == null || form.getTextFields()[1].getText() == null
				|| form.getTextFields()[2].getText().length() == 0 || form.getTextFields()[3].getText().length() == 0
				|| form.getTextFields()[4].getText().length() == 0) {
			JOptionPane.showMessageDialog(form.getFrame(), "���� �Է��� �ּ���.");
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

		JOptionPane.showMessageDialog(form.getFrame(), "�Է� �Ϸ� ");
		form.getTextFields()[0].setText("");
		form.getTextFields()[1].setText("");
		form.getTextFields()[2].setText("");
		form.getTextFields()[3].setText("");
		form.getTextFields()[4].setText("");

	}

	public void print() {
		// �Է� ���� ���� ��
		if (form.getList() == null || form.getList().size() == 0) {
			JOptionPane.showMessageDialog(form.getFrame(), "�Էµ� ������ �����ϴ�.");
			return;
		}
		// row�� 0�� �Ǽ� ó������ �ٽ� �ѷ��ش�.
		form.getModel().setNumRows(0);
		// 0���� ������ ������ �����Ǿ �ԷµǱ� ����
		// ���̺� �߰�
		for (int i = 0; i < form.getList().size(); i++) {
			Object[] object = { form.getList().get(i).getNum(), form.getList().get(i).getName(),
					form.getList().get(i).getKor(), form.getList().get(i).getEng(), form.getList().get(i).getMath(),
					form.getList().get(i).getTot(), form.getList().get(i).getAvg() };
			form.getModel().addRow(object);
		}

	}

	public void search() {
		if (form.getList() == null || form.getList().size() == 0) {
			JOptionPane.showMessageDialog(form.getFrame(), "�Էµ� ������ �����ϴ�. ");
			return;
		}
		String searchNum = JOptionPane.showInputDialog(form.getFrame(), "�˻��� �й��� �Է��ϼ���");
		int sw = 0;
		for (int i = 0; i < form.getList().size(); i++) {
			if (Integer.parseInt(searchNum) == form.getList().get(i).getNum()) {
				// row�� 0�� �Ǽ� ó������ �ٽ� �ѷ��ش�.
				form.getModel().setNumRows(0);
				Object[] object = { form.getList().get(i).getNum(), form.getList().get(i).getName(),
						form.getList().get(i).getKor(), form.getList().get(i).getEng(), form.getList().get(i).getMath(),
						form.getList().get(i).getTot(), form.getList().get(i).getAvg() };

				form.getModel().addRow(object);
				sw = 1;
			}
		}
		if (sw == 0) {
			JOptionPane.showMessageDialog(form.getFrame(), "ã�� �й��� �����ϴ�.");
			return;
		}
	}

	public void sort() {
		if (form.getList() == null || form.getList().size() == 0) {
			JOptionPane.showMessageDialog(form.getFrame(), "�Էµ� ������ �����ϴ�.");
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
			JOptionPane.showMessageDialog(form.getFrame(), "������ ������ �����ϴ�. ");
			return;
		}
		JFileChooser chooser = new JFileChooser();
		int select = chooser.showSaveDialog(form.getFrame());
		file = chooser.getSelectedFile();

		if (select == chooser.APPROVE_OPTION) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write("�й�\t�̸�\t����\t����\t����\t����\t���\n");
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
			JOptionPane.showMessageDialog(form.getFrame(), "����Ϸ�");
		} else {
			return;
		}
	}

	public void load() {

		JFileChooser chooser = new JFileChooser();
		int result = chooser.showOpenDialog(form.getFrame());
		// frame �ȿ� ����.
		if (file == null)
			return;

		if (result == chooser.APPROVE_OPTION) {
			// APPROVE_OPTION : ���������� ����
			try {
				file = chooser.getSelectedFile();
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = br.readLine()) != null) { // ���� �Ÿ��� ���� ������
					String[] str = line.split("\t"); // �ٹٲ� �������� ������

					// �� �𸣰ڴ�
					if (str[0].equals("�й�")) {

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
			// ���� ���̺� ������ ����
			// �ٽ� ���
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
		int result = JOptionPane.showConfirmDialog(form.getFrame(), "�����Ͻðڽ��ϱ�?");
		// ��������� �׳� ���� �ʰ� �ݱ�
		if (form.getList() == null && form.getList().size() == 0) {
			System.exit(0);
		} else if(form.getList() != null || form.getList().size() != 0){

			switch (result) {
			case JOptionPane.YES_OPTION:
				if (form.getList() == null || form.getList().size() == 0) {
					JOptionPane.showMessageDialog(form.getFrame(), "������ ������ �����ϴ�.");
					return;
				}
				JFileChooser chooser = new JFileChooser();
				chooser.showSaveDialog(form.getFrame());
				file = chooser.getSelectedFile();

				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					bw.write("�й�\t�̸�\t����\t����\t����\t����\t���\n");
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
				JOptionPane.showMessageDialog(form.getFrame(), "���� �Ϸ�");
				System.exit(0);
			case JOptionPane.NO_OPTION:
				System.exit(0);
			case JOptionPane.CANCEL_OPTION:
				return;
			}
		}
	}
}
