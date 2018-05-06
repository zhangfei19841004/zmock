package com.zf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataSettingService {

    public static String mockDataDir;

    public static int mockDataCount = 10;

    public static int mockDataRole = 0;

    @Autowired
    private DataProviderService dataProviderService;

    @Value("${data.dir}")
    public void setMockDataDir(String mockDataDir) {
        DataSettingService.mockDataDir = mockDataDir;
        dataProviderService.loadMockData();
    }
}
