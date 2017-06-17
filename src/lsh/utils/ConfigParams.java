package lsh.utils;

public enum ConfigParams {
	SHINGLE_SIZE("tam_shingle"),THRESHOLD("threshold"),NUM_BANDS("b"),NUM_ROWS("r");
	
	private String key;
	private ConfigParams(String strKey) {
		this.key = strKey;
	}
	public String getValue() {
		return key;
	}
}
