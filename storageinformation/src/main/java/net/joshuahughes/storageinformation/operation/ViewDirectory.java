package net.joshuahughes.storageinformation.operation;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import net.joshuahughes.storageinformation.Application;
import net.joshuahughes.storageinformation.StorageTableModel;
import net.joshuahughes.storageinformation.field.EditableField;
import net.joshuahughes.storageinformation.field.Field;
import net.joshuahughes.storageinformation.field.Files;
import net.joshuahughes.usbcddll.USBCDDLL;

public class ViewDirectory extends Operation{
	public static EditableField ziotekBin = new EditableField(Integer.class, "ZiotekBin");
	private static final long serialVersionUID = -5376384191644585428L;
	static{
		USBCDDLL.instance.InitUSBCDLibrary();
		AddColumn.columnSet.add(ziotekBin);
	}
	@Override
	public void actionPerformed(ActionEvent e, Application application, JTable table, StorageTableModel model) {
		int selectedRow = table.getSelectedRow();
		int directoryColumn = -1;
		for(int index=0;index<model.getColumnIdentifiers( ).size();index++)
		{
			Field field = model.getColumnIdentifiers().get(index);
			if(field instanceof Files)
			{
				directoryColumn = index;
				break;
			}
		}
		if(selectedRow<0 || directoryColumn<0) return;
		Object object = model.getValueAt(selectedRow, directoryColumn);
		JOptionPane.showConfirmDialog(application, new JLabel(object.toString()));
	}
}
