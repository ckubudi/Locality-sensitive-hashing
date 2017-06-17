package lsh.test.tests;

import lsh.utils.ConfigParams;
import lsh.utils.LSHConfig;
import lsh.utils.Utils;

public class MakeShingles {
	
	public static long time() {
		return System.currentTimeMillis();
	}
	
	public static void main(String[] args) throws Exception{
		String config_path = "rsc/config.txt";
		
		LSHConfig config = Utils.readConfigFile(config_path);
		System.out.println(config.getValue(ConfigParams.SHINGLE_SIZE));
		int shingleSize = Integer.parseInt(
				config.getValue(ConfigParams.SHINGLE_SIZE));
		int n_bands = Integer.parseInt(
				config.getValue(ConfigParams.NUM_BANDS));
		int n_rows = Integer.parseInt(
				config.getValue(ConfigParams.NUM_ROWS));
		int n = n_bands*n_rows;
		
//		File root = Utils.getRootFile("rsc/file_path");
//		ShingleMaker shingleMaker = new ShingleMaker();
//		shingleMaker.computeMinhashedShingles(root, shingleSize, n);
		
	}
}
