package net.joshuahughes.storageinformation.field;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

public class DriveLabel extends ComputedValueField<String>{
	@Override
	protected String compute(File drive) {
		FileSystemView view = FileSystemView.getFileSystemView();
		String name = view.getSystemDisplayName(drive);
		if (name == null) { return null; }
		name = name.trim();
		if (name == null || name.length() < 1) {
			return null;
		}
		int index = name.lastIndexOf(" (");
		if (index > 0) {
			name = name.substring(0, index);
		}
		return name;
	}
}
