package com.dz.utlis.bean;

import java.io.Serializable;

/**
 *creat_user: zhengzaihong
 *email:1096877329@qq.com
 *creat_date: 2018/5/27
 *creat_time: 12:09
 *describe: TODO
 **/
public class StorageInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 路径
     */
    public String path;
    /**
     * 挂载状态
     */
    public String state;
    /**
     * 是否移除
     */
    public boolean isRemoveable;

    public StorageInfo(String path) {
        this.path = path;
    }

    public boolean isMounted() {
        return "mounted".equals(state);
    }

    @Override
    public String toString() {
        return "StorageInfo{" +
                "path='" + path + '\'' +
                ", state='" + state + '\'' +
                ", isRemoveable=" + isRemoveable +
                '}';
    }
}