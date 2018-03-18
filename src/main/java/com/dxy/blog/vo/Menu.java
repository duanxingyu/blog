package com.dxy.blog.vo;

import java.io.Serializable;

/**
 * 后台管理菜单
 */
public class Menu implements Serializable{
    private static final long serialVersionUID=1L;

    private String name;    //菜单 名称
    private String url;     //菜单URL

    public Menu(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
