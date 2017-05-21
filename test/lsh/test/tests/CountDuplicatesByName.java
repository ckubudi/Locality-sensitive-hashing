package lsh.test.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lsh.test.methods.DocCounter;

public class CountDuplicatesByName {
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
	public static void main(String[] args) throws IOException{
		long start, end;
		BufferedReader reader = new BufferedReader(new FileReader(new File("rsc/file_path")));
		String path = reader.readLine();
		reader.close();
		print(path);
		File rootDir = new File(path);
		print(rootDir.isDirectory());
		print(rootDir.exists());
		
		start = time();
		print(countChildFiles(rootDir));
		DocCounter fdup = new DocCounter();
		fdup.countDocs(rootDir);
		end = time();
		print(fdup.getNumberOfDuplicates());
		print(fdup.getNumberOfDuplicatedFiles());
		print("Time to count: " + (end-start)/1000.0 + " s");
		
		List<String> dupNames = fdup.getDuplicatedFiles();
		
		Map<String,Integer> dc = fdup.getDocCountMap();
		System.out.println(dupNames.get(0));
		System.out.println(dc.get(dupNames.get(0)));
		System.out.println(dupNames.get(1));
		System.out.println(dc.get(dupNames.get(1)));
		System.out.println(dupNames.get(2));
		System.out.println(dc.get(dupNames.get(2)));
		
		//MAX OF DUPLICATES, shoud be 2, which is the number of music sites
		List<Integer> dupValues = new ArrayList<Integer>(dc.values());
		print("Max number of duplicates is " + max(dupValues));
	}
}
