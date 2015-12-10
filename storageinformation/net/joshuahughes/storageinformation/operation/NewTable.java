package net.joshuahughes.storageinformation.operation;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import net.joshuahughes.storageinformation.Application;
import net.joshuahughes.storageinformation.StorageTableModel;
import net.joshuahughes.storageinformation.field.ComputedValueField;
import net.joshuahughes.storageinformation.field.Field;

public class NewTable extends Operation{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5376384191644585428L;

	@Override
	public void actionPerformed(ActionEvent e, Application application, JTable table, StorageTableModel model) {
		Field[] fields = new Field[]{};
		if(JOptionPane.showConfirmDialog(null, "Insert default computed fields?", "Insertion type type", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			fields = ComputedValueField.computedFields;
		}
		setColumns(model,fields);
	}
	public static final void setColumns(StorageTableModel model,Field[] fields)
	{
		model.setColumnIdentifiers( new Vector<>(Arrays.asList( fields )) );
		Vector<Field> columns = new Vector<>(Arrays.asList( fields ));
		model.setColumnIdentifiers( columns );
		model.fireTableStructureChanged();
	}
}
