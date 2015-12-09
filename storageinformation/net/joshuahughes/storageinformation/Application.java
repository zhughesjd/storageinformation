package net.joshuahughes.storageinformation;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import net.joshuahughes.storageinformation.field.ComputedValueField;
import net.joshuahughes.storageinformation.field.Field;
import net.joshuahughes.storageinformation.operation.AddColumn;

public class Application extends JFrame
{
	private static final long serialVersionUID = 1L;
	StorageTableModel model  = new StorageTableModel( );
	JTable table = new JTable(model);
	AddColumn addColumnAction = new AddColumn(model);
	public Application()
	{
		Container pane = getContentPane( );
		pane.setLayout( new BorderLayout() );
		pane.add(getToolBar(), BorderLayout.NORTH);
		pane.add(new JScrollPane(table),BorderLayout.CENTER);
		JMenu fileMenu = new JMenu("File");

		JMenu newMenu = new JMenu("New"); 
		newMenu.add( new JMenuItem( new AbstractAction( "Empty table" )
		{
			private static final long serialVersionUID = 3202072365605857617L;

			@Override
			public void actionPerformed( ActionEvent e )
			{
				setTable(new Field[]{},new String[][]{});
			}
		} ) );
		newMenu.add( new JMenuItem( new AbstractAction( "Default table" )
		{
			private static final long serialVersionUID = 3202072365605857617L;

			@Override
			public void actionPerformed( ActionEvent e )
			{
				setTable(ComputedValueField.computedFields,new String[][]{});
			}
		} ) );
		JMenu addMenu = new JMenu("Add"); 
		addMenu.add( new JMenuItem( addColumnAction ) );
		addMenu.add( new JMenuItem( new AbstractAction( "Disc" )
		{
			private static final long serialVersionUID = 3202072365605857617L;

			@Override
			public void actionPerformed( ActionEvent e )
			{
				addRow();
			}
		} ) );
		fileMenu.add( newMenu );
		fileMenu.add( addMenu );

		setJMenuBar(new JMenuBar());
		getJMenuBar( ).add( fileMenu );
		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
		setSize( 500,500 );
		setVisible( true );
	}
	private JToolBar getToolBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.add( addColumnAction );
		return toolbar;
	}

	protected void addRow()
	{
		JPanel panel = new JPanel( new GridLayout(model.getColumnCount(),2) );
		for(Field field : model.getColumnIdentifiers())
		{
			panel.add(new JLabel(field.getId()+":"));
			panel.add(new JTextField("yyy"));
		}
		if(JOptionPane.showConfirmDialog(this, panel, "set fields", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
		{
			Object[] rowData = new Object[model.getColumnCount()];
			int index=0;
			for(Component component : panel.getComponents())
			{
				if(component instanceof JTextField)
				{
					rowData[index++] = ((JTextField)component).getText();
				}
			}
			model.addRow(rowData);
		}
	}
	private void setTable( Field[] fields, String[][] strings )
	{
		Vector<Field> columns = new Vector<>(Arrays.asList( fields ));
		model.setColumnIdentifiers( columns );
	}
	public static Image addColumnImage;
	static
	{
		int dim = 32;
		try {
			addColumnImage = ImageIO.read(new File("icons/addcolumn.png")).getScaledInstance(dim, dim, BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		new Application();
	}
	public static void main1(String[] args) throws IOException {
		for (FileStore store : FileSystems.getDefault().getFileStores()) {
			long total = store.getTotalSpace();
			long used = (store.getTotalSpace() - store.getUnallocatedSpace());
			long avail = store.getUsableSpace();
			System.out.format("%-20s %12d %12d %12d%n", store, total, used, avail);
			File file = new File("C:/");
			System.out.println(file.getTotalSpace());
			System.out.println(file.getName());
		}
	}

}
