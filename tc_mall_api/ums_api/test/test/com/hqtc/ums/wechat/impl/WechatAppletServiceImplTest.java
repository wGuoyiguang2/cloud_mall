package test.com.hqtc.ums.wechat.impl; 

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.ums.App;
import com.hqtc.ums.model.WxCode2SessionBean;
import com.hqtc.ums.wechat.WechatAppletService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** 
* WechatAppletServiceImpl Tester. 
* 
* @author $user 
* @since <pre>十月 11, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class WechatAppletServiceImplTest {

    private Logger logger = LoggerFactory.getLogger("WechatAppletServiceImplTest");

    @Autowired
    private WechatAppletService wechatAppletService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: checkWechatAppletExist(String applicationName)
    *
    */
    @Test
    public void testCheckWechatAppletExist() throws Exception {
    //TODO: Test goes here...
        logger.info("jinriyouxiang: " + wechatAppletService.checkWechatAppletExist("jinriyouxiang"));
        logger.info("mingriyouxiang: " + wechatAppletService.checkWechatAppletExist("mingriyouxiang"));
    }

    /**
     *
     * Method: ResultData<WxCode2SessionBean> getWxUserInfoByUserCode(String userCode, String applicationName)
     *
     */
    @Test
    public void testGetWxUserInfoByUserCode() throws Exception {
        //applicationName 不存在
//        ResultData resultData1 = wechatAppletService.getWxUserInfoByUserCode("wa", "ssssss");
//        Assert.assertEquals(ErrorCode.PARAM_ERROR, resultData1.getError());
//        //code 异常
//        ResultData resultData2 = wechatAppletService.getWxUserInfoByUserCode("wa", "jinriyouxiang");
//        Assert.assertEquals(ErrorCode.SERVER_EXCEPTION, resultData2.getError());
//        logger.info(resultData2.getMsg());
//        //正常(code需要终端获取)
//        ResultData<WxCode2SessionBean> resultData3 = wechatAppletService.getWxUserInfoByUserCode("081k1BW60XJCjI1n3JV604dnW60k1BW1", "jinriyouxiang");
//        Assert.assertEquals(ErrorCode.SUCCESS, resultData3.getError());
//        logger.info(resultData3.getData().getOpenid());
//        logger.info(resultData3.getData().getSession_key());
//        logger.info(resultData3.getData().getUnionid());
        String a = "qrscene_9";
        logger.error(a.substring(8,9));
    }


} 
