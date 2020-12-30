package com.github.verhagen.timesheet;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TimeSheetLoader {
	private Path sourcePath;


	public TimeSheetLoader(Path sourcePath) {
		this.sourcePath = sourcePath;
	}


	public List<File> findFiles() {
		return Arrays.asList(sourcePath.toFile().listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (Pattern.matches("^\\d{4}.\\d{2}-time-sheet\\.csv$", name)) {
					return true;
				}
				return false;
			}
		}));
	}

}
