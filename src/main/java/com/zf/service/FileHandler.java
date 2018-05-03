package com.zf.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zhangfei on 2017/6/7/007.
 */
@Service
public class FileHandler {

    @Async
    public void addDataFile(String collectionName, String mockName) {
        try {
            File fileCollection = new File(DataSettingService.mockDataDir + File.separator + collectionName);
            if (!fileCollection.exists()) {
                fileCollection.mkdirs();
            }
            File fileMock = new File(fileCollection.getAbsolutePath() + File.separator + mockName + ".json");
            if (!fileMock.exists()) {
                fileMock.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void moveDataDirAndDelete(String collectionName, String oldCollectionName) {
        this.moveDataDir(collectionName, oldCollectionName);
        this.deleteDir(oldCollectionName);
    }

    public void moveDataDir(String collectionName, String oldCollectionName) {
        try {
            File oldFile = new File(DataSettingService.mockDataDir + File.separator + oldCollectionName);
            File newFile = new File(DataSettingService.mockDataDir + File.separator + collectionName);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            File[] ofs = oldFile.listFiles();
            for (File of : ofs) {
                of.renameTo(new File(newFile.getAbsolutePath() + File.separator + of.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void moveDataFile(String collectionName, String mockName, String oldCollectionName, String oldMockName) {
        try {
            File oldFile = new File(DataSettingService.mockDataDir + File.separator + oldCollectionName + File.separator + oldMockName + ".json");
            File newFile = new File(DataSettingService.mockDataDir + File.separator + collectionName + File.separator + mockName + ".json");
            oldFile.renameTo(newFile);
            File oldDir = new File(DataSettingService.mockDataDir + File.separator + oldCollectionName);
            if (oldDir.listFiles().length == 0) {
                oldDir.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDir(String collectionName) {
        try {
            File file = new File(DataSettingService.mockDataDir + File.separator + collectionName);
            File[] fs = file.listFiles();
            for (File f : fs) {
                f.delete();
            }
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void deleteDataDir(String collectionName) {
        this.deleteDir(collectionName);
    }

    @Async
    public void deleteDataFile(String collectionName, String mockName) {
        try {
            File file = new File(DataSettingService.mockDataDir + File.separator + collectionName + File.separator + mockName + ".json");
            file.delete();
            File dir = new File(DataSettingService.mockDataDir + File.separator + collectionName);
            if (dir.listFiles().length == 0) {
                dir.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void saveDataFile(String collectionName, String mockName, String content) {
        OutputStream out = null;
        try {
            File fileCollection = new File(DataSettingService.mockDataDir + File.separator + collectionName);
            if (!fileCollection.exists()) {
                fileCollection.mkdirs();
            }
            File fileMock = new File(fileCollection.getAbsolutePath() + File.separator + mockName + ".json");
            if (!fileMock.exists()) {
                fileMock.createNewFile();
            }
            out = new FileOutputStream(fileMock);
            out.write(content.getBytes());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
