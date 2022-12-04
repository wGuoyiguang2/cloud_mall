package com.cibnvideo.common.controller;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cibnvideo.common.entity.VenderPayment;
import com.cibnvideo.common.entity.VenderSettlement;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.omsapi.VenderPaymentApi;
import com.cibnvideo.tcmalladmin.omsapi.VenderSettlementApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.entity.Vender;
import com.cibnvideo.common.entity.Tree;
import com.cibnvideo.common.service.VenderService;
import com.cibnvideo.common.utils.R;

@Controller
@RequestMapping("/system/sysVender")
public class VenderController extends BaseController {
	private String prefix = "system/vender";
	@Autowired
	private VenderService sysVenderService;

	@Autowired
	private VenderSettlementApi venderSettlementApi;

	@Autowired
	private VenderPaymentApi venderPaymentApi;

	@Log("访问厂商页面")
	@GetMapping()
	@RequiresPermissions("system:sysVender:list")
	String vender() {
		return prefix + "/vender";
	}

	@Log("列出所有厂商")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:sysVender:list")
	public Result<Vender> list(@RequestParam Map<String, Object> params) {
		Result<Vender> result = new Result<Vender>();
		List<Vender> sysVenderList = sysVenderService.list(params);
		for(Vender v:sysVenderList){
			ResultData<VenderSettlement> resultDataSettlement = venderSettlementApi.getVenderSettlement(v.getVenderId());
			if(resultDataSettlement.getError() == ErrorCode.SUCCESS){
				VenderSettlement settlement = resultDataSettlement.getData();
				v.setVenderSettlement(settlement);
			}
			ResultData<List<VenderPayment>> resultDataPayment = venderPaymentApi.getVenderPayments(v.getVenderId());
			if(resultDataPayment.getError() == ErrorCode.SUCCESS){
				List<VenderPayment> payments = resultDataPayment.getData();
				v.setVenderPayment(payments);
			}

		}
		result.setTotal(sysVenderService.count(params));
		result.setRows(sysVenderList);
		return result;
	}

