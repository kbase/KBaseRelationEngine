/*
A KBase module: KBaseRelationEngine
*/

module KBaseRelationEngine {

	typedef structure{
		int nodes_created;
		int nodes_deleted;		
		int relationships_created;
		int relationships_deleted;		
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
		KEAppDescriptor app;
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
		string taxonomy_guid;
		list<string> feature_guids;
		list<string> condition_guids;
	} Bicluster;		
		
	typedef structure{
		list<Bicluster> biclusters;
	} StoreBiclustersParams; 		
	funcdef storeBiclusters(StoreBiclustersParams params) returns(GraphUpdateStat) authentication required;
				
	typedef structure{
		string keapp_guid;
		string taxonomy_guid;
		string compendium_guid;
	}GetBiclustersParams;	
	funcdef getBiclusters(GetBiclustersParams params) returns (list<Bicluster>) authentication required;
		
	typedef string ws_genome_obj_ref;
	typedef string ws_feature_guid;
	typedef string ref_ontology_term_guid;


	typedef structure{
		ws_genome_obj_ref genome_ref;
		list<ws_feature_guid> feature_guids;				
	} StoreWSGenomeParams;	
	funcdef storeWSGenome(StoreWSGenomeParams params) returns (GraphUpdateStat) authentication required;

	typedef structure{
		mapping<string,string> ws2ref_feature_guids;		
	} ConnectWSFeatures2RefOrthologsParams;
	funcdef connectWSFeatures2RefOrthologs(ConnectWSFeatures2RefOrthologsParams params) returns (GraphUpdateStat) authentication required;

	typedef structure{
		mapping<ws_feature_guid,list<ref_ontology_term_guid>> feature2term_list;		
	} ConnectWSFeatures2RefOTermsParams;
	funcdef connectWSFeatures2RefOTerms(ConnectWSFeatures2RefOTermsParams params) returns (GraphUpdateStat) authentication required;

	
};
