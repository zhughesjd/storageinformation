package net.joshuahughes.storageinformation;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.joshuahughes.storageinformation.field.ComputedValueField;
import net.joshuahughes.storageinformation.field.EditableField;
import net.joshuahughes.storageinformation.field.Field;

public class StorageTableModel extends DefaultTableModel
{
    private static final long serialVersionUID = 7686101696099490903L;
    public Class<?> getColumnClass(int columnIndex)
    {
        return ((Field)this.columnIdentifiers.elementAt( columnIndex )).getType( );
    }
    public Vector<Field> getColumnIdentifiers()
    {
        Vector<Field> vector = new Vector<Field>();
        for(Object object : this.columnIdentifiers)
            vector.add( ( Field ) object );
        return vector;
    }
    public boolean isCellEditable(int row,int col)
    {
        return this.columnIdentifiers.get( col ) instanceof EditableField;        
    }
	public void addRow(File root) {
		ArrayList<Object> objectList = new ArrayList<>();
		for(Field field : getColumnIdentifiers())
		{
			Object value = "";
			if(field instanceof ComputedValueField)
			{
				ComputedValueField<?> cvf = (ComputedValueField<?>) field;
				value = cvf.compute(root);
			}
			objectList.add(value);
		}
		addRow(objectList.toArray());
	}
}