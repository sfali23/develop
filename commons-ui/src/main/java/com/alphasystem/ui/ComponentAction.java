/**
 * 
 */
package com.alphasystem.ui;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.KeyStroke;

/**
 * @author sali
 * 
 */
public class ComponentAction extends AbstractComponentAction {

	private static final long serialVersionUID = 7092960519212672969L;

	public static final String ACTION_DATA_KEY_PREFIX = "actionData.";

	public static final String NO_OP_KEY = "no-op";

	private static Pattern pattern;

	static {
		pattern = Pattern.compile(ACTION_DATA_KEY_PREFIX);
	}

	public static ComponentAction createAction(String name) {
		return createAction(name, null);
	}

	public static ComponentAction createAction(String name, Icon icon,
			String actionCommand) {
		return createAction(name, icon, actionCommand, null, null, null, null);
	}

	public static ComponentAction createAction(String name, Icon icon,
			String actionCommand, String shortDescription,
			String longDescription, KeyStroke keyStroke, Integer mnemonic) {
		return new ComponentAction(name, icon, actionCommand, shortDescription,
				longDescription, keyStroke, mnemonic);
	}

	public static ComponentAction createAction(String name, String actionCommand) {
		return createAction(name, null, actionCommand);
	}

	public static ComponentAction createAction(String name,
			String actionCommand, String shortDescription,
			String longDescription) {
		return createAction(name, null, actionCommand, shortDescription,
				longDescription, null, null);
	}

	public ComponentAction() {
		super();
	}

	public ComponentAction(String name) {
		super(name);
	}

	public ComponentAction(String name, Icon icon) {
		super(name, icon);
	}

	public ComponentAction(String name, Icon icon, String actionCommand,
			String shortDescription, String longDescription,
			KeyStroke keyStroke, Integer mnemonic) {
		super(name, icon, actionCommand, shortDescription, longDescription,
				keyStroke, mnemonic);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommnand = e.getActionCommand();
		if (actionCommnand == null) {
			actionCommnand = (String) getValue(ACTION_COMMAND_KEY);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		Object[] keys = getKeys();
		if (keys != null && keys.length > 0) {
			for (Object _key : keys) {
				String key = (String) _key;
				Matcher matcher = pattern.matcher(key);
				if (matcher.find()) {
					String paramKey = key.substring(matcher.end(0)).trim();
					params.put(paramKey, getValue(key));
				}
			}
		}
		//ObservableObject.getInstance().actionPerformed(actionCommnand, params);
	}

}
