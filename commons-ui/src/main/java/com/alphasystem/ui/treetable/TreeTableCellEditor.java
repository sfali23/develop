/**
 * 
 */
package com.alphasystem.ui.treetable;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;

/**
 * TreeTableCellEditor implementation. Component returned is the JTree.
 */
public class TreeTableCellEditor extends AbstractCellEditor implements
		TableCellEditor {

	private JTreeTable treeTable;

	public TreeTableCellEditor(JTreeTable treeTable) {
		this.treeTable = treeTable;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int r, int c) {
		return treeTable.getTree();
	}

	/**
	 * Overridden to return false, and if the event is a mouse event it is
	 * forwarded to the tree.
	 * <p>
	 * The behavior for this is debatable, and should really be offered as a
	 * property. By returning false, all keyboard actions are implemented in
	 * terms of the table. By returning true, the tree would get a chance to do
	 * something with the keyboard events. For the most part this is ok. But for
	 * certain keys, such as left/right, the tree will expand/collapse where as
	 * the table focus should really move to a different column. Page up/down
	 * should also be implemented in terms of the table. By returning false this
	 * also has the added benefit that clicking outside of the bounds of the
	 * tree node, but still in the tree column will select the row, whereas if
	 * this returned true that wouldn't be the case.
	 * <p>
	 * By returning false we are also enforcing the policy that the tree will
	 * never be editable (at least by a key sequence).
	 */
	public boolean isCellEditable(EventObject e) {
		TreeTableModelAdapter treeTableModel = (TreeTableModelAdapter) treeTable.getModel();
		JTree tree = treeTable.getTree();
		if (e instanceof MouseEvent) {
			for (int counter = treeTableModel.getColumnCount() - 1; counter >= 0; counter--) {
				if (treeTableModel.getColumnClass(counter) == TreeTableModel.class) {
					MouseEvent me = (MouseEvent) e;
					MouseEvent newME = new MouseEvent(
							tree,
							me.getID(),
							me.getWhen(),
							me.getModifiers(),
							me.getX()
									- treeTable.getCellRect(0, counter, true).x,
							me.getY(), me.getClickCount(), me.isPopupTrigger());
					tree.dispatchEvent(newME);
					break;
				}
			}
		}
		return false;
	}

	public Object getCellEditorValue() {
		return null;
	}
}
