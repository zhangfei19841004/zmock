package com.zf.decryptandverify;

import com.zf.dao.domain.MockInfoDao;

/**
 * Created by zhangfei on 2017/12/8/008.
 */
public interface IDecryptAndVerify {

    <T> T decrypt(T requestInfo, MockInfoDao mockInfo);

    <T> boolean verify(T requestInfo, MockInfoDao mockInfo);

}
