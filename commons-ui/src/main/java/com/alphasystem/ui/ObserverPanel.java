/**
 * 
 */
package com.alphasystem.ui;

import static com.alphasystem.util.context.BaseCommand.EXIT_COMMAND;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alphasystem.util.context.ContextCommand;
import com.alphasystem.util.context.ObservableContext;
import com.alphasystem.util.context.ObservableObject;

/**
 * @author sali
 * 
 */
public abstract class ObserverPanel<C extends ContextCommand, T extends ObservableContext<C>>
		extends JPanel implements Observer {

	private static final long serialVersionUID = -3891641101968078707L;

	protected static final Log LOG = LogFactory.getLog(ObserverPanel.class);

	protected JFrame owner;

	/**
	 * @param isDoubleBuffered
	 */
	public ObserverPanel(boolean isDoubleBuffered, JFrame owner) {
		this(new FlowLayout(), isDoubleBuffered, owner);
	}

	/**
	 * 
	 */
	public ObserverPanel(JFrame owner) {
		this(true, owner);
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public ObserverPanel(LayoutManager layout, boolean isDoubleBuffered,
			JFrame owner) {
		super(layout, isDoubleBuffered);
		@SuppressWarnings("rawtypes")
		ObservableObject instance = ObservableObject
				.getInstance(getObservableObjectClass());
		if (instance != null) {
			instance.addObserver(this);
		}
		this.owner = owner;
	}

	@SuppressWarnings("rawtypes")
	protected abstract Class<? extends ObservableObject> getObservableObjectClass();

	/**
	 * @param layout
	 */
	public ObserverPanel(LayoutManager layout, JFrame owner) {
		this(layout, true, owner);
	}

	protected abstract void doUpdate(T context);

	public void exit() {
		LOG.info("Exiting Application");
		preExit();
		if (owner != null) {
			owner.dispose();
			owner.setVisible(false);
		}
		System.exit(0);
	}

	public JFrame getOwner() {
		return owner;
	}

	protected abstract void preExit();

	public void setOwner(JFrame owner) {
		this.owner = owner;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg == null) {
			return;
		}
		@SuppressWarnings("unchecked")
		T context = (T) arg;
		ContextCommand command = context.getCommand();
		if (command == null) {
			return;
		}
		if (command.equals(EXIT_COMMAND)) {
			exit();
		}
		doUpdate(context);
	}

}