	@Log("访问添加厂商页面")
	@GetMapping("/add/{pId}")
	@RequiresPermissions("system:sysVender:add")
	String add(@PathVariable("pId") Long pId, Model model) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "总厂商");
		} else {
			model.addAttribute("pName", sysVenderService.get(pId).getName());
		}

		return  prefix + "/add";
	}

	@Log("编辑厂商信息")
	@GetMapping("/edit/{venderId}")
	@RequiresPermissions("system:sysVender:edit")
	String edit(@PathVariable("venderId") Long venderId, Model model) {
		Vender sysVender = sysVenderService.get(venderId);
		ResultData<VenderSettlement> resultDataSettlement = venderSettlementApi.getVenderSettlement(sysVender.getVenderId());
		if(resultDataSettlement.getError() == ErrorCode.SUCCESS){
			sysVender.setVenderSettlement(resultDataSettlement.getData());
		}

		ResultData<List<VenderPayment>> resultDataPayment = venderPaymentApi.getVenderPayments(sysVender.getVenderId());
		if(resultDataPayment.getError() == ErrorCode.SUCCESS){
			List<VenderPayment> venderPayment = resultDataPayment.getData();
			for(VenderPayment p:venderPayment){
				switch (p.getPayType()){
					case 1:
						model.addAttribute("wxPayment", p);
						break;
					case 2:
						model.addAttribute("zfbPayment", p);
						break;
				}
			}
		}

		model.addAttribute("sysVender", sysVender);
		if(sysVender.getParentId().equals(new Long(0l))) {
			model.addAttribute("parentVenderName", "无");
		}else {
			Vender parVender = sysVenderService.get(sysVender.getParentId());
			model.addAttribute("parentVenderName", parVender.getName());
		}
		return  prefix + "/edit";
	}

	/**
	 * 保存
	 */
	@Log("添加厂商")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:sysVender:add")
	public R save(Vender sysVender) {
		VenderSettlement venderSettlement = sysVender.getVenderSettlement();
		List<VenderPayment> venderPayment = sysVender.getVenderPayment();
		try {
			if (sysVenderService.save(sysVender) > 0) {
				venderSettlement.setVenderId(sysVender.getVenderId());
				for (VenderPayment p : venderPayment) {
					p.setVenderId(sysVender.getVenderId());
				}
				ResultData<Integer> resultDataSettlement = venderSettlementApi.addVenderSettlement(venderSettlement);
				if (resultDataSettlement.getError() == ErrorCode.SUCCESS) {
					if (resultDataSettlement.getData() > 0) {
						ResultData<Integer> resultDataPayment = venderPaymentApi.batchSaveVenderPayment(venderPayment);
						if (resultDataPayment.getError() != ErrorCode.SUCCESS || resultDataPayment.getData() <= 0) {
							return R.error("客户端支付信息设置失败！");
						}
					} else {
						return R.error("结算信息设置失败！");
					}
				} else {
					return R.error(resultDataSettlement.getMsg());
				}
				return R.ok();
			}
		}catch (DuplicateKeyException e) {
			return R.error("大客户已存在");
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@Log("修改厂商信息")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:sysVender:edit")
	public R update(Vender sysVender) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		sysVender.getVenderSettlement().setVenderId(sysVender.getVenderId());
		if (sysVenderService.update(sysVender) > 0) {
			ResultData<Integer> resultDataSettlement = venderSettlementApi.updateVenderSettlement(sysVender.getVenderSettlement());
			if(resultDataSettlement.getError() == ErrorCode.SUCCESS){
				if(resultDataSettlement.getData()>0){
					for(VenderPayment p:sysVender.getVenderPayment()){
						p.setVenderId(sysVender.getVenderId());
						params.put("venderId", p.getVenderId());
						params.put("payType", p.getPayType());
						ResultData<Integer> resultDataCount = venderPaymentApi.countVenderPayments(params);
						if(resultDataCount.getError() == ErrorCode.SUCCESS){
							int count = resultDataCount.getData();
							if(count>0) {
								ResultData<Integer> resultDataPayments = venderPaymentApi.updateVenderPayments(p);
								if(resultDataPayments.getError() == ErrorCode.SUCCESS){
									if (resultDataPayments.getData() <= 0) {
										return R.error("客户端支付信息设置失败！");
									}
								}else {
									return R.error(resultDataPayments.getMsg());
								}
							}else {
								List<VenderPayment> venderPayments = new ArrayList<VenderPayment>();
								venderPayments.add(p);
								ResultData<Integer> resultDataPayments = venderPaymentApi.batchSaveVenderPayment(venderPayments);
								if(resultDataPayments.getError() == ErrorCode.SUCCESS){
									if(resultDataPayments.getData() <= 0){
										return R.error("客户端支付信息设置失败！");
									}
								}else {
									return R.error(resultDataPayments.getMsg());
								}

							}
						}else {
							return R.error(resultDataCount.getMsg());
						}

					}

				}else {
					return R.error("结算信息设置失败！");
				}
			}else {
				return R.error(resultDataSettlement.getMsg());
			}

			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@Log("删除厂商")
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("system:sysVender:remove")
	public R remove(Long venderId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", venderId);
		if(sysVenderService.count(map)>0) {
			return R.error(1, "包含下级厂商,不允许修改");
		}
		if(sysVenderService.checkVenderHasUser(venderId)) {
			ResultData resultDataSettlement = venderSettlementApi.removeVenderSettlement(venderId);
			if(resultDataSettlement.getError() != ErrorCode.SUCCESS){
				return R.error(resultDataSettlement.getMsg());
			}
			ResultData<List<VenderPayment>> resultDataPayments = venderPaymentApi.getVenderPayments(venderId);
			if(resultDataPayments.getError() == ErrorCode.SUCCESS){
				List<VenderPayment> venderPayments = resultDataPayments.getData();
				if(venderPayments != null){
					for(VenderPayment p:venderPayments){
						ResultData<Integer> resultData = venderPaymentApi.removeVenderPayments(p);
						if(resultData.getError() != ErrorCode.SUCCESS){
							return R.error(resultData.getMsg());
						}else {
							if(resultData.getData() <= 0){
								return R.error("客户端支付信息删除失败");
							}
						}

					}
				}
			}else {
				return R.error(resultDataPayments.getMsg());
			}

			if (sysVenderService.remove(venderId) > 0) {
				return R.ok();
			}
		}else {
			return R.error(1, "厂商包含用户,不允许修改");
		}
		return R.error();
	}


	@Log("列出厂商结构")
	@GetMapping("/tree")
	@ResponseBody
	public Tree<Vender> tree() {
		Tree<Vender> tree = new Tree<Vender>();
		tree = sysVenderService.getTree();
		return tree;
	}

	@Log("列出厂商视图")
	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/venderTree";
	}

	@Log("访问增加预存款页面")
	@RequiresPermissions("system:sysVender:edit")
	@GetMapping("/balance/add/{venderId}")
	String balanceAddView(@PathVariable("venderId") Integer venderId, Model model) {
		model.addAttribute("venderId", venderId);
		return  prefix + "/balanceadd";
	}

	@Log("访问减少预存款页面")
	@RequiresPermissions("system:sysVender:edit")
	@GetMapping("/balance/reduce/{venderId}")
	String balanceReduceView(@PathVariable("venderId") Integer venderId, Model model) {
		model.addAttribute("venderId", venderId);
		return  prefix + "/balancereduce";
	}

	@Log("增加预存款")
	@RequiresPermissions("system:sysVender:edit")
	@PostMapping("/balance/add/{venderId}")
	@ResponseBody
	public R balanceAdd(@PathVariable("venderId") Integer venderId, BigDecimal balance) {
		ResultData<Integer> resultData = venderSettlementApi.balanceAdd(venderId, balance);
		if(resultData.getError() == ErrorCode.SUCCESS){
			if(resultData.getData() != null && resultData.getData() > 0){
				return R.ok();
			}else {
				return R.error("预存款增加失败");
			}
		}else {
			return R.error(resultData.getMsg());
		}
	}

	@Log("减少预存款")
	@RequiresPermissions("system:sysVender:edit")
	@PostMapping("/balance/reduce/{venderId}")
	@ResponseBody
	public R balanceReduce(@PathVariable("venderId") Integer venderId, BigDecimal balance) {
		ResultData<Integer> resultData = venderSettlementApi.balanceReduce(venderId, balance);
		if(resultData.getError() == ErrorCode.SUCCESS){
			if(resultData.getData() != null && resultData.getData() > 0){
				return R.ok();
			}else {
				return R.error("预存款扣除失败");
			}
		}else {
			return R.error(resultData.getMsg());
		}
	}

}
