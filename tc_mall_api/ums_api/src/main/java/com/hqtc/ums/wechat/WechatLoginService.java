package com.hqtc.ums.wechat;

import com.hqtc.ums.model.databasebean.TUserBean;
import com.hqtc.ums.model.response.AppletLoginResponseBean;

/**
 * Created by wanghaoyang on 18-10-11.
 */
public interface WechatLoginService {

    /**
     * 查询微信用户信息
     * add by wanghaoyang at 20181011
     * @param openId 微信用户唯一标识
     * @param appId 微信公众号唯一标识
     * @return 用户信息
     * */
    TUserBean getWechatUserInfo(String openId, String appId);

    /**
     * 创建微信用户
     * add by wanghaoyang at 20181011
     * @param appId 微信公众号唯一标识
     * @param openId 微信用户唯一标识
     * @param unionId 微信开放平台用户唯一标识
     * @throws RuntimeException 表示创建用户时插入数据库失败
     * */
    TUserBean createWecahtUser(String appId, String openId, String unionId);

    /**
     * 格式化返回结果
     * add by wanghaoyang at 20181011
     */
    AppletLoginResponseBean formatUserInfo(TUserBean tUserBean);

    /**
     * 更新微信用户的unionId
     * add by wanghaoyang at 20181012
     * @param id t_login_wx表的唯一id
     * @param unionId 开放平台下用户的唯一标识
     * */
    int updateWxUnionId(int id, String unionId);

    /**
     * 更新用户信息(头像和昵称)
     * add by wanghaoyang at 20181102
     * @param nickName 昵称
     * @param header 头像
     * @param userId 用户id
     *
     * @apiNote 此方法呢为异步方法
     * */
    void updateUserInfo(String nickName, String header, int userId);
}
