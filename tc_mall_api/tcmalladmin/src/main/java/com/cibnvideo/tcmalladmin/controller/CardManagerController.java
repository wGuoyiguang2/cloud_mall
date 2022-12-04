package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.entity.User;
import com.cibnvideo.common.service.UserService;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.tcmalladmin.bmsapi.CardApi;
import com.cibnvideo.tcmalladmin.model.bean.*;
import com.cibnvideo.tcmalladmin.service.CardService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.ExcelPOIUtil;
import com.hqtc.common.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/9/18 15:39
 */
@Controller
public class CardManagerController extends BaseController{

    @Autowired
    private CardApi cardApi;
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;
    @RequiresPermissions("admin:cardbatch:list")
    @GetMapping(Router.V1_ADMIN_CARD_BATCH_CARD_LIST)
    public String cardsByBatchHtml(Model model,@RequestParam("batchNo") String batchNo){
        model.addAttribute("batchNo",batchNo);
        //查询批次详情
        ResultData<CardBatchVo> resultData=cardApi.getCardBatchDetail(batchNo);
        CardBatchVo cardBatchVo=resultData.getData();
        if(cardBatchVo==null){
            cardBatchVo=new CardBatchVo();
        }else {
            cardBatchVo.setOperator(this.getAdminNameById(cardBatchVo.getOperator()));
        }
        model.addAttribute("bean",cardBatchVo);
        return "tcmalladmin/cardmanager/cardBatchCardList";
    }
    @RequiresPermissions("admin:card:list")
    @GetMapping(Router.ADMIN_CARD_HTML)
    public String cardHtml(){
        return "tcmalladmin/cardmanager/card";
    }
    @RequiresPermissions("admin:card:list")
    @GetMapping(Router.ADMIN_CARD_LIST)
    @ResponseBody
    public Result cardList(@RequestParam Map<String, Object> params){
        Result<CardVo> result =new Result<>();
        ResultData<Result<CardVo>> resultData= cardApi.cardList(params);
        if(resultData.getError()!=ErrorCode.SUCCESS){
            return result;
        }
        result = resultData.getData();
        return result;
    }
    @RequiresPermissions("admin:cardbatch:list")
    @GetMapping(Router.ADMIN_CARD_BATCH_HTML)
    public String cardBatchHtml(){
        return "tcmalladmin/cardmanager/cardbatch";
    }
    @RequiresPermissions("admin:cardbatch:list")
    @GetMapping(Router.ADMIN_CARD_BATCH_LIST)
    @ResponseBody
    public Result cardBatchList(@RequestParam Map<String, Object> params){
        Result<CardBatchVo> result =new Result<>();
        ResultData<Result<CardBatchVo>> resultData=cardApi.cardBatchList(params);
        if(resultData.getError()!=ErrorCode.SUCCESS){
            return result;
        }
        result = resultData.getData();
        return result;
    }

    @RequiresPermissions("admin:card:add")
    @GetMapping(Router.V1_ADMIN_CARD_ADD_HTML)
    public String addCardHtml(){
        return "tcmalladmin/cardmanager/addCard";
    }
    @RequiresPermissions("admin:card:add")
    @PostMapping(Router.V1_ADMIN_CARD_BATCH_ADD_CARD)
    @ResponseBody
    public ResultData addCard(@RequestParam Map<String,Object> params){
        ResultData resultData= Tools.getThreadResultData();
        Long userId=this.getUserId();
        params.put("operator",String.valueOf(userId));
        resultData=cardApi.addCard(params);
        return resultData;
    }
    @RequiresPermissions("admin:cardadminrecord:list")
    @GetMapping(Router.V1_ADMIN_CARD_BATCH_RECORD_HTML)
    public String cardAdminRecordHtml(){
        return "tcmalladmin/cardmanager/cardAdminRecord";
    }
    @RequiresPermissions("admin:cardadminrecord:list")
    @GetMapping(Router.V1_BMS_CARD_BATCH_RECORD_LIST)
    @ResponseBody
    public Result cardBatchAdminRecordList(@RequestParam Map<String, Object> params){
        Result<CardAdminRecord> result=new Result<>();
        ResultData<Result<CardAdminRecord>> resultData=cardApi.cardBatchRecordList(params);
        if(resultData.getError()!= ErrorCode.SUCCESS){
            return result;
        }
        result = resultData.getData();
        if(result!=null){
            List<CardAdminRecord> recordList=result.getRows();
            for (CardAdminRecord r:recordList) {
                r.setOperator(this.getAdminNameById(r.getOperator()));
            }
        }
        return result;
    }

