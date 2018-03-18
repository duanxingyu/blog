package com.dxy.blog.repository;

import com.dxy.blog.entity.Blog;
import com.dxy.blog.entity.Catalog;
import com.dxy.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog,Long> {
    /**
     * 根据（用户名，标题）按照创建时间降序 分页查询用户列表（最新）
     * 由findByUserAndTitleLikeOrderByCreateTimeDesc代替，可以根据标签进行查询
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    @Deprecated
    Page<Blog> findByUserAndTitleLikeOrderByCreateTimeDesc(User user, String title, Pageable pageable);

    /**
     * 根据（标题，用户名，标签）模糊分页查询用户列表
     * @param title
     * @param user
     * @param tags
     * @param user2
     * @param pageable
     * @return
     */
    Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title, User user, String tags, User user2, Pageable pageable);

    /**
     * 根据（用户名，标题）分页查询用户列表
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据（分类）分页查询用户列表
     * @param catalog
     * @param pageable
     * @return
     */
    Page<Blog> findByCatalog(Catalog catalog, Pageable pageable);
}
