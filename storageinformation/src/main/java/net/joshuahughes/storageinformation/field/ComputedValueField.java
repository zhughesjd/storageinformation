package net.joshuahughes.storageinformation.field;

import java.io.File;

public abstract class ComputedValueField<T> extends Field{
	public static ComputedValueField<?>[] computedFields = new ComputedValueField<?>[]{new DriveLabel(),new Files(),new FreeSpace(),new LastModified(),new TotalSpace(),new UsableSpace()};
	public abstract T compute(File file);
}
