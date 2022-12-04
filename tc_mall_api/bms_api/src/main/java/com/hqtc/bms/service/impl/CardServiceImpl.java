package com.hqtc.bms.service.impl;

import com.hqtc.bms.model.database.TCardBean;
import com.hqtc.bms.model.database.TCardUserRecordBean;
import com.hqtc.bms.model.database.TOrderBean;
import com.hqtc.bms.model.database.TOrderCardBean;
import com.hqtc.bms.model.mapper.TCardMapper;
import com.hqtc.bms.model.mapper.TCardUserRecordMapper;
import com.hqtc.bms.model.mapper.TOrderCardMapper;
import com.hqtc.bms.model.params.*;
import com.hqtc.bms.model.rpc.VenderPayment;
import com.hqtc.bms.service.*;
import com.hqtc.bms.service.rpc.UmsService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 甜橙购物充值卡的相关方法
 * Created by wanghaoyang on 18-9-19.
 */
@Service("CardServiceImpl")
public class CardServiceImpl implements CardService {

    @Value("${spring.config.encryptKey}")
    private String key;
    @Autowired
    private TCardMapper tCardMapper;

    @Autowired
    private TCardUserRecordMapper tCardUserRecordMapper;

    @Autowired
    @Resource(name = "CommonServiceImpl")
    private CommonService commonService;

    @Autowired
    private TOrderCardMapper tOrderCardMapper;

    @Autowired
    @Resource(name = "QrCodeServiceImpl")
    private QrCodeService qrCodeService;

    @Autowired
    @Resource(name = "OrderServiceImpl")
    private OrderService orderService;

    @Autowired
    private UmsService umsService;

    private Logger logger = LoggerFactory.getLogger("CardServiceImpl");

    @Override
    public ResultData useCard(List<String> cardNos, TOrderBean orderBean){
        ResultData resultData = new ResultData();
        //判断是否重复下单
        List<String> orderCardBeans = tOrderCardMapper.getOrderCardNos(orderBean.getOrder_sn());
        if(null != orderBean && !orderCardBeans.isEmpty()){
            resultData.setError(ErrorCode.ORDER_EXIST);
            resultData.setMsg("订单已存在,请勿重复下单");
            return resultData;
        }
        //判断购物卡是否可用
        resultData = this.checkCardsCanBeUsed(cardNos, orderBean.getUser_id(), orderBean.getVenderid());
        if(0 != resultData.getError()){
            return resultData;
        }
        //判断余额是否充足,若充足，则返回购买成功；否则返回需要结的尾款
        BigDecimal userPayPrice = orderBean.getPrice().add(orderBean.getFreight());
        List<String> cardIds = new ArrayList<>(cardNos.size());
        for(String card:cardNos){cardIds.add("\""+card+"\"");}
        BigDecimal totalBalance = tCardMapper.getTotalBalance(String.join(",", cardIds));
        if(totalBalance.compareTo(userPayPrice) >= 0){//购物卡余额充足
            int res = this.batchAddOrderCard(cardNos, orderBean.getOrder_sn());
            if(res < 1){
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("插入数据库失败");
            }else {
                PayNotifyUrlParams payNotifyUrlParams = new PayNotifyUrlParams();
                payNotifyUrlParams.setIp("0.0.0.0");
                payNotifyUrlParams.setOrder_sn("CARD"+System.currentTimeMillis());
                payNotifyUrlParams.setOut_trade_no(orderBean.getOrder_sn());
                payNotifyUrlParams.setPay_time(this.formatStringTime(new Date()));
                payNotifyUrlParams.setPay_type("4");
                payNotifyUrlParams.setTotal_fee("0");
                resultData = orderService.paySuccessNotify(payNotifyUrlParams);
                if(0 == resultData.getError()){
                    resultData.setMsg("购物卡全额支付成功");
                    Map<String, Object> map = new HashMap<>(2);
                    map.put("wxQrCode", "");
                    map.put("aliQrCode", "");
                    map.put("faceValue", "0");
                    resultData.setData(map);
                }else {
                    resultData.setError(ErrorCode.CARD_START_PAY_ERROR);
                    resultData.setMsg("购物卡支付失败,请重试");
                }
                return resultData;
            }
        }else {//购物卡余额不足,需要临时记录购物卡的卡号,方便支付成功回调时找到需要扣哪些卡的钱
            BigDecimal qrCodeFaceValue = userPayPrice.subtract(totalBalance);//需要生成的二维码的面值
            int res = this.batchAddOrderCard(cardNos, orderBean.getOrder_sn());
            if(res < 1){
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("插入数据库失败");
            }else {
                resultData = this.createPayQrCode(orderBean, qrCodeFaceValue);
            }
        }
        return resultData;
    }


