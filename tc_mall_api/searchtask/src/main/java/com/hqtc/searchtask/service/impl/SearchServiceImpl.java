package com.hqtc.searchtask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hqtc.searchtask.model.bean.ProductDo;
import com.hqtc.searchtask.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by makuan on 18-8-9.
 */
@Service("SearchServiceImpl")
public class SearchServiceImpl implements SearchService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final String INDEX_NAME = "tcmall_";

    private final String TYPE = "product";

    @Autowired
    private JestClient jestClient;

    @Value("${elasticsearch.settings.shards}")
    private int shards;

    @Value("${elasticsearch.settings.replicas}")
    private int replicas;

    @Value("${elasticsearch.settings.maxResultWindow}")
    private int maxResultWindow;

    @Override
    public boolean insertOrUpdateProduct(Integer venderId, ProductDo productDo) {
        createIndex(venderId);
        Index.Builder builder = new Index.Builder(productDo);
        builder.id(productDo.getSku().toString());
        builder.refresh(true);
        Index index = builder.index(INDEX_NAME + venderId).type(TYPE).build();
        try {
            JestResult result = jestClient.execute(index);
            if (result != null && !result.isSucceeded()) {
                logger.error(result.getErrorMessage());
                return false;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean productRemove(Integer venderId, Long sku) {
        Delete.Builder builder = new Delete.Builder("");
        builder.id(sku.toString());
        builder.refresh(true);
        Delete delete = builder.index(INDEX_NAME + venderId).type(TYPE).build();

        try {
            JestResult result = jestClient.execute(delete);
            if (result != null && !result.isSucceeded()) {
                logger.error("delete document failed, "  + result.getErrorMessage());
            }
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean venderRemove(Integer venderId) {
        Delete.Builder builder = new Delete.Builder("");
        builder.refresh(true);
        Delete delete = builder.index(INDEX_NAME + venderId).build();
        try {
            JestResult result = jestClient.execute(delete);
            if (result != null && result.isSucceeded()) {
                logger.error("delete index failed, "  + result.getErrorMessage());
            }
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
        return true;
    }

    @Override
    public ProductDo getProduct(Integer venderId, Long sku) {
        Get get = new Get.Builder(INDEX_NAME + venderId, sku.toString()).type(TYPE).build();
        ProductDo productDo = null;
        try {
            JestResult result = jestClient.execute(get);
            productDo = (ProductDo) result.getSourceAsObject(ProductDo.class);
        } catch (IOException e) {
            logger.error("getProduct, venderId = " + venderId + " sku = " + sku, e);
        }
        return productDo;
    }

    public void createIndex(Integer venderId){
        IndicesExists indicesExists = new IndicesExists.Builder(INDEX_NAME + venderId).build();
        try {
            JestResult jr = jestClient.execute(indicesExists);
            if (jr != null && !jr.isSucceeded()){
                Settings.Builder builder = Settings.builder();
                builder.put("number_of_shards", shards);
                builder.put("number_of_replicas", replicas);
                builder.put("max_result_window", maxResultWindow);
                CreateIndex createIndex = new CreateIndex.Builder(INDEX_NAME + venderId)
                        .settings(builder.build().getAsMap())
                        .build();
                JestResult result = jestClient.execute(createIndex);
                if (result != null && !result.isSucceeded()) {
                    logger.error("create index failed, " + result.getErrorMessage());
                } else {
                    createMapping(venderId);
                }
            }
        } catch (IOException e) {
            logger.error("indicesExists failed," + e.getMessage());
        }
    }

    public void createMapping(Integer venderId){
        JSONObject objSource = new JSONObject().fluentPut("properties", new JSONObject()
                .fluentPut("agreeprice", new JSONObject().fluentPut("type", "double"))
                .fluentPut("brandname", new JSONObject().fluentPut("type", "string").fluentPut("index", "not_analyzed"))
                .fluentPut("cat0", new JSONObject().fluentPut("type", "long"))
                .fluentPut("cat0name", new JSONObject().fluentPut("type", "string").fluentPut("analyzer", "ik_smart").fluentPut("search_analyzer", "ik_smart"))
                .fluentPut("cat1", new JSONObject().fluentPut("type", "long"))
                .fluentPut("cat1name", new JSONObject().fluentPut("type", "string").fluentPut("analyzer", "ik_smart").fluentPut("search_analyzer", "ik_smart"))
                .fluentPut("cat2", new JSONObject().fluentPut("type", "long"))
                .fluentPut("cat2name", new JSONObject().fluentPut("type", "string").fluentPut("analyzer", "ik_smart").fluentPut("search_analyzer", "ik_smart"))
                .fluentPut("cate", new JSONObject().fluentPut("type", "string").fluentPut("analyzer", "ik_smart").fluentPut("search_analyzer", "ik_smart"))
                .fluentPut("catename", new JSONObject().fluentPut("type", "string").fluentPut("analyzer", "ik_smart").fluentPut("search_analyzer", "ik_smart"))
                .fluentPut("collectionids", new JSONObject().fluentPut("type", "string").fluentPut("analyzer", "ik_smart").fluentPut("search_analyzer", "ik_smart"))
                .fluentPut("ctime", new JSONObject().fluentPut("type", "date").fluentPut("format", "strict_date_optional_time||epoch_millis"))
                .fluentPut("imagepath", new JSONObject().fluentPut("type", "string"))
                .fluentPut("jdprice", new JSONObject().fluentPut("type", "double"))
                .fluentPut("name", new JSONObject().fluentPut("type", "string").fluentPut("analyzer", "ik_smart").fluentPut("search_analyzer", "ik_smart"))
                .fluentPut("param", new JSONObject().fluentPut("type", "string").fluentPut("index", "not_analyzed"))
                .fluentPut("price", new JSONObject().fluentPut("type", "double"))
                .fluentPut("sales", new JSONObject().fluentPut("type", "long"))
                .fluentPut("sku", new JSONObject().fluentPut("type", "long"))
                .fluentPut("state", new JSONObject().fluentPut("type", "long"))
                .fluentPut("utime", new JSONObject().fluentPut("type", "date").fluentPut("format", "strict_date_optional_time||epoch_millis"))
        );
        PutMapping putMapping = new PutMapping.Builder(INDEX_NAME + venderId, TYPE, objSource.toJSONString()).build();

        try {
            JestResult result = jestClient.execute(putMapping);
            if (result != null && !result.isSucceeded()) {
                logger.error("create mapping failed " + result.getErrorMessage());
            }
        } catch (IOException e) {
            logger.error("create mapping failed, " + e.getMessage());
        }
    }

}
