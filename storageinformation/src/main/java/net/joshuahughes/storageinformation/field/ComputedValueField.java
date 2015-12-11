package net.joshuahughes.storageinformation.field;

import java.io.File;
import java.util.Arrays;

import net.joshuahughes.storageinformation.operation.AddColumn;

public abstract class ComputedValueField<T> extends Field{
	public static ComputedValueField<?>[] computedFields = new ComputedValueField<?>[]{new DriveLabel(),new Files(),new FreeSpace(),new LastModified(),new TotalSpace(),new UsableSpace()};
	static
	{
		AddColumn.columnSet.addAll(Arrays.asList(computedFields));
	}
	public abstract T compute(File file);
}
