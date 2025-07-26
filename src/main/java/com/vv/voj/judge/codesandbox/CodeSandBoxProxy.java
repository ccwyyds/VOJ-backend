package com.vv.voj.judge.codesandbox;


import com.vv.voj.judge.codesandbox.model.ExecuteCodeRequest;
import com.vv.voj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: 沙箱增强类
 * @Author: vv
 * @Date: 2025/7/26 12:19
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandbox {

    private CodeSandbox codeSandbox;


    //构造函数
    public CodeSandBoxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    /**
     * @Title: 打印沙箱日志
     * @Author: vv
     * @Date: 2025/7/26 12:22
     */
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("请求参数为" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("响应参数为" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
