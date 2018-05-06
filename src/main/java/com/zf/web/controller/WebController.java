package com.zf.web.controller;

import com.alibaba.fastjson.JSON;
import com.zf.dao.domain.MockInfoDao;
import com.zf.dto.MockRule;
import com.zf.dto.easyui.Datagrid;
import com.zf.service.DataProviderService;
import com.zf.service.DataSettingService;
import com.zf.service.MockWebService;
import com.zf.utils.MapperBuilder;
import com.zf.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
public class WebController {

    private final static Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private MockWebService mockWebService;

    @Autowired
    private DataProviderService dataProviderService;

    @RequestMapping(value = "/index")
    public String mockIndex(String role, Map<String, Object> map) {
        if (StringUtils.isNotBlank(role) && role.equals("whosyourdaddy")) {
            map.put("setting", 1);
        }
        map.put("mockDataDir", DataSettingService.mockDataDir);
        map.put("mockDataCount", DataSettingService.mockDataCount);
        map.put("mockDataRole", DataSettingService.mockDataRole);
        map.put("mockCurrentDataCount", DataProviderService.MOCK_DATAS.values().size());
        return "mock/index";
    }

    @RequestMapping(value = "/content")
    public String mockContent(String collectionName, String mockName, Map<String, Object> map) throws Exception {
        collectionName = URLDecoder.decode(collectionName, "UTF-8");
        mockName = URLDecoder.decode(mockName, "UTF-8");
        map.put("collectionName", collectionName);
        map.put("mockName", mockName);
        MockInfoDao rules = DataProviderService.MOCK_DATAS.get(collectionName + DataProviderService.MOCK_DATAS_KEY_CONNECTOR + mockName);
        map.put("rules", rules);
        return "mock/content_mock";
    }

    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<Map<String, Object>> getCollectionsTree() {
        List<Map<String, Object>> list = new ArrayList<>();
        int index = 0;
        for (String key : DataProviderService.MOCK_COLLECTIONS.keySet()) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("text", key);
            map.put("id", index++);
            List<Map<String, Object>> mockList = new ArrayList<>();
            for (String mockName : DataProviderService.MOCK_COLLECTIONS.get(key)) {
                Map<String, Object> mockMap = new LinkedHashMap<>();
                mockMap.put("text", mockName);
                mockMap.put("attributes", MapperBuilder.<String, String>getBuilder().put("pnode", key).build());
                mockMap.put("id", index++);
                mockList.add(mockMap);
            }
            map.put("children", mockList);
            list.add(map);
        }
        if (list.size() == 0) {
            return list;
        }
        List<Map<String, Object>> res = new ArrayList<>();
        Map<String, Object> resMap = new LinkedHashMap<>();
        resMap.put("text", "COLLECTIONS");
        resMap.put("children", list);
        resMap.put("id", index++);
        res.add(resMap);
        return res;
    }

    @RequestMapping(value = "/collections")
    @ResponseBody
    public List<Map<String, Object>> getCollections() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> first = new LinkedHashMap<>();
        first.put("id", "");
        first.put("text", "请选择或输入");
        list.add(first);
        for (String key : DataProviderService.MOCK_COLLECTIONS.keySet()) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", key);
            map.put("text", key);
            list.add(map);
        }
        return list;
    }

    @RequestMapping(value = "/add")
    public String add() {
        return "mock/collection_add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseUtil.ResponseInfo addMock(String collectionName, String mockName) {

        if (StringUtils.isBlank(collectionName)) {
            ResponseUtil.ResponseInfo res = ResponseUtil.getFailedResponse();
            res.setRetMsg("请输入COLLECTION名称！");
            return res;
        }
        if (!DataProviderService.MOCK_COLLECTIONS.containsKey(collectionName)) {
            DataProviderService.MOCK_COLLECTIONS.put(collectionName, new ArrayList<String>());
        }
        if (DataProviderService.MOCK_COLLECTIONS.get(collectionName).contains(mockName)) {
            ResponseUtil.ResponseInfo res = ResponseUtil.getFailedResponse();
            res.setRetMsg("已存在，请重新输入MOCK名称！");
            return res;
        }
        DataProviderService.MOCK_COLLECTIONS.get(collectionName).add(mockName);
        DataProviderService.MOCK_DATAS.put(collectionName + DataProviderService.MOCK_DATAS_KEY_CONNECTOR + mockName, new MockInfoDao());
        ResponseUtil.ResponseInfo res = ResponseUtil.getSuccessResponse();
        res.setRetMsg("添加成功");
        mockWebService.addDataFile(collectionName, mockName);
        return res;
    }

    @RequestMapping(value = "/edit")
    public String edit(String collectionName, String mockName, Map<String, Object> map) throws UnsupportedEncodingException {
        collectionName = URLDecoder.decode(collectionName, "UTF-8");
        mockName = URLDecoder.decode(mockName, "UTF-8");
        if (StringUtils.isBlank(mockName)) {
            map.put("showMockName", false);
        } else {
            map.put("showMockName", true);
        }
        map.put("collectionName", collectionName);
        map.put("mockName", mockName);
        return "mock/collection_edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseUtil.ResponseInfo editMock(String collectionName, String mockName, String oldCollectionName, String oldMockName) {
        if (StringUtils.isBlank(collectionName)) {
            ResponseUtil.ResponseInfo res = ResponseUtil.getFailedResponse();
            res.setRetMsg("请输入COLLECTION名称！");
            return res;
        }
        if (!collectionName.equals(oldCollectionName) && StringUtils.isBlank(mockName)) {
            boolean flag = false;
            if (!DataProviderService.MOCK_COLLECTIONS.containsKey(collectionName)) {
                DataProviderService.MOCK_COLLECTIONS.put(collectionName, new ArrayList<String>());
                flag = true;
            }
            List<String> clist = DataProviderService.MOCK_COLLECTIONS.get(collectionName);
            List<String> oclist = DataProviderService.MOCK_COLLECTIONS.get(oldCollectionName);
            for (String s : clist) {
                if (oclist.contains(s)) {
                    if (flag) {
                        DataProviderService.MOCK_COLLECTIONS.remove(collectionName);
                    }
                    ResponseUtil.ResponseInfo res = ResponseUtil.getFailedResponse();
                    res.setRetMsg("含有重复MOCK名称！");
                    return res;
                }
            }
            clist.addAll(oclist);
            DataProviderService.MOCK_COLLECTIONS.remove(oldCollectionName);
            for (String s : oclist) {
                MockInfoDao oMockInfo = DataProviderService.MOCK_DATAS.get(oldCollectionName + DataProviderService.MOCK_DATAS_KEY_CONNECTOR + s);
                DataProviderService.MOCK_DATAS.put(collectionName + DataProviderService.MOCK_DATAS_KEY_CONNECTOR + s, oMockInfo);
                DataProviderService.MOCK_DATAS.remove(oldCollectionName + DataProviderService.MOCK_DATAS_KEY_CONNECTOR + s);
            }
            mockWebService.moveDataDirAndDelete(collectionName, oldCollectionName);
        } else if (!collectionName.equals(oldCollectionName) || !mockName.equals(oldMockName)) {
            boolean flag = false;
            if (!DataProviderService.MOCK_COLLECTIONS.containsKey(collectionName)) {
                DataProviderService.MOCK_COLLECTIONS.put(collectionName, new ArrayList<String>());
                flag = true;
            }
            if (DataProviderService.MOCK_COLLECTIONS.get(collectionName).contains(mockName)) {
                if (flag) {
                    DataProviderService.MOCK_COLLECTIONS.remove(collectionName);
                }
                ResponseUtil.ResponseInfo res = ResponseUtil.getFailedResponse();
                res.setRetMsg("已存在，请重新输入MOCK名称！");
                return res;
            }
            DataProviderService.MOCK_COLLECTIONS.get(collectionName).add(mockName);
            DataProviderService.MOCK_COLLECTIONS.get(oldCollectionName).remove(oldMockName);
            String okey = oldCollectionName + DataProviderService.MOCK_DATAS_KEY_CONNECTOR + oldMockName;
            MockInfoDao oMockInfo = DataProviderService.MOCK_DATAS.get(okey);
            DataProviderService.MOCK_DATAS.put(collectionName + DataProviderService.MOCK_DATAS_KEY_CONNECTOR + mockName, oMockInfo);
            DataProviderService.MOCK_DATAS.remove(okey);
            if (DataProviderService.MOCK_COLLECTIONS.get(oldCollectionName).size() == 0) {
                DataProviderService.MOCK_COLLECTIONS.remove(oldCollectionName);
            }
            mockWebService.moveDataFile(collectionName, mockName, oldCollectionName, oldMockName);
        }
        ResponseUtil.ResponseInfo res = ResponseUtil.getSuccessResponse();
        res.setRetMsg("修改成功");
        return res;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseUtil.ResponseInfo delete(String collectionName, String mockName) {
        if (StringUtils.isBlank(mockName)) {
            List<String> list = DataProviderService.MOCK_COLLECTIONS.get(collectionName);
            for (String s : list) {
                DataProviderService.MOCK_DATAS.remove(collectionName + DataProviderService.MOCK_DATAS_KEY_CONNECTOR + s);
            }
            DataProviderService.MOCK_COLLECTIONS.remove(collectionName);
            mockWebService.deleteDataDir(collectionName);
        } else {
            DataProviderService.MOCK_COLLECTIONS.get(collectionName).remove(mockName);
            if (DataProviderService.MOCK_COLLECTIONS.get(collectionName).size() == 0) {
                DataProviderService.MOCK_COLLECTIONS.remove(collectionName);
            }
            DataProviderService.MOCK_DATAS.remove(collectionName + DataProviderService.MOCK_DATAS_KEY_CONNECTOR + mockName);
            mockWebService.deleteDataFile(collectionName, mockName);
        }
        ResponseUtil.ResponseInfo res = ResponseUtil.getSuccessResponse();
        res.setRetMsg("删除成功");
        return res;
    }

    @RequestMapping(value = "/rules")
    @ResponseBody
    public Datagrid<MockRule> getMockRules(String collectionName, String mockName) {
        List<MockRule> list = new ArrayList<>();
        MockInfoDao rules = DataProviderService.MOCK_DATAS.get(collectionName + DataProviderService.MOCK_DATAS_KEY_CONNECTOR + mockName);
        if (rules != null) {
            List<MockInfoDao.MockConditionInfo> conds = rules.getResponseCondition();
            for (MockInfoDao.MockConditionInfo cond : conds) {
                MockRule mr = new MockRule();
                mr.setMockRule(cond.getResCondition());
                mr.setMockRes(cond.getResValue());
                list.add(mr);
            }
        }
        return new Datagrid<>(list);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseUtil.ResponseInfo save(String collectionName, String mockName, MockInfoDao infos) {
        String[] conds = infos.getResCondition();
        String[] resValues = infos.getResValue();
        if (conds.length == 1 && resValues.length != 1) {
            String[] newResValues = new String[1];
            StringBuffer sb = new StringBuffer();
            for (String resValue : resValues) {
                if (sb.length() != 0) {
                    sb.append(",");
                }
                sb.append(resValue);
            }
            newResValues[0] = sb.toString();
            infos.setResValue(newResValues);
        }
        resValues = infos.getResValue();
        if (conds.length != resValues.length || conds.length == 0) {
            ResponseUtil.ResponseInfo res = ResponseUtil.getFailedResponse();
            res.setRetMsg("mock规则定义出错!");
            return res;
        }
        List<MockInfoDao.MockConditionInfo> responseCondition = new ArrayList<>();
        for (int i = 0; i < conds.length; i++) {
            MockInfoDao.MockConditionInfo condInfo = new MockInfoDao.MockConditionInfo();
            condInfo.setResCondition(conds[i]);
            try {
                Object obj = JSON.parse(resValues[i]);
                condInfo.setResValue(JSON.toJSONString(obj));
            } catch (Exception e) {
                condInfo.setResValue(resValues[i]);
            }
            responseCondition.add(condInfo);
        }
        infos.setId(System.currentTimeMillis());
        try {
            Object obj = JSON.parse(infos.getRequestParamTemplate());
            infos.setRequestParamTemplate(JSON.toJSONString(obj));
        } catch (Exception e) {
            //
        }
        infos.setResponseCondition(responseCondition);
        infos.setResCondition(null);
        infos.setResValue(null);
        DataProviderService.MOCK_DATAS.put(collectionName + DataProviderService.MOCK_DATAS_KEY_CONNECTOR + mockName, infos);
        mockWebService.saveDataFile(collectionName, mockName, JSON.toJSONString(infos, true));
        ResponseUtil.ResponseInfo res = ResponseUtil.getSuccessResponse();
        res.setRetMsg("保存成功");
        return res;
    }

    @RequestMapping(value = "/setting")
    public String setting(Map<String, Object> map) {
        map.put("mockDataDir", DataSettingService.mockDataDir);
        map.put("mockDataCount", DataSettingService.mockDataCount);
        map.put("mockDataRole", DataSettingService.mockDataRole);
        return "mock/setting";
    }

    @RequestMapping(value = "/setting", method = RequestMethod.POST)
    @ResponseBody
    public ResponseUtil.ResponseInfo setting(String mockDataDir, Integer mockDataCount, Integer mockDataRole) {
        if (StringUtils.isNotBlank(mockDataDir)) {
            DataSettingService.mockDataDir = mockDataDir.trim();
            dataProviderService.loadMockData();
        }
        if (mockDataCount != null) {
            DataSettingService.mockDataCount = mockDataCount;
        }
        if (mockDataRole != null) {
            DataSettingService.mockDataRole = mockDataRole;
        }
        ResponseUtil.ResponseInfo res = ResponseUtil.getSuccessResponse();
        res.setRetMsg("保存成功");
        return res;
    }

}
