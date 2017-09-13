/*
A KBase module: KBaseRelationEngine
*/

module KBaseRelationEngine {

    funcdef initReferenceData() returns() authentication required;


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

	funcdef getFeatureSequences(GetFeatureSequencesParams params) returns(list<FeatureSequence>);
	
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

	funcdef getCompendiumDescriptors(GetCompendiumDescriptorsParams params) returns(list<CompendiumDescriptor>);
	
	typedef structure{
		string guid;
		string name;
		int last_run_epoch;
		int nodes_created;
		int relations_created;
		int properties_set;
		
	} KEAppDescriptor;

	typedef structure{
		string keapp_guid;
		string compendium_guid;
		list<string> feature_guids;
		list<string> condition_guids;
	} Bicluster;
	
	typedef structure{
		list<Bicluster> biclusters;
	} StoreBiclustersParams; 
	
	
	funcdef storeBiclusters(StoreBiclustersParams params) returns();
    
};
