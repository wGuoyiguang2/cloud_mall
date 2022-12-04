package com.cibnvideo.jdsynctask.service.impl;

import com.cibnvideo.jdsynctask.dao.CityDao;
import com.cibnvideo.jdsynctask.dao.CountyDao;
import com.cibnvideo.jdsynctask.dao.ProvinceDao;
import com.cibnvideo.jdsynctask.dao.TownDao;
import com.cibnvideo.jdsynctask.model.City;
import com.cibnvideo.jdsynctask.model.County;
import com.cibnvideo.jdsynctask.model.Province;
import com.cibnvideo.jdsynctask.model.Town;
import com.cibnvideo.jdsynctask.service.JdAddressSyncService;
import com.cibnvideo.jdsynctask.service.JdGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class JdAddressSyncServiceImpl implements JdAddressSyncService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private CountyDao countyDao;
    @Autowired
    private TownDao townDao;

    @Autowired
    private JdGoodsService jdGoodsService;

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void syncAddressProvinces() throws Exception{
        if(lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                Map<String, Integer> provinces = jdGoodsService.getAddressProvinces();
                if (provinces != null) {
                    saveProvinces(provinces);
                    provinces.forEach((provinceName, provinceId) -> {
                        Map<String, Integer> citys = jdGoodsService.getAddressCityss(provinceId);
                        if (citys != null) {
                            saveCitys(provinceId, citys);
                            citys.forEach((cityName, cityId) -> {
                                Map<String, Integer> countys = jdGoodsService.getAddressCountys(cityId);
                                if (countys != null) {
                                    saveCountys(cityId, provinceId, countys);
                                    countys.forEach((countyName, countyId) -> {
                                        Map<String, Integer> towns = jdGoodsService.getAddressTowns(countyId);
                                        if (towns != null) {
                                            saveTowns(countyId, cityId, provinceId, towns);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            } catch (Exception e) {
                logger.error("syncAddress error: " + e.getMessage());
            } finally {
                lock.unlock();
            }
        } else {
            logger.error("syncAddress tryLock failed");
            throw new Exception("此任务正在执行");
        }
    }

    private void saveProvinces(Map<String, Integer> provinces) {
        if (provinces == null) {
            return;
        }
        Province province = new Province();
        for (String key : provinces.keySet()) {
            province.setName(key);
            province.setProvinceId(provinces.get(key));
            Province provinceSave = provinceDao.get(provinces.get(key));
            if (provinceSave != null) {
                if (!provinceSave.equals(province)) {
                    provinceDao.update(province);
                }
            } else {
                provinceDao.save(province);
            }

        }
    }

    @Override
    @Transactional
    public int removeByProvinceId(Integer provinceId) {
        int resultTown = townDao.removeByProvinceId(provinceId);
        int resultCounty = countyDao.removeByProvinceId(provinceId);
        int resultCity = cityDao.removeByProvinceId(provinceId);
        int resultProvince = provinceDao.remove(provinceId);
        if (resultTown == 1 && resultCounty == 1 && resultCity == 1 && resultProvince == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    private void saveCitys(Integer provinceId, Map<String, Integer> citys) {
        if (citys == null) {
            return;
        }
        City city = new City();
        for (String key : citys.keySet()) {
            city.setName(key);
            city.setCityId(citys.get(key));
            city.setProvinceId(provinceId);
            City citySave = cityDao.get(citys.get(key));
            if (citySave != null) {
                if (!citySave.equals(city)) {
                    cityDao.update(city);
                }
            } else {
                cityDao.save(city);
            }
        }
    }

    @Override
    @Transactional
    public int removeByCityId(Integer cityId) {
        int resultTown = townDao.removeByCityId(cityId);
        int resultCounty = countyDao.removeByCityId(cityId);
        int resultCity = cityDao.remove(cityId);
        if (resultTown == 1 && resultCounty == 1 && resultCity == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    private void saveCountys(Integer cityId, Integer provinceId, Map<String, Integer> countys) {
        if (countys == null) {
            return;
        }
        County county = new County();
        for (String key : countys.keySet()) {
            county.setName(key);
            county.setCountyId(countys.get(key));
            county.setCityId(cityId);
            county.setProvinceId(provinceId);
            County countySave = countyDao.get(countys.get(key));
            if (countySave != null) {
                if (!countySave.equals(county)) {
                    countyDao.update(county);
                }
            } else {
                countyDao.save(county);
            }
        }
    }

    @Override
    @Transactional
    public int removeByCountyId(Integer countyId) {
        int resultTown = townDao.removeByCountyId(countyId);
        int resultCounty = countyDao.remove(countyId);
        if (resultTown == 1 && resultCounty == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    private void saveTowns(Integer countyId, Integer cityId, Integer provinceId, Map<String, Integer> towns) {
        if (towns == null) {
            return;
        }
        Town town = new Town();
        for (String key : towns.keySet()) {
            town.setName(key);
            town.setTownId(towns.get(key));
            town.setCountyId(countyId);
            town.setCityId(cityId);
            town.setProvinceId(provinceId);
            Town townSave = townDao.get(towns.get(key));
            if (townSave != null) {
                if (!townSave.equals(town)) {
                    townDao.update(town);
                }
            } else {
                townDao.save(town);
            }
        }
    }

    @Override
    @Transactional
    public int removeByTownId(Integer townId) {
        int resultTown = townDao.remove(townId);
        if (resultTown == 1) {
            return 1;
        } else {
            return 0;
        }
    }
}
