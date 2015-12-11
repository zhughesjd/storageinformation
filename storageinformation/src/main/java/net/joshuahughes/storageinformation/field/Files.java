package net.joshuahughes.storageinformation.field;

import java.io.File;

public class Files extends ComputedValueField<String>{
	@Override
	public String compute(File candidate) {
		File[] files = candidate.listFiles();
		if(candidate.isFile() || files == null)
			return candidate.getAbsolutePath();
		StringBuilder builder = new StringBuilder();
		for(File child : files)
			builder.append(compute(child)+",");
		return builder.toString();
	}
}
