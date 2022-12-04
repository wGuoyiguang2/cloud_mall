package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class AddressProvinces implements Serializable {
	public AddressResponse getBiz_address_allProvinces_query_response() {
		return biz_address_allProvinces_query_response;
	}

	public void setBiz_address_allProvinces_query_response(AddressResponse biz_address_allProvinces_query_response) {
		this.biz_address_allProvinces_query_response = biz_address_allProvinces_query_response;
	}

	private AddressResponse biz_address_allProvinces_query_response;
}
