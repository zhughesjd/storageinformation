package net.joshuahughes.storageinformation.operation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import net.joshuahughes.storageinformation.StorageTableModel;
import net.joshuahughes.storageinformation.field.ComputedValueField;
import net.joshuahughes.storageinformation.field.Field;

public class SaveTable extends FileIO
{
	private static final long serialVersionUID = -8024875966141790511L;
	@Override
	protected void operate(File file)
	{
		try {
			if(!file.getName().endsWith(".txt")) file = new File(file.getAbsolutePath()+".txt");
			PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)));
			Vector<Field> vector = ((StorageTableModel)application.getTable().getModel()).getColumnIdentifiers();
			for(int index=0;index<vector.size();index++)
			{
				Field field = vector.get(index);
				String value = field.getType().getCanonicalName()+","+field.getId(); 
				if(field instanceof ComputedValueField)
					value = field.getClass().getCanonicalName();
				ps.print(value+(index==vector.size()-1?"\n":"\t"));
			}
			for(int y=0;y<application.getTable().getRowCount();y++)
				for(int x=0;x<application.getTable().getColumnCount();x++)
				{
					Object object = application.getTable().getModel().getValueAt(y, x);
					String value = object==null?"":object.toString();
					ps.print(value + (x==vector.size()-1?"\n":"\t"));
				}
			ps.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	
	}
}
