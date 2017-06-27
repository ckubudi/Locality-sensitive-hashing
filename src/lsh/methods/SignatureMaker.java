package lsh.methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lsh.utils.ConfigParams;
import lsh.utils.LSHConfig;

/**Cria um vetor de inteiros que serve de assinatura para documentos
 * dentro do dataset, especificados de acordo com os parâmetros
 * do objeto LSHConfig.*/
public class SignatureMaker {
	
	private LSHConfig lconf;
	private Map<String, Integer> docCount;
	private List<String> musicNames = new ArrayList<String>();
	private List<File> musicFiles = new ArrayList<File>();
	private List<Integer> musicSizes = new ArrayList<Integer>();
	private int[][] minHashShingles;
	
	public SignatureMaker(LSHConfig lconf) {
		this.docCount = new HashMap<String, Integer>();
		this.lconf = lconf;
	}
	/**Retorna o número de caracteres em cada documento*/
	public List<Integer> getDocLengths() {
		return musicSizes;
	}
	
	/**Retorna o número de arquivos com o nome <i>autor_musica</i> especificado*/
	public Map<String,Integer> getDocCounts() {
		return docCount;
	}
	/**Retorna uma matriz, onde a linha é o id da música e as colunas formam
	 * as assinaturas*/
	public int[][] getSignatures(){
		return minHashShingles;
	}
	/**Retorna o nome <i>autor_musica</i> dado o id*/
	public List<String> getFilenames() {
		return musicNames;
	}
	
	/**Cria os ids inteiros das músicas, computa duplicatas por nome,
	 * conta o número de arquivos no dataset, etc...*/
	private void preProcess(File root) throws IOException {
		for(File fSite : root.listFiles()) {
			for(File fAuthor : fSite.listFiles()) {
				for(File fMusic : fAuthor.listFiles()) {
					String key = fAuthor.getName() + '_' + fMusic.getName();
					if(!docCount.containsKey(key)) {
						docCount.put(key, 0);
					}
					docCount.put(key, docCount.get(key)+1);
					musicNames.add(fAuthor.getName() + "_" + fMusic.getName());
					musicFiles.add(fMusic);
				}
			}
		}
		System.out.println("There are " + musicFiles.size() + " files");
	}
	
	/**Cria as assinaturas dos documentos via shingles, seguido de minhashing.*/
	public void process(File root) throws IOException{
		long start, end = 0;
		start = System.currentTimeMillis();
		preProcess(root);
		end = System.currentTimeMillis();
		System.out.printf("Pre-process time: %.2f\n",(end-start)/1000.0);
		start = System.currentTimeMillis();
		int shingleSize = Integer.parseInt(lconf.getValue(ConfigParams.SHINGLE_SIZE));
		int nBands = Integer.parseInt(lconf.getValue(ConfigParams.NUM_BANDS));
		int nRows = Integer.parseInt(lconf.getValue(ConfigParams.NUM_ROWS));
		int nHash = nBands*nRows;
		int nMusics = musicNames.size();
		
		
		minHashShingles = new int[nMusics][nHash];
		Minhashing sm = new Minhashing(nHash);
		
		int check = nMusics/100;
		
		for(int music_id = 0; music_id < nMusics; music_id ++) {
			File fMusic = musicFiles.get(music_id);
			
			if(music_id % check==0) {
				end = System.currentTimeMillis();
				double elapsed = (end-start)/1000.0;
				double p = music_id*100/(double)nMusics;
				System.out.printf("Process at: %3.2f %% - elapsed:%.2f s\n",p,elapsed);
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(fMusic));
			
			Set<Integer> shingleBag = new HashSet<Integer>();
			Deque<Character> dc = new ArrayDeque<Character>();
			
			while(reader.ready()) {
				char c = (char)reader.read();
				dc.push(c);
				if(dc.size() == shingleSize) {
					int shingleCode = Arrays.hashCode(dc.toArray());
					shingleBag.add(shingleCode);
					dc.pollLast();
				}
			}
			reader.close();
			
			//There are some documents that are empty
			//or have less than "shingle size" characters.
			//They'll all be equal between themselves
			
			int[] signature = sm.make(shingleBag);
			minHashShingles[music_id] = signature;
		}
	}
}
