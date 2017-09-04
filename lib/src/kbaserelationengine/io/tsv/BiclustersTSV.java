package kbaserelationengine.io.tsv;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class BiclustersTSV extends TSVFile{

	/**
	 * headers:
	 * 
	 * kbbicid= KBaseBicID- a bicluster id
	 * kbcmpid= KBaseCmpID - a compendium id
	 * method= how was this produced 2D HCL, MAK, cMonkey...
	 * row_order= row ordering 
	 * col_order= column ordering 
	 * @author psnovichkov
	 *
	 */
	enum H{kbbicid, kbcmpid, method, row_order, col_order}
	static final String ID_PREFIX = "kb_bcl"; 
	static final int DATA_SIZE = 500000;	
	static final int MIN_ROW_SIZE = 10;
	static final int MAX_ROW_SIZE = 100;
	static final int MIN_COL_SIZE = 10;
	static final int MAX_COL_SIZE = 100;	
	
	public BiclustersTSV(String fileName) {
		super(fileName, ID_PREFIX, DATA_SIZE, H.class);
	}

	@Override
	public void processRecord(CSVRecord record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildFakeRecord(int index, List<String> record) {
//		kbbicid 
		record.add(ID_PREFIX + index);
//		kbcmpid 
		record.add( rndID(CompendiaTSV.ID_PREFIX,CompendiaTSV.DATA_SIZE ));
//		method 
		record.add("method" + index);
//		row_order 
		record.add(fakeGeneIds(index));
//		col_order
		record.add(fakeConditionIds(index));		
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
