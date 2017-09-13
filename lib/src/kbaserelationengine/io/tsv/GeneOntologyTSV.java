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

public class GeneOntologyTSV extends TSVFile{
	enum H{guid, space, goid, parents, name}
	 
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
////		kbgoid
//		record.add(ID_PREFIX + index);
////		space
//		record.add(ONTOLOGY_SPACES[ (int)(Math.random()*3) ]);
////		parents
//		record.add("name" + index);
////		name
//		record.add("name" + index);
////		synonyms		
//		record.add("synonyms" + index);
	}
	
	public void convertOBO2TSV(String oboFileName, String tsvFileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(oboFileName));
		CSVFormat tsvFileFormat = CSVFormat.TDF;
		BufferedWriter bw = new BufferedWriter(new FileWriter(tsvFileName));
		CSVPrinter printer = new CSVPrinter(bw, tsvFileFormat);
		
		printer.printRecord(H.values());
		List<String> record = new ArrayList<String>();
				
		
		String _id = "";
		String _name = "";
		String _namespace = "";
//		String _def;
//		String _synonym;
		StringBuffer _is_a = new StringBuffer();
		
		final int NONE = 0;
		final int TERM_FOUND = 1;
		
		int status = NONE;
		for(String line = br.readLine(); line != null; line = br.readLine()){
			line = line.trim();
			switch(status){
			case NONE:{
				if(line.startsWith("[Term]")){
					status = TERM_FOUND;
					_id = "";
					_name = "";
					_namespace = "";
					_is_a.setLength(0);					
				}
				break;
			}
			case TERM_FOUND:{
				if(line.length() == 0){
					//guid, space, goid, parents, name
					record.clear();					
					record.add(_id);
					record.add(_namespace);
					record.add(_id);
					record.add(_is_a.toString());
					record.add(_name);
					printer.printRecord(record);
					status = NONE;
				} else if(line.startsWith("id:")){
					_id = line.substring("id:".length()).trim();
				} else if(line.startsWith("name:")){
					_name = line.substring("name:".length()).trim();
				} else if(line.startsWith("namespace:")){
					_namespace = line.substring("namespace:".length()).trim();
				} else if(line.startsWith("is_a:")){
					String parent = line.split(" ")[1];
					if(_is_a.length() > 0){
						_is_a.append(";");
					}
					_is_a.append(parent);
				}
			}
			}
		}		
		bw.flush();
		bw.close();
		printer.close();				
	}	
	
	public static void main(String[] args) throws IOException {
		GeneOntologyTSV go = new GeneOntologyTSV("");
		go.convertOBO2TSV("/Volumes/PavelsBackup/tsv_from_Adam/go-basic.obo", "/Volumes/PavelsBackup/tsv_from_Adam/processed/go.tsv");
		
	}
}
