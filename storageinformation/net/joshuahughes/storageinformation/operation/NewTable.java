package net.joshuahughes.storageinformation.operation;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JOptionPane;

import net.joshuahughes.storageinformation.StorageTableModel;
import net.joshuahughes.storageinformation.field.ComputedValueField;
import net.joshuahughes.storageinformation.field.Field;

public class NewTable extends Operation{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5376384191644585428L;

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Field[] fields = new Field[]{};
		if(JOptionPane.showConfirmDialog(null, "Insert default computed fields?", "Insertion type type", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			fields = ComputedValueField.computedFields;
		}
		StorageTableModel model = new StorageTableModel();
		model.setColumnIdentifiers( new Vector<>(Arrays.asList( fields )) );
		Vector<Field> columns = new Vector<>(Arrays.asList( fields ));
		model.setColumnIdentifiers( columns );
		application.getTable().setModel(model);
	}
}
