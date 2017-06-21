/*
package com.zf.web.controller;

import com.alibaba.fastjson.JSON;
import com.zf.dao.domain.MockInfoDao;
import com.zf.service.DataProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class WebController1 {
    private final static Logger logger = LoggerFactory.getLogger(WebController1.class);

    @RequestMapping(value = "/index")
    public String mockIndex() {
        return "/index";
    }

    @RequestMapping(value = "/addmockcase", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public String addMockCase(@RequestBody String info) throws Exception {
        MockInfoDao jsoninfo = JSON.parseObject(info, MockInfoDao.class);
        jsoninfo.setId(DataProviderService.MOCK_INFOS.size() + 1);
        DataProviderService.MOCK_INFOS.add(jsoninfo);
        logger.info(DataProviderService.MOCK_INFOS.toString());
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/delmockcase", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String delMockCase(@RequestParam(value = "id") int id) throws Exception {
        DataProviderService.MOCK_INFOS.remove(id - 1);
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/getmockcase", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getMockCase() throws Exception {
        String caseJson = JSON.toJSONString(DataProviderService.MOCK_INFOS);
        return caseJson;
    }


}
*/
