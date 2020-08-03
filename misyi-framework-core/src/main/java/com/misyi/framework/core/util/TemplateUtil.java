package com.misyi.framework.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模板替换工具
 *
 * @author licong
 * @since 2020/7/31 9:09 下午
 */
public class TemplateUtil {

    /**
     * 将template中 {{name}} 替换为model的属性，如果有无替换的属性则报错
     * @param template 模板
     * @param model 参数
     * @return 返回替换后的信息
     */
    public static String processTemplate(String template, Map<String, Object> model) {
        try {
            if(model == null) {
                model = new HashMap<>();
            }
            // 处理模板变量 {{name}}
            if (model.size() > 0) {
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    String regex = "\\{\\{" + entry.getKey() + "\\}\\}";
                    template = template.replaceAll(regex, entry.getValue().toString());
                }
            }
            // 校验是否处理完毕
            String pattern = "\\{\\{.*\\}\\}";
            Pattern p1 = Pattern.compile(pattern);
            Matcher m1 = p1.matcher(template);
            if(m1.find()) {
                String group = m1.group();
                String empty = group.substring(2, group.indexOf("}}"));
                throw new RuntimeException(String.format("参数%s不能为空", empty));
            }

            return template;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
}
