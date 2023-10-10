package com.papa.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

public interface DynamicSecurityService {

    public Map<String, ConfigAttribute> loadDataSource();


}
