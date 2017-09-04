package kbaserelationengine.io.tsv;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class OrthologTSV extends TSVFile{

	/**
	 * header:
	 * 
	 * kbiortd= KBaseOrtID - a orthology relationship id
	 * kbgenid= a KBase gene ID
	 * source= source of the orthology e.g. OMA or FEBA
	 * type= 1:1 pr 1:many, 
	 * ogrouo= orthology groupname
	 * 
	 * @author psnovichkov
	 *
	 */
	enum H{kbortid, kbgenid, source, type, ogroup}
	 
	static final String ID_PREFIX = "kb_orel"; 
	static final int DATA_SIZE = GeneTSV.DATA_SIZE;
	static final String OG_ID_PREDIX = "kb_og";
	static final int OG_SIZE = 500000;
	
		
	public OrthologTSV(String fileName) {
		super(fileName, ID_PREFIX, DATA_SIZE, H.class);	
	}
	
	
	@Override
	public void processRecord(CSVRecord record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildFakeRecord(int index, List<String> record) {
		//kbortid 		
		record.add(ID_PREFIX + index);
		//kbgenid
		record.add(GeneTSV.ID_PREFIX + index);
		//source
		record.add("source" + index);
		//type
		record.add("type" + index);
		//ogroup
		record.add(rndID(OG_ID_PREDIX, OG_SIZE) );
	}; 
}
