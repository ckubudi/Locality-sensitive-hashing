package lsh.run;

import java.util.Set;

import lsh.methods.LSH;
import lsh.utils.LSHConfig;
import lsh.utils.Utils;

public class FindLSHPairs {
	public static void main(String[] args) throws Exception{
		String config_path = "rsc/config.txt";
		LSHConfig lconf = Utils.readConfigFile(config_path);
		
		System.out.println("Loading signatures...");
		int[][] signatures = (int[][])Utils.readObject(
								"rsc/signatures_list.obj");
		
		LSH lsh = new LSH(lconf);
		double threshold = lsh.getSimilarityThreshold();
		
		long start, end = 0;
		
		System.out.println("Computing candidate pairs...");
		start = System.currentTimeMillis();
		Set<int[]> pairSet = lsh.getPairsCandidates(signatures);
		end = System.currentTimeMillis();
		System.out.printf("Time to compute candidates: %.2f s\n", (end-start)/1000.0 );
		System.out.println("Found " + pairSet.size() + " candidate pairs\n");
		
		System.out.printf("Filter by threshold: %.2f ...\n",threshold);
		start = System.currentTimeMillis(); 
		lsh.filterPairsBySimilarity(
				pairSet, signatures);
		end = System.currentTimeMillis();
		System.out.printf("Time to filter by similarity %.2f s\n", (end-start)/1000.0);
		System.out.println(pairSet.size() + " pairs above " + threshold + " similarity");
	}
}
