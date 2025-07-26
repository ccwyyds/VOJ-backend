package com.vv.voj.judge.codesandbox;

import com.vv.voj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.vv.voj.judge.codesandbox.model.ExecuteCodeRequest;
import com.vv.voj.judge.codesandbox.model.ExecuteCodeResponse;
import com.vv.voj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CodeSandboxTest {
    @Value("${codesandbox.type:example}")
    private String type;

    @Test
    void executeCode() {

        CodeSandbox codeSandbox = CodeSandBoxFactory.newInstance(type);
        codeSandbox = new CodeSandBoxProxy(codeSandbox);
        String code = "int main(){}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");

        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder().code(code).language(language).inputList(inputList).build();

        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
//        断言 executeCodeResponse 不为 null。
//如果该对象是 null，测试就会 失败，并抛出 AssertionFailedError 异常。
        Assertions.assertNotNull(executeCodeResponse);

    }

}