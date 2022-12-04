package com.hqtc.bms.service.impl;

import com.hqtc.bms.service.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wanghaoyang on 18-8-10.
 */
@Service("CommonServiceImpl")
public class CommonServiceImpl implements CommonService {

    @Override
    public boolean isPositiveNumericByComma(String str){
        Pattern pattern = Pattern.compile("\\d+(,\\d+)*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    @Override
    public boolean isNumericByComma(String str){
        return true;
    }

    @Override
    public String timeFormatToString(long mtime){
        Date date = new Date(mtime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
