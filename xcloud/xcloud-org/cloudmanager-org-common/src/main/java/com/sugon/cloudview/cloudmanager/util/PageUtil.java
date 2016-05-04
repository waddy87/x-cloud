package com.sugon.cloudview.cloudmanager.util;

import org.springframework.data.domain.PageRequest;

/**
 * 分页工具
 * 
 * @author zhangdapeng
 *
 */
public class PageUtil {

    private static final Integer DEFAULT_PER_PAGE = 10;

    /**
     * 构造分页对象
     * 
     * @param page
     * @param per_page
     * @return
     */
    public static PageRequest buildPageRequest(Integer page, Integer per_page) {

        if (page == null) {
            page = 1;
        }
        if (per_page == null) {
            per_page = DEFAULT_PER_PAGE;
        }
        return new PageRequest(page - 1, per_page);
    }

}
