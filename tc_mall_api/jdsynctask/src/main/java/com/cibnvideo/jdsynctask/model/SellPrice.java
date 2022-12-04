package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class SellPrice implements Serializable {
    public SellPriceResponse getJd_kpl_open_getsellprice_query_response() {
        return jd_kpl_open_getsellprice_query_response;
    }

    public void setJd_kpl_open_getsellprice_query_response(SellPriceResponse jd_kpl_open_getsellprice_query_response) {
        this.jd_kpl_open_getsellprice_query_response = jd_kpl_open_getsellprice_query_response;
    }

    private SellPriceResponse jd_kpl_open_getsellprice_query_response;
}
