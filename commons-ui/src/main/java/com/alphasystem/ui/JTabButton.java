/**
 * 
 */
package com.alphasystem.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

import com.alphasystem.util.AppUtil;

/**
 * @author sali
 * 
 */
public class JTabButton extends JButton {

	private static final long serialVersionUID = 2735956557335006492L;

	private final static MouseListener buttonMouseListener = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		public void mouseExited(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	};

	public JTabButton(String imagePath) {
		ImageIcon icon = new ImageIcon(AppUtil.getUrl(imagePath));
		setIcon(icon);
		setPreferredSize(new Dimension(icon.getIconWidth(),
				icon.getIconHeight()));
		// Make the button looks the same for all Laf's
		setUI(new BasicButtonUI());
		// Make it transparent
		setContentAreaFilled(false);
		// No need to be focusable
		setFocusable(false);
		setBorder(BorderFactory.createEtchedBorder());
		setBorderPainted(false);
		// Making nice rollover effect
		// we use the same listener for all buttons
		addMouseListener(buttonMouseListener);
		setRolloverEnabled(true);
	}

	@Override
	public int getHeight() {
		return getIcon().getIconHeight();
	}

	@Override
	public int getWidth() {
		return getIcon().getIconWidth();
	}

}
