package net.joshuahughes.storageinformation.operation;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import net.joshuahughes.storageinformation.Application;
import net.joshuahughes.storageinformation.StorageTableModel;
import net.joshuahughes.storageinformation.field.EditableField;
import net.joshuahughes.storageinformation.field.Field;

public class AddColumn extends Operation{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5376384191644585428L;
	private static final String customLabel = "Custom...";
	public static LinkedHashSet<Field> columnSet = new LinkedHashSet<>();
	@Override
	public void actionPerformed(ActionEvent e, Application application, JTable table, StorageTableModel model) {
		ArrayList<Object> list  = new ArrayList<Object>();
		for(Field field : columnSet)
			if(!model.getColumnIdentifiers( ).contains( field ))
				list.add(field);
		list.add( customLabel );
		Object[] options = list.toArray( new Object[]{} );
		Object option = JOptionPane.showInputDialog( null, "Choose a column", "Column Chooser", JOptionPane.PLAIN_MESSAGE, null, options, options[0] );
		if(option == null) return;
		if(option instanceof Field)
		{
			model.addColumn( option );
			return;
		}
		if(customLabel == option)
		{
			JPanel panel = new JPanel(new GridLayout(5,1));
			panel.add( new JLabel("Name:") );
			JTextField field = new JTextField("id");
			panel.add( field );
			panel.add( new JLabel(""));
			panel.add( new JLabel("Type:") );
			JComboBox<Class<?>> type = new JComboBox<Class<?>>(new Vector<>(Field.defaultTypes));
			type.setRenderer(new ListCellRenderer<Class<?>>() {
				JLabel label = new JLabel();
				@Override
				public Component getListCellRendererComponent(JList<? extends Class<?>> list, Class<?> value, int index,
						boolean isSelected, boolean cellHasFocus) {
					label.setText(value.getSimpleName());
					label.setBackground(isSelected?Color.gray:Color.lightGray);
					return label;
				}
			});
			panel.add( type );
			if(JOptionPane.showConfirmDialog( null, panel ) == JOptionPane.OK_OPTION)
			{
				model.addColumn( new EditableField((Class<?>)type.getSelectedItem( ),field.getText( )) );
			}
		}
		
	}

}
