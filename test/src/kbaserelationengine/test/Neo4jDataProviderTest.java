package kbaserelationengine.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import kbaserelationengine.Bicluster;
import kbaserelationengine.CompendiumDescriptor;
import kbaserelationengine.FeatureSequence;
import kbaserelationengine.FeatureTerms;
import kbaserelationengine.GetBiclustersParams;
import kbaserelationengine.GetCompendiumDescriptorsParams;
import kbaserelationengine.GetFeatureSequencesParams;
import kbaserelationengine.GetFeatureTermsParams;
import kbaserelationengine.KEAppDescriptor;
import kbaserelationengine.Neo4jDataProvider;
import kbaserelationengine.StoreBiclustersParams;
import kbaserelationengine.StoreKEAppDescriptorParams;

public class Neo4jDataProviderTest {

	@Test
	public void test1(){
		Neo4jDataProvider dp = new Neo4jDataProvider(null);
	    List<CompendiumDescriptor> compendia = dp.getCompendiumDescriptors(
	    		new GetCompendiumDescriptorsParams()
	    			.withDataType("gene expression"));
	        
	    //Process biclusters for each compendium
	    for(CompendiumDescriptor cmp: compendia){
	        System.out.println("Compendium: " + cmp);
	    	List<FeatureTerms> featureTerms = dp.getFeatureTerms(new GetFeatureTermsParams()
	        			.withTaxonGuid(cmp.getTaxonomyGuid())
	        			.withTermSpace("biological_process"));        	
			Map<String, List<String>> entityTermSet = new Hashtable<String,List<String>>();
			for(FeatureTerms ft: featureTerms){
				if(ft.getTermGuids().size() > 0){
					entityTermSet.put(ft.getFeatureGuid(), ft.getTermGuids());
					for(String tGuid: ft.getTermGuids()){
						System.out.println("\t" + tGuid);
						break;
					}
					
				}
			}
				
			System.out.println("Total set size:" + entityTermSet.size());
			
			
			
	        List<Bicluster> biclusters = dp.getBiclusters(new GetBiclustersParams()
	        		.withCompendiumGuid(cmp.getGuid()));
	        	
	        for(Bicluster b: biclusters){	        		
	        	List<String> sampleSet = b.getFeatureGuids();
	        	if(sampleSet.size() == 0) continue;
	        		        	    
	        	System.out.println("Sample set size:" + sampleSet.size());
	        	break;
	        }
	        break;
	    }		
	}
	
	
	//@Test
	public void testLoadReferenceData() throws IOException {
		new Neo4jDataProvider(null).loadReferenceData();		
	}
	
	//@Test
	public void testCreateApp() {
		new Neo4jDataProvider(null).storeKEAppDescriptor(new StoreKEAppDescriptorParams()
		.withApp(new KEAppDescriptor()
				.withGuid("KEApp1")
				.withLastRunEpoch(System.currentTimeMillis())
				.withName("Expression Biclusters")
				.withNodesCreated(12342134L)
				.withPropertiesSet(242342L)
				.withRelationsCreated(145234L)
				.withVersion("1.0")
		));		
	}
	
	//@Test
	public void testCreateBiclusters() {
		String[][] _bis = new String[][]{
			{"BIC:1234","KEApp1", "KBaseGen123;KBaseGen120;KBaseGen121"},
			{"BIC:3422","KEApp1", "KBaseGen101;KBaseGen100;KBaseGen104;KBaseGen111"},
			{"BIC:3451","KEApp1", "KBaseGen101;KBaseGen101;KBaseGen104;KBaseGen121"}
		};
		List<Bicluster> biclusters = new ArrayList<Bicluster>();
		for(String[] vals: _bis){
			biclusters.add(new Bicluster()
					.withGuid(vals[0])
					.withKeappGuid(vals[1])
					.withFeatureGuids(Arrays.asList(vals[2].split(";"))));			
		}
		new Neo4jDataProvider(null).storeBiclusters(new StoreBiclustersParams().withBiclusters(biclusters) );		
	}
	
	//@Test
	public void testGetFeatureSequences() {
		List<FeatureSequence> fss = new Neo4jDataProvider(null).getFeatureSequences(
		new GetFeatureSequencesParams()
		.withTaxonomyGuid("KBaseTax9222")
//		.withOrthologGuid("KBaseHgp455296")
//		.withGotermGuid("GO:0000002")				
		
		);
		for(FeatureSequence fs: fss){
			System.out.println(fs);
		}	
	}

	//@Test
	public void testGetCompendiumDescriptors() {
//		List<CompendiumDescriptor> items = new Neo4jDataProvider(null).getCompendiumDescriptors(new GetCompendiumDescriptorsParams().withDataType("gene expression"));
		List<CompendiumDescriptor> items = new Neo4jDataProvider(null).getCompendiumDescriptors(new GetCompendiumDescriptorsParams().withTaxonomyGuid("KBaseTax3163218"));
		for(CompendiumDescriptor item: items){
			System.out.println(item);
		}
	}	
	
	

}
