package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class AddressCitys implements Serializable {
	public AddressResponse getBiz_address_citysByProvinceId_query_response() {
		return biz_address_citysByProvinceId_query_response;
	}

	public void setBiz_address_citysByProvinceId_query_response(
			AddressResponse biz_address_citysByProvinceId_query_response) {
		this.biz_address_citysByProvinceId_query_response = biz_address_citysByProvinceId_query_response;
	}

	private AddressResponse biz_address_citysByProvinceId_query_response;
}
