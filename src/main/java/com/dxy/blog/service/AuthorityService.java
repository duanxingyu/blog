package com.dxy.blog.service;

import com.dxy.blog.entity.Authority;

/**
 * Authority权限服务接口
 */
public interface AuthorityService {
    /**
     * 根据id获取Authority
     */
    Authority getAuthorityById(Long id);
}
