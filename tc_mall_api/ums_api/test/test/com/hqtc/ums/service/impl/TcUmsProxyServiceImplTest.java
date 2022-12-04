package test.com.hqtc.ums.service.impl;

import com.hqtc.ums.App;
import com.hqtc.ums.service.TcUmsProxyService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** 
* TcUmsProxyServiceImpl Tester. 
* 
* @author $user 
* @since <pre>十一月 20, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TcUmsProxyServiceImplTest {

    @Autowired
    private TcUmsProxyService tcUmsProxyService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: sendCode(String mobile)
    *
    */
    @Test
    public void testSendCode() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: verifyCode(String mobile, String code)
    *
    */
    @Test
    public void testVerifyCode() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: phonePassWordRegister(String mobile, String passWord, String code, String source)
    *
    */
    @Test
    public void testPhonePassWordRegister() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: mobileLogin(String mobile, String code, String source)
    *
    */
    @Test
    public void testMobileLogin() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: passWordLogin(String mobile, String passWord)
    *
    */
    @Test
    public void testPassWordLogin() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: passWordReset(String mobile, String passWord, String code)
    *
    */
    @Test
    public void testPassWordReset() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: appletLogin(String userCode, String appName, String source)
    *
    */
    @Test
    public void testAppletLogin() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: createQrCode(String mac, String source)
    *
    */
    @Test
    public void testCreateQrCode() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: scanQrCode(String sceneId, String ticket)
    *
    */
    @Test
    public void testScanQrCode() throws Exception {
    //TODO: Test goes here...
    }


    /**
    *
    * Method: formatParams(Map<String, String> map, String uri)
    *
    */
    @Test
    public void testFormatParams() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = TcUmsProxyServiceImpl.getClass().getMethod("formatParams", Map<String,.class, String.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

} 
