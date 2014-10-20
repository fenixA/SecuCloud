package view;

import javax.swing.table.DefaultTableModel;

/**
 * The Class NonEditableJTable is a table model, where the rows and columns are
 * not editable for the user.
 */
public class NonEditableJTable extends DefaultTableModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2994127624996711899L;

	/**
	 * Instantiates a new non editable j table.
	 * 
	 * @param tableData
	 *            the table data
	 * @param colNames
	 *            the col names
	 */
	public NonEditableJTable(Object[][] tableData, Object[] colNames) {
		super(tableData, colNames);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}