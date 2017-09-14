package kbaserelationengine.io.tsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class Trasform2TSV {
	
	String[] _wsId2name = new String[]{
	"25895/5/1 colombos_bcere_exprdata_20151029",
	"25895/6/1 colombos_ecoli_exprdata_20151029",
	"25895/7/1 colombos_bsubt_exprdata_20151029",
	"25895/8/1 colombos_bthet_exprdata_20151029",
	"25895/9/1 colombos_meta_sente_exprdata_20151030",
	"25895/10/1 colombos_mtube_exprdata_20151029",
	"25895/11/1 colombos_paeru_exprdata_20151029",
	"25895/12/1 colombos_scoel_exprdata_20151029",
	"25895/13/1 colombos_sente_lt2_exprdata_20151029",
	"25895/14/1 colombos_sente_14028s_exprdata_20151029"};
	
	
	public void transofrm(String dirName, String tsvFileName) throws IOException{
		Hashtable<String,String> name2wsId = new Hashtable<String,String>();
		for(String line: _wsId2name){
			String[] vals = line.split(" ");
			name2wsId.put(vals[1]+ "_generic.tsv", vals[0]);
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(tsvFileName));
		bw.write("guid\tsource\tws_id\tname\tdescription\ttype\tfeature_guids");
		
		for(File file: new File(dirName).listFiles()){
			doFile(file, bw, name2wsId);
		}		
		bw.close();
	}

	private void doFile(File file, BufferedWriter bw, Hashtable<String, String> name2wsId) throws IOException {
		String fileName = file.getName();
		String name = "";
		String wsId = "";
		String description = "";
		String type = "";		
		StringBuffer geneGuids = new StringBuffer(); 
		
		wsId = name2wsId.get(fileName);
		if(wsId == null) throw new IllegalStateException("wsId for " + fileName + " not found");
		
		
		System.out.println("Doing file: " + fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));		
		final int NONE = 0;
		final int FOUND = 1;
		final int DONE = 2;
		int status = NONE;
		
		for(String line = br.readLine(); line != null; line = br.readLine()){
			if(status == DONE){
				break;
			}
			
			switch(status){
			case NONE:{
				String[] vals = line.split(",");
				if(vals[0].equals("name")){
					name = vals[1].trim();
				} else if(vals[0].equals("description")){
					description = vals[1].trim();
				} else if(vals[0].equals("type")){
					type = vals[1].trim().split("<")[0].trim();
				} else if(vals[0].equals("dmeta")){
					status = FOUND;
				}
				break;
			}
			case FOUND:{
				if(line.startsWith("dmeta, 2")){
					String guid = "CMP:" + System.currentTimeMillis();
//					bw.write("guid\tsource\tws_id\tname\tdescription\ttype\tfeature_guids");
					bw.write("\n" 
							+ guid  
							+ "\t" + fileName
							+ "\t" + wsId
							+ "\t" + name
							+ "\t" + description
							+ "\t" + type
							+ "\t" + geneGuids.toString()
							);
					status = DONE;
				} else{
					String guid = line.split(" ")[1].trim();	
					if( geneGuids.length() > 0 ){
						geneGuids.append(";");
					}
					geneGuids.append(guid);
				}
				break;
			}
			}
		}
		br.close();
	}
	
	public static void main(String[] args) throws Exception {
		new Trasform2TSV().transofrm(
				"/Volumes/PavelsBackup/tsv_from_Adam/processed/colombos/drive-download-20170914T070114Z-001", 
				"/Volumes/PavelsBackup/tsv_from_Adam/processed/colombos/colombos.tsv");
	}
}