    /**
     * 批次激活
     * @param intro
     * @param batchNo
     * @return
     */
    @RequiresPermissions("admin:cardbatch:start")
    @PostMapping(Router.V1_ADMIN_CARD_BATCH_START)
    @ResponseBody
    public ResultData cardBatchStart(@RequestParam("intro") String intro,@RequestParam("batchNo") String batchNo){
        Integer userId=this.getUserId().intValue();
        int type=2;
        ResultData resultData=cardApi.cardBatchOperate(type,batchNo,intro,userId);
        return resultData;
    }

    /**
     * 批次暂停
     * @param intro
     * @param batchNo
     * @return
     */
    @RequiresPermissions("admin:cardbatch:pause")
    @PostMapping(Router.V1_ADMIN_CARD_BATCH_PAUSE)
    @ResponseBody
    public ResultData cardBatchPause(@RequestParam("intro") String intro,@RequestParam("batchNo") String batchNo){
        Integer userId=this.getUserId().intValue();
        int type=4;
        ResultData resultData=cardApi.cardBatchOperate(type,batchNo,intro,userId);
        return resultData;
    }

    /**
     * 批次导出
     * @param intro
     * @param batchNo
     * @return
     */
    @RequiresPermissions("admin:cardbatch:export")
    @GetMapping(Router.V1_ADMIN_CARD_BATCH_EXPORT)
    @ResponseBody
    public ResultData cardBatchExport(HttpServletRequest request, HttpServletResponse response,@RequestParam("intro") String intro, @RequestParam("batchNo") String batchNo) throws Exception {
        ResultData resultData=Tools.getThreadResultData();
        Integer userId=this.getUserId().intValue();
        resultData=cardService.cardBatchExport(request,response,userId,intro,batchNo);
        return resultData;
    }

    /**
     * 批次废弃
     * @param intro
     * @param batchNo
     * @return
     */
    @RequiresPermissions("admin:cardbatch:abandon")
    @PostMapping(Router.V1_ADMIN_CARD_BATCH_ABANDON)
    @ResponseBody
    public ResultData cardBatchAbandon(@RequestParam("intro") String intro,@RequestParam("batchNo") String batchNo){
        Integer userId=this.getUserId().intValue();
        int type=5;
        ResultData resultData=cardApi.cardBatchOperate(type,batchNo,intro,userId);
        return resultData;
    }
    /**
     * 返回操作页面
     * @param id
     * @return
     */
    @RequiresPermissions("admin:cardbatch:operate")
    @GetMapping(Router.V1_ADMIN_CARD_BATCH_OPERATE_HTML)
    public String cardOperateHtml(Model model,@RequestParam("id") Integer id){
        ResultData<CardBatchVo> resultData=cardApi.getByBatchId(id);
        CardBatchVo bean = resultData.getData();
        if(bean==null) {
            bean=new CardBatchVo();
        }else {
            bean.setOperator(this.getAdminNameById(bean.getOperator()));
        }
        model.addAttribute("bean",bean);
        return "tcmalladmin/cardmanager/cardBatchOperate";
    }


