package com.hqtc.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.hqtc.search.model.bean.BrandParamsBean;
import com.hqtc.search.model.bean.CateParamsBean;
import com.hqtc.search.model.bean.ProductBean;
import com.hqtc.search.service.CateService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by makuan on 18-8-16.
 */
@Service("CateServiceImpl")
public class CateServiceImpl implements CateService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JestClient jestClient;

    @Value("${service.config.brandLimit}")
    private int brandLimit;

    @Override
    public List<Integer> getCategory(CateParamsBean cateParams){
        List<Integer> cateList = new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchAllQuery())
                    .must(QueryBuilders.termQuery("state", 1));

        TermsBuilder termsBuilder = AggregationBuilders.terms("cat");
        if (cateParams.getCatClass() == 0){
            termsBuilder.field("cat0");
        } else if (cateParams.getCatClass() == 1){
            queryBuilder.must(QueryBuilders.termQuery("cat0", cateParams.getParentId()));
            termsBuilder.field("cat1");
        } else if (cateParams.getCatClass() == 2){
            queryBuilder.must(QueryBuilders.termQuery("cat1", cateParams.getParentId()));
            termsBuilder.field("cat2");
        } else {
            return cateList;
        }
        termsBuilder.size(1000);
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.aggregation(termsBuilder);
        searchSourceBuilder.from(0).size(0);

        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(ProductBean.INDEX_NAME + cateParams.getVenderId())
                .addType(ProductBean.TYPE).build();

        try {
            JestResult result = jestClient.execute(search);
            JsonArray buckets = result.getJsonObject().getAsJsonObject("aggregations").getAsJsonObject("cat").getAsJsonArray("buckets");
            for(JsonElement bucket : buckets){
                Map<String, Integer> map = (Map) JSON.parse(bucket.toString());
                cateList.add(map.get("key"));
            }
        }catch (IOException e){
            logger.error("search category failed, due to: " + e.getMessage());
        }catch (Exception e){
            logger.error("search category failed, due to: " + e.getMessage());
        }
        return cateList;
    }

    @Override
    public List<String> getBrandList(BrandParamsBean brandParams){
        List<String> brandList = new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchAllQuery())
                    .must(QueryBuilders.termQuery("state", 1));

        if (StringUtils.isNotBlank(brandParams.getKeyword())) {
            QueryBuilder queryBuilder1 = QueryBuilders.disMaxQuery()
                    .add(QueryBuilders.matchQuery("cat2name", brandParams.getKeyword()).analyzer("ik_smart"))
                    .add(QueryBuilders.matchQuery("name", brandParams.getKeyword()).analyzer("ik_smart"));
            queryBuilder.must(queryBuilder1);
        }

        if (brandParams.getCollectionId() != null && brandParams.getCollectionId() != 0){
            queryBuilder.must(QueryBuilders.matchQuery("collectionids", brandParams.getCollectionId()).analyzer("ik_smart"));
        }
        if (brandParams.getCat0() != null && brandParams.getCat0() != 0) {
            queryBuilder.must(QueryBuilders.termQuery("cat0", brandParams.getCat0()));
        }
        if (brandParams.getCat1() != null && brandParams.getCat1() != 0) {
            queryBuilder.must(QueryBuilders.termQuery("cat1", brandParams.getCat1()));
        }
        if (brandParams.getCat2() != null && brandParams.getCat2() != 0) {
            queryBuilder.must(QueryBuilders.termQuery("cat2", brandParams.getCat2()));
        }

        TermsBuilder termsBuilder = AggregationBuilders.terms("brand").field("brandname");

        int size = (brandParams.getLimit() != null && brandParams.getLimit() >0) ? brandParams.getLimit() : brandLimit;
        termsBuilder.size(size);
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.aggregation(termsBuilder);
        searchSourceBuilder.from(0).size(0);

        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(brandParams.INDEX_NAME + brandParams.getVenderId())
                .addType(brandParams.TYPE).build();

        try {
            JestResult result = jestClient.execute(search);
            JsonArray buckets = result.getJsonObject().getAsJsonObject("aggregations").getAsJsonObject("brand").getAsJsonArray("buckets");
            for(JsonElement bucket : buckets){
                Map<String, String> map = (Map) JSON.parse(bucket.toString());
                brandList.add(map.get("key"));
            }
        }catch (IOException e){
            logger.error("search brandname failed, due to: " + e.getMessage());
        }catch (Exception e){
            logger.error("search brandname failed, due to: " + e.getMessage());
        }
        return brandList;

    }
}
