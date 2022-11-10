package com.ds.assignment;

	import java.io.File;
	import java.io.IOException;
	import java.nio.file.Files;
	import java.nio.file.StandardOpenOption;
	import java.util.ArrayList;

	public class LifeGuards {

		public static void main(String[] args) {

			System.out.println("Remove Lifeguard.");

			String outputLoc = "./OutputFiles/";
			String inputLoc = "./InputFiles/";
			File inputDir = new File(inputLoc);
			File[] listFiles = inputDir.listFiles();

			for (File file : listFiles) {
				if (file.isFile() && file.getName().endsWith(".in")) {

					System.out.println("Input file: " + file.getName());

					InputReader reader = new InputReader(file.getAbsolutePath());
					try {
						ArrayList<GetShiftTime> listShifts = reader.readInputRecords();
						GaurdRemove removeLifeGuard = new GaurdRemove(listShifts);
						Integer maxCoverage = removeLifeGuard.maxCoverage();

						String outputFileName = file.getName().split(".in", 2)[0] + ".out";
						String outputFileLoc = outputLoc + outputFileName;
						File outputPath = new File(outputFileLoc);
						Files.write(outputPath.toPath(), maxCoverage.toString().getBytes());

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
