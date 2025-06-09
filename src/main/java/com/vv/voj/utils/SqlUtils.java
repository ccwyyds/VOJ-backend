package com.vv.voj.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL 工具
 *
 * @Author: vv
 * @Date: 2025/6/10 1:33
 */
public class SqlUtils {

    /**
     * 校验排序字段是否合法（防止 SQL 注入）
     *
     * @param sortField
     * @return
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
