package com.zf.decryptandverify;

import com.zf.dao.domain.MockInfoDao;

/**
 * Created by zhangfei on 2017/12/8/008.
 */
public interface IEncrypt {

    String encrypt(String resValue, MockInfoDao mockInfo);

}
