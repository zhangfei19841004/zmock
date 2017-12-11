package com.zf.decryptandverify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;

@Service
public class EncryptFactory {

	@Autowired
	private ApplicationContext applicationContext;

	private static Map<String, IEncrypt> ENCRYPT;

	@PostConstruct
	public void init() {
		ENCRYPT = applicationContext.getBeansOfType(IEncrypt.class);
	}

	public static int getEncryptSize(){
		if(ENCRYPT==null){
			return 0;
		}
		return ENCRYPT.size();
	}

	public static IEncrypt getEncrypt(String key){
		return ENCRYPT.get(key);
	}

	public static Set<String> getEncryptKeys(){
		return ENCRYPT.keySet();
	}

}
