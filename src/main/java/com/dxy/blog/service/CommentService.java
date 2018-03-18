package com.dxy.blog.service;

import com.dxy.blog.entity.Comment;

public interface CommentService {
    /**
     * 根据id获取Comment
     * @param id
     * @return
     */
    Comment getCommentById(Long id);

    /**
     * 删除评论
     * @param id
     */
    void  removeComment(Long id);
}
