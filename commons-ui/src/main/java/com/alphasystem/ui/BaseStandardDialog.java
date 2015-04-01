/**
 * 
 */
package com.alphasystem.ui;

import static com.jidesoft.dialog.ButtonPanel.AFFIRMATIVE_BUTTON;
import static com.jidesoft.dialog.ButtonPanel.CANCEL_BUTTON;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.plaf.UIDefaultsLookup;

/**
 * @author tkuser
 * 
 */
public abstract class BaseStandardDialog extends StandardDialog {

	private static final long serialVersionUID = 2380131772849824599L;

	/**
	 * @throws HeadlessException
	 */
	public BaseStandardDialog() throws HeadlessException {
	}

	/**
	 * @param owner
	 * @param modal
	 * @throws HeadlessException
	 */
	public BaseStandardDialog(Dialog owner, boolean modal)
			throws HeadlessException {
		super(owner, modal);
	}

	/**
	 * @param owner
	 * @param title
	 * @throws HeadlessException
	 */
	public BaseStandardDialog(Dialog owner, String title)
			throws HeadlessException {
		super(owner, title);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @throws HeadlessException
	 */
	public BaseStandardDialog(Dialog owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 * @throws HeadlessException
	 */
	public BaseStandardDialog(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
	}

	/**
	 * @param owner
	 * @throws HeadlessException
	 */
	public BaseStandardDialog(Frame owner) throws HeadlessException {
		super(owner);
	}

	/**
	 * @param owner
	 * @param modal
	 * @throws HeadlessException
	 */
	public BaseStandardDialog(Frame owner, boolean modal)
			throws HeadlessException {
		super(owner, modal);
	}

	/**
	 * @param owner
	 * @param title
	 * @throws HeadlessException
	 */
	public BaseStandardDialog(Frame owner, String title)
			throws HeadlessException {
		super(owner, title);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @throws HeadlessException
	 */
	public BaseStandardDialog(Frame owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
	}

	protected void closeDialog(int result) {
		setDialogResult(result);
		setVisible(false);
		dispose();
	}

	@Override
	public JComponent createBannerPanel() {
		return null;
	}

	@Override
	public ButtonPanel createButtonPanel() {
		ButtonPanel buttonPanel = new ButtonPanel();

		JButton okButton = new JButton();
		JButton cancelButton = new JButton();
		buttonPanel.addButton(okButton, AFFIRMATIVE_BUTTON);
		buttonPanel.addButton(cancelButton, CANCEL_BUTTON);

		okButton.setAction(new AbstractAction(UIDefaultsLookup
				.getString("OptionPane.okButtonText")) {

			private static final long serialVersionUID = -3188466467466180040L;

			public void actionPerformed(ActionEvent e) {
				try {
					if (doPostAffirm()) {
						closeDialog(RESULT_AFFIRMED);
					}
				} catch (IllegalArgumentException ex) {
				}
			}
		});
		cancelButton.setAction(new AbstractAction(UIDefaultsLookup
				.getString("OptionPane.cancelButtonText")) {

			private static final long serialVersionUID = 1435364162907847060L;

			public void actionPerformed(ActionEvent e) {
				if (doPostCancel()) {
					closeDialog(RESULT_CANCELLED);
				}
			}
		});

		setDefaultCancelAction(cancelButton.getAction());
		setDefaultAction(okButton.getAction());
		getRootPane().setDefaultButton(okButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return buttonPanel;
	}

	protected boolean doPostAffirm() {
		return true;
	}

	protected boolean doPostCancel() {
		return true;
	}

}
