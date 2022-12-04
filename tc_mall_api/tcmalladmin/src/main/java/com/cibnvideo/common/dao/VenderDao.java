package com.cibnvideo.common.dao;

import java.util.List;
import java.util.Map;

import com.cibnvideo.tcmalladmin.model.bean.VenderInvoiceManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.VenderOrderManagerVo;
import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.common.entity.Vender;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VenderDao {

	Vender get(Long venderId);
	
	List<Vender> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(Vender vender);
	
	int update(Vender vender);
	
	int remove(Long venderId);
	
	Long[] listParentVender();
	
	int getVenderUserNumber(Long venderIds);

	List<VenderOrderManagerVo> listVenderOrderList(Map<String, Object> params);

	int countVenderOrderList(Map<String, Object> params);

    List<VenderInvoiceManagerVo> listVenderInvoice(Map<String, Object> params);

	int countVenderInvoice(Map<String, Object> params);
}
