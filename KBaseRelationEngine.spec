/*
A KBase module: KBaseRelationEngine
*/

module KBaseRelationEngine {

	typedef structure{
		int nodes_created;
		int relationships_created;
		int properties_set;		
	} GraphUpdateStat;

    typedef structure{
    	string taxonomy_guid;
    	string feature_guid;
    	string proteinSequence;
    	string nucleotideSequence;
    } FeatureSequence;

	/**
		One of guids should provided.
	*/
    typedef structure{
    	string taxonomy_guid;
    	string ortholog_guid;
    	string goterm_guid;
	}GetFeatureSequencesParams;

	funcdef getFeatureSequences(GetFeatureSequencesParams params) returns(list<FeatureSequence>) authentication required;
	
	typedef structure{
		string guid;
		string name;
		string data_type;
		string taxonomy_guid;
		string ws_ndarray_id;
	} CompendiumDescriptor;


	/**
		data_type - one of ["expression","fitness"]
	*/
    typedef structure{
    	string taxonomy_guid;
    	string data_type;
	}GetCompendiumDescriptorsParams;

	funcdef getCompendiumDescriptors(GetCompendiumDescriptorsParams params) returns(list<CompendiumDescriptor>) authentication required;
	
	typedef structure{
		string guid;
		string name;
		string version;
		int last_run_epoch;
		int nodes_created;
		int relations_created;
		int properties_set;
		
	} KEAppDescriptor;
	
	typedef structure{
		KEAppDescriptor keapp;
	}StoreKEAppDescriptorParams; 
	funcdef storeKEAppDescriptor(StoreKEAppDescriptorParams params) returns (GraphUpdateStat) authentication required; 
	
	typedef structure{
		string app_guid;
	}CleanKEAppResultsParams;	
	funcdef cleanKEAppResults(CleanKEAppResultsParams params) returns () authentication required;
	
	typedef structure{
		string app_guid;
	}GetKEAppDescriptorParams;	
	funcdef getKEAppDescriptor(GetKEAppDescriptorParams params) returns (KEAppDescriptor) authentication required;
	
	typedef structure{
		string guid; 
		string keapp_guid;
		string compendium_guid;
		list<string> feature_guids;
		list<string> condition_guids;
	} Bicluster;		
		
	typedef structure{
		list<Bicluster> biclusters;
	} StoreBiclustersParams; 		
	funcdef storeBiclusters(StoreBiclustersParams params) returns(GraphUpdateStat) authentication required;
	
	typedef structure{
		string guid; 
		string keapp_guid;
		string compendium_guid;
	} BiclusterDescriptor;
			
	typedef structure{
		string taxonomy_guid;
		string keapp_guid;
		string compendium_guid;
	}GetBiclusterDescriptorsParams;	
	funcdef getBiclusterDescriptors(GetBiclusterDescriptorsParams params) returns (list<BiclusterDescriptor>) authentication required;
	
	typedef structure{
		list<string> bicluster_guids;
	} GetBiclustersParams;	
	funcdef getBiclusters(GetBiclustersParams params) returns (list<Bicluster>) authentication required;
};
