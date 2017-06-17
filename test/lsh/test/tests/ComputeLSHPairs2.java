package lsh.test.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lsh.test.methods.LSH;
import lsh.utils.Utils;

public class ComputeLSHPairs2 {
	public static void print(Object p) {
		System.out.println(p);
	}

	public static int countChildFiles(File f) {
		int count = 0;
		if (f.isDirectory()) {
			for (File fChild : f.listFiles()) {
				if (fChild.isDirectory()) {
					count += countChildFiles(fChild);
				} else {
					count += 1;
				}
			}
		} else {
			count += 1;
		}
		return count;
	}

	public static int max(List<Integer> l) {
		int m0 = l.get(0);
		for (int i = 1; i < l.size(); i++) {
			if (l.get(i) > m0) {
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
	
	public static double jaccard(int[] doc_i, int[] doc_j) {
		double sim = 0;
		for(int k = 0; k < doc_i.length; k++) {
			if(doc_i[k] == doc_j[k]) {
				sim += 1;
			}
		}
		sim /= doc_i.length;
		return sim;
	}

	public static void main(String[] args) throws Exception {
		String dupPath = "rsc/duplicates.obj";
		String signaturePath = "rsc/signatures.obj";
		String namesPath = "rsc/id_to_name.obj";
		Map<String, Integer> dupMap = (Map<String, Integer>) Utils.readObject(dupPath);
		List<int[]> fileSignatures = (List<int[]>) Utils.readObject(signaturePath);
		List<String> fileNames = (List<String>) Utils.readObject(namesPath);

		Map<String, List<Integer>> name_to_ids = new HashMap<String, List<Integer>>();
		for (int i = 0; i < fileNames.size(); i++) {
			String nome = fileNames.get(i);
			if (!name_to_ids.containsKey(nome)) {
				name_to_ids.put(nome, new ArrayList<Integer>());
			}
			name_to_ids.get(nome).add(i);
		}

		List<int[]> namePairs = new ArrayList<int[]>();
		for (String name : name_to_ids.keySet()) {
			List<Integer> repeated = name_to_ids.get(name);
			for (int i = 0; i < repeated.size(); i++) {
				for (int j = i + 1; j < repeated.size(); j++) {
					int id_i = repeated.get(i);
					int id_j = repeated.get(j);
					int[] pair = { id_i, id_j };
					namePairs.add(pair);
				}
			}
		}
		
		double sim_sum = 0;

		for(int[] pair : namePairs) {
			int[] sign_i = fileSignatures.get(pair[0]);
			int[] sign_j = fileSignatures.get(pair[1]);
			double sim_ij = jaccard(sign_i,sign_j);
			sim_sum += sim_ij;
		}
		sim_sum /= (float)namePairs.size();
		
		double sim_var= 0;
		for(int[] pair : namePairs) {
			int[] sign_i = fileSignatures.get(pair[0]);
			int[] sign_j = fileSignatures.get(pair[1]);
			double sim_ij = jaccard(sign_i,sign_j);
			sim_var += (sim_ij - sim_sum)*(sim_ij - sim_sum);
		}
		double sim_dp = Math.sqrt(sim_var)/(float)namePairs.size();
		
		System.out.println("Similaridade média entre documentos pares: " + sim_sum);
		System.out.println("DP documentos pares: " + sim_dp);
	}
}
