package lsh.test.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import lsh.test.methods.LSH;

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
		String signaturePath = "rsc/signatures.obj";
		FileInputStream fis = new FileInputStream(new File(signaturePath));
		ObjectInputStream ois = new ObjectInputStream(fis);
		List<int[]> fileSignatures = (List<int[]>)ois.readObject();
		ois.close();
//		print(Arrays.toString(fileSignatures.get(0)));
//		print(Arrays.toString(fileSignatures.get(1)));
//		print(fileSignatures.get(0).length);
//		
		List<int[]> miniSignatures = fileSignatures.subList(0, 2350);
		
		LSH lsh = new LSH(1);
		Set<int[]> pairCandidates = lsh.getPairsCandidates(miniSignatures);
		List<int[]> pairList = new ArrayList<int[]>(pairCandidates);
//		print(pairCandidates.size());
//		print(fileSignatures.size());
//		int n = fileSignatures.size();
//		print(n*n/2);
//		print("End");
		print(pairCandidates.size());
		for(int i = 0; i < pairList.size() && i < 100; i++) {
			System.out.println(Arrays.toString(pairList.get(i)));
			int[] pair = pairList.get(i);
			int[] doc_i = miniSignatures.get(pair[0]);
			int[] doc_j = miniSignatures.get(pair[1]);
			boolean are_equal = true;
			for(int k = 0; k < doc_i.length; k++) {
				if(doc_i[k] != doc_j[k]) {
					are_equal = false;
				}
			}
			print(are_equal);
		}
//		print(listString(miniSignatures.get(874)));
//		print(listString(miniSignatures.get(889)));
	}
}
