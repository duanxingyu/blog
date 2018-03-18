package com.dxy.blog.service.serviceImp;

import com.dxy.blog.entity.Comment;
import com.dxy.blog.repository.CommentRepository;
import com.dxy.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    /**
     * 删除评论
     *
     * @param id
     */
    @Override
    @Transactional
    public void removeComment(Long id) {
        commentRepository.delete(id);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findOne(id);
    }
}
