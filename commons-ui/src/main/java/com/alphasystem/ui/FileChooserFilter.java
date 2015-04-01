/**
 * 
 */
package com.alphasystem.ui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.alphasystem.util.FileFilterAdapter;

/**
 * @author sali
 * 
 */
public class FileChooserFilter extends FileFilter {

	private FileFilterAdapter adapter;

	public FileChooserFilter(String... extensions) {
		super();
		adapter = new FileFilterAdapter(extensions);
	}

	@Override
	public boolean accept(File f) {
		return adapter.accept(f);
	}

	@Override
	public String getDescription() {
		return adapter.getDescription();
	}

}
