package com.dxy.blog.service.serviceImp;

import com.dxy.blog.entity.*;
import com.dxy.blog.entity.es.EsBlog;
import com.dxy.blog.repository.BlogRepository;
import com.dxy.blog.service.BlogService;
import com.dxy.blog.service.EsBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BlogServiceImp implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private EsBlogService esBlogService;

    /**
     * 新建博客
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        boolean isNew = (blog.getId() == null);
        EsBlog esBlog = null;
        Blog returnBlog = blogRepository.save(blog);
        if (isNew) {
            esBlog = new EsBlog(returnBlog);
        } else {
            esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
            esBlog.update(returnBlog);

        }
        esBlogService.updateEsBlog(esBlog);
        return returnBlog;
    }

    /**
     * 删除博客
     * @param id
     */
    @Override
    public void removeBlog(Long id) {
        blogRepository.delete(id);
        EsBlog esBlog = esBlogService.getEsBlogByBlogId(id);
        esBlogService.removeEsBlog(esBlog.getId());
    }

    /**
     * 通过blog id 查询
     * @param id
     * @return
     */
    @Override
    public Blog getBlogById(Long id) {

        return blogRepository.findOne(id);
    }

    /**
     *
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable) {
//        模糊查询
        title = "%" + title + "%";
        String tags = title;
        Page<Blog> blogs = blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title, user, tags, user, pageable);
        return blogs;
    }

    @Override
    public Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable) {
        title = "%" + title + "%";
        Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
        return blogs;
    }

    @Override
    public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
        Page<Blog> blogs = blogRepository.findByCatalog(catalog, pageable);
        return blogs;
    }

    /**
     * 浏览文章数量
     *
     * @param id
     */
    @Override
    public void readingIncrease(Long id) {
        Blog blog = blogRepository.findOne(id);
        blog.setReadSize(blog.getCommentSize() + 1);
        this.saveBlog(blog);
    }

    /**
     * 添加评论
     *
     * @param blogId
     * @param commentContent
     * @return
     */
    @Override
    public Blog createComment(Long blogId, String commentContent) {
        Blog originalBlog = blogRepository.findOne(blogId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment(user, commentContent);
        originalBlog.addComment(comment);
        return originalBlog;
    }

    /**
     * 删除评论
     *
     * @param blogId
     * @param commentId
     * @return
     */
    @Override
    public Blog removeComment(Long blogId, Long commentId) {
        Blog originalBlog = blogRepository.findOne(blogId);
        originalBlog.removeComment(commentId);
        this.saveBlog(originalBlog);
        return originalBlog;
    }

    /**
     * 增加点赞
     *
     * @param blogId
     * @return
     */
    @Override
    public Blog createVote(Long blogId) {
        Blog originalBlog = blogRepository.findOne(blogId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote vote = new Vote(user);
        boolean isExist = originalBlog.addVote(vote);
        if (isExist) {
            throw new IllegalArgumentException("该用户已经点过赞");
        }
        return this.saveBlog(originalBlog);
    }

    /**
     * 取消点赞
     *
     * @param blogId
     * @param voteId
     * @return
     */
    @Override
    public Blog removeVote(Long blogId, Long voteId) {
        Blog originalBlog = blogRepository.findOne(blogId);
        originalBlog.removeVote(voteId);
        this.saveBlog(originalBlog);
        return originalBlog;
    }
}
