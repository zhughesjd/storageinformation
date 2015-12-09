package net.joshuahughes.storageinformation.field;

import java.io.File;

public class TotalSpace extends ComputedValueField<Long>{
	@Override
	protected Long compute(File drive) {
		return drive.getTotalSpace();
	}
}
