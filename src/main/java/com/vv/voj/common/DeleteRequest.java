package com.vv.voj.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 删除请求
 *
 * @Author: vv
 * @Date: 2025/6/10 1:33
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}