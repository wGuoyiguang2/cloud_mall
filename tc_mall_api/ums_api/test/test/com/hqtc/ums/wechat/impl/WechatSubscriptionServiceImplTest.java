package test.com.hqtc.ums.wechat.impl; 

import com.hqtc.ums.App;
import com.hqtc.ums.model.WechatUserInfoBean;
import com.hqtc.ums.wechat.WechatSubscriptionService;
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
* WechatSubscriptionServiceImpl Tester. 
* 
* @author $user 
* @since <pre>十月 12, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class WechatSubscriptionServiceImplTest {

    private Logger logger = LoggerFactory.getLogger("WechatSubscriptionServiceImplTest");

    @Autowired
    private WechatSubscriptionService wechatSubscriptionService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: checkWechatSubscriptionExist(String applicationName)
    *
    */
    @Test
    public void testCheckWechatSubscriptionExist() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getWecahtToken(String appId, String secret)
    *
    */
    @Test
    public void testGetWecahtToken() throws Exception {
    //TODO: Test goes here...
    }

    /**
     *
     * Method: getWechatUserInfoByOpenId(String openId, String token)
     *
     * */
    @Test
    public void testGetWechatUserInfoByOpenId() throws Exception {
        String openId = "oxXu94j8SwqBP4ChZPO0QUdkHbKg";
        String token = "14_9xO135otpoMGmHKf_8ulcdU73-Ut3GZ5kg6k8Kdvnz_eLLx0NgDOmoIhxB3eorQDk5XSwwbahYWse27ISmffe7v83UVe4seW7HQvE0JUPXVYqvKt8kgWSYk3MAeThYzUnTOzi1krs7YQ4WE_JXAjAIAAOR";
        WechatUserInfoBean wechatUserInfoBean = wechatSubscriptionService.getWechatUserInfoByOpenId(openId, token);
        logger.info("ok");
    }


} 
