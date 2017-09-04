package kbaserelationengine.io.tsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;


public abstract class TSVFile {
	private String fileName;
	private int dataSize;
	private String idPrefix;
	private Class headerClass;
	private CSVFormat format;
	private int currentId = 0;
	
	public static String rndID(String idPrefix, int dataSize){
		return idPrefix + (int)(Math.random() * dataSize);
	}
	
	public TSVFile(String fileName, String idPrefix, int dataSize, Class headerClass) {
		super();
		this.fileName = fileName;
		this.idPrefix = idPrefix;
		this.dataSize = dataSize;
		this.headerClass = headerClass;
		format = CSVFormat.TDF.withHeader(headerClass);
	}
	
	public abstract void processRecord(CSVRecord record);
	public abstract void buildFakeRecord(int index, List<String> record);
	
	
	public void parse() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try{
			for(CSVRecord record: format.parse(br)){
				processRecord(record);
			}
		} finally{
			br.close();
		}
	}
	
	
	public void buildFakeData() throws IOException{
		CSVFormat tsvFileFormat = CSVFormat.TDF;
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		CSVPrinter printer = new CSVPrinter(bw, tsvFileFormat);
		
		printer.printRecord(headerClass.getEnumConstants());
		List<String> record = new ArrayList<String>();
		for(int i = 0; i < dataSize; i++ ){
			record.clear();
			buildFakeRecord(i, record);
			printer.printRecord(record);
		}
		bw.flush();
		bw.close();
		printer.close();
	}	
	
}
