package net.joshuahughes.storageinformation.operation;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import net.joshuahughes.storageinformation.Application;
import net.joshuahughes.storageinformation.StorageTableModel;

public abstract class Operation extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2999523167659139366L;
	public Operation()
	{
		String name = getClass().getSimpleName();
        putValue(Action.NAME,name);
        try
        {
			putValue(Action.SMALL_ICON, new ImageIcon(ImageIO.read(Operation.class.getResourceAsStream("/icons/"+name.toLowerCase()+".png"))));
		}
        catch (IOException e)
        {
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Application application = getApplication(e.getSource());
		if(application!=null)
			actionPerformed(e,application,application.getTable(),application.getModel());
	}
	protected abstract void actionPerformed(ActionEvent e, Application application, JTable table, StorageTableModel model);
	private static final Application getApplication(Object source) {
		if(source instanceof Application)
			return (Application) source;
		if(source instanceof JPopupMenu)
			return getApplication(((JPopupMenu)source).getInvoker());
		if(source instanceof Component)
			return getApplication(((Component)source).getParent());
		return null;
	}
}
