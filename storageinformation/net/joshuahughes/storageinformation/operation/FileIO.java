package net.joshuahughes.storageinformation.operation;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public abstract class FileIO extends Operation{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5376384191644585428L;

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "*.txt (Tab delimited)";
			}
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().endsWith(".txt");
			}
		});
		chooser.setAcceptAllFileFilterUsed(false);
		if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			File file = chooser.getSelectedFile();
			operate(file);
		}
	}

	protected abstract void operate(File file);
}
