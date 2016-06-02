package org.waddys.xcloud.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

	public static PageRequest buildPageRequest(Integer page, Integer per_page, Sort sort) {
        if (page == null) {
            page = 1;
        }
        if (per_page == null) {
            per_page = DEFAULT_PER_PAGE;
        }
        return new PageRequest(page - 1, per_page, sort);
	}

}
