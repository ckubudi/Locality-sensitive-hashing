package lsh.run;

import java.io.File;
import java.util.List;

import lsh.methods.SignatureMaker;
import lsh.utils.LSHConfig;
import lsh.utils.Utils;

public class MakeDocSignatures {
	public static void main(String[] args) throws Exception{
		String config_path = "rsc/config.txt";
		String data_path = "rsc/file_path";
		
		LSHConfig config = Utils.readConfigFile(config_path);
		
		SignatureMaker sm = new SignatureMaker(config);
		File rootFile = Utils.readPathFile(data_path);
		
		System.out.println("Computing document signatures from " + rootFile.getAbsolutePath());
		
		long start = System.currentTimeMillis();
		sm.process(rootFile);
		long end = System.currentTimeMillis();
		
		System.out.println("Took " + (end-start)/1000.0 + " seconds...");
		
		int[][] signatures = sm.getSignatures();
		List<String> filenames = sm.getFilenames();
		
		System.out.println("Saving dataset information...");
		
		Utils.writeObject(signatures, "rsc/signatures_list.obj");
		Utils.writeObject(filenames, "rsc/filenames_list.obj");
		Utils.writeObject(sm.getDocLengths(), "rsc/filesizes_list.obj");
		
		System.out.println("Signatures saved.");
		
		
	}
}
