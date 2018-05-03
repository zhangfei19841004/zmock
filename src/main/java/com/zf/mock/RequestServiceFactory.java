package com.zf.mock;

import com.zf.mock.enums.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestServiceFactory {
    private final static Logger logger = LoggerFactory.getLogger(RequestServiceFactory.class);
    @Autowired
    private ApplicationContext applicationContext;

    private final static Map<RequestType, IRequestService> REQUEST_SERVICES = new ConcurrentHashMap<RequestType, IRequestService>();

    @PostConstruct
    public void init() {
        logger.info("RequestServiceFactory init");
        Map<String, IRequestService> requestServices = applicationContext.getBeansOfType(IRequestService.class);
        for (Entry<String, IRequestService> requestService : requestServices.entrySet()) {
            REQUEST_SERVICES.put(requestService.getValue().getRequestType(), requestService.getValue());
        }
    }

    public static IRequestService getRequestServiceByType(RequestType requestType) {
        logger.info("RequestType getRequestServiceByType");
        return REQUEST_SERVICES.get(requestType);
    }

}
