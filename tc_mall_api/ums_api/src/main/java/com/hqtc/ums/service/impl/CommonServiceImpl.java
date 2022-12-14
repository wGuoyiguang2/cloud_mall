package com.hqtc.ums.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by wanghaoyang on 18-6-30.
 */
public class CommonServiceImpl {

    public static String channel = "90001";
    public static Type typeToken = new TypeToken<TreeMap<String, Object>>(){}.getType();
    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(
                    new TypeToken<TreeMap<String, Object>>() {
                    }.getType(),
                    new JsonDeserializer() {
                        @Override
                        public TreeMap<String, Object> deserialize(
                                JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {

                            TreeMap<String, Object> treeMap = new TreeMap<>();
                            JsonObject jsonObject = json.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                            for (Map.Entry<String, JsonElement> entry : entrySet) {
                                Object ot = entry.getValue();
                                if (ot instanceof JsonPrimitive) {
                                    treeMap.put(entry.getKey(), ((JsonPrimitive) ot).getAsString());
                                } else {
                                    treeMap.put(entry.getKey(), ot);
                                }
                            }
                            return treeMap;
                        }

                    }).create();


    public ResultData umsErrorCodeFormat(int code){
        ResultData result = new ResultData();
        result.setError(code);
        switch (code) {
            case 0:
                result.setError(ErrorCode.SUCCESS);
                result.setMsg("??????");
                break;
            case 1:
                result.setError(ErrorCode.FALI);
                result.setMsg("??????");
                break;
            case 2:
                result.setError(ErrorCode.PARAM_ERROR);
                result.setMsg("????????????????????????????????????????????????");
                break;
            case -1:
                result.setError(ErrorCode.PHONE_EXIST);
                result.setMsg("??????????????????????????????..");
                break;
            case -2:
                result.setError(ErrorCode.USER_NOT_EXIST);
                result.setMsg("??????????????????????????????..");
                break;
            case -3:
                result.setError(ErrorCode.PARAM_ERROR);
                result.setMsg("?????????????????????");
                break;
            case -4:
                result.setError(ErrorCode.CODE_ERROR);
                result.setMsg("??????????????????");
                break;
            case -5:
                result.setError(ErrorCode.FALI);
                result.setMsg("???????????????");
                break;
            case -6:
                result.setError(ErrorCode.PHONE_ERROR);
                result.setMsg("??????????????????");
                break;
            case -7:
                result.setError(ErrorCode.SERVER_EXCEPTION);
                result.setMsg("???????????????");
                break;
            case 100:
                result.setError(ErrorCode.PARAM_ERROR);
                result.setMsg("???????????????????????????");
                break;
            case 101:
                result.setError(ErrorCode.USER_OR_PW_ERROR);
                result.setMsg("???????????????????????????????????????");
                break;
            case 102:
                result.setError(ErrorCode.WX_NOT_SCAN);
                result.setMsg("???????????????");
                break;
            case 103:
                result.setError(ErrorCode.LOGIN_LIMIT);
                result.setMsg("??????????????????");
                break;
            case 104:
                result.setError(ErrorCode.USER_NOT_EXIST);
                result.setMsg("??????????????????????????????");
                break;
            default:
                result.setError(ErrorCode.FALI);
                result.setMsg("??????");
                break;
        }
        return result;
    }
}
