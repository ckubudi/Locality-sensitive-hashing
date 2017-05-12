import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;


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
	
	public void showFiles(File dir){
		   File[] directoryListing = dir.listFiles();
		  
		   for (File file : directoryListing) {
		       if (file.isDirectory()) {
		           showFiles(file); // Calls same method again.
		       } else {
		    	   Document document = new Document(file.getName(), dir.getName());
		    	   try 
		    	   {
		    		   FileReader fr = new FileReader(file);
		               BufferedReader reader = new BufferedReader(fr);
		               CharBuffer charBuffer = CharBuffer.allocate(shingleSize);
		               int i;
		               String shingle = "";
		               while(reader.read(charBuffer) > 0){
		            	   
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
