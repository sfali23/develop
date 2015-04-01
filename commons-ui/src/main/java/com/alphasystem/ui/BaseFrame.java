/**
 * 
 */
package com.alphasystem.ui;

import static java.lang.String.format;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.ResourceBundle;

import javax.swing.JFrame;

/**
 * @author sali
 * 
 */
public abstract class BaseFrame extends JFrame {

	private static final long serialVersionUID = 745337048570862399L;
	
	protected ResourceBundle resourceBundle;

	/**
	 * @throws HeadlessException
	 */
	public BaseFrame() throws HeadlessException {
	}

	/**
	 * @param gc
	 */
	public BaseFrame(GraphicsConfiguration gc) {
		super(gc);
	}

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public BaseFrame(String title) throws HeadlessException {
		super(title);
	}

	/**
	 * @param title
	 * @param gc
	 */
	public BaseFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		try {
			resourceBundle = ResourceBundle.getBundle(getResourceBundleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	protected abstract String getResourceBundleName();

	public String getString(String key) {
		String value = format("??%s??", key);
		if (resourceBundle != null) {
			try {
				value = resourceBundle.getString(key);
			} catch (Exception e) {
			}
		}
		return value;
	}

}
