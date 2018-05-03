package com.zf.service;

import com.zf.dao.domain.MockInfoDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MockInfoService {

    public MockInfoDao getMockInfoDao(String url, String requestMethod) {
        for (String key : DataProviderService.MOCK_DATAS.keySet()) {
            MockInfoDao mockInfoDao = DataProviderService.MOCK_DATAS.get(key);
            if (StringUtils.isNotBlank(mockInfoDao.getRequestMethod()) && StringUtils.isNotBlank(mockInfoDao.getRequestUrl())) {
                List<String> methods = new ArrayList<String>(Arrays.asList(mockInfoDao.getRequestMethod().split("\\,")));
                if (url.equals(mockInfoDao.getRequestUrl()) && methods.contains(requestMethod)) {
                    return mockInfoDao;
                }
            }
        }
        return null;
    }

}
