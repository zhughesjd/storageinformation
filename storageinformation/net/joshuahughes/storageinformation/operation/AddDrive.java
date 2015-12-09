package net.joshuahughes.storageinformation.operation;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class AddDrive extends Operation{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5376384191644585428L;

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<File> rootBox = new JComboBox<>(File.listRoots());
		if(JOptionPane.showConfirmDialog(null, rootBox)== JOptionPane.YES_OPTION)
		{
			File root = (File) rootBox.getSelectedItem();
			application.getModel().addRow(root);
		}
		
	}
}
