package kbaserelationengine.io;

import java.io.IOException;

import kbaserelationengine.io.tsv.BiclustersTSV;
import kbaserelationengine.io.tsv.CompendiaTSV;
import kbaserelationengine.io.tsv.CompoundsTSV;
import kbaserelationengine.io.tsv.ConditionsTSV;
import kbaserelationengine.io.tsv.GeneOntologyTSV;
import kbaserelationengine.io.tsv.GeneTSV;
import kbaserelationengine.io.tsv.OrthologTSV;
import kbaserelationengine.io.tsv.TSVFile;
import kbaserelationengine.io.tsv.TaxonomyTSV;

public class TestDataUploader {
	static TSVFile[] FILES = new TSVFile[]{
		new BiclustersTSV("data/biclusters.tsv"),
		new CompendiaTSV("data/compendia.tsv"),
		new CompoundsTSV("data/compounds.tsv"),
		new ConditionsTSV("data/conditions.tsv"),
		new GeneOntologyTSV("data/geneontology.tsv"),
		new GeneTSV("data/gene.tsv"),
		new OrthologTSV("data/ortholog.tsv"),
		new TaxonomyTSV("data/taxonomy.tsv"),
	};
	
	public static void main(String[] args) throws IOException {
		for(TSVFile file: FILES){
			System.out.print("Building " + file.getClass().getName() + "...");
			file.buildFakeData();
			System.out.println("Done!");
		}
	}
	
}
