package com.vv.voj.model.dto.question;

import lombok.Data;

/**
 * @Title: 判题配置
 * @Author: vv
 * @Date: 2025/6/14 0:40
 */

@Data
public class JudgeConfig {
    //时间限制（ms）
    private Long timeLimit;
    //空间限制(kb)
    private Long memoryLimit;
    //堆栈限制(kb)
    private Long stackLimit;
}
