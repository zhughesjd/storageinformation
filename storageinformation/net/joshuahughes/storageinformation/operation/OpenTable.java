package net.joshuahughes.storageinformation.operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import net.joshuahughes.storageinformation.Application;
import net.joshuahughes.storageinformation.StorageTableModel;
import net.joshuahughes.storageinformation.field.EditableField;
import net.joshuahughes.storageinformation.field.Field;


public class OpenTable extends FileIO{
	private static final long serialVersionUID = 479013800129214119L;

	@Override
	protected void operate(File file,Application application) {
		try {
			StorageTableModel model = new StorageTableModel();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			String[] headers = line.split("\t");
			for(String header : headers)
			{
				Field field = null;
				if(header.contains(","))
				{
					String[] array = header.split(",");
					field = new EditableField(Class.forName(array[0]), array[1]);
				}
				else
					field = (Field) Class.forName(header).newInstance();
				model.addColumn(field);
			}
			while((line = br.readLine())!=null)
			{
				String[] values = line.split("\t");
				Vector<Object> vector = new Vector<Object>();
				for(int columnIndex =0;columnIndex<model.getColumnCount();columnIndex++)
				{
					String value = columnIndex<values.length?values[columnIndex]:"";
					vector.add(Field.fromString(model.getColumnIdentifiers().get(columnIndex),value));
				}
				model.addRow(vector);
			}
			br.close();
			application.getTable().setModel(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
