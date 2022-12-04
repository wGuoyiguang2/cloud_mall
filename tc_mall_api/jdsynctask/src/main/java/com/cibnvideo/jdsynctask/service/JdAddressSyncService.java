package com.cibnvideo.jdsynctask.service;

public interface JdAddressSyncService {
    void syncAddressProvinces() throws Exception;
    int removeByProvinceId(Integer provinceId);
    int removeByCityId(Integer cityId);
    int removeByCountyId(Integer countyId);
    int removeByTownId(Integer townId);
}
