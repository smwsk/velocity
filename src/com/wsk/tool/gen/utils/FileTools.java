package com.wsk.tool.gen.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
public class FileTools {
	public void writeFile(File path, String className, String suffix, String context) {

		if (!path.exists()) {
			path.mkdirs();
		}
		File desFile = new File(path.getAbsolutePath() + "\\" +className+ suffix);
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(desFile),"UTF-8"));
			writer.write(context.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
