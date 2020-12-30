package com.github.verhagen.timesheet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TimeSheetLoaderTest {
	private Path resourcesPath = Paths.get("src", "test", "resources");
	private TimeSheetLoader loader = new TimeSheetLoader(resourcesPath);


	@Test
	void loadFiles() throws Exception {
		List<File> files = loader.findFiles();

		assertNotNull(files);
		assertEquals(1, files.size());
		assertEquals("2020.01-time-sheet.csv", files.get(0).getName());
	}

	@Test
	void loadTimeSheet() throws Exception {
//		loader.load("2020.01");
	}

}
