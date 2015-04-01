package com.alphasystem.ui;

import static com.alphasystem.util.Utils.NEW_LINE;
import static java.awt.BorderLayout.CENTER;
import static java.lang.String.format;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.alphasystem.util.Utils;

public class ExceptionViewer extends BaseDialog {

	private static final long serialVersionUID = -6042879347346592078L;

	/**
	 * DOCUMENT ME!
	 * 
	 * @param t
	 *            DOCUMENT ME!
	 * @return DOCUMENT ME!
	 */
	private static void getStackTrace(Throwable t, StringBuilder buffer) {
		if (t == null) {
			return;
		}
		buffer.append(format("%s:%s%s", t.getClass().getName(), t.getMessage(),
				NEW_LINE));
		StackTraceElement[] ste = t.getStackTrace();
		for (int i = 0; i < ste.length; i++) {
			buffer.append(format("%s %s%s", "\tat",
					getStackTraceElement(ste[i]), NEW_LINE));
		}
		getStackTrace(t.getCause(), buffer);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param ste
	 *            DOCUMENT ME!
	 * @return DOCUMENT ME!
	 */
	private static String getStackTraceElement(StackTraceElement ste) {
		StringBuilder result = new StringBuilder();
		result.append(ste.getClassName() + "." + ste.getMethodName() + "(");
		if (ste.isNativeMethod()) {
			result.append("Native Method");
		} else {
			String fileName = ste.getFileName();
			int lineNumber = ste.getLineNumber();
			if (fileName == null) {
				result.append("Unknown Source");
			} else {
				result.append(fileName);
			}
			if (lineNumber > 0) {
				result.append(":" + lineNumber);
			}
		}
		result.append(")");
		return result.toString();
	}

	public static void showExceptionViewer(String title, JFrame parent,
			Throwable t) {
		ExceptionViewer exceptionViewer = new ExceptionViewer(title, parent, t);
		exceptionViewer.showDialog();
	}

	private String stackTrace;

	private boolean abort;

	/**
	 * Creates a new ExceptionViewer object.
	 * 
	 * @param title
	 *            DOCUMENT ME!
	 * @param message
	 *            DOCUMENT ME!
	 * @param parent
	 *            DOCUMENT ME!
	 * @param stackTrace
	 *            DOCUMENT ME!
	 */
	public ExceptionViewer(String title, JFrame parent, String stackTrace) {
		super(parent, title);
		this.stackTrace = stackTrace;
	}

	public ExceptionViewer(String title, JFrame parent, Throwable t) {
		this(title, parent, "");
		stackTrace = getStackTrace(t);
	}

	public ExceptionViewer(String title, Throwable t) {
		this(title, null, t);
	}

	@Override
	public JComponent createContentPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JTextArea textArea = Utils.createTextArea();
		textArea.setText(stackTrace);
		JScrollPane pane = new JScrollPane(textArea);
		panel.add(pane, CENTER);
		return pane;
	}

	@Override
	protected boolean doOnOK(ActionEvent e) {
		if (abort) {
			System.exit(0);
		}
		return true;
	}

	@Override
	public String getOkButtonText() {
		return abort ? "Abort" : super.getOkButtonText();
	}

	public String getStackTrace(Throwable t) {
		StringBuilder stackTrace = new StringBuilder();
		getStackTrace(t, stackTrace);
		return stackTrace.toString();
	}

	public void setAbort(boolean abort) {
		this.abort = abort;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public void setThrowable(Throwable throwable) {
		stackTrace = getStackTrace(throwable);
	}

	public void showDialog() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				pack();
				setLocationRelativeTo(getParent());
				setVisible(true);
			}
		});
	}

}
