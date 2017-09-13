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
//	static final int OG_SIZE = 500000;
	static final int OG_SIZE = 10000;
	
		
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
	
	enum _h{ kbgenid, _x, hom_type, kbhgpid };
	public void convertHomologs2OrtologsTSV(String homologFileName, String orthologFileName) throws IOException{
		CSVFormat h_format = CSVFormat.TDF.withHeader(_h.class);
		BufferedReader h_br = new BufferedReader(new FileReader(homologFileName));
		h_br.readLine();
		
		CSVFormat o_format = CSVFormat.TDF;
		BufferedWriter o_bw = new BufferedWriter(new FileWriter(orthologFileName));
		CSVPrinter o_printer = new CSVPrinter(o_bw, o_format);		
		o_printer.printRecord(new String[]{"geneguid","ogguid"});
		List<String> o_record = new ArrayList<String>();
		
		
		String prevGeneGuid = null;
		try{
			long count = 0;
			long geneCount = 0;
			for(CSVRecord h_record: h_format.parse(h_br)){
				count++;
				if(count % 10000000 ==0){
					System.out.println("Processed: " + count + "\tgeneCount: " + geneCount);
				}
				String geneGuid = h_record.get("kbgenid");				
				if(prevGeneGuid == null || !geneGuid.equals(prevGeneGuid)){
					geneCount++;
					prevGeneGuid = geneGuid;
					
					String ogGuid = h_record.get("kbhgpid");
					if(ogGuid.trim().length() == 0){
						continue;
					}
					o_record.clear();
					o_record.add(geneGuid);
					o_record.add(ogGuid);
					o_printer.printRecord(o_record);
				}
			}
		} finally{
			h_br.close();
		}		
		o_bw.close();
	}
	
	public static void main(String[] args) throws IOException {
		OrthologTSV og = new OrthologTSV("");
		og.convertHomologs2OrtologsTSV("/Volumes/PavelsBackup/tsv_from_Adam/kbasehomology_oma.tsv", "/Volumes/PavelsBackup/tsv_from_Adam/processed/kbaseorthologs_oma.tsv");
				
	}
}
