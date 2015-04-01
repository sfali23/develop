package com.alphasystem.ui;

import static com.jidesoft.dialog.ButtonPanel.AFFIRMATIVE_BUTTON;
import static com.jidesoft.dialog.ButtonPanel.CANCEL_BUTTON;
import static com.jidesoft.plaf.UIDefaultsLookup.getString;
import static java.awt.Cursor.WAIT_CURSOR;
import static java.awt.Cursor.getPredefinedCursor;
import static javax.swing.BorderFactory.createEmptyBorder;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;

import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;

public abstract class BaseDialog extends StandardDialog {

	public static final String BUTTON_TYPE_KEY = "buttonType";

	private static final long serialVersionUID = 5616089297916954410L;

	private static final String OK_BUTTON_TEXT = "OptionPane.okButtonText";
	private static final String CANCEL_BUTTON_TEXT = "OptionPane.cancelButtonText";

	protected JButton okButton;

	protected JButton cancelButton;

	protected JButton defaultButton;

	protected boolean showCancelButton = true;

	protected JFrame parentFrame;

	public BaseDialog() throws HeadlessException {
		this(null);
	}

	public BaseDialog(Dialog owner, boolean modal) throws HeadlessException {
		this(owner, null, modal);
	}

	public BaseDialog(Dialog owner, String title) throws HeadlessException {
		this(owner, title, true);
	}

	public BaseDialog(Dialog owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
		initButtons();
	}

	public BaseDialog(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
		initButtons();
	}

	public BaseDialog(Frame owner) throws HeadlessException {
		this(owner, null, true);
	}

	public BaseDialog(Frame owner, boolean modal) throws HeadlessException {
		this(owner, null, modal);
	}

	public BaseDialog(Frame owner, String title) throws HeadlessException {
		this(owner, title, true);
	}

	public BaseDialog(Frame owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
		initButtons();
	}

	@Override
	public JComponent createBannerPanel() {
		return null;
	}

	@Override
	public ButtonPanel createButtonPanel() {
		ButtonPanel buttonPanel = new ButtonPanel();

		JButton[] buttons = getButtons();
		if (isEmpty(buttons)) {
			return buttonPanel;
		}
		for (JButton button : buttons) {
			String type = (String) button.getClientProperty(BUTTON_TYPE_KEY);
			buttonPanel.add(button, type);
		}

		JButton defaultButton = getDefaultButton();
		setDefaultAction(defaultButton.getAction());
		getRootPane().setDefaultButton(defaultButton);
		buttonPanel.setBorder(createEmptyBorder(10, 10, 10, 10));

		return buttonPanel;
	}

	protected void dispose(int code) {
		setDialogResult(code);
		setVisible(false);
		dispose();
	}

	protected void doOnCancel(ActionEvent e) {
	}

	protected abstract boolean doOnOK(ActionEvent e);

	public JButton[] getButtons() {
		return new JButton[] { okButton, cancelButton };
	}

	public JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.putClientProperty(BUTTON_TYPE_KEY, CANCEL_BUTTON);
			cancelButton.setAction(new AbstractAction(getCancelButtonText()) {

				private static final long serialVersionUID = 1435364162907847060L;

				public void actionPerformed(ActionEvent e) {
					doOnCancel(e);
					dispose(RESULT_CANCELLED);
				}
			});
			setDefaultCancelAction(cancelButton.getAction());
		}
		return cancelButton;
	}

	public String getCancelButtonText() {
		return getString(CANCEL_BUTTON_TEXT);
	}

	public JButton getDefaultButton() {
		return okButton;
	}

	public JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.putClientProperty(BUTTON_TYPE_KEY, AFFIRMATIVE_BUTTON);
			okButton.setAction(new AbstractAction(getOkButtonText()) {

				private static final long serialVersionUID = 2768672264270528961L;

				@Override
				public void actionPerformed(final ActionEvent e) {
					final Cursor cursor = BaseDialog.this.getCursor();
					BaseDialog.this.setCursor(getPredefinedCursor(WAIT_CURSOR));
					okButton.setEnabled(false);
					Thread thread = new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								if (doOnOK(e)) {
									dispose(RESULT_AFFIRMED);
								}
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								BaseDialog.this.setCursor(cursor);
								okButton.setEnabled(true);
							}
						}
					});
					thread.start();
				}
			});
		}

		return okButton;
	}

	public String getOkButtonText() {
		return getString(OK_BUTTON_TEXT);
	}

	private void initButtons() {
		getOkButton();
		getCancelButton();
		setDefaultButton(null);
	}

	public boolean isShowCancelButton() {
		return showCancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setDefaultButton(JButton defaultButton) {
		this.defaultButton = defaultButton == null ? okButton : defaultButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public void setShowCancelButton(boolean showCancelButton) {
		this.showCancelButton = showCancelButton;
	}
}
