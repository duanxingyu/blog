package com.dxy.blog.service;

import com.dxy.blog.entity.User;
import com.dxy.blog.entity.es.EsBlog;
import com.dxy.blog.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EsBlogService {
    /**
     * 删除Blog
     * @param id
     */
    void removeEsBlog(String id);

    /**
     * 更新EsBlog
     * @param esBlog
     * @return
     */
    EsBlog updateEsBlog(EsBlog esBlog);

    /**
     * 根据id获取Blog
     * @param blogId
     * @return
     */
    EsBlog getEsBlogByBlogId(Long blogId);

    /**
     * 最新 博客列表，分页
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable);

    /**
     * 最热 博客列表，分页
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable);

    /**
     * 博客列表，分页
     * @param pageable
     * @return
     */
    Page<EsBlog> listEsBlogs(Pageable pageable);

    /**
     * 最热前五条博客
     * @return
     */
    List<EsBlog>listTop5HotestEsBlogs();

    /**
     * 最热前三十标签
     * @return
     */
    List<TagVO> listTop30Tags();

    /**
     * 最热前12用户
     * @return
     */
    List<User>listTop12Users();

    /**
     * 最新前5
     * @return
     */
    List<EsBlog> listTop5NewestEsBlogs();
}
