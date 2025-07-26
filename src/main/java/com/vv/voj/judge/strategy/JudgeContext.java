package com.vv.voj.judge.strategy;

import com.vv.voj.model.dto.question.JudgeCase;
import com.vv.voj.model.dto.questionsubmit.JudgeInfo;
import com.vv.voj.model.entity.Question;
import com.vv.voj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @Title: 用于定义在策略中传递的参数
 * @Author: vv
 * @Date: 2025/7/26 16:05
 */

@Data
public class JudgeContext {

    private List<JudgeCase> judgeCaseList;

    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private Question question;
    private QuestionSubmit questionSubmit;
}
