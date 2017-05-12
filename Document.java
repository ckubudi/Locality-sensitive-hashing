import java.util.HashSet;


public class Document {
	private HashSet<String> shingles;
	private String name;
	private String artist;
	
	public Document(String name, String artist){
		this.name=name;
		this.artist=artist;
	}
	
	public void addShingle(String shingle){
		shingles.add(shingle);
	}

	public HashSet<String> getShingles() {
		return shingles;
	}

	public String getName() {
		return name;
	}

	public String getArtist() {
		return artist;
	}
}
