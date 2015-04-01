/**
 * 
 */
package com.alphasystem.ui;

import static java.awt.BorderLayout.CENTER;
import static java.awt.Color.WHITE;
import static javax.swing.JDesktopPane.OUTLINE_DRAG_MODE;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * @author sali
 * 
 */
public class InternalFramePanel extends JPanel {

	private class InternalFrameListenerAdapter extends InternalFrameAdapter {

		@Override
		public void internalFrameClosed(InternalFrameEvent event) {
			JInternalFrame currentFrame = getCurrentFrame();
			if (currentFrame != null) {

			}
		}
	}

	private static final long serialVersionUID = 8692878771822122342L;

	@SuppressWarnings("unused")
	private static final int xOffset = 30;

	@SuppressWarnings("unused")
	private static final int yOffset = 30;

	private JDesktopPane desktop;

	public InternalFramePanel() {
		super(new BorderLayout());

		desktop = new JDesktopPane();
		desktop.setBackground(WHITE);
		desktop.setDragMode(OUTLINE_DRAG_MODE);

		add(desktop, CENTER);
	}

	public void addPanel(String title, JComponent panel) {
		addPanel(title, title, panel);
	}

	public void addPanel(String key, String title, JComponent panel) {
		JInternalFrame internalFrame = createInternalFrame(key, title, true,
				true, true, true, true, panel);
		internalFrame.setVisible(true);
		try {
			internalFrame.setMaximum(true);
		} catch (PropertyVetoException e) {
		}
	}

	public void closeAll() {
		JInternalFrame[] allFrames = desktop.getAllFrames();
		if (allFrames != null && allFrames.length > 0) {
			for (JInternalFrame jInternalFrame : allFrames) {
				closeFrame(jInternalFrame);
			}
		}
	}

	public void closeFrame() {
		closeFrame(getCurrentFrame());
	}

	private void closeFrame(JInternalFrame internalFrame) {
		if (internalFrame != null) {
			try {
				internalFrame.setClosed(true);
			} catch (PropertyVetoException e) {
			}
		}
	}

	public void closeFrame(String title) {
		JInternalFrame internalFrame = getInternalFrame(title);
		closeFrame(internalFrame);
	}

	private JInternalFrame createInternalFrame(String key, String title,
			boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable, boolean selected, JComponent panel) {
		JInternalFrame internalFrame = new JInternalFrame(title, resizable,
				closable, maximizable, iconifiable);
		if (panel != null) {
			internalFrame
					.addInternalFrameListener(new InternalFrameListenerAdapter());
			internalFrame.add(panel);
		}
		internalFrame.pack();
		// internalFrame.setLocation(xOffset * openFrameCount, yOffset
		// * openFrameCount);
		desktop.add(internalFrame);
		try {
			internalFrame.setSelected(selected);
		} catch (PropertyVetoException e) {
		}
		return internalFrame;
	}

	public JInternalFrame getCurrentFrame() {
		return desktop.getSelectedFrame();
	}

	public JPanel getCurrentPanel() {
		JInternalFrame currentFrame = getCurrentFrame();
		if (currentFrame != null) {
			JPanel contentPane = (JPanel) currentFrame.getContentPane();
			return (JPanel) contentPane.getComponent(0);
		}
		return null;
	}

	private JInternalFrame getInternalFrame(String title) {
		JInternalFrame result = null;
		JInternalFrame[] allFrames = desktop.getAllFrames();
		if (allFrames != null && allFrames.length > 0) {
			for (JInternalFrame jInternalFrame : allFrames) {
				if (jInternalFrame.getTitle().equals(title)) {
					result = jInternalFrame;
					break;
				}
			}
		}
		return result;
	}

	public boolean moveToFront(String title) {
		JInternalFrame frame = getInternalFrame(title);
		boolean exists = frame != null;
		if (exists) {
			frame.moveToFront();
			frame.pack();
			try {
				frame.setMaximum(false);
				frame.setMaximum(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
		return exists;
	}

}
