package kbaserelationengine.io.tsv;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class TaxonomyTSV extends TSVFile{

	/**
	 * Header
	 * 
	 * kbid= KBaseTaxID - a taxonomy id
	 * source= source of taxonomy e.g. OTOL (open tree of life)
	 * sourceid= id from source 
	 * parents= a semicolon separated list kbids given complete parentage of this taxa back to the root.  
	 * name= name of taxa
	 * rank= strain, species, genus, etc. 
	 * ncbi= the ncbi taxid if obtained
	 * gbif, irmng, worms, silve: cross-reference ids to this resource. 
	 * 
	 * @author psnovichkov
	 *
	 */
	enum H{kbid, source, ottid, parents, name, rank, ncbi, gbif, irmng, worms, silva}
	
	static final String ID_PREFIX = "kb_tax"; 
	static final int DATA_SIZE = 3594551;
	static final int GENOME_NUMBER = 2085;
	public TaxonomyTSV(String fileName) {
		super(fileName, ID_PREFIX, DATA_SIZE, H.class);
	}

	@Override
	public void processRecord(CSVRecord record) {
		// TODO Auto-generated method stub
		
		// Example
		record.get(H.kbid);
	}

	@Override
	public void buildFakeRecord(int index, List<String> record) {
		//kbid, source, ottid, parents, name, rank, ncbi, gbif, irmng, worms, silva
		record.add(ID_PREFIX + index);
		record.add("source" + index);
		record.add("ottid" + index);
		record.add( rndID(ID_PREFIX, index));
		record.add("name" + index);
		record.add("ncbi" + index);
		record.add("gbif" + index);
		record.add("irmng" + index);
		record.add("worms" + index);
		record.add("silva" + index);		
	}
}
