package com.ds.assignment;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class InputReader {

	private String filePath = null;
	private int totalRecords = -1;
	private final String SPACE = " ";
	private final String RECORD_SEPERATOR = SPACE;

	public InputReader(String filePath) {
		File inputFile = new File(filePath);
		if (!inputFile.exists())
			throw new IllegalArgumentException("File existing. File: " + filePath);
		this.filePath = filePath;
	}

	public ArrayList<GetShiftTime> readInputRecords() throws IOException {
		File inputFile = new File(filePath);
		String line;
		BufferedReader reader = null;
		ArrayList<GetShiftTime> listGuardShifts = null;
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			line = reader.readLine();

			this.totalRecords = Integer.parseInt(line);
			listGuardShifts = new ArrayList<GetShiftTime>(totalRecords);

			for (int counter = 1; counter <= totalRecords; counter++) {
				line = reader.readLine();
				GetShiftTime shift = parseShift(line);
				listGuardShifts.add(shift);
			}

		} catch (IOException e) {
			throw e;
		} finally {
			reader.close();
		}
		
		return listGuardShifts;
	}

	private GetShiftTime parseShift(String line) {

		String[] splits = line.split(RECORD_SEPERATOR);
		int start = Integer.parseInt(splits[0]);
		int end = Integer.parseInt(splits[1]);
		return new GetShiftTime(start, end);
	}
}