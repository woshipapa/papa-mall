package com.papa.portal.service;

import com.papa.mbg.model.UmsMember;
import org.springframework.stereotype.Service;

@Service
public interface UmsMemberService {
    public UmsMember getCurrentMember();
}
