package lsh.methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lsh.test.methods.SignatureMaker;

public class ShingleMaker {
	private Map<String, Integer> docCount;
	private List<String> shingleNames;
	private List<int[]> mh_shingle = new ArrayList<int[]>();
	
	public ShingleMaker() {
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
	
	public List<int[]> getComputedMinhashShingles(){
		return mh_shingle;
	}
	public List<String> getShingeFilenames() {
		return shingleNames;
	}
	
	public void computeMinhashedShingles(File root, int shingleSize, int n_hash) throws IOException{
		mh_shingle = new ArrayList<int[]>();
		shingleNames = new ArrayList<String>();
		SignatureMaker sm = new SignatureMaker(n_hash);
		
		for(File fSite : root.listFiles()) {
			for(File fAuthor : fSite.listFiles()) {
				for(File fMusic : fAuthor.listFiles()) {
					BufferedReader reader = new BufferedReader(new FileReader(fMusic));
					StringBuffer contentBuffer = new StringBuffer();
					while(reader.ready()) {
						contentBuffer.append(reader.readLine() + '\n');
					}
					reader.close();
					String content = contentBuffer.toString();
					
					Set<Integer> shingleBag = new HashSet<Integer>();
					
					for(int i = 0; i < content.length()-shingleSize; i++) {
						String shingle = content.substring(i, i+shingleSize);
						int shingleCode = shingle.hashCode();
						shingleBag.add(shingleCode);
					}
					//Beware of empty documents
					if(content.length() == 0){
						System.out.println(fSite.getName() + "_" + fAuthor.getName() + "_" + fMusic.getName());
					}
					
					int[] signature = sm.make(shingleBag);
					mh_shingle.add(signature);
					shingleNames.add(fAuthor.getName() + "_" + fMusic.getName());
				}
			}
		}
	}
	
	public Map<String, Integer> getDocCountMap() {
		return docCount;
	}
	public int getNumberOfDuplicatesWithRepetition() {
		int count = 0;
		for(String d : docCount.keySet()) {
			if(docCount.get(d) > 1) {
				count += docCount.get(d);
			}
		}
		return count;
	}
	public int getNumberOfDuplicatedWithoutRepetition() {
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
