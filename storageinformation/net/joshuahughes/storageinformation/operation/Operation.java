package net.joshuahughes.storageinformation.operation;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import net.joshuahughes.storageinformation.StorageTableModel;

public abstract class Operation extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2999523167659139366L;
	StorageTableModel model;

	public Operation(StorageTableModel model)
	{
		String name = getClass().getSimpleName();
        putValue(Action.NAME,name);
        try
        {
			putValue(Action.SMALL_ICON, new ImageIcon(ImageIO.read(new File("icons/"+name.toLowerCase()+".png"))));
		}
        catch (IOException e)
        {
			e.printStackTrace();
		}
        this.model = model;
	}
}
