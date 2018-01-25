package org.test.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.junit.Test;
import org.test.app.util.FileUtil;

public class FileUtilTest {
	Logger logger = Logger.getLogger(FileUtil.class.getName());
	Formatter formatter = new SimpleFormatter();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    /**
     * Test Case to check if File does not exist is shown in logger
     */
	@Test
	public void testCSVFileNotExist() {
	    Handler handler = new StreamHandler(out, formatter);
	    logger.addHandler(handler);

		String filename = "test.csv";
		try {
			FileUtil.readFileCSV(filename);

			handler.flush();
	        String logMsg = out.toString();
	        assertNotNull(logMsg);
	        assertTrue(logMsg.toLowerCase().contains(("CSV File not found: " + filename).toLowerCase()));
		} finally {
			logger.removeHandler(handler);
		}
	}

	/**
	 * Test to check if a CSV doest have record
	 */
	@Test
	public void testCSVFileNoRecords() {
		Handler handler = new StreamHandler(out, formatter);
	    logger.addHandler(handler);

		String filename = "test_sample_no_contents.csv";

		List<String[]> noRecord = FileUtil.readFileCSV(filename);
		assertFalse(noRecord.isEmpty());
	}

	/**
	 * Test to check if CSV has 10 records based on the sample
	 */
	@Test
	public void testCSVHas10Records() {
		Handler handler = new StreamHandler(out, formatter);
	    logger.addHandler(handler);

		String filename = "sample.csv";

		List<String[]> records = FileUtil.readFileCSV(filename);
		assertEquals(records.size(), 10);
	}
}
