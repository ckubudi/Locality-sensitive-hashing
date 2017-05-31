package lsh.test.methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LSH {
	private int n_buckets, bucket_size;
	
	public LSH(int n_buckets, int bucket_size) {
		this.n_buckets = n_buckets;
		this.bucket_size = bucket_size;
	}
	public List<int[]> getPairsCandidates(List<int[]> signatureDocs) {
		List<int[]> candidates = new ArrayList<int[]>();
		
		int r = signatureDocs.get(0).length/n_buckets;
		for(int b = 0; b < n_buckets; b++) {
			Map<Integer, Set<Integer>> bucket = new HashMap<Integer, Set<Integer>>();
			for(int j = 0; j < signatureDocs.size(); j++) {
				int[] signatureBand = Arrays.copyOfRange(signatureDocs.get(j),b*r,(b+1)*r);
				
				int signatureCode = Arrays.hashCode(signatureBand);
				if(!bucket.containsKey(signatureCode)) {
					bucket.put(signatureCode, new HashSet<Integer>());
				}
				bucket.get(signatureCode).add(j);	
			}
		}
		return candidates;
	}
}
