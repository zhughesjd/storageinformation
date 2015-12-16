package net.joshuahughes.storageinformation.operation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.filechooser.FileSystemView;

import net.joshuahughes.storageinformation.Application;
import net.joshuahughes.storageinformation.DiscTrayUtility;
import net.joshuahughes.storageinformation.StorageTableModel;
import net.joshuahughes.usbcddll.SingularlyConnectedDevice;


public class AddStorage extends Operation{
	private static final long serialVersionUID = -5376384191644585428L;
	@Override
	public void actionPerformed(ActionEvent e, Application application, JTable table, StorageTableModel model) {
		CancelOperationDialog dialog = new CancelOperationDialog();
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
		if(JOptionPane.showConfirmDialog(null, rootBox) == JOptionPane.YES_OPTION)
		{
			File root = (File) rootBox.getSelectedItem();
			if(getDescription(root).startsWith("empty"))
			{
				dialog.setVisible(true);
				application.setVisible(false);
				DiscTrayUtility.open(root.toString());
				new Thread(){
					public void run()
					{
						while(getDescription(root).startsWith("empty") && dialog.isVisible())
						{
							try {
								Thread.sleep(1000l);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
						dialog.setVisible(false);
						application.setVisible(true);
						if(!getDescription(root).startsWith("empty"))
							model.addRow(root);
						DiscTrayUtility.open(root.toString());
						int ziotekBin = getEmptyBin();
						if(OpenZiotek.isValidBin(ziotekBin) )
						{
							int ziotekColumn = OpenZiotek.getZiotekColumnIndex(model);
							if(ziotekColumn>0 && ziotekColumn<model.getColumnCount())
							{
								model.setValueAt(ziotekBin, model.getRowCount()-1, ziotekColumn);
								SingularlyConnectedDevice.USBCDGetCDDown();
								SingularlyConnectedDevice.USBCDMoveto(ziotekBin);
							}
						}
					}

					private int getEmptyBin() {
						int ziotekColumn = OpenZiotek.getZiotekColumnIndex(model);
						if(ziotekColumn>0 && ziotekColumn<model.getColumnCount())
						{
							for(int row = 0;row<model.getRowCount();row++)
								for(int bin=1;bin<=150;bin++)
								{
									if(!model.getValueAt(row, ziotekColumn).equals(bin))
										return bin;
								}
						}
						return Integer.MIN_VALUE;
					}
				}.start();
			}
			else
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
	public class CancelOperationDialog extends JDialog
	{
		private static final long serialVersionUID = -3647411068204229524L;
		public CancelOperationDialog()
		{
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(new JLabel("Waiting for you to insert disc and close tray."),BorderLayout.CENTER);
			getContentPane().add(new JLabel("Close this window to cancel this operation."),BorderLayout.SOUTH);
			setSize(300,100);
		}
	}
}
