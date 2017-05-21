package lsh.test.methods;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocCounter {
	private Map<String, Integer> docCount;
	
	public DocCounter() {
		docCount = new HashMap<String, Integer>();
	}
	
	public void countDocs(File root) {
		for(File fSite : root.listFiles()) {
			for(File fAuthor : fSite.listFiles()) {
				for(File fMusic : fAuthor.listFiles()) {
					String key = fAuthor.getName() + '_' + fMusic.getName();
					if(!docCount.containsKey(key)) {
						docCount.put(key, 0);
					}
					docCount.put(key, docCount.get(key)+1);
				}
			}
		}
	}
	
	public Map<String, Integer> getDocCountMap() {
		return docCount;
	}
	public int getNumberOfDuplicates() {
		int count = 0;
		for(String d : docCount.keySet()) {
			if(docCount.get(d) > 1) {
				count += docCount.get(d)-1;
			}
		}
		return count;
	}
	public int getNumberOfDuplicatedFiles() {
		int count = 0;
		for(String d : docCount.keySet()) {
			if(docCount.get(d) > 1) {
				count += 1;
			}
		}
		return count;
	}
	public List<String> getDuplicatedFiles() {
		List<String> dupFiles = new ArrayList<String>();
		for(String d : docCount.keySet()) {
			if(docCount.get(d) > 1) {
				dupFiles.add(d);
			}
		}
		return dupFiles;
	}
}
