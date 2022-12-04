package test.com.hqtc.bms.service.impl; 

import com.hqtc.bms.App;
import com.hqtc.bms.service.CardService;
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

import java.math.BigDecimal;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/** 
* CardServiceImpl Tester. 
* 
* @author $user 
* @since <pre>九月 25, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class CardServiceImplTest {

    private Logger logger = LoggerFactory.getLogger("CardServiceImplTest");

    @Autowired
    private CardService cardService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: useCard(List<String> cardNos, TOrderBean orderBean)
    *
    */
    @Test
    public void testUseCard() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: checkBindByCardNo(List<String> cards)
    *
    */
    @Test
    public void testCheckBindByCardNo() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: checkBindByPassWord(String passWord)
    *
    */
    @Test
    public void testCheckBindByPassWord() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: bindCard(String passWord, int userId)
    *
    */
    @Test
    public void testBindCard() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: addCardUserRecord(TCardUserRecordBean tCardUserRecordBean)
    *
    */
    @Test
    public void testAddCardUserRecord() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getAllMyCard(int userId)
    *
    */
    @Test
    public void testGetAllMyCard() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getCardRecord(String cardNo, int start, int size)
    *
    */
    @Test
    public void testGetCardRecord() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: deductMoneyFromCards(List<String> cards, BigDecimal totalMoney, String orderSn)
    *
    */
    @Test
    public void testDeductMoneyFromCards() throws Exception {
    //TODO: Test goes here...
        List<String> cards = new ArrayList<>();
        cards.add("20190920115622123657");
        cards.add("20190920115622123659");
        String orderSn = "20180926151756241053";
        BigDecimal totalMoney = new BigDecimal(13);
        BigDecimal restMoney = cardService.deductMoneyFromCards(cards, totalMoney, orderSn, 1);
        logger.error(restMoney.toString());
    }


    /**
    *
    * Method: batchAddOrderCard(List<TOrderCard> tOrderCards)
    *
    */
    @Test
    public void testBatchAddOrderCard() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = CardServiceImpl.getClass().getMethod("batchAddOrderCard", List<TOrderCard>.class);
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
     * Method: checkCardsCanBeUsed(List<String> cardNos, int userId)
     *
     */
    @Test
    public void testCheckCardsCanBeUsed() throws Exception {
        //TODO: Test goes here...
        List<String> cards = new ArrayList<>();
        cards.add("20190920115622123659");
        int userId = 161568;
        int venderId= 56;
        ResultData resultData =  cardService.checkCardsCanBeUsed(cards, userId, venderId);
        logger.error(resultData.getMsg().toString());
    }


    /**
     *
     * Method: encryptCardPassWord(String passWord)
     *
     */
    @Test
    public void testEncryptCardPassWord() throws Exception {
        //TODO: Test goes here...
        List<String> p = new ArrayList<>();
        p.add("77fMyDcafS8j8T/OsIz0cVk7jj/Xeil/7uexRI/rMDE=");
        for (String s:p){
            logger.error(cardService.decryptCardPassWord(s));
        }
    }

    /**
     *
     * Method: encryptCardPassWord(String passWord)
     *
     */
    @Test
    public void testDecryptCardPassWord() throws Exception {
        //TODO: Test goes here..
        String s = "JBWFL193ONMSO2I6";
        logger.error(cardService.encryptCardPassWord(s));
    }
} 
