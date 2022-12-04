package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.CategoryPicture;
import com.cibnvideo.oms.tcmallcustomer.dao.CategoryPictureDao;
import com.cibnvideo.oms.tcmallcustomer.service.CategoryPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryPictureServiceImpl implements CategoryPictureService {

    @Autowired
    CategoryPictureDao categoryPictureDao;
    @Override
    public List<CategoryPicture> list(Map<String, Object> map) {
        return categoryPictureDao.list(map);
    }

    @Override
    public CategoryPicture get(Integer id) {
        return categoryPictureDao.get(id);
    }

    @Override
    public CategoryPicture getByCatId(Integer venderId, Integer catId) {
        return categoryPictureDao.getByCatId(venderId, catId);
    }

    @Override
    public List<CategoryPicture> getByCatIds(Integer venderId, List<Integer> catIds) {
        return categoryPictureDao.getByCatIds(venderId, catIds);
    }

    @Override
    public int count(Map<String, Object> map) {
        return categoryPictureDao.count(map);
    }

    @Override
    public int save(CategoryPicture categoryPicture) {
        return categoryPictureDao.save(categoryPicture);
    }

    @Override
    public int update(CategoryPicture categoryPicture) {
        return categoryPictureDao.update(categoryPicture);
    }

    @Override
    public int remove(Integer id) {
        return categoryPictureDao.remove(id);
    }
}
