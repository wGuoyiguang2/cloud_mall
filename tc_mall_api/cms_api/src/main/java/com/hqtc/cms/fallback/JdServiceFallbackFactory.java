package com.hqtc.cms.fallback;

import com.hqtc.cms.model.bean.SimilarRequestBean;
import com.hqtc.cms.model.bean.WarrantyRequestBean;
import com.hqtc.cms.model.service.JdService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * Created by laiqingchuang on 19-1-23 .
 */
@Component
public class JdServiceFallbackFactory implements FallbackFactory<JdService> {
    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Override
    public JdService create(Throwable cause) {
        return new JdService() {
            @Override
            public Map getSimilarGoods(SimilarRequestBean requestBean) {
                return errorResponse();
            }

            @Override
            public Map getWarrantyList(WarrantyRequestBean requestBean) {
                return errorResponse();
            }

            private Map errorResponse() {
                logger.error("FeignClient Request JdService Failed");
                return null;
            }
        };
    }

}