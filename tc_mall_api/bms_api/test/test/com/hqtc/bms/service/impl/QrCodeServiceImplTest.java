package test.com.hqtc.bms.service.impl; 

import com.hqtc.bms.App;
import com.hqtc.bms.model.database.TOrderRefundBean;
import com.hqtc.bms.model.mapper.TOrderRefundMapper;
import com.hqtc.bms.service.QrCodeService;
import com.hqtc.bms.service.RefundService;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** 
* QrCodeServiceImpl Tester. 
* 
* @author $user 
* @since <pre>七月 27, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class QrCodeServiceImplTest {

    private Logger logger = LoggerFactory.getLogger("QrCodeServiceImplTest");

    @Autowired
    private TOrderRefundMapper orderRefundMapper;

    @Autowired
    private QrCodeService qrCodeService;

    @Autowired
    private RefundService refundService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: createWechatQrCode(TOrderBean orderBean, VenderPayment venderPayment)
    *
    */
    @Test
    public void testCreateWechatQrCode() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: createPayQrCode(TOrderBean orderBean, VenderPayment venderPayment, int payType)
    *
    */
    @Test
    public void testCreatePayQrCodeForOrderBeanVenderPaymentPayType() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: createPayQrCode(TOrderBean orderBean, List<VenderPayment> venderPayments)
    *
    */
    @Test
    public void testCreatePayQrCodeForOrderBeanVenderPayments() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getVenderPayment(int venderId, int type)
    *
    */
    @Test
    public void testGetVenderPayment() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getVenderAllPayment(int venderId)
    *
    */
    @Test
    public void testGetVenderAllPayment() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: checkOrder(TOrderBean tOrderBean, int userId, int venderId)
    *
    */
    @Test
    public void testCheckOrder() throws Exception {
    //TODO: Test goes here...
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
        List<TOrderRefundBean> orderRefundBeans = new ArrayList<>();
        for(int i = 0; i<4; i++){
            TOrderRefundBean tOrderRefundBean = new TOrderRefundBean();
            tOrderRefundBean.setVenderid(56);
            tOrderRefundBean.setUser_id(12);
            tOrderRefundBean.setOrder_sn("201807271235487"+i);
            tOrderRefundBean.setProduct_id(Integer.parseInt("12345"+i));
            tOrderRefundBean.setName("");
            tOrderRefundBean.setCount(i);
            tOrderRefundBean.setRefund_price(Float.parseFloat("5.6"));
            tOrderRefundBean.setPay_type(1);
            tOrderRefundBean.setPay_order_sn("201807271"+i*10+"235487"+i);
            tOrderRefundBean.setRefund_status(0);
            tOrderRefundBean.setPay_order_sn("15465215653224155"+i);
            tOrderRefundBean.setRefund_no("12315458521422555"+i*11);
            orderRefundBeans.add(tOrderRefundBean);
        }
        logger.info(refundService.addUserRefund(orderRefundBeans)+"");
    }


    /**
    *
    * Method: userRefund(RefundParams refundParams, VenderPayment venderPayment)
    *
    */
    @Test
    public void testUserRefund2() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = QrCodeServiceImpl.getClass().getMethod("userRefund", RefundParams.class, VenderPayment.class);
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
       Method method = QrCodeServiceImpl.getClass().getMethod("createQrcode", HashMap<String,.class);
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
       Method method = QrCodeServiceImpl.getClass().getMethod("pmsUserRefund", Map<String,.class);
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
       Method method = QrCodeServiceImpl.getClass().getMethod("getRefundFee", TOrderBean.class, Map<String,.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

} 