    @Override
    public ResultData useCard(List<String> cardNos, TOrderBean orderBean, int payType, MutiplePayParams payParams){
        ResultData resultData = new ResultData();
        //判断是否重复下单
        List<String> orderCardBeans = tOrderCardMapper.getOrderCardNos(orderBean.getOrder_sn());
        if(null != orderBean && !orderCardBeans.isEmpty()){
            resultData.setError(ErrorCode.ORDER_EXIST);
            resultData.setMsg("订单已存在,请勿重复下单");
            return resultData;
        }
        //判断购物卡是否可用
        resultData = this.checkCardsCanBeUsed(cardNos, orderBean.getUser_id(), orderBean.getVenderid());
        if(0 != resultData.getError()){
            return resultData;
        }
        //判断余额是否充足,若充足，则返回购买成功；否则返回需要结的尾款
        BigDecimal userPayPrice = orderBean.getPrice().add(orderBean.getFreight());
        List<String> cardIds = new ArrayList<>(cardNos.size());
        for(String card:cardNos){cardIds.add("\""+card+"\"");}
        BigDecimal totalBalance = tCardMapper.getTotalBalance(String.join(",", cardIds));
        if(totalBalance.compareTo(userPayPrice) >= 0){//购物卡余额充足
            int res = this.batchAddOrderCard(cardNos, orderBean.getOrder_sn());
            if(res < 1){
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("插入数据库失败");
            }else {
                PayNotifyUrlParams payNotifyUrlParams = new PayNotifyUrlParams();
                payNotifyUrlParams.setIp("0.0.0.0");
                payNotifyUrlParams.setOrder_sn("CARD"+System.currentTimeMillis());
                payNotifyUrlParams.setPay_time(this.formatStringTime(new Date()));
                payNotifyUrlParams.setOut_trade_no(orderBean.getOrder_sn());
                payNotifyUrlParams.setPay_type("4");
                payNotifyUrlParams.setTotal_fee("0");
                resultData = orderService.paySuccessNotify(payNotifyUrlParams);
                if(0 == resultData.getError()){
                    resultData.setMsg("购物卡全额支付成功");
                    Map<String, Object> map = new HashMap<>(7);
                    map.put("timeStamp", "");
                    map.put("nonce_str", "0");
                    map.put("appid", "");
                    map.put("sign", "");
                    map.put("mch_id", "");
                    map.put("prepay_id", "");
                    map.put("faceValue", "0");
                    resultData.setData(map);
                }else {
                    resultData.setError(ErrorCode.CARD_START_PAY_ERROR);
                    resultData.setMsg("购物卡支付失败,请重试");
                }
                return resultData;
            }
        }else {//购物卡余额不足,需要临时记录购物卡的卡号,方便支付成功回调时找到需要扣哪些卡的钱
            BigDecimal qrCodeFaceValue = userPayPrice.subtract(totalBalance);//需要生成的二维码的面值
            int res = this.batchAddOrderCard(cardNos, orderBean.getOrder_sn());
            if(res < 1){
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("插入数据库失败");
            }else {
                if(3 == payType) {//小程序
                    resultData = this.createPayAuth(orderBean, qrCodeFaceValue, payParams);
                }else {
                    resultData = this.createPayQrCode(orderBean, qrCodeFaceValue);
                }
            }
        }
        return resultData;
    }

    @Override
    public ResultData checkBindByCardNo(List<String> cards){
        return null;
    }

    @Override
    public ResultData checkBindByPassWord(String passWord){
        return null;
    }

