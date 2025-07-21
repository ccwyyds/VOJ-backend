package com.vv.voj.model.dto.questionsubmit;

import com.vv.voj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 题目提交查询请求
 *
 * @Author: vv
 * @Date: 2025/6/10 1:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {
    //题目id
    private Long questionId;

    //编程语言
    private String language;

    //用户id
    private Long userId;

    //提交状态
    private String status;

    private static final long serialVersionUID = 1L;
}