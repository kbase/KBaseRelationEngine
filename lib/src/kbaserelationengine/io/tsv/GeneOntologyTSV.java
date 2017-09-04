package kbaserelationengine.io.tsv;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class GeneOntologyTSV extends TSVFile{
	enum H{kbgoid, space, name, synonyms}
	 
	static final String ID_PREFIX = "kb_go";
	static final String[] ONTOLOGY_SPACES = new String[]{"biological_process", "cellular_componenet", "molecular_function"};
	static final int DATA_SIZE = 49269;
		
	public GeneOntologyTSV(String fileName) {
		super(fileName, ID_PREFIX, DATA_SIZE, H.class);	
	}

	@Override
	public void processRecord(CSVRecord record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildFakeRecord(int index, List<String> record) {
//		kbgoid
		record.add(ID_PREFIX + index);
//		space
		record.add(ONTOLOGY_SPACES[ (int)(Math.random()*3) ]);
//		name
		record.add("name" + index);
//		synonyms		
		record.add("synonyms" + index);
	}
}
