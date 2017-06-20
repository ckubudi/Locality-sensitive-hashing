package lsh.methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LSHTmp {
	private int n_buckets;
	
	public LSHTmp(int n_buckets) {
		this.n_buckets = n_buckets;
	}
	public HashSet<int[]> getPairsCandidates(int[][] signatureDocs) {
		int bucket_size = signatureDocs[0].length/n_buckets;
		
		HashSet<int[]> pairs = new HashSet<int[]>();
		
		int r = signatureDocs[0].length/n_buckets;
		
		for(int b = 0; b < n_buckets; b++) {
			Set<Integer> pairBuckets = new HashSet<Integer>();
			Map<Integer, List<Integer>> bucket = new HashMap<Integer, List<Integer>>();
			for(int j = 0; j < signatureDocs.length; j++) {
				//banda b da assinatura
				int[] signatureBand = Arrays.copyOfRange(signatureDocs[j],b*r,(b+1)*r);
				//computa bucket da banda
				int signatureCode = Arrays.hashCode(signatureBand);
				//se não há ninguém no bucket, cria bucket
				if(!bucket.containsKey(signatureCode)) {
					bucket.put(signatureCode, new ArrayList<Integer>());
				}else {
					pairBuckets.add(signatureCode);
				}
				bucket.get(signatureCode).add(j);	
			}
			for(Integer bucket_id : pairBuckets) {
				List<Integer> pair_candidates = bucket.get(bucket_id);
				for(int i = 0; i < pair_candidates.size(); i++) {
					for(int j = i+1; j < pair_candidates.size(); j++) {
						int ci = pair_candidates.get(i);
						int cj = pair_candidates.get(j);
						int[] pair = {ci,cj};
						pairs.add(pair);
					}
				}
			}
		}
		return pairs;
	}
}
