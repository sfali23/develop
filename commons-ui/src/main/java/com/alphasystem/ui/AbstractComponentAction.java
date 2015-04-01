/**
 * 
 */
package com.alphasystem.ui;

import static com.alphasystem.util.AppUtil.getUrl;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.FileBasedBuilderParameters;
import org.apache.commons.configuration2.builder.fluent.Parameters;

import com.jidesoft.icons.IconsFactory;

/**
 * @author sali
 * 
 */
public abstract class AbstractComponentAction extends AbstractAction {

	private static final long serialVersionUID = -2409160653941903477L;

	protected PropertiesConfiguration configuration;

	public AbstractComponentAction() {
		super();
		loadConfiguration();
		Class<? extends AbstractComponentAction> actionClass = getClass();
		setName(getStringProperty(actionClass, NAME));
		setActionCommand(getStringProperty(actionClass, ACTION_COMMAND_KEY));
		setIcon(getIcon(actionClass));
		setShortDescription(getStringProperty(actionClass, SHORT_DESCRIPTION));
		setLongDescription(getStringProperty(actionClass, LONG_DESCRIPTION));
	}

	public AbstractComponentAction(String name) {
		this(name, null, null, null, null, null, null);
	}

	public AbstractComponentAction(String name, Icon icon) {
		this(name, icon, null, null, null, null, null);
	}

	public AbstractComponentAction(String name, Icon icon, String actionCommand) {
		this(name, icon, actionCommand, null, null, null, null);
	}

	public AbstractComponentAction(String name, Icon icon,
			String actionCommand, String shortDescription,
			String longDescription, KeyStroke keyStroke, Integer mnemonic) {
		super(name, icon);
		setActionCommand(actionCommand);
		setShortDescription(shortDescription);
		setLongDescription(longDescription);
		setKeyStroke(keyStroke);
		setMnemonic(mnemonic);
	}

	public AbstractComponentAction(String name, String actionCommand) {
		this(name, null, actionCommand, null, null, null, null);
	}

	public String getActionCommand() {
		return (String) getValue(ACTION_COMMAND_KEY);
	}

	public Icon getIcon() {
		return (Icon) getValue(SMALL_ICON);
	}

	protected Icon getIcon(Class<? extends AbstractComponentAction> actionClass) {
		String iconPath = getStringProperty(actionClass, SMALL_ICON);
		return isBlank(iconPath) ? null : IconsFactory.getImageIcon(
				actionClass, iconPath);
	}

	public KeyStroke getKeyStroke() {
		return (KeyStroke) getValue(ACCELERATOR_KEY);
	}

	public String getLongDescription() {
		return (String) getValue(LONG_DESCRIPTION);
	}

	public Integer getMnemonic() {
		return (Integer) getValue(MNEMONIC_KEY);
	}

	public String getName() {
		return (String) getValue(NAME);
	}

	protected <T> T getProperty(Class<T> propertyValueClass,
			Class<? extends AbstractComponentAction> actionClass,
			String propertyName) {
		if (configuration == null) {
			return null;
		}
		String key = format("%s.%s", actionClass.getName(), propertyName);
		T result = null;
		try {
			result = configuration.get(propertyValueClass, key);
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	public String getShortDescription() {
		return (String) getValue(SHORT_DESCRIPTION);
	}

	protected String getStringProperty(
			Class<? extends AbstractComponentAction> actionClass,
			String propertyName) {
		return getProperty(String.class, actionClass, propertyName);
	}

	private void loadConfiguration() {
		Parameters parameters = new Parameters();
		URL url = null;
		try {
			url = getUrl("action.properties");
		} catch (Exception e) {
		}
		try {
			FileBasedBuilderParameters params = parameters.fileBased();
			if (url != null) {
				params = params.setURL(url);
			}
			FileBasedConfigurationBuilder<PropertiesConfiguration> builder = new FileBasedConfigurationBuilder<PropertiesConfiguration>(
					PropertiesConfiguration.class).configure(params);
			configuration = builder.getConfiguration();
		} catch (Exception ex) {
			configuration = new PropertiesConfiguration();
		}
	}

	public void setActionCommand(String actionCommand) {
		String ac = actionCommand == null ? getName() : actionCommand;
		putValue(ACTION_COMMAND_KEY, ac);
	}

	public void setIcon(Icon icon) {
		putValue(SMALL_ICON, icon);
	}

	public void setKeyStroke(KeyStroke keyStroke) {
		if (keyStroke != null) {
			putValue(ACCELERATOR_KEY, keyStroke);
		}
	}

	public void setLongDescription(String longDescription) {
		String ld = (String) (longDescription == null ? getValue(NAME)
				: longDescription);
		putValue(LONG_DESCRIPTION, ld);
	}

	public void setMnemonic(Integer mnemonic) {
		if (mnemonic != null) {
			putValue(MNEMONIC_KEY, mnemonic);
		}
	}

	public void setName(String name) {
		putValue(NAME, name);
	}

	public void setShortDescription(String shortDescription) {
		String sd = (String) (shortDescription == null ? getValue(NAME)
				: shortDescription);
		putValue(SHORT_DESCRIPTION, sd);
	}

}
