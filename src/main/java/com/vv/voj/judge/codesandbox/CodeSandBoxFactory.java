package com.vv.voj.judge.codesandbox;

import com.vv.voj.common.ErrorCode;
import com.vv.voj.exception.BusinessException;
import com.vv.voj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.vv.voj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.vv.voj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * @Title: 简单工厂模式
 * @Author: vv
 * @Date: 2025/7/25 22:38
 */

public class CodeSandBoxFactory {

    /**
     * @Title: 根据用户输入的字符来选择对应的沙箱
     * @Author: vv
     * @Date: 2025/7/25 22:39
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

}