    @RequiresPermissions("admin:card:bind")
    @GetMapping(Router.V1_ADMIN_CARD_BIND_HTML)
    public String cardBindHtml(Model model,@RequestParam("cardNos") String cardNos){
        BigDecimal balance=this.getBalanceByCardNos(cardNos);
        model.addAttribute("balance",balance==null?new BigDecimal(0):balance);
        model.addAttribute("cardNos",cardNos);
        return "tcmalladmin/cardmanager/cardBind";
    }
    /**
     * 购物卡绑定
     * @param intro
     * @param cardNos
     * @param bindUser
     * @return
     */
    @RequiresPermissions("admin:card:bind")
    @PostMapping(Router.V1_ADMIN_CARD_BIND)
    @ResponseBody
    public ResultData cardBind(@RequestParam("intro") String intro,@RequestParam("cardNos") String cardNos,@RequestParam("bindUser") Integer bindUser){
        Integer userId=this.getUserId().intValue();
        int type=3;
        ResultData resultData=cardApi.cardOperate(type,cardNos,intro,userId,bindUser);
        return resultData;
    }

    @RequiresPermissions("admin:card:export")
    @GetMapping(Router.V1_ADMIN_CARD_EXPORT_HTML)
    public String cardExport(Model model,@RequestParam("cardNos") String cardNos){
        BigDecimal balance=this.getBalanceByCardNos(cardNos);
        model.addAttribute("balance",balance==null?new BigDecimal(0):balance);
        model.addAttribute("cardNos",cardNos);
        return "tcmalladmin/cardmanager/cardExport";
    }
    /**
     * 购物卡导出
     * @param intro
     * @param cardNos
     * @return
     */
    @RequiresPermissions("admin:card:export")
    @GetMapping(Router.V1_ADMIN_CARD_EXPORT)
    @ResponseBody
    public ResultData cardExport(HttpServletRequest request,HttpServletResponse response,@RequestParam("intro") String intro,@RequestParam("cardNos") String cardNos) throws Exception {
        Integer userId=this.getUserId().intValue();
        ResultData resultData=cardService.cardExport(request,response,userId,intro,cardNos);
        return resultData;
    }

    @RequiresPermissions("admin:card:abandon")
    @GetMapping(Router.V1_ADMIN_CARD_ABANDON_HTML)
    public String cardAbandonHtml(Model model,@RequestParam("cardNos") String cardNos){
        BigDecimal balance=this.getBalanceByCardNos(cardNos);
        model.addAttribute("balance",balance==null?new BigDecimal(0):balance);
        model.addAttribute("cardNos",cardNos);
        return "tcmalladmin/cardmanager/cardAbandon";
    }
    /**
     * 购物卡废弃
     * @param intro
     * @param cardNos
     * @return
     */
    @RequiresPermissions("admin:card:abandon")
    @PostMapping(Router.V1_ADMIN_CARD_ABANDON)
    @ResponseBody
    public ResultData cardAbandon(@RequestParam("intro") String intro,@RequestParam("cardNos") String cardNos){
        Integer userId=this.getUserId().intValue();
        int type=5;
        ResultData resultData=cardApi.cardOperate(type,cardNos,intro,userId,null);
        return resultData;
    }

    @RequiresPermissions("admin:card:start")
    @GetMapping(Router.V1_ADMIN_CARD_START_HTML)
    public String cardStartHtml(Model model,@RequestParam("cardNos") String cardNos){
        BigDecimal balance=this.getBalanceByCardNos(cardNos);
        model.addAttribute("balance",balance==null?new BigDecimal(0):balance);
        model.addAttribute("cardNos",cardNos);
        return "tcmalladmin/cardmanager/cardStart";
    }
    /**
     * 购物卡激活
     * @param intro
     * @param cardNos
     * @return
     */
    @RequiresPermissions("admin:card:start")
    @PostMapping(Router.V1_ADMIN_CARD_START)
    @ResponseBody
    public ResultData cardStart(@RequestParam("intro") String intro,@RequestParam("cardNos") String cardNos){
        Integer userId=this.getUserId().intValue();
        int type=2;
        ResultData resultData=cardApi.cardOperate(type,cardNos,intro,userId,null);
        return resultData;
    }

