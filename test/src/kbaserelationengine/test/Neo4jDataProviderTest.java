package kbaserelationengine.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import kbaserelationengine.Bicluster;
import kbaserelationengine.FeatureSequence;
import kbaserelationengine.GetFeatureSequencesParams;
import kbaserelationengine.KEAppDescriptor;
import kbaserelationengine.Neo4jDataProvider;
import kbaserelationengine.StoreBiclustersParams;
import kbaserelationengine.StoreKEAppDescriptorParams;

public class Neo4jDataProviderTest {

	@Test
	public void testLoadReferenceData() throws IOException {
		new Neo4jDataProvider(null).loadReferenceData();		
	}
	@Test
	public void testCreateApp() {
		new Neo4jDataProvider(null).storeKEAppDescriptor(new StoreKEAppDescriptorParams()
		.withKeapp(new KEAppDescriptor()
				.withGuid("KEApp1")
				.withLastRunEpoch(System.currentTimeMillis())
				.withName("Expression Biclusters")
				.withNodesCreated(12342134L)
				.withPropertiesSet(242342L)
				.withRelationsCreated(145234L)
				.withVersion("1.0")
		));		
	}
	
	@Test
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
	@Test
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


}
