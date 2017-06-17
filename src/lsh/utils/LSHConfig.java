package lsh.utils;

import java.util.Map;

public class LSHConfig {
	private Map<String,String> config_params;
	
	public LSHConfig(Map<String,String>config_params) {
		this.config_params = config_params;
	}
	public String getValue(ConfigParams param) {
		return config_params.get(param.getValue());
	}
}
