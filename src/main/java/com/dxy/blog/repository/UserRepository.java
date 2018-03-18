package com.dxy.blog.repository;

import com.dxy.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 根据用户姓名分页查询用户列表
     * @param name
     * @param pageable
     * @return
     * 模糊查询
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    /**
     * 根据账号查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    List<User> findByUsernameIn(Collection<String> username);
}
