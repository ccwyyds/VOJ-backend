package com.vv.voj.judge;

import cn.hutool.json.JSONUtil;
import com.vv.voj.common.ErrorCode;
import com.vv.voj.exception.BusinessException;
import com.vv.voj.judge.codesandbox.CodeSandBoxFactory;
import com.vv.voj.judge.codesandbox.CodeSandBoxProxy;
import com.vv.voj.judge.codesandbox.CodeSandbox;
import com.vv.voj.judge.codesandbox.model.ExecuteCodeRequest;
import com.vv.voj.judge.codesandbox.model.ExecuteCodeResponse;
import com.vv.voj.judge.strategy.JudgeContext;
import com.vv.voj.model.dto.question.JudgeCase;
import com.vv.voj.model.dto.questionsubmit.JudgeInfo;
import com.vv.voj.model.entity.Question;
import com.vv.voj.model.entity.QuestionSubmit;
import com.vv.voj.model.enums.QuestionSubmitStateEnum;
import com.vv.voj.service.QuestionService;
import com.vv.voj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionService questionService;

    @Resource
    private JudgeManage judgeManage;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1. 获取用户的 提交的id（内含代码，编程语言等），传递给代码沙箱
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交记录不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        //2. 如果状态不为等待中，用户再次提交就不用重复执行
        if (!questionSubmit.getStatus().equals(QuestionSubmitStateEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题");
        }
        //3.传递后将判题状态改为 判题中，防止重复执行，也能让用户知晓状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStateEnum.RUNNING.getValue());
        boolean flag = questionSubmitService.updateById(questionSubmitUpdate);
        if (!flag) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目状态更新失败");
        }
        //4. 代码沙箱执行代码，返回结果
        CodeSandbox codeSandbox = CodeSandBoxFactory.newInstance(type);
        CodeSandBoxProxy codeSandBoxProxy = new CodeSandBoxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        //获取的是Json字符串
        String judgeCaseStr = question.getJudgeCase();
        //将字符串转换为列表
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        //提取列表里的输入用例
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        //对ExecuteCodeRequest进行赋值
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder().language(language).code(code).inputList(inputList).build();
        //调用沙箱
        ExecuteCodeResponse executeCodeResponse = codeSandBoxProxy.executeCode(executeCodeRequest);

        List<String> outputList = executeCodeResponse.getOutputList();
        //5. 根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);


        JudgeInfo judgeInfo = judgeManage.doJudge(judgeContext);

        //修改数据库的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        questionSubmitUpdate.setStatus(QuestionSubmitStateEnum.SUCCEED.getValue());

        flag = questionSubmitService.updateById(questionSubmitUpdate);
        if (!flag) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目状态更新失败");
        }
        QuestionSubmit questionSubmitRes = questionSubmitService.getById(questionSubmitId);
        return questionSubmitRes;
    }
}

