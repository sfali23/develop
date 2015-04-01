/**
 * 
 */
package com.alphasystem.ui;

import static javax.swing.Action.SMALL_ICON;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.alphasystem.util.AppUtil;

/**
 * @author sali
 * 
 */
public class ImageComponent extends JLabel {

	private static final long serialVersionUID = -3793572594964415100L;

	private ImageIcon icon;

	public ImageComponent(Action action) {
		this((ImageIcon) action.getValue(SMALL_ICON));
	}

	public ImageComponent(ImageIcon icon) {
		this.icon = icon;
		setIcon(icon);
		setOpaque(true);
		setPreferredSize(new Dimension(getWidth(), getHeight()));
	}

	public ImageComponent(String path) {
		this(new ImageIcon(AppUtil.getUrl(path)));
	}

	@Override
	public int getHeight() {
		return icon.getIconHeight();
	}

	@Override
	public int getWidth() {
		return icon.getIconWidth();
	}

	@Override
	public String toString() {
		return icon.toString();
	}

}
