package net.joshuahughes.storageinformation.field;

import java.io.File;
import java.util.Date;

public class LastModified extends ComputedValueField<Date>{
	@Override
	protected Date compute(File drive) {
		return new Date(drive.lastModified());
	}
}
