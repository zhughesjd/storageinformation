package net.joshuahughes.storageinformation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class SearchTreePanel extends JPanel{
	private static final long serialVersionUID = -3872975456383125270L;
	private final JTree tree       = new JTree(new DefaultTreeModel(new DefaultMutableTreeNode("")));
	private final JTextField field = new JTextField("");
	private final MyTreeCellRenderer renderer = new MyTreeCellRenderer(tree.getCellRenderer());
	public SearchTreePanel() {
		super(new BorderLayout(5, 5));
		field.getDocument().addDocumentListener(new DocumentListener() {
			@Override public void insertUpdate(DocumentEvent e) {
				fireDocumentChangeEvent();
			}
			@Override public void removeUpdate(DocumentEvent e) {
				fireDocumentChangeEvent();
			}
			@Override public void changedUpdate(DocumentEvent e) {}
		});
		tree.setCellRenderer(renderer);
		renderer.q = field.getText();
		fireDocumentChangeEvent();
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(field, BorderLayout.NORTH);
		add(new JScrollPane(tree));
	}
	public JTextField getField()
	{
		return field;
	}
	public JTree getTree()
	{
		return tree;
	}
	private void fireDocumentChangeEvent() {
		String q = field.getText();
		renderer.q = q;
		TreePath root = tree.getPathForRow(0);
		collapseAll(tree, root);
		if (!q.isEmpty()) searchTree(tree, root, q);
		tree.repaint();
	}
	private static void searchTree(JTree tree, TreePath path, String q) {
		TreeNode node = (TreeNode)path.getLastPathComponent();
		if (node==null) return;
		if (node.toString().startsWith(q)) tree.expandPath(path.getParentPath());
		if (!node.isLeaf() && node.getChildCount()>=0) {
			Enumeration<?> e = node.children();
			while (e.hasMoreElements())
				searchTree(tree, path.pathByAddingChild(e.nextElement()), q);
		}
	}
	private static void collapseAll(JTree tree, TreePath parent) {
		TreeNode node = (TreeNode)parent.getLastPathComponent();
		if (!node.isLeaf() && node.getChildCount()>=0) {
			Enumeration<?> e = node.children();
			while (e.hasMoreElements()) {
				TreeNode n = (TreeNode)e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				collapseAll(tree, path);
			}
		}
		tree.collapsePath(parent);
	}
	public static DefaultMutableTreeNode createRoot(String value) {
		  DefaultMutableTreeNode root =  new DefaultMutableTreeNode("Root");
		  String[] array = value.split(",");
		  for(String leafString : array)
		  {
			  ArrayList<String> path = new ArrayList<>(Arrays.asList(leafString.split("\\\\")));
			  addLeaf(root,path);
		  }
		  return root;
	}
	private static void addLeaf(DefaultMutableTreeNode parent,ArrayList<String> path)
	{
		if(path.isEmpty()) return;
		String childName = path.remove(0);
		DefaultMutableTreeNode child = null;new DefaultMutableTreeNode(childName,true);
		for(int index = 0;index<parent.getChildCount();index++)
			if(parent.getChildAt(index).toString().equals(childName))
			{
				child = (DefaultMutableTreeNode) parent.getChildAt(index);
				break;
			}
		if(child == null)
		{
			child = new DefaultMutableTreeNode(childName,true);
			parent.add(child);
		}
		addLeaf(child,path);
	}

}
class MyTreeCellRenderer extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final TreeCellRenderer renderer;
	public String q;
	public MyTreeCellRenderer(TreeCellRenderer renderer) {
		this.renderer = renderer;
	}
	@Override public Component getTreeCellRendererComponent(
			JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		JComponent c = (JComponent)renderer.getTreeCellRendererComponent(
				tree, value, isSelected, expanded, leaf, row, hasFocus);
		if (isSelected) {
			c.setOpaque(false);
			c.setForeground(getTextSelectionColor());
		} else {
			c.setOpaque(true);
			if (q!=null && !q.isEmpty() && value.toString().startsWith(q)) {
				c.setForeground(getTextNonSelectionColor());
				c.setBackground(Color.YELLOW);
			} else {
				c.setForeground(getTextNonSelectionColor());
				c.setBackground(getBackgroundNonSelectionColor());
			}
		}
		return c;
	}

}