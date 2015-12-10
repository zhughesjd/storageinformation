package net.joshuahughes.storageinformation.operation;

import java.awt.event.ActionEvent;

import javax.swing.JTable;

import net.joshuahughes.storageinformation.Application;
import net.joshuahughes.storageinformation.StorageTableModel;
import net.joshuahughes.storageinformation.field.EditableField;

public class OpenZiotek extends Operation{
	private static final long serialVersionUID = -5376384191644585428L;
	@Override
	public void actionPerformed(ActionEvent e, Application application, JTable table, StorageTableModel model) {

		int selectedRow = table.getSelectedRow();
		int ziotekColumn = model.getColumnIdentifiers( ).indexOf( EditableField.ziotekBin );
		if(selectedRow<0 || ziotekColumn<0) return;
		Object object = model.getValueAt(selectedRow, ziotekColumn);
		if(!(object instanceof Number)) return;
		int ziotekBin = ((Number)object).intValue();
		if(ziotekBin<1 || ziotekBin>150) return;
		System.out.println(ziotekBin);
	}
}
