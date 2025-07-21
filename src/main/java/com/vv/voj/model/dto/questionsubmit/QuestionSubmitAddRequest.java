package com.vv.voj.model.dto.questionsubmit;

import com.vv.voj.model.dto.question.JudgeCase;
import com.vv.voj.model.dto.question.JudgeConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建请求
 *
 * @Author: vv
 * @Date: 2025/6/10 1:33
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {
    //题目id
    private Long questionId;
    //编程语言
    private String language;
    //代码
    private String code;


    private static final long serialVersionUID = 1L;
}