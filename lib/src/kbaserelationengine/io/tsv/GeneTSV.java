package kbaserelationengine.io.tsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
		
	public void convertFasta2TSV(String fastFileName, String tsvFileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fastFileName));
		BufferedWriter bw = new BufferedWriter(new FileWriter(tsvFileName));
		
		bw.write("sourceid\tsequence\n");
		
		String geneId = null;
		StringBuffer seq = new StringBuffer();
		
		for(String line = br.readLine(); line != null; line = br.readLine()){
			line = line.trim();
			if(line.startsWith(">")){
				if(geneId != null){
					// process previous record
					bw.write(geneId + "\t" + seq.toString() + "\n");
					geneId = null;
					seq.setLength(0);
				}
				geneId = line.substring(1).trim();
			} else{
				seq.append(line);
			}
		}
		
		// check the last record
		if(geneId != null){
			bw.write(geneId + "\t" + seq.toString() + "\n");
		}
		br.close();
		bw.close();
	}

	public static void main(String[] args) throws IOException {
		new GeneTSV("").convertFasta2TSV(
				"/Volumes/PavelsBackup/tsv_from_Adam/oma-seqs.fa",
				"/Volumes/PavelsBackup/tsv_from_Adam/processed/oma-seqs.tsv");
	}
}
