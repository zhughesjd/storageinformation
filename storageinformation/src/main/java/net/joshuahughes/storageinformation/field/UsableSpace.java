package net.joshuahughes.storageinformation.field;

import java.io.File;

public class UsableSpace extends ComputedValueField<Long>{
	@Override
	public Long compute(File drive) {
		return drive.getUsableSpace();
	}
}
