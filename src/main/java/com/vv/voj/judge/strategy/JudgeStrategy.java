package com.vv.voj.judge.strategy;

import com.vv.voj.model.dto.questionsubmit.JudgeInfo;

public interface JudgeStrategy {

    /**
     * @Title: 执行判题
     * @Author: vv
     * @Date: 2025/7/26 16:06
     */

    JudgeInfo doJudge(JudgeContext judgeContext);

}
