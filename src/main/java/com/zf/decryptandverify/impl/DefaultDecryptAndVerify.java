package com.zf.decryptandverify.impl;

import com.zf.dao.domain.MockInfoDao;
import com.zf.decryptandverify.IDecryptAndVerify;
import org.springframework.stereotype.Service;

/**
 * Created by zhangfei on 2017/12/11/011.
 */
@Service
public class DefaultDecryptAndVerify implements IDecryptAndVerify{
    @Override
    public <T> T decrypt(T requestInfo, MockInfoDao mockInfo) {
        return requestInfo;
    }

    @Override
    public <T> boolean verify(T requestInfo, MockInfoDao mockInfo) {
        return true;
    }
}
