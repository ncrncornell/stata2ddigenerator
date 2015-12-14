package edu.ncrn.cornell.ced2ar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;


import edu.ncrn.cornell.ced2ar.csv.StataCsvGenerator;
import edu.ncrn.cornell.ced2ar.csv.VariableCsv;
import edu.ncrn.cornell.ced2ar.ddi.CodebookVariable;
import edu.ncrn.cornell.ced2ar.ddi.VariableDDIGenerator;

/**
*
*@author NCRN Project Team
* Gnerates DDI from data file. (SAS ot STATA)  
*@author Cornell University, Copyright 2012-2015
*@author  Venky Kambhampaty
*
*@author Cornell Institute for Social and Economic Research
*@author Cornell Labor Dynamics Institute
*@author NCRN Project Team 
*/


public class GenerateDDI {
	private static final Logger logger = Logger.getLogger(GenerateDDI.class);
	
	public void generateDDI(String dataFile,boolean processSummaryStatics, long observationLimit) throws Exception {
		long s  = System.currentTimeMillis();
		VariableCsv variableCsv =null;
		if(dataFile.toLowerCase().endsWith(".dta")) {
			StataCsvGenerator stataCsvGenerator = new StataCsvGenerator();
			variableCsv = stataCsvGenerator.generateVariablesCsv(dataFile,processSummaryStatics,observationLimit);
		}
		else  {
			throw new RuntimeException("Looks like datafile is not stata file. File ext should be .dta");
		}
		
		logger.info("Time to gen csv: " +((System.currentTimeMillis()-s)/1000) + "Seconds ");
		createFile(variableCsv.getVariableStatistics(),"vars.csv");
		createFile(variableCsv.getVariableValueLables(),"varLabels.csv");
		logger.info("Successfully created csv files.");
		
		
		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		List<CodebookVariable> codebookVariables =  variableDDIGenerator.getCodebookVariables(variableCsv);
		Document document = variableDDIGenerator.getCodebookDocument(codebookVariables);
		String ddi2String = variableDDIGenerator.DOM2String(document);
		createFile(ddi2String,"ddi.xml");
		logger.info("Successfully created DDI file.");
	}
	
	private void createFile(String csv,String fileName) throws IOException{
		BufferedWriter bw  = null;
		try {
			File varsFile = new File(fileName);
			varsFile.createNewFile();
			FileWriter fw = new FileWriter(varsFile.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(csv);
		}
		finally {
			bw.close();
		}
	}
}
