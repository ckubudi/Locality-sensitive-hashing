package lsh.test.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lsh.methods.LSHTmp;
import lsh.utils.Utils;

public class ComputeLSHPairs {
	public static void print(Object p) {
		System.out.println(p);
	}
	public static int countChildFiles(File f) {
		int count = 0;
		if (f.isDirectory()) {
			for(File fChild : f.listFiles()) {
				if(fChild.isDirectory()) {
					count += countChildFiles(fChild);
				}else {
					count += 1;
				}
			}
		}else {
			count += 1;
		}
		return count;
	}
	public static int max(List<Integer> l){
		int m0 = l.get(0);
		for(int i = 1; i < l.size(); i++) {
			if(l.get(i) > m0) {
				m0 = l.get(i);
			}
		}
		return m0;
	}
	public static long time() {
		return System.currentTimeMillis();
	}
	public static String listString(int[] a) {
		return Arrays.toString(a);
	}

	public static void main(String[] args) throws Exception{
//		String dupPath = "rsc/duplicates.obj";
		String signaturePath = "rsc/signatures_list.obj";
		String namesPath = "rsc/filenames_list.obj";
//		Map<String,Integer> dupMap = (Map<String,Integer>)Utils.readObject(dupPath);
		int[][] fileSignatures = (int[][])Utils.readObject(signaturePath);
		List<String> fileNames = (List<String>)Utils.readObject(namesPath);
		
		LSHTmp lsh = new LSHTmp(1);
		Set<int[]> pairCandidates = lsh.getPairsCandidates(fileSignatures);
		List<int[]> pairList = new ArrayList<int[]>(pairCandidates);
		
		Set<int[]> realPairs = new HashSet<int[]>();
		
		print(pairCandidates.size());
		int foundNameDups = 0;
		for(int i = 0; i < pairList.size(); i++) {
			print(Arrays.toString(pairList.get(i)));
			int[] pair = pairList.get(i);
			print(fileNames.get(pair[0]) + " vs " + fileNames.get(pair[1]));
			int[] doc_i = fileSignatures[pair[0]];
			int[] doc_j = fileSignatures[pair[1]];
			boolean are_equal = true;
			double sim = 0;
			for(int k = 0; k < doc_i.length; k++) {
				if(doc_i[k] != doc_j[k]) {
					are_equal = false;
				}else {
					sim += 1;
				}
			}
			sim /= doc_i.length;
			print(are_equal);
			print("signature similarity: " + sim);
			String name0 = fileNames.get(pair[0]);
			String name1 = fileNames.get(pair[1]);
			if(name0.equals(name1)) {
				foundNameDups += 1;
			}
		}
		
		System.out.println("Found " + foundNameDups + " name dups");
	}
}
