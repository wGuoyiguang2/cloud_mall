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
                result.setMsg("成功");
                break;
            case 1:
                result.setError(ErrorCode.FALI);
                result.setMsg("失败");
                break;
            case 2:
                result.setError(ErrorCode.PARAM_ERROR);
                result.setMsg("参数值错误（手机号或选择类型）！");
                break;
            case -1:
                result.setError(ErrorCode.PHONE_EXIST);
                result.setMsg("手机号已注册！请登录..");
                break;
            case -2:
                result.setError(ErrorCode.USER_NOT_EXIST);
                result.setMsg("该账户不存在！请注册..");
                break;
            case -3:
                result.setError(ErrorCode.PARAM_ERROR);
                result.setMsg("请求参数错误！");
                break;
            case -4:
                result.setError(ErrorCode.CODE_ERROR);
                result.setMsg("验证码错误！");
                break;
            case -5:
                result.setError(ErrorCode.FALI);
                result.setMsg("渠道错误！");
                break;
            case -6:
                result.setError(ErrorCode.PHONE_ERROR);
                result.setMsg("手机号错误！");
                break;
            case -7:
                result.setError(ErrorCode.SERVER_EXCEPTION);
                result.setMsg("系统错误！");
                break;
            case 100:
                result.setError(ErrorCode.PARAM_ERROR);
                result.setMsg("微信请求参数错误！");
                break;
            case 101:
                result.setError(ErrorCode.USER_OR_PW_ERROR);
                result.setMsg("登录失败，用户名或密码错误");
                break;
            case 102:
                result.setError(ErrorCode.WX_NOT_SCAN);
                result.setMsg("微信未扫描");
                break;
            case 103:
                result.setError(ErrorCode.LOGIN_LIMIT);
                result.setMsg("登录超过限制");
                break;
            case 104:
                result.setError(ErrorCode.USER_NOT_EXIST);
                result.setMsg("用户不存在或已被禁用");
                break;
            default:
                result.setError(ErrorCode.FALI);
                result.setMsg("未知");
                break;
        }
        return result;
    }
}
