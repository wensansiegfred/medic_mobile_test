package org.test.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.test.app.model.Debt;
import org.test.app.util.FileUtil;

public class CSVParser {
	//Logger Handler
	private static final Logger logger = Logger.getLogger(CSVParser.class.getName());
	private static String csvFile;
	private static List<String[]> csvData;
	private static String outputCSVFile = "output.csv";

	public static void main(String args[]) {
		//getting parameters
		try {
			for(int i = 0; i < args.length; i++) {
				if (args[i] != null) {
					if (args[i].split("=")[0].equals("--csvFile") && args[i].split("=")[1] != null) {
						csvFile = args[i].split("=")[1];
					} else if (args[i].split("=")[0].equals("--csvOutputFile") && args[i].split("=")[1] != null) {
						outputCSVFile = args[i].split("=")[1];
					}
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			logger.log(Level.INFO, "Invalid Parameters.");
			System.exit(0);
		}

		if (csvFile == null) {
			logger.log(Level.INFO, "MISSING CSV file, please provide path for CSV file.");
			System.exit(0);
		}

        List<Debt> debtList = new ArrayList<Debt>();

        logger.log(Level.INFO, "CSV Parsing Start:");
        csvData = FileUtil.readFileCSV(csvFile);

        if (csvData != null && csvData.size() > 0) {
        	for (String lineValue[] : csvData) {
        		if (debtList.size() < 1) {
                	debtList.add(new Debt(lineValue[0], lineValue[1], Double.parseDouble(lineValue[2])));
                } else {
                	boolean exist = false;
                	int listSize = debtList.size();
                	for(int i = 0; i < listSize; i++) {
                		if (debtList.get(i).getDebtor().equals(lineValue[0]) && debtList.get(i).getCreditor().equals(lineValue[1])) {
                			Double amount = debtList.get(i).getAmount();
                			debtList.get(i).setAmount(Double.parseDouble(lineValue[2]) + amount);
                			exist = true;
                			break;
                		}
                	}
                	if (!exist)
                		debtList.add(new Debt(lineValue[0], lineValue[1], Double.parseDouble(lineValue[2])));
                }
        	}

        	//sort Collection according to names of creditor
        	Collections.sort(debtList);

    		logger.log(Level.INFO, "DONE: Parsing CSV File., START: Creating CSV output file.");
    		FileUtil.writeDebtCSVFile(outputCSVFile, debtList);
        } else {
        	logger.log(Level.INFO, "CSV file contains no data.");
        }
	}
}
