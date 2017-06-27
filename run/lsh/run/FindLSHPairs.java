package lsh.run;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lsh.methods.LSH;
import lsh.utils.LSHConfig;
import lsh.utils.UnionFind;
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
		
		System.out.printf("Computing candidate pairs w threshold %.2f...\n",threshold);
		start = System.currentTimeMillis();
		Set<int[]> pairSet = lsh.getCheckedPairsCandidates(signatures,threshold);
		end = System.currentTimeMillis();
		System.out.printf("Time to compute candidates: %.2f s\n", (end-start)/1000.0 );
		System.out.println("Found " + pairSet.size() + " candidate pairs\n");
		
		List<String> namesList = (List<String>)Utils.readObject("rsc/filenames_list.obj");
		
		//Criamos uma floresta disjunta para contar o número de grupos definidos pelos pares
		UnionFind uf = new UnionFind(namesList.size());
		
		int namePairs = 0;
		for(int[] pair : pairSet) {
			String name1 = namesList.get(pair[0]);
			String name2 = namesList.get(pair[1]);
			if(name1.equals(name2)) {
				namePairs ++;
			}
			uf.union(pair[0], pair[1]);
		}
		
		System.out.println("There are " + namePairs + " name pairs");
		
		//recuperamos os grupos
		int[] parents = uf.getParents();
		int[] count = new int[parents.length];
		int dupFiles = 0;
		for(int p : parents) {
			count[p]++;
			//se um grupo possui 2 ou mais elementos, representa 1 arquivo duplicado
			if(count[p] >= 2) {
				dupFiles++;
			}
		}
		System.out.println("There are " + dupFiles + " replicated files");
		
		
	}
}
