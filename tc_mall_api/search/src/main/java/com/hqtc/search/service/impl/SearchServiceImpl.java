package com.hqtc.search.service.impl;

import com.hqtc.search.model.bean.*;
import com.hqtc.search.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by makuan on 18-8-9.
 */
@Service("SearchServiceImpl")
public class SearchServiceImpl implements SearchService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${service.config.pageSize}")
    private int defaultPageSize;

    @Autowired
    private JestClient jestClient;

    /**
     * 分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageBean paginator(Integer pageNum, Integer pageSize){
        PageBean pageBean = new PageBean();
        pageNum = (pageNum == null || pageNum <= 0) ? 0: pageNum -1;
        pageSize = (pageSize == null || pageSize <= 0) ? defaultPageSize: pageSize;
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        return pageBean;
    }

    @Override
    public ProductListBean fuzzySearch(SearchParamsBean searchParams){
        ProductListBean productListBean = new ProductListBean();
        PageBean page = paginator(searchParams.getPageNum(), searchParams.getPageSize());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchAllQuery())
                    .must(QueryBuilders.termQuery("state", searchParams.getState()));
        if (StringUtils.isNotBlank(searchParams.getKeyword())){
            QueryBuilder queryBuilder1 = QueryBuilders.disMaxQuery()
                    .add(QueryBuilders.matchQuery("cat2name", searchParams.getKeyword()).analyzer("ik_smart"))
                    .add(QueryBuilders.matchQuery("name", searchParams.getKeyword()).analyzer("ik_smart"));
            queryBuilder.must(queryBuilder1);
        }
        if (StringUtils.isNotBlank(searchParams.getBrandName())){
            queryBuilder.must(QueryBuilders.matchPhraseQuery("brandname", searchParams.getBrandName()));
        }
        if (searchParams.getCat0() != null && searchParams.getCat0() != 0){
            queryBuilder.must(QueryBuilders.termQuery("cat0", searchParams.getCat0()));
        }
        if (searchParams.getCat1() != null && searchParams.getCat1() != 0){
            queryBuilder.must(QueryBuilders.termQuery("cat1", searchParams.getCat1()));
        }
        if (searchParams.getCat2() != null && searchParams.getCat2() != 0){
            queryBuilder.must(QueryBuilders.termQuery("cat2", searchParams.getCat2()));
        }
        if (searchParams.getCollectionId() != null && searchParams.getCollectionId() != 0){
            queryBuilder.must(QueryBuilders.matchQuery("collectionids", searchParams.getCollectionId()).analyzer("ik_smart"));
        }

        searchSourceBuilder.query(queryBuilder);
        //排序
        if (searchParams.getIsPrice() != null){
            searchSourceBuilder.sort("price", searchParams.getIsPrice() == 0 ? SortOrder.ASC : SortOrder.DESC);
        }
        if (searchParams.getIsNew() != null){
            searchSourceBuilder.sort("utime", searchParams.getIsNew() == 0 ? SortOrder.ASC : SortOrder.DESC);
        }
        if (searchParams.getIsSales() != null){
            searchSourceBuilder.sort("sales", searchParams.getIsSales() == 0 ? SortOrder.ASC : SortOrder.DESC);
        }

        if (StringUtils.isNotBlank(searchParams.getKeyword())){
            searchSourceBuilder.sort("_score", SortOrder.DESC);
        }

        searchSourceBuilder.from(page.getPageNum() * page.getPageSize()).size(page.getPageSize());
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(ProductBean.INDEX_NAME + searchParams.getVenderId())
                .addType(ProductBean.TYPE).build();

        return jestSearch(search, productListBean);
    }

    @Override
    public ProductListBean idsQuery(SkuSearchParamsBean skuSearchParams){
        ProductListBean productListBean = new ProductListBean();
        PageBean page = paginator(skuSearchParams.getPageNum(), skuSearchParams.getPageSize());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (skuSearchParams.getState() != null) {
            queryBuilder.must(QueryBuilders.termQuery("state", skuSearchParams.getState()));
        }
        if (skuSearchParams.getBrandName() != null){
            queryBuilder.must(QueryBuilders.matchPhraseQuery("brandname", skuSearchParams.getBrandName()));
        }
        QueryBuilder idsQueryBuilder = QueryBuilders.idsQuery();
        List<String> skuList = Arrays.asList(skuSearchParams.getSkus().split(","));
        ((IdsQueryBuilder) idsQueryBuilder).addIds(skuList);
        queryBuilder.must(idsQueryBuilder);
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(page.getPageNum() * page.getPageSize()).size(page.getPageSize());
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(ProductBean.INDEX_NAME + skuSearchParams.getVenderId())
                .addType(ProductBean.TYPE).build();

        return jestSearch(search, productListBean);
    }

    public ProductListBean jestSearch(Search search, ProductListBean productListBean){
        try {
            JestResult result = jestClient.execute(search);
            productListBean.setDataList(result.getSourceAsObjectList(ProductBean.class));
            productListBean.setTotal(result.getJsonObject().get("hits").getAsJsonObject().get("total").getAsInt());
        }catch (IOException e){
            logger.error("es search failed, due to: " + e.getMessage());
        }catch (Exception e){
            logger.error("es search failed, due to: " + e.getMessage());
        }
        return productListBean;
    }
}
