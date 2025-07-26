package com.vv.voj.judge.codesandbox.impl;


import com.vv.voj.judge.codesandbox.CodeSandbox;
import com.vv.voj.judge.codesandbox.model.ExecuteCodeRequest;
import com.vv.voj.judge.codesandbox.model.ExecuteCodeResponse;
import com.vv.voj.model.dto.questionsubmit.JudgeInfo;
import com.vv.voj.model.enums.JudgeInfoMessageEnum;
import com.vv.voj.model.enums.QuestionSubmitStateEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        //暂时设置输入输出一致
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStateEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setTime(100L);
        judgeInfo.setMemory(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
