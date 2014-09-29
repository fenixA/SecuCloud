package view;


import javax.swing.table.DefaultTableModel;

public class NonEditableJTable extends DefaultTableModel {
	private static final long serialVersionUID = -2994127624996711899L;

	public NonEditableJTable(Object[][] tableData, Object[] colNames) {
		super(tableData, colNames);
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}