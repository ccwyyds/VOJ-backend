package com.vv.voj.judge;

import com.vv.voj.judge.strategy.DefaultJudgeStrategy;
import com.vv.voj.judge.strategy.JavaLanguageJudgeStrategy;
import com.vv.voj.judge.strategy.JudgeContext;
import com.vv.voj.judge.strategy.JudgeStrategy;
import com.vv.voj.model.dto.questionsubmit.JudgeInfo;
import com.vv.voj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @Title: 判题管理, 简化调用
 * @Author: vv
 * @Date: 2025/7/26 16:47
 */
@Service
public class JudgeManage {

    /**
     * @Title: 判题逻辑
     * @Author: vv
     * @Date: 2025/7/26 16:48
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }


}
