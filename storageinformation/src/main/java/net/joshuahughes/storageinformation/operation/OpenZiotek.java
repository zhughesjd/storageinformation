package net.joshuahughes.storageinformation.operation;

import java.awt.event.ActionEvent;

import javax.swing.JTable;

import net.joshuahughes.storageinformation.Application;
import net.joshuahughes.storageinformation.StorageTableModel;
import net.joshuahughes.storageinformation.field.EditableField;
import net.joshuahughes.storageinformation.field.Field;
import net.joshuahughes.usbcddll.SingularlyConnectedDevice;

public class OpenZiotek extends Operation{
	public static EditableField ziotekBin = new EditableField(Integer.class, "ZiotekBin");
	private static final long serialVersionUID = -5376384191644585428L;
	static{
		AddColumn.columnSet.add(ziotekBin);
	}
	@Override
	public void actionPerformed(ActionEvent e, Application application, JTable table, StorageTableModel model) {
		int selectedRow = table.getSelectedRow();
		int ziotekColumn = getZiotekColumnIndex(model);
		if(selectedRow<0 || ziotekColumn<0) return;
		Object object = model.getValueAt(selectedRow, ziotekColumn);
		if(!(object instanceof Number)) return;
		int ziotekBin = ((Number)object).intValue();
		if(!isValidBin(ziotekBin)) return;
		SingularlyConnectedDevice.USBCDGetCDDown();
		SingularlyConnectedDevice.USBCDMoveto(ziotekBin);
	}
	public static int getZiotekColumnIndex(StorageTableModel model)
	{
		int ziotekColumn = -1;
		for(int index=0;index<model.getColumnIdentifiers( ).size();index++)
		{
			Field field = model.getColumnIdentifiers().get(index);
			if(field.getType().equals(ziotekBin.getType()) && field.getId().equals(ziotekBin.getId()))
			{
				ziotekColumn = index;
				break;
			}
		}
		return ziotekColumn;
	}
	public static boolean isValidBin(int bin) {
		return 0<bin & bin<151;
	}
}
