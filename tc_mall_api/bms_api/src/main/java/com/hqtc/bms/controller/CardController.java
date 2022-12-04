package com.hqtc.bms.controller;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.model.database.TCardBean;
import com.hqtc.bms.model.params.*;
import com.hqtc.bms.model.response.Result;
import com.hqtc.bms.service.CardService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by wanghaoyang on 18-9-18.
 */
@Api(value = "充值卡相关接口", tags = "充值卡相关接口")
@RestController
public class CardController {

    private Logger logger = LoggerFactory.getLogger("CardController");

    @Autowired
    @Resource(name = "CardServiceImpl")
    private CardService cardService;

    @ApiOperation(value = "获取用户所有绑定的充值卡", notes = "获取用户绑定的所有充值卡信息(需要登录)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Cookie", value = "登录后获取的cookie", required = true, dataType = "String", paramType = "header")
    })
    @RequestMapping(value = Router.ROUTER_CARD_ALL, method = {RequestMethod.GET})
    public ResultData getUserCard(@ApiIgnore @RequestAttribute(value = "userId") Integer userId){
        ResultData resultData = Tools.getThreadResultData();
        if(null == userId || userId<1){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("请尝试重新登录");
            return resultData;
        }
        resultData.setData(cardService.getAllMyCard(userId));
        return resultData;
    }


    @RequestMapping(value = Router.ROUTER_CARD_DETAIL, method = {RequestMethod.GET})
    public ResultData getCardDetail(@RequestAttribute("userId") Integer userId,
                                    @RequestParam(value = "cardNo", defaultValue = "") String cardNo,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "50")int size){
        ResultData resultData = Tools.getThreadResultData();
        if(null == userId || userId<1){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("请尝试重新登录");
            return resultData;
        }
        if(null == cardNo || cardNo.isEmpty()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("cardNo参数错误");
            return resultData;
        }
        page = page >=1?page:1;
        size = (size>=100 || size<1)?100:size;
        int pageStart = (page-1)*size;
        List<CardUseLogBean> cardUseLogBeans =  cardService.getCardRecord(cardNo, pageStart, size);
        resultData.setData(cardUseLogBeans);
        return resultData;
    }


    @RequestMapping(value = Router.ROUTER_CARD_BIND, method = {RequestMethod.POST})
    public ResultData bindCard(@RequestAttribute("userId") Integer userId,
                               @RequestParam(value = "passWord", defaultValue = "")String passWord){
        passWord = cardService.encryptCardPassWord(passWord);
        ResultData resultData = Tools.getThreadResultData();
        if(null == userId || userId<1){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("请尝试重新登录");
            return resultData;
        }
        if(null == passWord || "".equals(passWord)){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("passWord参数错误");
            return resultData;
        }
        try {
            resultData = cardService.bindCard(passWord, userId);
        }catch (TransactionTimedOutException e){
            logger.error("绑定充值卡事务超时:"+passWord);
            resultData.setError(ErrorCode.CARD_BIND_FAIL);
            resultData.setMsg("购物卡绑定失败,请重试");
        }catch (RuntimeException e){
            logger.error("插入绑定记录失败,进行绑定回滚:"+passWord);
            resultData.setError(ErrorCode.CARD_BIND_FAIL);
            resultData.setMsg("购物卡绑定失败,请重试!");
        }
        return resultData;
    }


    @RequestMapping(value = Router.ROUTER_CARD_INFO, method = {RequestMethod.POST})
    public ResultData getCardDetail(@RequestAttribute("userId") Integer userId,
                                    @RequestParam(value = "passWord", defaultValue = "") String passWord){
        passWord = cardService.encryptCardPassWord(passWord);
        ResultData resultData = Tools.getThreadResultData();
        if(null == userId || userId<1){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("请尝试重新登录");
            return resultData;
        }
        if(null == passWord || "".equals(passWord)){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("passWord参数错误");
            return resultData;
        }
        TCardBean cardInfo = cardService.getCardInfoByPassword(passWord);
        if(null == cardInfo){
            resultData.setError(ErrorCode.CARD_PASSWORD_ERROR);
            resultData.setMsg("未查找到对应购物卡");
        }else if(0 != cardInfo.getBind_user()){
            resultData.setError(ErrorCode.CARD_BINDEBD);
            resultData.setMsg("购物卡已被绑定");
        }else {
            CardSimpleInfoBean simpleInfoBean = new CardSimpleInfoBean();
            simpleInfoBean.setCardState(cardInfo.getStatus());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleInfoBean.setExpireTime(simpleDateFormat.format(cardInfo.getEtime()));
            simpleInfoBean.setBalance(cardInfo.getBalance());
            simpleInfoBean.setFaceValue(cardInfo.getFace_value());
            simpleInfoBean.setCardNo(cardInfo.getCard_no());
            resultData.setData(simpleInfoBean);
        }
        return resultData;
    }

    /**
     * 购物卡批次后台管理列表查询
     * @param map
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_BATCH_MANAGER_LIST)
    public ResultData<Result<CardBatchVo>> listCardBatch(@RequestParam Map<String,Object> map){
        ResultData<Result<CardBatchVo>> resultData= Tools.getThreadResultData();
        Result<CardBatchVo> result=new Result<>();
        int count=cardService.countCardBatch(map);
        if(count==0){
            return resultData;
        }
        List<CardBatchVo> list=cardService.listCardBatch(map);
        result.setTotal(count);
        result.setRows(list);
        resultData.setData(result);
        return resultData;
    }
    /**
     * 购物卡后台管理列表查询
     * @param map
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_MANAGER_LIST)
    public ResultData<Result<CardVo>> listCard(@RequestParam Map<String,Object> map){
        ResultData<Result<CardVo>> resultData= Tools.getThreadResultData();
        Result<CardVo> result=new Result<>();
        int count=cardService.countCard(map);
        if(count==0){
            return resultData;
        }
        List<CardVo> list=cardService.listCard(map);
        result.setTotal(count);
        result.setRows(list);
        resultData.setData(result);
        return resultData;
    }

    /**
     * 添加购物卡
     * @param params
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_BATCH_ADD_CARD)
    public ResultData addCard(@RequestParam Map<String,Object> params){
        ResultData resultData=Tools.getThreadResultData();
        try {
            int count=cardService.addCard(params);
            resultData.setMsg("成功条数："+count);
        }catch (Exception e){
            logger.error("创建购物卡异常："+e.getMessage());
            resultData.setMsg("创建购物卡失败！");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }

    /**
     * 购物卡批次操作记录列表
     * @param map
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_BATCH_ADMIN_RECORD_LIST)
    public ResultData<Result<CardAdminRecord>> listCardBatchAdminRecord(@RequestParam Map<String,Object> map){
        ResultData<Result<CardAdminRecord>> resultData= Tools.getThreadResultData();
        Result<CardAdminRecord> result=new Result<>();
        int count=cardService.countCardBatchAdminRecord(map);
        if(count==0){
            return resultData;
        }
        List<CardAdminRecord> list=cardService.listCardBatchAdminRecord(map);
        result.setTotal(count);
        result.setRows(list);
        resultData.setData(result);
        return resultData;
    }

    /**
     * 购物卡批次操作
     * @param type
     * @param intro
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_BATCH_OPERATE)
    public ResultData cardBatchOperate(@RequestParam("type") Integer type,@RequestParam("intro") String intro,@RequestParam("batchNo") String batchNo,@RequestParam("userId") Integer operator){
        ResultData resultData=Tools.getThreadResultData();
        try{
            resultData=cardService.cardBatchOperate(type,batchNo,intro,operator);
        }catch (Exception e){
            logger.error("购物卡批次管理操作异常:"+e.getMessage()+"类型："+type);
            resultData.setMsg("购物卡批次管理操作失败！");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }

    /**
     * 通过购物卡批次id获取批次详情信息
     * @param id
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_BATCH_GETBY_ID)
    public ResultData<CardBatchVo> getBatchById(@RequestParam("id") Integer id){
        ResultData resultData=Tools.getThreadResultData();
        CardBatchVo cardBatchVo=cardService.getBatchById(id);
        resultData.setData(cardBatchVo);
        return resultData;
    }

    /**
     * 购物卡操作
     * @param type
     * @param intro
     * @param cardNos
     * @param operator
     * @param bindUser
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_OPERATE)
    public ResultData cardOperate(@RequestParam("type") Integer type, @RequestParam("intro") String intro, @RequestParam("cardNos") String cardNos, @RequestParam("userId") Integer operator, @RequestParam(value = "bindUser",required = false) Integer bindUser){
        ResultData resultData=Tools.getThreadResultData();
        try{
            resultData=cardService.cardOperate(type,cardNos,intro,operator,bindUser);
        }catch (Exception e){
            logger.error("购物卡卡管理操作异常："+e.getMessage()+"类型："+type);
            resultData.setMsg("购物卡卡管理操作失败！");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }

    /**
     * 通过记录id获取购物卡批次操作记录详情信息
     * @param id
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_BATCH_ADMIN_RECORD_DETAIL)
    public ResultData cardBatchAdminRecordDetail(@RequestParam("id") Integer id){
        ResultData resultData=Tools.getThreadResultData();
        CardBatchAdminRecordDetailVo cardAdminRecordDetailVo=cardService.cardBatchAdminRecordDetail(id);
        resultData.setData(cardAdminRecordDetailVo);
        return resultData;
    }

    /**
     * 通过购物卡号获取卡内余额
     * @param cardNos
     * @return\
     */
    @PostMapping(Router.V1_BMS_CARD_GET_BALANCE_BY_CARDNOS)
    public ResultData getBalanceByCardNos(@RequestParam("cardNos") String cardNos){
        ResultData resultData=Tools.getThreadResultData();
        BigDecimal balance=cardService.getBalanceByCardNos(cardNos);
        resultData.setData(balance);
        return resultData;
    }

    /**
     * 通过批次号获取批次详情
     * @param batchNo
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_BATCH_DETAIL)
    public ResultData<CardBatchVo> getBatchDetail(@RequestParam("batchNo") String batchNo){
        ResultData<CardBatchVo> resultData=Tools.getThreadResultData();
        CardBatchVo cardBatchVo=cardService.getBatchDetail(batchNo);
        resultData.setData(cardBatchVo);
        return resultData;
    }

    /**
     * 添加管理员操作记录
     * @param cardAdminRecord
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_ADMIN_OPERATE_ADD)
    ResultData addCardAdminOperateRecord(@RequestBody CardAdminRecord cardAdminRecord) throws Exception {
        ResultData<List<CardVo>> resultData=Tools.getThreadResultData();
        int result=cardService.addOperateRecord(cardAdminRecord,cardAdminRecord.getCardNoList());
        if(result<=0){
            resultData.setMsg("添加管理员操作记录失败！");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }

    /**
     * 导出所需购物卡列表
     * @param cardNos
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_BY_CARDNOS)
    ResultData<List<CardVo>> listCardByCardNos(@RequestBody List<String> cardNos){
        ResultData<List<CardVo>> resultData=Tools.getThreadResultData();
        List<CardVo> list=cardService.getCardByCardNos(cardNos);
        if(list!=null){
            for(CardVo vo:list){
                String pwd=cardService.decryptCardPassWord(vo.getPasswd());
                vo.setPasswd(pwd);
            }
        }
        resultData.setData(list);
        return resultData;
    }
    /**
     * 导出所需购物卡列表
     * @param map
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_LIST)
    public ResultData<List<CardVo>> cardList(@RequestParam Map<String, Object> map){
        ResultData<List<CardVo>> resultData=Tools.getThreadResultData();
        List<CardVo> list = cardService.listCard(map);
        if(list!=null){
            for(CardVo vo:list){
                String pwd=cardService.decryptCardPassWord(vo.getPasswd());
                vo.setPasswd(pwd);
            }
        }
        resultData.setData(list);
        return resultData;
    }

    /**
     * 获取购物卡使用记录
     * @param map
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_USER_RECORD_LIST)
    public ResultData<Result<CardUserRecordManagerVo>> listCardUserRecord(@RequestParam Map<String,Object> map){
        ResultData<Result<CardUserRecordManagerVo>> resultData= Tools.getThreadResultData();
        Result<CardUserRecordManagerVo> result=new Result<>();
        int count=cardService.countCardUserRecord(map);
        if(count==0){
            return resultData;
        }
        List<CardUserRecordManagerVo> list=cardService.listCardUserRecord(map);
        result.setTotal(count);
        result.setRows(list);
        resultData.setData(result);
        return resultData;
    }

    /**
     * 获取购物卡详情
     * @param cardNo
     * @return
     */
    @PostMapping(Router.V1_BMS_CARD_DETAIL)
    public ResultData<CardVo> getCardDetail(@RequestParam("cardNo") String cardNo){
        ResultData<CardVo> resultData=Tools.getThreadResultData();
        CardVo cardVo=cardService.getCardDetail(cardNo);
        resultData.setData(cardVo);
        return resultData;
    }
    @PostMapping(Router.V1_BMS_CARD_DETAIL_LIST)
    public ResultData<Result<CardDetailVo>> listCardDetail(@RequestParam Map<String,Object> map){
        ResultData<Result<CardDetailVo>> resultData=Tools.getThreadResultData();
        Result<CardDetailVo> result=new Result<>();
        int count=cardService.countCardDetail(map);
        if(count==0){
            return resultData;
        }
        List<CardDetailVo> list=cardService.listCardDetail(map);
        result.setRows(list);
        result.setTotal(count);
        resultData.setData(result);
        return resultData;
    }
}
