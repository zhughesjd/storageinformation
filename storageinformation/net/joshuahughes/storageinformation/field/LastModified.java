package net.joshuahughes.storageinformation.field;

import java.io.File;
import java.util.Date;

public class LastModified extends ComputedValueField<Date>{
	@Override
	public Date compute(File drive) {
		return new Date(drive.lastModified());
	}
}
