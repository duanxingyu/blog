package com.dxy.blog.service.serviceImp;

import com.dxy.blog.entity.Authority;
import com.dxy.blog.repository.AuthorityRepository;
import com.dxy.blog.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImp implements AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority getAuthorityById(Long id){
        return authorityRepository.findOne(id);
    }
}
