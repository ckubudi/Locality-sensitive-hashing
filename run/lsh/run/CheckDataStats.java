package lsh.run;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lsh.utils.Utils;

public class CheckDataStats {
	public static void main(String[] args) throws Exception{
		
		int[][] signatures = (int[][])Utils.readObject(
								"rsc/signatures_list.obj");
		
		List<String> namesList = (List<String>)Utils.readObject("rsc/filenames_list.obj");
		
		HashMap<String,List<Integer>> nameIds = new HashMap<String,List<Integer>>();
		HashMap<String, Integer> nameCount = new HashMap<String,Integer>();
		for(int i = 0; i < namesList.size(); i++) {
			String name = namesList.get(i);
			if(!nameCount.containsKey(name)) {
				nameCount.put(name, 0);
				nameIds.put(name, new ArrayList<Integer>());
			}
			nameCount.put(name, nameCount.get(name)+1);
			nameIds.get(name).add(i);
		}
		
		int dupFiles = 0;
		for(String name : nameCount.keySet()) {
			if(nameCount.get(name) >= 2) {
				dupFiles++;
			}
		}
		System.out.println("There are " + dupFiles + " replicated files with same name on dataset");
		
		double sameNameSim = 0;
		int count = 0;
		for(String name : namesList) {
			List<Integer> docs = nameIds.get(name);
			for(int i = 0; i < docs.size(); i++) {
				for(int j = i+1; j < docs.size(); j++) {
					int doci = docs.get(i);
					int docj = docs.get(j);
					double sim = Utils.similarity(signatures[doci],signatures[docj]);
					sameNameSim += sim;
					count++;
				}
			}
		}
		sameNameSim /= (double)count;
		System.out.println("Average similarity between files with same name is " + sameNameSim);
	}
}
