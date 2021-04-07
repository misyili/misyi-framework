package com.misyi.framework.web.automation;

import com.misyi.framework.api.IEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自动化静态数据仓库
 *
 * @author licong
 * @since 2020-08-07 10:36 上午
 */
public class AutomationStaticRepository {

    /**
     * 需校验的
     */
    private static List<IEnum<String>> CODE_LIST = new ArrayList<>();


    public static List<IEnum<String>> getCodeList() {
        return CODE_LIST;
    }

    /**
     * 新增CODE LIST
     * @param addList 需要新增的list
     */
    public static void addCodeEnum(List<IEnum<String>> addList) {
        CODE_LIST.addAll(addList);
    }
    public static void addCodeEnum(IEnum<String>[] addList) {
        CODE_LIST.addAll(Arrays.asList(addList));
    }

    /**
     * 移除所有CODE LIST
     */
    public static void removeCodeList() {
        CODE_LIST = null;
    }
}
