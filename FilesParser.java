import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class FilesParser {
	private ArrayList<Document> documentsList;
	private int shingleSize;
	
	public FilesParser(int shingleSize){
		this.shingleSize=shingleSize;
		documentsList=new ArrayList<Document>();
	}
	
	public void parse (String dirPath){
		showFiles(new File(dirPath));
	}
	
	/**
	 * Maps the given shingle to his index and adds do the document
	 * 
	 * @param document
	 * @param shingle
	 */
	private void addShingle (Document document, LinkedList<Integer> shingle){
		
	}
	
	public void showFiles(File dir){
		   File[] directoryListing = dir.listFiles();
		   
		   for (File file : directoryListing) {
		       if (file.isDirectory()) {
		           showFiles(file); // Calls same method again.
		       } else {
		    	   System.out.println(file.getName());
		    	   Document document = new Document(file.getName(), dir.getName());
		    	   try 
		    	   {
		    		   LinkedList<Integer> shingle = new  LinkedList<Integer>();
		    		   FileReader fr = new FileReader(file);
		               BufferedReader reader = new BufferedReader(fr);
		               int r;
		               int i=0;
		               //fills first shingle 
		               while(i < shingleSize && (r = reader.read()) != -1){
		            	   shingle.add(r);
		            	   i++;
		               }
		               addShingle(document, shingle);
		               //files the rest of the shingle
		               while((r = reader.read()) != -1){
		            	   shingle.add(r);
		            	   shingle.poll();
		            	   addShingle(document, shingle);
		               }
		           } catch (FileNotFoundException e) {
		              e.printStackTrace();
		           } catch (IOException e) {
		              e.printStackTrace();
		           }
		    	   documentsList.add(document);
		       }
		   }
	}
	
}
