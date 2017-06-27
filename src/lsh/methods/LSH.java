package lsh.methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lsh.utils.ConfigParams;
import lsh.utils.LSHConfig;
import lsh.utils.Utils;

public class LSH {
	private int n_buckets;
	private int n_rows;
	private double threshold;
	
	public int getNumberOfBuckets() {
		return n_buckets;
	}
	public int getNumberOfRows() {
		return n_rows;
	}
	public double getSimilarityThreshold() {
		return threshold;
	}
	
	public LSH(LSHConfig lconf) {
		this.n_buckets = Integer.parseInt(
				lconf.getValue(ConfigParams.NUM_BANDS));
		this.n_rows = Integer.parseInt(
				lconf.getValue(ConfigParams.NUM_ROWS));
		threshold = Double.parseDouble(
				lconf.getValue(ConfigParams.THRESHOLD));
		
	}
	public void filterPairsBySimilarity(Set<int[]> candidatePairs, int[][] signatureDocs) {
		Set<int[]> removed = new HashSet<int[]>();
		for(int[] pair : candidatePairs) {
			int[] sig1 = signatureDocs[pair[0]];
			int[] sig2 = signatureDocs[pair[1]];
			if(Utils.similarity(sig1, sig2) < threshold) {
				removed.add(pair);
			}
		}
		candidatePairs.removeAll(removed);
	}
	public Set<int[]> getCheckedPairsCandidates(int[][] signatureDocs, double threshold) {
		HashSet<int[]> pairs = new HashSet<int[]>();
		
		for(int b = 0; b < n_buckets; b++) {
			Set<Integer> pairBuckets = new HashSet<Integer>();
			Map<Integer, List<Integer>> bucket = new HashMap<Integer, List<Integer>>();
			
			for(int j = 0; j < signatureDocs.length; j++) {
				//banda b da assinatura
				int[] signatureBand = Arrays.copyOfRange(
						signatureDocs[j],b*n_rows,(b+1)*n_rows);
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
						if(Utils.similarity(signatureDocs[ci], signatureDocs[cj]) > threshold) {
							int[] pair = {ci,cj};
							pairs.add(pair);
						}
					}
				}
			}
		}
		return pairs;
	}
	public Set<int[]> getPairsCandidates(int[][] signatureDocs) {
		HashSet<int[]> pairs = new HashSet<int[]>();
		
		for(int b = 0; b < n_buckets; b++) {
			Set<Integer> pairBuckets = new HashSet<Integer>();
			Map<Integer, List<Integer>> bucket = new HashMap<Integer, List<Integer>>();
			
			for(int j = 0; j < signatureDocs.length; j++) {
				//banda b da assinatura
				int[] signatureBand = Arrays.copyOfRange(
						signatureDocs[j],b*n_rows,(b+1)*n_rows);
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
