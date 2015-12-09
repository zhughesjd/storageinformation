package net.joshuahughes.storageinformation.field;

import java.io.File;

public class Files extends ComputedValueField<String>{
	@Override
	protected String compute(File candidate) {
		if(candidate.isFile())
			return candidate.getAbsolutePath();
		StringBuilder builder = new StringBuilder();
		for(File child : candidate.listFiles())
			builder.append(compute(child)+",");
		return builder.toString();
	}
}
