package com.cibnvideo.thirdpart.feign.fallback;

import com.cibnvideo.thirdpart.feign.ThirdPartFeign;
import com.cibnvideo.thirdpart.model.bean.SyncJDSitePriceResponse;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:23
 */
@Component
public class ThirdPartFallbackFactory implements FallbackFactory<ThirdPartFeign> {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public ThirdPartFeign create(Throwable throwable) {
        return new ThirdPartFeign() {

            @Override
            public List<SyncJDSitePriceResponse> getJDSitePrice(String skuIds) {
                logger.error("调用第三方接口获取京东商品价格出错！");
                return null;
            }
        };
    }
}
