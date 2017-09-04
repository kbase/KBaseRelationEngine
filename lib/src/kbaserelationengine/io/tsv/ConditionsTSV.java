package kbaserelationengine.io.tsv;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class ConditionsTSV extends TSVFile{
	/**
	 * header:
	 * 
	 * kbcondid= KBaseConID - a condition id
	 * media= a medium name e.g. LB, M9, etc/ 
	 * pH= the pH if known
	 * Temperature= the Temperature if known
	 * Pressure= the pressure if known
	 * Time= the Time if known
	 * OD= the optical density of known
	 * Growth_Phase= e.g Exponential, Stationary, etc. 
	 * Growth_State= Planktonic or Swarmer or Sporulating or.... 
	 * Growth_Mode= Chemostat or Drip_FLOW_REACTOR etc. 
	 * kbchemid_list= a list of KBCheID which are chemical ids along with 
	 * concentration values if relevant
	 * strain_variant= if comparison among strains is given- name of comparison 
	 * strain (be nice to map to taxonomy but I haven't)
	 * plasmid= plasmid used in test strain 
	 * Mutant= mutant in test strain : gene name (not yet mapped to KBaseGenID) and type -- complement, overexpression, etc. 
	 * Other_Label= other information about the condition
	 * 
	 * @author psnovichkov
	 *
	 */
	enum H{kbcondid, media, pH, Temperature, Pressure, Time, OD, 
		Growth_Phase, Growth_State, Growth_Mode, kbchemid_list, Strain_variant, 
		Plasmid, Mutant, Other_Label}
	
	static final String ID_PREFIX = "kb_cnd"; 
	static final int DATA_SIZE = 500000;
		
	public ConditionsTSV(String fileName) {
		super(fileName, ID_PREFIX, DATA_SIZE, H.class);
	}

	@Override
	public void processRecord(CSVRecord record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildFakeRecord(int index, List<String> record) {
//		kbcondid
		record.add(ID_PREFIX + index);
//		media
		record.add("media" + index);
//		pH
		record.add("pH" + index);
//		Temperature
		record.add("Temperature" + index);
//		Pressure
		record.add("Pressure" + index);
//		Time
		record.add("Time" + index);
//		OD
		record.add("OD" + index);
//		Growth_Phase
		record.add("Growth_Phase" + index);
//		Growth_State
		record.add("Growth_State" + index);
//		Growth_Mode
		record.add("Growth_Mode" + index);
//		kbchemid_list
		record.add("kbchemid_list" + index);
//		Strain_variant, 
		record.add("Strain_variant" + index);
//		Plasmid
		record.add("Plasmid" + index);
//		Mutant
		record.add("Mutant" + index);
//		Other_Label
		record.add("Other_Label" + index);
	}

}
