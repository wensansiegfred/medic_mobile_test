package org.test.app.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.test.app.model.Debt;
/**
 *
 * @author WensanSiegfred
 * CSV Reader/Writer
 *
 */
public class FileUtil {
	private static BufferedReader br = null;
	private static final Logger logger = Logger.getLogger(FileUtil.class.getName());
	private static final String csvLineSeparator = ",";
	private static final String newLineSeparator = "\n";
	private static FileWriter csvWriter = null;

	/**
	 *
	 * @param csvPath
	 * @return Collection of data per line
	 */
	public static List<String[]> readFileCSV(String csvPath) {
		String line = "";
		List<String[]> readData = new ArrayList<String[]>();

		try {
			br = new BufferedReader(new FileReader(csvPath));
			while ((line = br.readLine()) != null) {
				readData.add(line.split(csvLineSeparator));
			}
		} catch (FileNotFoundException e) {
        	logger.log(Level.SEVERE, "CSV File not found: " + csvPath);
        } catch (IOException e) {
        	logger.log(Level.SEVERE, "An ERROR occured parsing CSV file.");
        	e.printStackTrace();
        } finally {
        	logger.log(Level.INFO, "START: Closing connections.");
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "An ERROR occured closing buffer.");
                    e.printStackTrace();
                }
            }
        }

		return readData;
	}

	/**
	 *
	 * @param csvPath (path/filename of CSV output file)
	 * @param debtList (Collection list of Debt objects)
	 * Writes an output CSV file
	 */
	public static void writeDebtCSVFile(String csvPath, List<Debt> debtList) {

		try {
			csvWriter = new FileWriter(csvPath);

			for(Debt debt : debtList) {
				csvWriter.append(debt.getDebtor());
				csvWriter.append(csvLineSeparator);
				csvWriter.append(debt.getCreditor());
				csvWriter.append(csvLineSeparator);
				csvWriter.append(String.valueOf(debt.getAmount()));
				csvWriter.append(newLineSeparator);
			}
		} catch(IOException e) {
			logger.log(Level.SEVERE, "An ERROR occured parsing CSV file.");
        	e.printStackTrace();
		} finally {
			try {
				csvWriter.flush();
				csvWriter.close();
				logger.log(Level.INFO, "DONE: Creating CSV file: " + csvPath);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "An ERROR occured closing csvWriter.");
	            e.printStackTrace();
			}
		}
	}
}
