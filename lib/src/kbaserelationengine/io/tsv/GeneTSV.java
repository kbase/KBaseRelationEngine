package kbaserelationengine.io.tsv;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class GeneTSV extends TSVFile{


	/**
	 * header:
	 * 
	 * kbid= KBaseGenID - a gene id
	 * source= source of gene e.g. OMA 
	 * gi= gi number if obtained
	 * refseq= refseq number if obtained 
	 * uniprot= uniprot references if obtained
	 * ontologyids= list of GO ids for now.  
	 * ncbitaxon= NCBI taxonomy reference if obtained
	 * kbtaxon= mapping to KBTaxID
	 * replicon= which chromosome/plasmid is this on?
	 * location= A locator string on the genome
	 * annotation= annotation
	 * 
	 * @author psnovichkov
	 *
	 */
	enum H{kbid, seqsource, sourceid, gi, refseq, uniprot, ontologyids, 
		ncbitaxon, kbtaxon, replicon, location, annotation}

	
	static final String ID_PREFIX = "kb_gen"; 
//	static final int GENES_PER_TAXON = 5000;
	static final int GENES_PER_TAXON = 500;
	static final int MAX_ONTOLOGY_IDS_PER_GENE = 3;
	static final int DATA_SIZE = TaxonomyTSV.GENOME_NUMBER*GENES_PER_TAXON;	
	
	public GeneTSV(String fileName) {
		super(fileName, ID_PREFIX, DATA_SIZE, H.class);
	}	
	
	@Override
	public void processRecord(CSVRecord record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildFakeRecord(int index, List<String> record) {
//		kbid 
		record.add(ID_PREFIX + index);		
//		seqsource
		record.add("seqsource" + index);		
//		sourceid
		record.add("sourceid" + index);
//		gi
		record.add("gi" + index);
//		refseq
		record.add("refseq" + index);
//		uniprot
		record.add("uniprot" + index);
//		ontologyids 
		record.add(fakeOntologyIds(index));
//		ncbitaxon
		record.add("ncbitaxon" + index);
//		kbtaxon
		record.add(fakeTaxonId(index));
//		replicon
		record.add("replicon" + index);
//		location
		record.add("location" + index);
//		annotation			
		record.add("annotation" + index);
	};
	
	private String fakeOntologyIds(int index){
		int goCount = (int)(Math.random()*MAX_ONTOLOGY_IDS_PER_GENE);
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < goCount; i++){
			if(i > 0){
				sb.append(",");
			}
			sb.append(rndID(GeneOntologyTSV.ID_PREFIX, GeneOntologyTSV.DATA_SIZE));
		}
		return sb.toString();
	}
	
	private String fakeTaxonId(int index){
		int taxonId = index % GENES_PER_TAXON;
		return TaxonomyTSV.ID_PREFIX + taxonId;
	}
	
	
}
