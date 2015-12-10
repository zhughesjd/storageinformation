package net.joshuahughes.storageinformation.operation;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.filechooser.FileSystemView;

import net.joshuahughes.storageinformation.Application;
import net.joshuahughes.storageinformation.CDUtils;
import net.joshuahughes.storageinformation.StorageTableModel;


public class AddStorage extends Operation{
	private static final long serialVersionUID = -5376384191644585428L;
	@Override
	public void actionPerformed(ActionEvent e, Application application, JTable table, StorageTableModel model) {
		JComboBox<File> rootBox = new JComboBox<>(File.listRoots());
		rootBox.setRenderer(new ListCellRenderer<File>(){

			@Override
			public Component getListCellRendererComponent(JList<? extends File> list, File value, int index,boolean isSelected, boolean cellHasFocus) {
				JLabel label = new JLabel(value.getAbsolutePath()+" ("+getDescription(value)+" drive)");
				if(isSelected)
				{
					label.setOpaque(true);
					label.setBackground(Color.lightGray);
				}
				return label;
			}});
		if(JOptionPane.showConfirmDialog(null, rootBox)== JOptionPane.YES_OPTION)
		{
			File root = (File) rootBox.getSelectedItem();
			if(getDescription(root).startsWith("empty"))
			{
				CDUtils.open(root.toString());
				while(getDescription(root).startsWith("empty"))
				{
					try {
						Thread.sleep(1000l);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			application.getModel().addRow(root);
		}
	}
	public static String getDescription(File rootFile)
	{
		try
	      {
	          Files.getFileStore(rootFile.toPath());
	          if(FileSystemView.getFileSystemView().getSystemTypeDescription(rootFile).startsWith("CD")) return "disc";
	      }
	      catch (IOException e)
	      {
	    	  return "empty disc";
	      }
		return "hard";
	}
}
