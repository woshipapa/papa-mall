package com.papa.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class WebLog {

    private String description;

    private String userName;

    private Long startTime;


    private Integer spendTime;


    private String basePath;


    private String uri;

    private String url;


    private String requestMethod;

    private String parameter;

    private Object result;

    private String ip;



}
