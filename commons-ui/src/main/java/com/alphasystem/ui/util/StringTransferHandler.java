package com.alphasystem.ui.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public abstract class StringTransferHandler extends TransferHandler {

	private static final long serialVersionUID = -6319178796444218522L;

	@Override
	public boolean canImport(JComponent c, DataFlavor[] flavors) {
		for (int i = 0; i < flavors.length; i++) {
			if (DataFlavor.stringFlavor.equals(flavors[i])) {
				return true;
			}
		}
		return false;
	}

	protected abstract void cleanup(JComponent c, boolean remove);

	@Override
	protected Transferable createTransferable(JComponent c) {
		return new StringSelection(exportString(c));
	}

	@Override
	protected void exportDone(JComponent c, Transferable data, int action) {
		cleanup(c, action == MOVE);
	}

	protected abstract String exportString(JComponent c);

	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	public boolean importData(JComponent c, Transferable t) {
		if (canImport(c, t.getTransferDataFlavors())) {
			try {
				String str = (String) t
						.getTransferData(DataFlavor.stringFlavor);
				importString(c, str);
				return true;
			} catch (UnsupportedFlavorException ufe) {
			} catch (IOException ioe) {
			}
		}

		return false;
	}

	protected abstract void importString(JComponent c, String str);
}
