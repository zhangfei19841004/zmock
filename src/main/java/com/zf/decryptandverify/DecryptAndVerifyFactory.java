package com.zf.decryptandverify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;

@Service
public class DecryptAndVerifyFactory {

	@Autowired
	private ApplicationContext applicationContext;

	private static Map<String, IDecryptAndVerify> DECRYPT_AND_VERIFY;

	@PostConstruct
	public void init() {
		DECRYPT_AND_VERIFY = applicationContext.getBeansOfType(IDecryptAndVerify.class);
	}

	public static int getDecryptAndVerifySize(){
		if(DECRYPT_AND_VERIFY==null){
			return 0;
		}
		return DECRYPT_AND_VERIFY.size();
	}

	public static IDecryptAndVerify getDecryptAndVerify(String key){
		return DECRYPT_AND_VERIFY.get(key);
	}

	public static Set<String> getDecryptAndVerifyKeys(){
		return DECRYPT_AND_VERIFY.keySet();
	}

}
