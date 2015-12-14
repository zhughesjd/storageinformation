package net.joshuahughes.storageinformation.operation;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.joshuahughes.storageinformation.Application;
import net.joshuahughes.storageinformation.StorageTableModel;
import net.joshuahughes.storageinformation.field.Field;
import net.joshuahughes.storageinformation.field.Files;

public class ViewDirectory extends Operation{
	private static final long serialVersionUID = -5376384191644585428L;
	JTree tree = new JTree(new DefaultMutableTreeNode());
	JDialog dlg = new JDialog();
	@Override
	public void actionPerformed(ActionEvent e, Application application, JTable table, StorageTableModel model) {
		int selectedRow = table.getSelectedRow();
		int directoryColumn = -1;
		for(int index=0;index<model.getColumnIdentifiers( ).size();index++)
		{
			Field field = model.getColumnIdentifiers().get(index);
			if(field instanceof Files)
			{
				directoryColumn = index;
				break;
			}
		}
		if(selectedRow<0 || directoryColumn<0) return;
		Object object = model.getValueAt(selectedRow, directoryColumn);
		DefaultTreeModel treeModel = new DefaultTreeModel(buildTreeFromString(object.toString()));
		tree.setModel(treeModel);
		dlg.setContentPane(new JScrollPane(tree));
		dlg.setSize(500, 500);
		dlg.setVisible(true);
	}

	/**
	 * Builds a tree from a given forward slash delimited string.
	 * 
	 * @param model The tree model
	 * @param str The string to build the tree from
	 */
	private DefaultMutableTreeNode buildTreeFromString(String input) {
		String rootString = input.substring(0, Math.min(3,input.length()));
		input = input.replaceAll(rootString.replaceAll("\\\\","\\\\\\\\"), "");
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootString,true);
		String [] strings = input.split(",");
		// Iterate of the string array
		for (String nodeString : strings)
		{
			DefaultMutableTreeNode parent = root;
			if(nodeString.isEmpty())continue;
			Vector<String> vector = new Vector<>(Arrays.asList(nodeString.split("\\\\")));
			boolean appending = false;

			for(int index=0;index<vector.size();index++)
			{
				if(!appending)
				{
					boolean added = false;
					for(int childIndex =0;childIndex<parent.getChildCount();childIndex++)
					{
						if(((DefaultMutableTreeNode)parent.getChildAt(childIndex)).getUserObject().toString().equals(vector.get(index)))
						{
							added = true;
							parent = (DefaultMutableTreeNode) parent.getChildAt(childIndex);
						}
					}
					if(!added) appending = true;
				}
				if(appending)
				{
					DefaultMutableTreeNode child = new DefaultMutableTreeNode(vector.get(index),index<vector.size()-1);
					parent.add(child);
					parent = child;
					continue;
				}
			}
		}
		return root;
	}
}
