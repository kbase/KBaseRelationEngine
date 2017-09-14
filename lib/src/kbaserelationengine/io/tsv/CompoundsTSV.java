package kbaserelationengine.io.tsv;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class CompoundsTSV extends TSVFile{

	enum H{KBChemID, Name, CAS, Company_CatalogNumber, FW, Synonyms, Antibiotics, Metal, 
		FEBA_carbon, FEBA_nitrogen, FEBA_stress, All_star}
	
	static final String ID_PREFIX = "kb_cnp"; 
//	static final int DATA_SIZE = 500000;
	static final int DATA_SIZE = 100000;
	
	public CompoundsTSV(String fileName) {
		super(fileName, ID_PREFIX, DATA_SIZE, H.class);
	}

	@Override
	public void processRecord(CSVRecord record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildFakeRecord(int index, List<String> record) {
//		KBChemID
		record.add(ID_PREFIX + index);
//		Name
		record.add("Name" + index);
//		CAS
		record.add("CAS" + index);
//		Company_CatalogNumber
		record.add("Company_CatalogNumber" + index);
//		FW
		record.add("FW" + index);
//		Synonyms
		record.add("Synonyms" + index);
//		Antibiotics
		record.add("Antibiotics" + index);
//		Metal
		record.add("Metal" + index);
//		FEBA_carbon
		record.add("FEBA_carbon" + index);
//		FEBA_nitrogen
		record.add("FEBA_nitrogen" + index);
//		FEBA_stress
		record.add("FEBA_stress" + index);
//		All_star
		record.add("All_star" + index);		
	}

}
