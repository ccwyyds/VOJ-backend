package com.vv.voj.judge;


import com.vv.voj.model.entity.QuestionSubmit;

/**
 * @Title: 判题服务
 * @Author: vv
 * @Date: 2025/7/26 12:59
 */

public interface JudgeService {
    /**
     * @Title: 判题
     * @Author: vv
     * @Date: 2025/7/26 12:59
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
