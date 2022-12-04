package test.com.hqtc.bms.service.impl; 

import com.hqtc.bms.App;
import com.hqtc.bms.model.database.TOrderRefundBean;
import com.hqtc.bms.model.mapper.TOrderRefundMapper;
import com.hqtc.bms.service.RefundService;
import com.hqtc.common.response.ResultData;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/** 
* RefundServiceImpl Tester. 
* 
* @author $user 
* @since <pre>八月 14, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class RefundServiceImplTest {
    private Logger logger = LoggerFactory.getLogger("RefundServiceImplTest");

    @Autowired
    private RefundService refundService;

    @Autowired
    private TOrderRefundMapper refundMapper;


    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: userRefund(String orderSn, Map<String, Integer> products, String refundReason)
    *
    */
    @Test
    public void testUserRefund() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: addUserRefund(TOrderRefundBean orderRefundBean)
    *
    */
    @Test
    public void testAddUserRefundOrderRefundBean() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: addUserRefund(List<TOrderRefundBean> orderRefundBeans)
    *
    */
    @Test
    public void testAddUserRefundOrderRefundBeans() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: userRefundNotify(String refundNo, String refundTradeNo)
    *
    */
    @Test
    public void testUserRefundNotify() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getSuccessRefundByOrderSnAndProduct(String orderSn, int product)
    *
    */
    @Test
    public void testGetSuccessRefundByOrderSnAndProduct() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getRefundedProductCount(String orderSn, int product)
    *
    */
    @Test
    public void testGetRefundedProductCount() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: venderRefund(String orderSn, Map<String, Integer> products, String refundReason)
    *
    */
    @Test
    public void testVenderRefund() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: refund(String jdOrderId)
    *
    */
    @Test
    public void testRefund() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: successRefundHandler(TOrderRefundBean tOrderRefundBean)
    *
    */
    @Test
    public void testSuccessRefundHandler() throws Exception {
    //TODO: Test goes here...
        String refundNo = "20180814101412196179";
        String refundOrderSn = "20180814101412100000145380";
        List<TOrderRefundBean> tOrderRefundBeans = refundMapper.getRefundInfo(refundOrderSn, refundNo);
        ResultData resultData = refundService.successRefundHandler(tOrderRefundBeans);
        logger.info(String.valueOf(resultData.getError()));
        logger.info(String.valueOf(resultData.getMsg()));
    }


    /**
    *
    * Method: userRefund(RefundParams refundParams, VenderPayment venderPayment)
    *
    */
    @Test
    public void testUserRefund1() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = RefundServiceImpl.getClass().getMethod("userRefund", RefundParams.class, VenderPayment.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: createQrcode(HashMap<String, Object> pmsParams)
    *
    */
    @Test
    public void testCreateQrcode() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = RefundServiceImpl.getClass().getMethod("createQrcode", HashMap<String,.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: pmsUserRefund(Map<String, Object> pmsParams)
    *
    */
    @Test
    public void testPmsUserRefund() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = RefundServiceImpl.getClass().getMethod("pmsUserRefund", Map<String,.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: getRefundFee(TOrderBean orderBean, Map<String, Integer> products)
    *
    */
    @Test
    public void testGetRefundFee() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = RefundServiceImpl.getClass().getMethod("getRefundFee", TOrderBean.class, Map<String,.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: formatRefundBeans(String orderSn, Map<String, Integer> products)
    *
    */
    @Test
    public void testFormatRefundBeans() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = RefundServiceImpl.getClass().getMethod("formatRefundBeans", String.class, Map<String,.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: getVenderRefundPrice(String orderSn, Map<String, Integer> products)
    *
    */
    @Test
    public void testGetVenderRefundPrice() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = RefundServiceImpl.getClass().getMethod("getVenderRefundPrice", String.class, Map<String,.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: formatRefundBean(String orderSn, Map<String, Integer> products)
    *
    */
    @Test
    public void testFormatRefundBean() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = RefundServiceImpl.getClass().getMethod("formatRefundBean", String.class, Map<String,.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: getCertStream(Long venderId)
    *
    */
    @Test
    public void testGetCertStream() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = RefundServiceImpl.getClass().getMethod("getCertStream", Long.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: changeCert(InputStream inputStream)
    *
    */
    @Test
    public void testChangeCert() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = RefundServiceImpl.getClass().getMethod("changeCert", InputStream.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    @Test
    public void testDenyReceiveRefund() throws Exception {
        ResultData resultData = refundService.denyReceiveRefund("87604882254", "afe2c36ac1f57fd2efb20ba2f5a12348");
        logger.info(resultData.toString());
    }

} 
