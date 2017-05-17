import java.util.HashSet;


public class Document {
	private HashSet<Integer> shingles;
	private String name;
	private String artist;
	
	public Document(String name, String artist){
		this.name=name;
		this.artist=artist;
	}
	
	public void addShingle(String shingle){
	}

	public HashSet<Integer> getShingles() {
		return shingles;
	}

	public String getName() {
		return name;
	}

	public String getArtist() {
		return artist;
	}
}
