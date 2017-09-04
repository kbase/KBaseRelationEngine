package kbaserelationengine.io.tsv;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class CompendiaTSV extends TSVFile{

	/**
	 * header:
	 * 
	 * kbcmpid= KBaseCmpID - a compendium id
	 * type= gene expression or fitness
	 * source= colombos or FEB
	 * row_ids= ordered list of KBaseGenIDs
	 * col_ids= ordered list of KBaseConIDs
	 * row_order= row ordering under standard 2D HCL biclustering
	 * col_order= column ordering under standard 2D HCL biclustering
	 * 
	 * @author psnovichkov
	 *
	 */
	enum H{kbcmpid, type, source, row_ids, col_ids, row_order, col_order, compendium_data_file}
	static final String ID_PREFIX = "kb_cmp"; 
	static final int DATA_SIZE = 5000;	
	static final int MIN_ROW_SIZE = 400;
	static final int MAX_ROW_SIZE = 500;
	static final int MIN_COL_SIZE = 10;
	static final int MAX_COL_SIZE = 100;
	
	
	public CompendiaTSV(String fileName) {
		super(fileName, ID_PREFIX, DATA_SIZE, H.class);
	}

	@Override
	public void processRecord(CSVRecord record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildFakeRecord(int index, List<String> record) {
//		kbcmpid
		record.add(ID_PREFIX + index);
//		type
		record.add("type" + index);
//		source
		record.add("source" + index);
//		row_ids
		record.add(fakeGeneIds(index));
//		col_ids
		record.add(fakeConditionIds(index));
//		row_order
		record.add("row_order" + index);
//		col_order
		record.add("col_order" + index);
//		compendium_data_file		
		record.add("compendium_data_file" + index);
	}



	public String fakeGeneIds(int index){
		int taxonId = (int) (Math.random()*TaxonomyTSV.GENOME_NUMBER);
		int geneStartIndex = taxonId * GeneTSV.GENES_PER_TAXON;
		int geneCount = (int) (Math.random()*(MAX_ROW_SIZE - MIN_ROW_SIZE) + MIN_ROW_SIZE);
		geneStartIndex += (int) (Math.random()*(GeneTSV.GENES_PER_TAXON - geneCount));
		StringBuffer sb = new StringBuffer();
		for(int i = geneStartIndex, n = 0; n <  geneCount; i++,n++){
			if(n > 0){
				sb.append(",");
			}
			sb.append(GeneTSV.ID_PREFIX + i);
		}
		return sb.toString();
	}
	
	private String fakeConditionIds(int index) {
		int cndCount = (int) (Math.random()*(MAX_COL_SIZE - MIN_COL_SIZE) + MIN_COL_SIZE);
		int cndStartIndex = (int) (Math.random()*(ConditionsTSV.DATA_SIZE - cndCount));
		StringBuffer sb = new StringBuffer();
		for(int i = cndStartIndex, n = 0; n <  cndCount; i++,n++){
			if(n > 0){
				sb.append(",");
			}
			sb.append(ConditionsTSV.ID_PREFIX + i);
		}
		return sb.toString();

	}	
}
