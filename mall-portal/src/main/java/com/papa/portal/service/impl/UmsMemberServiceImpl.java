package com.papa.portal.service.impl;

import com.papa.mbg.model.UmsMember;
import com.papa.portal.domain.MemberDetails;
import com.papa.portal.service.UmsMemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UmsMemberServiceImpl implements UmsMemberService {
    @Override
    public UmsMember getCurrentMember() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        MemberDetails userDetails =(MemberDetails)  authentication.getPrincipal();
        return userDetails.getUmsMember();
    }
}