    /**
     * 操作记录详情
     * @param model
     * @param id
     * @return
     */
    @RequiresPermissions("admin:cardadminrecord:detail")
    @GetMapping(Router.V1_ADMIN_CARD_BATCH_ADMIN_RECORD_DETAIL_HTML)
    public String cardBatchAdminRecordDetail(Model model,@RequestParam("id") Integer id){
        ResultData<CardBatchAdminRecordDetailVo> resultData=Tools.getThreadResultData();
        resultData=cardApi.getCardBatchAdminRecordDetail(id);
        CardBatchAdminRecordDetailVo bean=resultData.getData();
        if(bean==null){
            bean=new CardBatchAdminRecordDetailVo();
        }else{
            bean.setCreateUser(this.getAdminNameById(bean.getCreateUser()));
            bean.setOperator(this.getAdminNameById(bean.getOperator()));
        }
        model.addAttribute("bean",bean);
        return "tcmalladmin/cardmanager/batchRecordDetail";
    }

    /**
     * 通过购物卡号获取总余额
     * @param cardNos
     * @return
     */
    private BigDecimal getBalanceByCardNos(String cardNos){
        if(StringUtils.isEmpty(cardNos)){
            return new BigDecimal(0);
        }
        ResultData<BigDecimal> resultData=cardApi.getBalanceByCardNos(cardNos);
        return resultData.getData();
    }

    /**
     * 通过管理员id获取管理员姓名
     * @param userId
     * @return
     */
    private String getAdminNameById(String userId){
        if(StringUtils.isEmpty(userId)){
            return "";
        }
        String name="";
        User user=userService.get(Long.valueOf(userId));
        if(user!=null){
            name=user.getName();
        }
        return name;
    }

    /**
     * 购物卡使用详情
     * @param model
     * @param batchNo
     * @return
     */
    @RequiresPermissions("admin:card:detail")
    @GetMapping(Router.V1_ADMIN_CARD_DETAIL)
    public String cardDetail(Model model,@RequestParam("batchNo") String batchNo,@RequestParam("cardNo") String cardNo){
        model.addAttribute("cardNo",cardNo);
        //查询批次详情
        ResultData<CardBatchVo> resultData=cardApi.getCardBatchDetail(batchNo);
        CardBatchVo cardBatchVo=resultData.getData();
        if(cardBatchVo==null){
            cardBatchVo=new CardBatchVo();
        }else {
            cardBatchVo.setOperator(this.getAdminNameById(cardBatchVo.getOperator()));
        }
        model.addAttribute("bean",cardBatchVo);
        //查询购物卡详情
        ResultData<CardVo> resultData2=cardApi.getCardDetail(cardNo);
        CardVo cardVo=resultData2.getData();
        if(cardVo==null){
            cardVo=new CardVo();
        }
        model.addAttribute("bean2",cardVo);
        return "tcmalladmin/cardmanager/cardDetail";
    }

    /**
     * 购物卡使用详情列表
     * @param map
     * @return
     */
    @RequiresPermissions("admin:card:detail")
    @GetMapping(Router.V1_ADMIN_CARD_DETAIL_LIST)
    @ResponseBody
    public Result<CardDetailVo> cardDetailList(@RequestParam Map<String,Object> map){
        Result<CardDetailVo> result=new Result<>();
        ResultData<Result<CardDetailVo>> resultData=cardApi.cardDetailList(map);
        if(resultData.getError()!=ErrorCode.SUCCESS){
            return result;
        }
        result = resultData.getData();
        return result;
    }

    /**
     * 购物卡使用记录
     * @return
     */
    @RequiresPermissions("admin:carduserrecord:list")
    @GetMapping(Router.V1_ADMIN_CARD_USER_RECORD_HTML)
    public String cardUserRecordHtml(){
        return "tcmalladmin/cardmanager/carduserrecord";
    }
    @RequiresPermissions("admin:carduserrecord:list")
    @GetMapping(Router.V1_ADMIN_CARD_USER_RECORD_LIST)
    @ResponseBody
    public Result<CardUserRecordManagerVo> cardUserRecordList(@RequestParam Map<String,Object> map){
        Result<CardUserRecordManagerVo> result=new Result<>();
        ResultData<Result<CardUserRecordManagerVo>> resultData=cardApi.listCardUserRecord(map);
        if(resultData.getError()!=ErrorCode.SUCCESS){
            return result;
        }
        result = resultData.getData();
        return result;
    }
}
