package test.com.hqtc.bms.service.impl; 

import com.google.gson.Gson;
import com.hqtc.bms.App;
import com.hqtc.bms.model.params.JdDeliveryMessageBean;
import com.hqtc.bms.model.rpc.MessageRepVo;
import com.hqtc.bms.service.MessageService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* MessageServiceImpl Tester. 
* 
* @author $user 
* @since <pre>八月 13, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class MessageServiceImplTest {
    private Logger logger = LoggerFactory.getLogger("MessageServiceImplTest");

    @Autowired
    private MessageService messageService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: getMessage(String type)
    *
    */
    @Test
    public void testGetMessage() throws Exception {
    //TODO: Test goes here...
        ResultData<List<MessageRepVo>> resultData = messageService.getMessage("5");
        List<MessageRepVo> jdDeliveryMessageBeans = resultData.getData();
        for(MessageRepVo messageBean:jdDeliveryMessageBeans){
            logger.info(messageBean.getId());
            logger.info(messageBean.getType());
            Map<String,String> data = messageBean.getResult();
            logger.info(data.toString());
        }
    }

    /**
    *
    * Method: delMessage(String messageId)
    *
    */
    @Test
    public void testDelMessage() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: handleDeliveryMessage(List<JdDeliveryMessageBean> jdMessages)
    *
    */
    @Test
    public void testHandleDeliveryMessage() throws Exception {
    //TODO: Test goes here...
//        ResultData<List<MessageRepVo>> resultData = messageService.getMessage("5");
//        List<MessageRepVo> messageRepVos = resultData.getData();

        List<MessageRepVo> messageRepVoss = new ArrayList<>();

        MessageRepVo messageRepVos = new MessageRepVo();
        messageRepVos.setId("1000550801");
        messageRepVos.setTime("");
        messageRepVos.setType("5");
        Map<String, String> data = new HashMap<>(2);
        data.put("state","2");
        data.put("orderId", "10004560101");
        messageRepVos.setResult(data);
        messageRepVoss.add(messageRepVos);

        MessageRepVo messageRepVo = new MessageRepVo();
        messageRepVo.setId("1000550802");
        messageRepVo.setTime("");
        messageRepVo.setType("5");
        Map<String, String> data1 = new HashMap<>(2);
        data1.put("state","2");
        data1.put("orderId", "10004560102");
        messageRepVo.setResult(data1);
        messageRepVoss.add(messageRepVo);

        MessageRepVo messageRepVo1 = new MessageRepVo();
        messageRepVo1.setId("1000550803");
        messageRepVo1.setTime("");
        messageRepVo1.setType("5");
        Map<String, String> data2 = new HashMap<>(2);
        data2.put("state","2");
        data2.put("orderId", "10004560103");
        messageRepVo1.setResult(data2);
        messageRepVoss.add(messageRepVo1);

        ResultData resultData1 = messageService.handleDeliveryMessage(messageRepVoss);
        logger.info(new Gson().toJson(resultData1));
    }

    /**
    *
    * Method: successRefundFinally(String jdOrderId)
    *
    */
    @Test
    public void testSuccessRefundFinally() throws Exception {
    //TODO: Test goes here...
    }


    /**
    *
    * Method: refund(JdDeliveryMessageBean deliveryMessageBean)
    *
    */
    @Test
    public void testRefund() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = MessageServiceImpl.getClass().getMethod("refund", JdDeliveryMessageBean.class);
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
    * Method: writeMessageToRedis(JdDeliveryMessageBean deliveryMessageBean)
    *
    */
    @Test
    public void testWriteMessageToRedis() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = MessageServiceImpl.getClass().getMethod("writeMessageToRedis", JdDeliveryMessageBean.class);
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
    * Method: deleteMessageFromRedis(JdDeliveryMessageBean deliveryMessageBean)
    *
    */
    @Test
    public void testDeleteMessageFromRedisDeliveryMessageBean() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = MessageServiceImpl.getClass().getMethod("deleteMessageFromRedis", JdDeliveryMessageBean.class);
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
    * Method: deleteMessageFromRedis(String jdOrderId)
    *
    */
    @Test
    public void testDeleteMessageFromRedisJdOrderId() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = MessageServiceImpl.getClass().getMethod("deleteMessageFromRedis", String.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

} 