    /**
     * 事务内部使用select for update 对本行数据进行加锁
     * 事务的隔离级别设置为Isolation.READ_COMMITTED，防止脏读
     * 事务超时时间设为3秒,防止数据库死锁造成程序阻塞
     * @throws org.springframework.transaction.TransactionTimedOutException 事务超时
     * @throws RuntimeException 插入记录失败
     * */
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 3)
    @Override
    public ResultData bindCard(String passWord, int userId){
        ResultData resultData = new ResultData();
        TCardBean tCardBean = tCardMapper.getCardInfoByPassWord(passWord);
        if(null == tCardBean){
            resultData.setError(ErrorCode.CARD_PASSWORD_ERROR);
            resultData.setMsg("密码错误！");
            return resultData;
        }
        if(tCardBean.getStatus() != 1){
            resultData.setError(ErrorCode.CARD_CANT_USE);
            resultData.setMsg("购物卡不可用！");
            return resultData;
        }
        if(tCardBean.getBind_user()>0){
            resultData.setError(ErrorCode.CARD_BINDEBD);
            resultData.setMsg("该购物卡已被使用，请更换购物卡继续绑定，谢谢！");
            return resultData;
        }
        if(tCardBean.getEtime().compareTo(new Date())<0){
            resultData.setError(ErrorCode.CARD_EXPIRE);
            resultData.setMsg("该购物卡已过期");
            return resultData;
        }
        int res = tCardMapper.bindCard(userId, tCardBean.getId());
        if(res < 1){
            resultData.setError(ErrorCode.CARD_BIND_FAIL);
            resultData.setMsg("绑定失败,请重试！");
            return resultData;
        }
        TCardUserRecordBean tCardUserRecordBean = new TCardUserRecordBean();
        tCardUserRecordBean.setCard_no(tCardBean.getCard_no());
        tCardUserRecordBean.setOperate_type(1);
        tCardUserRecordBean.setUser_id(userId);
        tCardUserRecordBean.setCtime(new Date());
        tCardUserRecordBean.setUse_fee(tCardBean.getFace_value());
        int logRes = tCardUserRecordMapper.addRecord(tCardUserRecordBean);
        if(logRes < 1){
            logger.error("插入绑定记录失败,进行绑定回滚cardNo:"+tCardBean.getCard_no()+" userId:"+userId);
            throw new RuntimeException("插入绑定记录失败,进行绑定回滚");
        }
        CardSimpleInfoBean cardSimpleInfoBean = new CardSimpleInfoBean();
        cardSimpleInfoBean.setCardNo(tCardBean.getCard_no());
        cardSimpleInfoBean.setExpireTime(commonService.timeFormatToString(tCardBean.getEtime().getTime()));
        cardSimpleInfoBean.setFaceValue(tCardBean.getFace_value());
        cardSimpleInfoBean.setBalance(tCardBean.getBalance());
        cardSimpleInfoBean.setCardState(tCardBean.getStatus());
        resultData.setData(cardSimpleInfoBean);
        return resultData;
    }

    @Override
    public int addCardUserRecord(TCardUserRecordBean tCardUserRecordBean){
        return tCardUserRecordMapper.addRecord(tCardUserRecordBean);
    }

    @Override
    public List<CardSimpleInfoBean> getAllMyCard(int userId){
//        return tCardMapper.getAllMyCards(userId);
//        return tCardMapper.getAllMyCards2(userId);
        List<CardSimpleInfoBean> validCard = tCardMapper.getUserValidCard(userId);
        validCard.addAll(tCardMapper.getUserInvalidCard(userId));
        long currentTime = System.currentTimeMillis()/1000;
        for(CardSimpleInfoBean cardSimpleInfoBean:validCard){
            cardSimpleInfoBean.setServerTime(currentTime);
            cardSimpleInfoBean.setEtime(this.dateToStamp(cardSimpleInfoBean.getExpireTime()));
        }
        return validCard;
    }

    @Override
    public List<CardUseLogBean> getCardRecord(String cardNo, int start, int size){
        return tCardUserRecordMapper.getCardUseLogLimit(cardNo, start, size);
    }

    @Transactional
    @Override
    public BigDecimal deductMoneyFromCards(List<String> cards, BigDecimal totalMoney, String orderSn, int userId){
        List<TCardUserRecordBean> tCardUserRecordBeans = new ArrayList<>(cards.size());
        List<TOrderCardBean> tOrderCardBeans = new ArrayList<>(cards.size());
        for(String cardNo:cards){
            if(new BigDecimal(0).compareTo(totalMoney)>=0){
                break;
            }
            TCardBean cardBean = tCardMapper.getCardInfoByCardNoLock(cardNo);
            if(null == cardBean){
                String errorLog = "无此购物卡:"+cardNo;
                logger.error(errorLog);
                throw new RuntimeException(errorLog);
            }
            if(new BigDecimal(0).compareTo(cardBean.getBalance())>=0){
                String errorLog = "1购物卡:"+cardNo + "余额为0";
                logger.error(errorLog);
                throw new RuntimeException(errorLog);
            }
            BigDecimal currentDedcut = new BigDecimal(0);
            if(cardBean.getBalance().compareTo(totalMoney) >= 0){//当前卡的余额大于等于
                currentDedcut = totalMoney;
            }else {
                currentDedcut = cardBean.getBalance();
            }
            totalMoney = totalMoney.subtract(currentDedcut);
            int res = tCardMapper.updateBalance(currentDedcut, cardBean.getId());
            if(res < 1){
                logger.error("更新数据库失败");
                throw new RuntimeException("更新数据库失败");
            }
            TCardBean cardBean1 = tCardMapper.getCardInfoByCardNo(cardNo);
            if(null == cardBean1 || new BigDecimal(0).compareTo(cardBean1.getBalance())>0){
                String errorLog = "2购物卡:"+cardNo + "扣款失败回滚";
                logger.error(errorLog);
                throw new RuntimeException(errorLog);
            }
            TCardUserRecordBean tCardUserRecordBean = new TCardUserRecordBean();
            tCardUserRecordBean.setUse_fee(currentDedcut);
            tCardUserRecordBean.setUser_id(userId);
            tCardUserRecordBean.setCard_no(cardNo);
            tCardUserRecordBean.setOperate_type(2);
            tCardUserRecordBean.setCtime(new Date());
            tCardUserRecordBean.setOrder_sn(orderSn);
            tCardUserRecordBeans.add(tCardUserRecordBean);

            TOrderCardBean tOrderCardBean = new TOrderCardBean();
            tOrderCardBean.setCard_no(cardNo);
            tOrderCardBean.setOrder_sn(orderSn);
            tOrderCardBean.setBalance(cardBean.getBalance());
            tOrderCardBean.setUse_fee(currentDedcut);
            tOrderCardBeans.add(tOrderCardBean);
        }
        if(new BigDecimal(0).compareTo(totalMoney)<0){
            throw new RuntimeException("购物卡余额不足,需要补齐剩余款额");
        }
        int res1 = tCardUserRecordMapper.batchAddUserCardRecord(tCardUserRecordBeans);
        if(res1 < 1){
            throw new RuntimeException("插入订单购物卡记录失败1");
        }
        for(TOrderCardBean tOrderCardBean: tOrderCardBeans){
            int res2 = tOrderCardMapper.updateOrderCard(tOrderCardBean);
            if(res2 < 1){
                throw new RuntimeException("更新订单购物卡记录失败2");
            }
        }
        return totalMoney;
    }

    @Override
    public BigDecimal getTotalBalance(List<String> cardNos){
        return tCardMapper.getTotalBalance(String.join(",", cardNos));
    }

    /**
     * 校验购物卡是否可用
     * */
    @Override
    public ResultData checkCardsCanBeUsed(List<String> cardNos, int userId, int venderId){
        ResultData resultData = new ResultData();
        List<String> cardIds = new ArrayList<>(cardNos.size());
        for(String card:cardNos){
            cardIds.add("\""+card+"\"");
        }
        List<TCardBean> cardInfos = tCardMapper.getCardInfoByCardNos(String.join(",", cardIds));
        if(null == cardInfos || cardInfos.isEmpty()){
            resultData.setError(ErrorCode.CARD_CARDNO_ERROR);
            resultData.setMsg("未查找到相关购物卡");
            return resultData;
        }
        List<String> errorLogs = new ArrayList<>();
        for(TCardBean cardBean: cardInfos){
            if(cardBean.getBind_user() != userId){
                String errorLog = cardBean.getCard_no()+"不在当前用户下";
                logger.error(errorLog);
                errorLogs.add(errorLog);
            }
            if(1 != cardBean.getStatus()){
                String errorLog = cardBean.getCard_no()+"不可用";
                logger.error(errorLog);
                errorLogs.add(errorLog);
            }
            if(new BigDecimal(0).compareTo(cardBean.getBalance())>=0){
                String errorLog = cardBean.getCard_no()+"余额为0";
                logger.error(errorLog);
                errorLogs.add(errorLog);
            }
            if(cardBean.getEtime().compareTo(new Date())<0){
                String errorLog = cardBean.getCard_no()+"已过期";
                logger.error(errorLog);
                errorLogs.add(errorLog);
            }
            if((1 == cardBean.getBind_vender()) && (cardBean.getBind_vender() != venderId)){
                String errorLog = cardBean.getCard_no()+"不可在此渠道下使用";
                logger.error(errorLog);
                errorLogs.add(errorLog);
            }
        }
        //判断购物卡是否有余额(默认余额为0的不能参与此次购买)
        if(!errorLogs.isEmpty()){
            resultData.setError(ErrorCode.CARD_START_PAY_ERROR);
            resultData.setMsg("购物卡混合支付发起失败,具体原因如下："+errorLogs.toString());
            logger.error(errorLogs.toString());
            return resultData;
        }
        return resultData;
    }

    @Override
    public TCardBean getCardInfoByPassword(String passWord){
        return tCardMapper.getSimpleCardInfoByPassWord(passWord);
    }

    @Override
    public List<TOrderCardBean> getOrderCards(String orderSn){
        return tOrderCardMapper.getOrderCards(orderSn);
    }

    @Override
    public List<String> getOrderCardNos(String orderSn){
        return tOrderCardMapper.getOrderCardNos(orderSn);
    }

    /**
     * 购物卡,扫码混合支付是临时记录用到的购物卡
     * */
    private int batchAddOrderCard(List<String> cardNos, String orderSn){
        List<TOrderCardBean> tOrderCardBeans = new ArrayList<>(cardNos.size());
        for(String card: cardNos){
            TOrderCardBean tOrderCardBean = new TOrderCardBean();
            tOrderCardBean.setOrder_sn(orderSn);
            tOrderCardBean.setCard_no(card);
            tOrderCardBean.setBalance(new BigDecimal(-1));
            tOrderCardBean.setUse_fee(new BigDecimal(0));
            tOrderCardBean.setCtime(new Date());
            tOrderCardBeans.add(tOrderCardBean);
        }
        return tOrderCardMapper.batchAddOrderCards(tOrderCardBeans);
    }

    private String formatStringTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);
    }

    private ResultData createPayQrCode(TOrderBean orderBean, BigDecimal payPrice){
        ResultData resultData = new ResultData();
        List<VenderPayment> vps = qrCodeService.getVenderAllPayment(orderBean.getVenderid());
        if(null == vps || vps.isEmpty()){
            resultData.setError(10011);
            resultData.setMsg("商户不提供此种支付方式");
            return resultData;
        }
        Map<String, String> qrCodeUrl= qrCodeService.createQuotaQrCode(orderBean, vps, payPrice);
        if(null ==qrCodeUrl || qrCodeUrl.isEmpty()){
            resultData.setError(10012);
            resultData.setMsg("生成二维码失败");
            return resultData;
        }
        qrCodeUrl.put("faceValue", payPrice.toString());
        resultData.setData(qrCodeUrl);
        return resultData;
    }

    private ResultData createPayAuth(TOrderBean orderBean, BigDecimal payPrice, MutiplePayParams payParams){
        ResultData resultData = new ResultData();
        VenderPayment vps = qrCodeService.getWechatAppletPayment(orderBean.getVenderid());
        if(null == vps){
            resultData.setError(10011);
            resultData.setMsg("商户不提供此种支付方式");
            return resultData;
        }
        Map<String, String> info= qrCodeService.createQuotaPayAuth(orderBean, vps, payPrice, payParams);
        if(null ==info || info.isEmpty()){
            resultData.setError(10012);
            resultData.setMsg("生成支付链接失败");
            return resultData;
        }
        info.put("faceValue", payPrice.toString());
        resultData.setData(info);
        return resultData;
    }

    @Override
    public CardVo getBatchNoByCardNo(String cardNo) {
        return tCardMapper.getCardByCardNo(cardNo);
    }

    @Override
    public List<CardBatchVo> listCardBatch(Map<String, Object> map) {
        return tCardMapper.listCardBatch(map);
    }

    @Override
    public int countCardBatch(Map<String, Object> map) {
        return tCardMapper.countCardBatch(map);
    }

    @Override
    public List<CardVo> listCard(Map<String, Object> map) {
        return tCardMapper.listCard(map);
    }

    @Override
    public int countCard(Map<String, Object> map) {
        return tCardMapper.countCard(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addCard(Map<String, Object> map) throws Exception{
        //获取数据
        Integer venderId=Integer.valueOf((String)map.get("venderId"));
        BigDecimal faceValue=new BigDecimal(Double.valueOf((String)map.get("faceValue")));
        String now= DateUtil.getSimpleDateFormat();
        String batchNo=(String)map.get("batchNo");
        if(StringUtils.isEmpty(batchNo)){
            batchNo=this.createBatchNo((String)map.get("venderId"));
            map.put("batchNo",batchNo);
        }
        //添加购物卡批次
        map.put("batchNo",batchNo);
        map.put("ctime",now);
        int batchResult=0;
        try {
            batchResult=tCardMapper.addCardBatch(map);
        }catch (DuplicateKeyException e){
            throw new Exception("批次号重复！");
        }
        if(batchResult<=0){
            throw new Exception("添加批次失败！");
        }
        //添加记录
        map.put("operateType",1);
        map.put("intro","创建批次购物卡");
        int recordResult=tCardMapper.addRecord(map);
        if(recordResult<=0){
            throw new Exception("添加操作记录失败！");
        }
        //批量生成购物卡
        List<CardVo> cardList=new ArrayList<>();
        Integer count=Integer.valueOf((String)map.get("count"));
        String stime=(String)map.get("stime");
        String etime=(String)map.get("etime");
        String bindVender=(String)map.get("bindVender");
        //生成日期字符串
        String cardNoBefore=DateUtil.getFormatDate("yyyyMMddHHmm");
        for(int i=0;i<count;i++){
            CardVo card=new CardVo();
            card.setCtime(now);
            card.setStime(stime);
            card.setEtime(etime);
            card.setFaceValue(faceValue);
            card.setBalance(faceValue);
            card.setVenderid(venderId);
            card.setPasswd(this.createCardPwd());
            card.setBatchNo(batchNo);
            card.setStatus("0");
            card.setCardNo(this.createCardNo(cardNoBefore,i+1));
            card.setBindVender(bindVender);
            cardList.add(card);
        }
        int cardResult=tCardMapper.addCard(cardList);
        if(cardResult<=0){
            throw new Exception("添加购物卡失败！");
        }
        return cardResult;
    }

    @Override
    public List<CardAdminRecord> listCardBatchAdminRecord(Map<String, Object> map) {
        List<CardAdminRecord> list=tCardMapper.listCardBatchAdminRecord(map);
        return list;
    }

    @Override
    public int countCardBatchAdminRecord(Map<String, Object> map) {
        return tCardMapper.countCardBatchAdminRecord(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultData cardBatchOperate(Integer type,String batchNo,String intro,Integer operator) throws Exception{
        ResultData resultData= Tools.getThreadResultData();
        //这里状态很多很乱，如果用枚举就好了
        int status=0;
        List<Integer> statusList=new ArrayList<>();
        if(type==2){
            //如果是激活操作，将状态置为已激活1，可更新状态为０，２
            status=1;
            statusList.add(0);
            statusList.add(2);
        }else if(type==4){
            //如果是暂停操作，将状态置为已暂停2，可更新状态为１
            status=2;
            statusList.add(1);
        }else if(type==5){
            //如果是废弃操作，将状态置为已废弃3,可更新状态为０，１，２
            status=3;
            statusList.add(0);
            statusList.add(1);
            statusList.add(2);
        }
        //对要操作的购物卡数据加锁，以防止更新卡和记录的卡不一致
        List<String> cardNoList = tCardMapper.selectCardByBatchForUpdate(batchNo, statusList);
        if (cardNoList == null || cardNoList.size() <= 0) {
            throw new Exception("没有查到需要更新的卡！");
        }
        //记录操作
        CardAdminRecord cardAdminRecord=new CardAdminRecord();
        cardAdminRecord.setOperateType(type);
        cardAdminRecord.setOperator(String.valueOf(operator));
        cardAdminRecord.setBatchNo(batchNo);
        cardAdminRecord.setIntro(intro);
        int recordResult=this.addOperateRecord(cardAdminRecord,cardNoList);
        if(recordResult<=0){
            throw new Exception("添加管理员操作记录失败！");
        }
        //更新卡状态
        int cardResult= tCardMapper.updateCardStatus(cardNoList, status);
        if (cardResult <= 0) {
            throw new Exception("操作失败！");
        }
        resultData.setMsg("成功条数："+cardResult);
        return resultData;
    }

    @Override
    public CardBatchVo getBatchById(Integer id) {
        CardBatchVo cardBatchVo= tCardMapper.getBatchById(id);
        return cardBatchVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultData cardOperate(Integer type, String cardNos, String intro, Integer operator,Integer bindUser) throws Exception{
        ResultData resultData=Tools.getThreadResultData();
        List<String> cardNoList=Arrays.asList(cardNos.split(","));
        //这里状态很多很乱，如果用枚举就好了
        int status=0;
        List<Integer> statusList=new ArrayList<>();
        if(type==2){
            //如果是激活操作，将状态置为已激活1，可更新状态为０，２
            status=1;
            statusList.add(0);
            statusList.add(2);
        }else if(type==3){
            //如果是绑卡操作，可更新状态为１
            statusList.add(1);
        }else if(type==5){
            //如果是废弃操作，将状态置为已废弃3,可更新状态为０，１，２
            status=3;
            statusList.add(0);
            statusList.add(1);
            statusList.add(2);
        }
        //对要操作的购物卡数据加锁，并去除不符合状态的数据，以防止更新卡和记录的卡不一致
        cardNoList = tCardMapper.selectCardByCardNosForUpdate(cardNoList, statusList);
        if (cardNoList == null || cardNoList.size() <= 0) {
            throw new Exception("没有查到需要更新的卡！");
        }
        //记录操作
        CardAdminRecord cardAdminRecord=new CardAdminRecord();
        cardAdminRecord.setOperateType(type);
        cardAdminRecord.setOperator(String.valueOf(operator));
        cardAdminRecord.setIntro(intro);
        int recordResult=this.addOperateRecord(cardAdminRecord,cardNoList);
        if(recordResult<=0){
            throw new Exception("添加管理员操作记录失败！");
        }
        //更新卡状态
        int cardResult=0;
        if(type==3){
            //给用户操作记录表中添加
            List<CardUserRecord> cardUserRecords=new ArrayList<>();
            for(String cardNo:cardNoList) {
                CardUserRecord cardUserRecord = new CardUserRecord();
                cardUserRecord.setCtime(new Date());
                cardUserRecord.setOperateType(1);
                cardUserRecord.setUserId(bindUser);
                cardUserRecord.setCardNo(cardNo);
                cardUserRecords.add(cardUserRecord);
            }
            tCardMapper.addCardUserRecord(cardUserRecords);
            //通过userId获取用户信息，判断是否存在
            boolean isExist=this.isExistUser(bindUser);
            if(!isExist){
                throw new Exception("不存在的账号！");
            }
            //查询这些卡中有没有被绑定的，如果有，则不予绑定
            String existUser=tCardMapper.isExistBindedUser(cardNoList);
            if(StringUtils.isNotEmpty(existUser)){
                throw new Exception("绑定失败，存在已绑定的账号！");
            }
            cardResult=tCardMapper.bindUser(cardNoList,bindUser);
            if(cardResult<=0){
                throw new Exception("绑定失败！");
            }
            resultData.setMsg("成功条数："+cardResult);
        }else{
            cardResult = tCardMapper.updateCardStatus(cardNoList, status);
            if(cardResult<=0){
                throw new Exception("操作失败！");
            }
            resultData.setMsg("成功条数："+cardResult);
        }
        return resultData;
    }

    @Override
    public CardBatchAdminRecordDetailVo cardBatchAdminRecordDetail(Integer id) {
        CardBatchAdminRecordDetailVo bean=tCardMapper.cardBatchAdminRecordDetail(id);
        return bean;
    }

    @Override
    public BigDecimal getBalanceByCardNos(String cardNos) {
        List<String> cardNoList=Arrays.asList(cardNos.split(","));
        BigDecimal balance=tCardMapper.getBalanceByCardNos(cardNoList);
        return balance;
    }

    @Override
    public CardBatchVo getBatchDetail(String batchNo) {
        CardBatchVo cardBatchVo=tCardMapper.getBatchDetail(batchNo);
        return cardBatchVo;
    }

    @Override
    public String encryptCardPassWord(String passWord){
        try {
            passWord = AESTool.Encrypt(passWord, key);
        }catch (Exception e){
            passWord = "";
        }
        return passWord;
    }

    @Override
    public String decryptCardPassWord(String passWord){
        try {
            passWord = AESTool.Decrypt(passWord, key);
        }catch (Exception e){
            passWord = "";
        }
        return passWord;
    }

    @Override
    public int batchUpdateCardBalance(List<TCardBean> tCardBeans){
        for(TCardBean tCardBean: tCardBeans){
            tCardMapper.updateCardBalance(tCardBean.getCard_no(), tCardBean.getBalance());
        }
        return 1;
    }

    @Override
    public int batchAddUserCardRecord(List<TCardUserRecordBean> tCardUserRecordBeans){
        return tCardUserRecordMapper.batchAddUserCardRecord(tCardUserRecordBeans);
    }

    /**
     * 生成批次号
     * @param venderId
     * @return
     */
    private String createBatchNo(String venderId){
        String time=DateUtil.getSimpleDateFromatInt();
        return time+venderId;
    }

    /**
     * 生成卡号
     * @param cardNoBefore
     * @param i
     * @return
     */
    private String createCardNo(String cardNoBefore,int i){
        //对i补零
        String str = String.format("%05d", i);
        return cardNoBefore+str;
    }

    /**
     * 生成密码
     * @return
     */
    private String createCardPwd(){
        String pwd="";
        try {
            String uuid=UUIDUtil.generateNumberAndCharButo1IO(10,6);
            logger.info("CardEncrypt："+uuid);
            pwd = AESTool.Encrypt(uuid, key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pwd;
    }

    private boolean isExistUser(Integer userId){
        ResultData<Map<String,Object>> resultData=umsService.getUserInfo(Integer.valueOf(userId),"");
        if(resultData.getError()!=ErrorCode.SUCCESS||resultData.getData()==null){
            return false;
        }
        Map<String,Object> map=resultData.getData();
        Integer uId=Integer.valueOf((String)map.get("userId"));
        if(uId!=null&&uId>0){
            return true;
        }
        return false;
    }

    /**
     * 记录操作
     * @param cardAdminRecord
     * @param cardNoList
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addOperateRecord(CardAdminRecord cardAdminRecord,List<String> cardNoList) throws Exception{
        String batchNo=cardAdminRecord.getBatchNo();
        if(StringUtils.isEmpty(batchNo)){
            CardVo card=this.getBatchNoByCardNo(cardNoList.get(0));
            if(card!=null) {
                cardAdminRecord.setBatchNo(card.getBatchNo());
            }
        }
        int recordResult=tCardMapper.addCardAdminRecord(cardAdminRecord);
        int recordId=cardAdminRecord.getId();
        if(recordResult<=0||recordId<=0){
            throw new Exception("记录操作失败！");
        }
        List<CardAdminRecordDetail> list=new ArrayList<>();
        for (String cardNo:cardNoList) {
            CardAdminRecordDetail detail=new CardAdminRecordDetail();
            detail.setRecordId(recordId);
            detail.setCardNo(cardNo);
            list.add(detail);
        }
        int recordDetail=tCardMapper.addCardAdminRecordDetail(list);
        if(recordDetail<=0){
            throw new Exception("添加记录详情失败！");
        }
        return recordResult;
    }

    @Override
    public List<CardVo> getCardByCardNos(List<String> cardNos) {
        return tCardMapper.listCardByCardNos(cardNos);
    }

    @Override
    public int countCardUserRecord(Map<String, Object> map) {
        return tCardMapper.countCardUserRecord(map);
    }

    @Override
    public List<CardUserRecordManagerVo> listCardUserRecord(Map<String, Object> map) {
        return tCardMapper.listCardUserRecord(map);
    }

    @Override
    public CardVo getCardDetail(String cardNo) {
        return tCardMapper.getCardByCardNo(cardNo);
    }

    @Override
    public int countCardDetail(Map<String,Object> map) {
        return tCardMapper.countCardDetail(map);
    }

    @Override
    public List<CardDetailVo> listCardDetail(Map<String,Object> map) {
        return tCardMapper.listCardDetail(map);
    }

    @Override
    public TCardBean getCardInfoByCardNo(String cardNo){
        return tCardMapper.getCardInfoByCardNo(cardNo);
    }

    @Override
    public BigDecimal getCardRefundFee(String orderSn, String cardNo){
        return tCardUserRecordMapper.getCardRefundFee(cardNo, orderSn);
    }

    @Override
    public BigDecimal getOrderCardRefundFee(String orderSn){
        return tCardUserRecordMapper.getOrderCardRefundFee(orderSn);
    }

    private long dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = simpleDateFormat.parse(s);
        }catch (ParseException e){
            date = new Date();
        }
        return date.getTime() / 1000;
    }
}
