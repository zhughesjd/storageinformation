package net.joshuahughes.storageinformation;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.JTableHeader;

import net.joshuahughes.storageinformation.field.ComputedValueField;
import net.joshuahughes.storageinformation.operation.AddColumn;
import net.joshuahughes.storageinformation.operation.AddStorage;
import net.joshuahughes.storageinformation.operation.Delete;
import net.joshuahughes.storageinformation.operation.NewTable;
import net.joshuahughes.storageinformation.operation.OpenTable;
import net.joshuahughes.storageinformation.operation.OpenZiotek;
import net.joshuahughes.storageinformation.operation.Operation;
import net.joshuahughes.storageinformation.operation.SaveTable;

public class Application extends JFrame
{
	private static final long serialVersionUID = 1L;
	JTable table = new JTable(new StorageTableModel());
	Operation[] operations = new Operation[]{new NewTable(),new SaveTable(),new OpenTable(),new AddColumn(),new AddStorage(),new Delete(),new OpenZiotek()};
	public Application()
	{
		table.addMouseListener(new MouseAdapter() {

	        public void mouseClicked(MouseEvent mouseEvent) {
	            table.setColumnSelectionAllowed(false);
	            table.setRowSelectionAllowed(true);

	            if (table.isCellSelected(table.getSelectedRow(), 0)) {
	                table.setColumnSelectionAllowed(false);
	                table.setRowSelectionAllowed(true);                    
	            }

	        }
	    });
		JTableHeader columnHeader = table.getTableHeader();
	    columnHeader.addMouseListener(new MouseAdapter() {

	        public void mouseClicked(MouseEvent mouseEvent) {
	            int columnPoint =  columnHeader.columnAtPoint(mouseEvent.getPoint());

	            int columnCursorType = columnHeader.getCursor().getType();

	            if (columnCursorType == Cursor.E_RESIZE_CURSOR)
	                mouseEvent.consume();
	            else {

	                if (columnPoint == 0) //the very first column header
	                    table.selectAll(); //will select all table cells
	                else {
	                    table.setColumnSelectionAllowed(true);
	                    table.setRowSelectionAllowed(false);
	                    table.clearSelection();
	                    table.setColumnSelectionInterval(columnPoint, columnPoint);
	                }

	            }
	        }
	    });
		Container pane = getContentPane( );
		pane.setLayout( new BorderLayout() );
		pane.add(getToolBar(), BorderLayout.NORTH);
		pane.add(new JScrollPane(table),BorderLayout.CENTER);
		JMenu fileMenu = new JMenu("File");
		JMenu addMenu = new JMenu("Operation");
		for(Operation operation : operations)
			addMenu.add( new JMenuItem( operation ) );
		fileMenu.add( addMenu );

		setJMenuBar(new JMenuBar());
		getJMenuBar( ).add( fileMenu );
		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
		setSize( 500,500 );
		setVisible( true );
		NewTable.setColumns(getModel(), ComputedValueField.computedFields);
	}
	private JToolBar getToolBar() {
		JToolBar toolbar = new JToolBar();
		for(Operation operation : operations)
			toolbar.add( operation );
		return toolbar;
	}

	public static void main(String[] args)
	{
		new Application();
	}
	public static void main1(String[] args) throws IOException {
		File[] roots = File.listRoots();
		for(int i = 0; i < roots.length ; i++)
		    System.out.println(roots[i]);
		for(File file : File.listRoots())
		{
			
			System.out.println(FileSystemView.getFileSystemView().getSystemDisplayName (file));
			System.out.println(FileSystemView.getFileSystemView().getSystemTypeDescription(file));
			System.out.println("------------------");
		}


		
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
	public JTable getTable()
	{
		return table;
	}
	public StorageTableModel getModel() {
		return (StorageTableModel) table.getModel();
	}
}
