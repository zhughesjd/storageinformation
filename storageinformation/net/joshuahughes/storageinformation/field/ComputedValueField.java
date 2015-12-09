package net.joshuahughes.storageinformation.field;

import java.io.File;
import java.lang.reflect.ParameterizedType;

public abstract class ComputedValueField<T> extends Field{
	public static ComputedValueField<?>[] computedFields = new ComputedValueField<?>[]{new DriveLabel(),new Files(),new FreeSpace(),new LastModified(),new TotalSpace(),new UsableSpace()};
	public ComputedValueField() {
		super(null,null);
		this.id = this.getClass().getSimpleName();
		this.type = (Class<?>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	public final T compute(char driveLetter)
	{
		return compute(new File(driveLetter+":/"));
	}
	protected abstract T compute(File file);
}
