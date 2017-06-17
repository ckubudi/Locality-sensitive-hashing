package lsh.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Utils {
	
	public static Object readObject(String path) throws Exception {
		Object obj;
		FileInputStream fis = new FileInputStream(new File(path));
		ObjectInputStream ois = new ObjectInputStream(fis);
		obj = ois.readObject();
		ois.close();
		return obj;
	}
	public static void writeObject(Object obj, String path) throws Exception {
		FileOutputStream fos = new FileOutputStream(new File(path));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.close();
	}
	public static String readFile(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
		StringBuffer sb = new StringBuffer();
		String nl = System.lineSeparator();
		while(reader.ready()) {
			sb.append(reader.readLine() + nl);
		}
		reader.close();
		return sb.toString();
	}
	public static LSHConfig readConfigFile(String path) throws IOException{
		String content = readFile(path);
		Map<String, String> config_params = new HashMap<String,String>();
		
		String[] param_lines = content.split("\n");
		for(String line : param_lines) {
			String cleanLine = line.trim();
			if(!cleanLine.startsWith("#")) {
				String[] param_value = cleanLine.split("=");
				String param = param_value[0].trim();
				String value = param_value[1].trim();
				config_params.put(param, value);
			}
		}
		return new LSHConfig(config_params);
		
	}
}
