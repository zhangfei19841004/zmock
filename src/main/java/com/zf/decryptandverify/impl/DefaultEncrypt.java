package com.zf.decryptandverify.impl;

import com.zf.dao.domain.MockInfoDao;
import com.zf.decryptandverify.IEncrypt;
import org.springframework.stereotype.Service;

/**
 * Created by zhangfei on 2017/12/11/011.
 */
@Service
public class DefaultEncrypt implements IEncrypt {
    @Override
    public String encrypt(String resValue, MockInfoDao mockInfo) {
        return resValue;
    }
}
