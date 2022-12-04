package com.hqtc.ums.wechat.impl;

import java.io.Serializable;

/**
 * Created by wanghaoyang on 18-11-2.
 */
public class WechatTempQrCodeParams implements Serializable{
    private int expire_seconds = 2592000;
    private String action_name = "QR_SCENE";
    private ActionInfo action_info;

    class ActionInfo{
        private SceneInfo scene;

        public ActionInfo(SceneInfo scene){
            this.scene = scene;
        }

        public SceneInfo getScene() {
            return scene;
        }

        public void setScene(SceneInfo scene) {
            this.scene = scene;
        }
    }

    class SceneInfo{
        private int scene_id;

        public SceneInfo(int sceneId){
            this.scene_id = sceneId;
        }

        public int getScene_id() {
            return scene_id;
        }

        public void setScene_id(int scene_id) {
            this.scene_id = scene_id;
        }
    }

    public int getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(int expire_seconds) {
        this.expire_seconds = expire_seconds;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public ActionInfo getAction_info() {
        return action_info;
    }

    public void setAction_info(ActionInfo action_info) {
        this.action_info = action_info;
    }
}
