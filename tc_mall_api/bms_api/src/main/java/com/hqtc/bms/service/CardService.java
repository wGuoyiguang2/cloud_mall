package com.hqtc.bms.service;

import com.hqtc.bms.model.database.TCardBean;
import com.hqtc.bms.model.database.TCardUserRecordBean;
import com.hqtc.bms.model.database.TOrderBean;
import com.hqtc.bms.model.database.TOrderCardBean;
import com.hqtc.bms.model.params.*;
import com.hqtc.common.response.ResultData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-9-19.
 */
public interface CardService {

    /**
     * 充值卡消费
     * add by wanghaoyang at 20180919
     * 注意:在减去卡的余额的时候需要做事务，防止一个用户在不同的终端上同时使用同一张卡时扣余额成负的情况
     * @param cardNos 卡号
     * @param tOrderBean 订单信息
     * @return 扣款成功失败
     * */
    ResultData useCard(List<String> cardNos, TOrderBean tOrderBean);


    ResultData useCard(List<String> cardNos, TOrderBean tOrderBean, int payType, MutiplePayParams payParams);

    /**
     * 检验卡是否已经绑定
     * add by wanghaoyang at 20180920
     * @param cards 充值卡号
     * */
    ResultData checkBindByCardNo(List<String> cards);

    /**
     * 检验卡是否已经被绑定
     * add by wanghaoyang at 20180920
     * @param passWord 充值卡密
     * */
    ResultData checkBindByPassWord(String passWord);

    /**
     * 绑定充值卡
     * add by wanghaoyang at 20180920
     * @param passWord 卡密码
     * @param userId 要绑定的用户id
     * */
    ResultData bindCard(String passWord, int userId);

    /**
     * 添加卡的使用记录
     * add by wanghaoyang at 20180920
     * @param tCardUserRecordBean
     * @return 成功失败
     * */
    int addCardUserRecord(TCardUserRecordBean tCardUserRecordBean);

    /**
     * 获取我的所有购物卡
     * add by wanghaoyang at 20180920
     * @param userId 用户id
     * */
    List<CardSimpleInfoBean> getAllMyCard(int userId);

    /**
     * 获取购物卡的使用记录(分页获取)
     * add by wanghaoyang at 20180921
     * @param cardNo 购物卡号
     * @param start 页数
     * @param size 每页的数量
     * */
    List<CardUseLogBean> getCardRecord(String cardNo, int start, int size);

    /**
     * 从购物卡中扣除应付金额(本接口通过事务和悲观锁来控制并发的问题)
     * add by wanghaoyang at 20180925
     * @param cards 购物卡
     * @param totalMoney 应扣总金额
     * @param orderSn 平台订单号
     * @param userId 用户ID
     * @return 还需要支付的金额
     * @throws RuntimeException 事务失败会抛出此异常
     * */
    BigDecimal deductMoneyFromCards(List<String> cards, BigDecimal totalMoney, String orderSn, int userId);

    /**
     * 获取本次交易的购物卡的所有余额
     * add by wanghaoyang at 20180925
     * @param  cardNos 购物卡号
     * @return 购物卡余额
     * */
    BigDecimal getTotalBalance(List<String> cardNos);

    /**
     * 校验购物卡是否可用
     * add by wanghaoyang at 20180926
     * @param cardNos 卡号
     * @param userId 用户id
     * @param venderId 大客户id
     * */
    ResultData checkCardsCanBeUsed(List<String> cardNos, int userId, int venderId);

    /**
     * 通过卡密查询卡的信息
     * add by wanghaoyang at 20180928
     * @param passWord 卡密
     * @return 卡信息
     * */
    TCardBean getCardInfoByPassword(String passWord);

    /**
     * 查询某笔订单使用的购物卡信息
     * add by wanghaoyang at 20180928
     * @param orderSn 平台订单号
     * @return 这笔订单使用的购物卡信息
     * */
    List<TOrderCardBean> getOrderCards(String orderSn);

    /**
     * 查询某笔订单使用的购物卡信息
     * add by wanghaoyang at 20180928
     * @param orderSn 平台订单号
     * @return 这笔订单使用的购物卡号
     * */
    List<String> getOrderCardNos(String orderSn);

    CardVo getBatchNoByCardNo(String cardNo);

    List<CardBatchVo> listCardBatch(Map<String, Object> map);

    int countCardBatch(Map<String, Object> map);

    List<CardVo> listCard(Map<String, Object> map);

    int countCard(Map<String, Object> map);

    int addCard(Map<String, Object> map) throws Exception;

    List<CardAdminRecord> listCardBatchAdminRecord(Map<String, Object> map);

    int countCardBatchAdminRecord(Map<String, Object> map);
    ResultData cardBatchOperate(Integer type, String batchNo, String intro, Integer operator) throws Exception;

    CardBatchVo getBatchById(Integer id);

    ResultData cardOperate(Integer type, String cardNos, String intro, Integer operator,Integer bindUser) throws Exception;

    CardBatchAdminRecordDetailVo cardBatchAdminRecordDetail(Integer id);

    BigDecimal getBalanceByCardNos(String cardNos);

    CardBatchVo getBatchDetail(String batchNo);

    /**
     * 解密卡的密码
     * add by wanghaoyang at 20180929
     * @param passWord 加密后的密码
     * @return 原始密码
     * */
    String decryptCardPassWord(String passWord);

    /**
     * 加密购物卡的密码
     * add by wanghaoyang
     * @param passWord 原始密码
     * @return 加密后的密码
     * */
    String encryptCardPassWord(String passWord);

    /**
     * 批量更新购物卡的余额(一般用于退款)
     * add by wanghaoyang at 20181009
     * */
    int batchUpdateCardBalance(List<TCardBean> tCardBeans);

    /**
     * 批量添加卡的使用记录
     * add by wanghaoyang at 20181009
     * */
    int batchAddUserCardRecord(List<TCardUserRecordBean> tCardUserRecordBeans);
    /**
     * 添加操作记录
     * @param cardAdminRecord
     * @param cardNoList
     * @throws Exception
     */
    int addOperateRecord(CardAdminRecord cardAdminRecord,List<String> cardNoList) throws Exception;

    List<CardVo> getCardByCardNos(List<String> cardNos);

    int countCardUserRecord(Map<String, Object> map);

    List<CardUserRecordManagerVo> listCardUserRecord(Map<String, Object> map);

    CardVo getCardDetail(String cardNo);

    int countCardDetail(Map<String,Object> map);

    List<CardDetailVo> listCardDetail(Map<String,Object> map);

    /**
     * 通过卡密查询卡的信息
     * add by wanghaoyang at 20180928
     * @param cardNo 卡号
     * @return 卡信息
     * */
    TCardBean getCardInfoByCardNo(String cardNo);

    /**
     * 获取某张卡在某个订单中的退款的金额总和
     * add by wanghaoyang at 20181015
     * @param orderSn 平台订单号
     * @param cardNo 购物卡号
     * */
    BigDecimal getCardRefundFee(String orderSn, String cardNo);

    /**
     * 获取某笔订单退款到卡的金额总和
     * add by wanghaoyang at 20181015
     * @param orderSn 平台订单号
     * */
    BigDecimal getOrderCardRefundFee(String orderSn);
}
