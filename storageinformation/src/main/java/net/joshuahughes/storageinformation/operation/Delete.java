package net.joshuahughes.storageinformation.operation;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import net.joshuahughes.storageinformation.Application;
import net.joshuahughes.storageinformation.StorageTableModel;

public class Delete extends Operation{
	private static final long serialVersionUID = -5376384191644585428L;
	@Override
	public void actionPerformed(ActionEvent e, Application application, JTable table, StorageTableModel model) {
		int selectedRow = application.getTable().getSelectedRow();
		int selectedColumn = application.getTable().getSelectedColumn();
		if(selectedRow>=0)
		{
			model.removeRow(selectedRow);
			return;
		}
		if(selectedColumn>=0)
			removeColumnAndData(application.getTable(),selectedColumn);
	}
	public void removeColumnAndData(JTable table, int vColIndex) {
		StorageTableModel model = (StorageTableModel)table.getModel();
	    TableColumn col = table.getColumnModel().getColumn(vColIndex);
	    int columnModelIndex = col.getModelIndex();
	    Vector<?> data = model.getDataVector();
	    Vector<?> colIds = model.getColumnIdentifiers();

	    // Remove the column from the table
	    table.removeColumn(col);

	    // Remove the column header from the table model
	    colIds.removeElementAt(columnModelIndex);

	    // Remove the column data
	    for (int r=0; r<data.size(); r++) {
	        Vector<?> row = (Vector<?>)data.get(r);
	        row.removeElementAt(columnModelIndex);
	    }
	    model.setDataVector(data, colIds);

	    // Correct the model indices in the TableColumn objects
	    // by decrementing those indices that follow the deleted column
	    Enumeration<?> enumeration = table.getColumnModel().getColumns();
	    for (; enumeration.hasMoreElements(); ) {
	        TableColumn c = (TableColumn)enumeration.nextElement();
	        if (c.getModelIndex() >= columnModelIndex) {
	            c.setModelIndex(c.getModelIndex()-1);
	        }
	    }
	    model.fireTableStructureChanged();
	}
}
