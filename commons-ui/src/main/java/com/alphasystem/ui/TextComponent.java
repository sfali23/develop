/**
 * 
 */
package com.alphasystem.ui;

import static javax.swing.text.StyleContext.DEFAULT_STYLE;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * @author sali
 * 
 */
public class TextComponent extends JTextPane {

	private static final long serialVersionUID = 5379591563797088801L;

	public static final String DEFAULT_STYLE_NAME = "default";

	protected Style defaultStyle;

	public TextComponent() {
		super();
		setEditable(false);
		initDefaultStyle();
		addStylesToDocument();
	}

	public void addStylesToDocument() {
		addStylesToDocument(getStyledDocument());
	}

	protected void addStylesToDocument(StyledDocument doc) {
		doc.addStyle("default", defaultStyle);
	}

	protected void initDefaultStyle() {
		defaultStyle = StyleContext.getDefaultStyleContext().getStyle(
				DEFAULT_STYLE);
		StyleConstants.setFontSize(defaultStyle, 12);
		StyleConstants.setFontFamily(defaultStyle, "Courier New");
	}

	public void setCharacterAttributes(int offset, int length, String styleName) {
		DefaultStyledDocument document = (DefaultStyledDocument) getStyledDocument();
		document.setCharacterAttributes(offset, length,
				document.getStyle(styleName), false);
	}

	public void write(String styleName, int index, String text) {
		StyledDocument doc = getStyledDocument();
		try {
			doc.insertString(index, text, doc.getStyle(styleName));
			setCaretPosition(index);
		} catch (BadLocationException e) {
			System.err.println(e.getMessage());
		}
		setCaretPosition(index);
	}

	public void write(String styleName, String text) {
		try {
			StyledDocument doc = getStyledDocument();
			int length = doc.getLength();
			doc.insertString(length, text, doc.getStyle(styleName));
			setCaretPosition(length);
		} catch (BadLocationException e) {
			System.err.println(e.getMessage());
		}
	}

	public void writeDefault(String text) {
		write(DEFAULT_STYLE_NAME, text);
	}
}
