package test.com.hqtc.ums.service.impl;

import com.hqtc.common.response.ResultData;
import com.hqtc.ums.App;
import com.hqtc.ums.service.ShortMessageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.management.StringValueExp;

/** 
* RPCNewShortMessageServiceImpl Tester. 
* 
* @author $user 
* @since <pre>十一月 17, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class RPCNewShortMessageServiceImplTest {

    private Logger logger = LoggerFactory.getLogger("RPCNewShortMessageServiceImplTest");

    @Resource(name = "RPCNewShortMessageServiceImpl")
    private ShortMessageService shortMessageService;

    /**
    *
    * Method: sendShortMessage(String mobile, int msgType, String mac)
    *
    */
    @Test
    public void testSendShortMessage() throws Exception {
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
    * Method: phoneRegister(String mobile, String passWord, String mac, String code)
    *
    */
    @Test
    public void testPhoneRegister() throws Exception {
    //TODO: Test goes here...
        ResultData resultData = shortMessageService.phoneRegister("15910386364",
                "f379eaf3c831b04de153469d1bec3455", "", "118227");
        logger.info(resultData.getMsg());
    }

    /**
    *
    * Method: phoneLogin(String mobile, String passWord, String mac)
    *
    */
    @Test
    public void testPhoneLogin() throws Exception {
    //TODO: Test goes here...
        ResultData resultData = shortMessageService.phoneLogin("15910386364", "09a5fbb0b8155753fce619be1bd50c4e", "123123123");
        logger.info(resultData.getMsg());
    }

    /**
    *
    * Method: passWordReset(String mobile, String passWord, String code, String mac)
    *
    */
    @Test
    public void testPassWordReset() throws Exception {
    //TODO: Test goes here...
        ResultData resultData = shortMessageService.passWordReset("15910386364",
                "f379eaf3c831b04de153469d1bec345e", "118227", "123123");
        logger.info(resultData.getMsg());
    }

    /**
    *
    * Method: getUserInfoByUserId(int userId, String mac)
    *
    */
    @Test
    public void testGetUserInfoByUserId() throws Exception {
    //TODO: Test goes here...
    }


    /**
    *
    * Method: rpcResponseFormat(ResultData resultData)
    *
    */
    @Test
    public void testRpcResponseFormat() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = RPCNewShortMessageServiceImpl.getClass().getMethod("rpcResponseFormat", ResultData.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

} 
