package scoreManageSystem;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

interface Score {
	public void insert(ScoreDTO dto);
	public void print(DefaultTableModel model);
	public int search(DefaultTableModel model, String num);
	public int delete(ScoreDTO dto, String num);
	public void sort();
	public void save();
	public void load();
}
