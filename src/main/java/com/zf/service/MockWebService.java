package com.zf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockWebService {

    @Autowired
    private FileHandler fileHandler;

    public void addDataFile(String collectionName, String mockName) {
        fileHandler.addDataFile(collectionName, mockName);
    }

    public void moveDataDirAndDelete(String collectionName, String oldCollectionName) {
        fileHandler.moveDataDirAndDelete(collectionName, oldCollectionName);
    }

    public void moveDataFile(String collectionName, String mockName, String oldCollectionName, String oldMockName) {
        fileHandler.moveDataFile(collectionName, mockName, oldCollectionName, oldMockName);
    }

    public void deleteDataDir(String collectionName) {
        fileHandler.deleteDataDir(collectionName);
    }

    public void deleteDataFile(String collectionName, String mockName) {
        fileHandler.deleteDataFile(collectionName, mockName);
    }

    public void saveDataFile(String collectionName, String mockName, String content) {
        fileHandler.saveDataFile(collectionName, mockName, content);
    }
}
