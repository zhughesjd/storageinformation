package net.joshuahughes.storageinformation.field;

import java.io.File;

public class FreeSpace extends ComputedValueField<Long>{
	@Override
	public Long compute(File drive) {
		return drive.getFreeSpace();
	}
}
