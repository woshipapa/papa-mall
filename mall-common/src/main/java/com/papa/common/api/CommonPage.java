package com.papa.common.api;

import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 封装分页查询得到的数据
 */
public class CommonPage<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;

    private List<T> data;
    public CommonPage(){}
    public CommonPage(Integer pageNum, Integer pageSize, Integer totalPage, Long total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.total = total;
    }
    public static <T> CommonPage<T> restPage(List<T> list){
        CommonPage commonPage=new CommonPage();
        PageInfo pageInfo=new PageInfo(list);
        commonPage.setPageNum(pageInfo.getPageNum());
        commonPage.setPageSize(pageInfo.getPageSize());
        commonPage.setTotalPage(pageInfo.getPages());
        commonPage.setTotal(pageInfo.getTotal());
        commonPage.setData(list);
        return commonPage;
    }
    public static <T> CommonPage<T> restPage(Page<T> pageInfo){
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageInfo.getNumber());
        commonPage.setPageSize(pageInfo.getSize());
        commonPage.setTotalPage(pageInfo.getTotalPages());
        commonPage.setTotal(pageInfo.getTotalElements());
        commonPage.setData(pageInfo.getContent());
        return commonPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
