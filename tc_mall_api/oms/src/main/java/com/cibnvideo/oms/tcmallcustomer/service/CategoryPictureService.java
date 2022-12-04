package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.CategoryPicture;

import java.util.List;
import java.util.Map;

public interface CategoryPictureService {
    List<CategoryPicture> list(Map<String,Object> map);

    CategoryPicture get(Integer id);

    CategoryPicture getByCatId(Integer venderId, Integer catId);

    List<CategoryPicture> getByCatIds(Integer venderId, List<Integer> catIds);

    int count(Map<String,Object> map);

    int save(CategoryPicture categoryPicture);

    int update(CategoryPicture categoryPicture);

    int remove(Integer id);
}
