package com.vv.voj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.vv.voj.model.dto.question.JudgeCase;
import com.vv.voj.model.dto.question.JudgeConfig;
import com.vv.voj.model.dto.questionsubmit.JudgeInfo;
import com.vv.voj.model.entity.Question;
import com.vv.voj.model.enums.JudgeInfoMessageEnum;

import java.util.List;


/**
 * @Title: Java的判题逻辑
 * @Author: vv
 * @Date: 2025/7/26 16:06
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long time = judgeInfo.getTime();
        Long memory = judgeInfo.getMemory();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTime(time);
        judgeInfoResponse.setMemory(memory);

        // 1）判断 输出的用例 是否和 预期用例 的数量相同
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // 2）判断每一个输出和预期输出是否相同
        for (int i = 0; i < judgeCaseList.size(); i++) {
            // 返回的是类型为 JudgeCase 下标为 i 的对象
            if (!judgeCaseList.get(i).getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        // 3）判断题目的限制是否符合要求

        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long maxTimeLimit = judgeConfig.getTimeLimit();
        Long maxMemoryLimit = judgeConfig.getMemoryLimit();
        if (time > maxTimeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (memory > maxMemoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        //全都没问题,返回默认值AC
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
