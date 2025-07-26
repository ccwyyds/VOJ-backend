package com.vv.voj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 提交状态枚举
 *
 * @Author: vv
 * @Date: 2025/6/10 1:33
 */
public enum QuestionSubmitStateEnum {

    WAITING("等待中", "等待中"),
    RUNNING("运行中", "运行中"),
    SUCCEED("成功", "成功"),
    FAILED("失败", "失败");

    private final String text;

    private final String value;

    QuestionSubmitStateEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static QuestionSubmitStateEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestionSubmitStateEnum anEnum : QuestionSubmitStateEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
