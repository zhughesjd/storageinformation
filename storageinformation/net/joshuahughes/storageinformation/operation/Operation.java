package net.joshuahughes.storageinformation.operation;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import net.joshuahughes.storageinformation.StorageTableModel;

public abstract class Operation extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2999523167659139366L;
	StorageTableModel model;

	public Operation(String name,String imagePath,StorageTableModel model)
	{
		super(name,getImage(imagePath));
		this.model = model;
	}
	public static ImageIcon getImage(String imagePath)
	{
		try {
			return new ImageIcon(ImageIO.read(new File(imagePath)));
		} catch (IOException e) {
			return null;
		}
	}

}
