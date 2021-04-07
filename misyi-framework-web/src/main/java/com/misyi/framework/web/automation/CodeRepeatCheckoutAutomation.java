package com.misyi.framework.web.automation;

import com.misyi.framework.api.IEnum;
import com.misyi.framework.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Code 码重复校验处理自动化操作
 *
 * @author licong
 * @since 2020/8/7 10:57 上午
 */
@Component
@Slf4j
public class CodeRepeatCheckoutAutomation implements CommandLineRunner {


    /**
     * 启动异常码校验
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        List<IEnum<String>> codeList = AutomationStaticRepository.getCodeList();
        // 校验异常码是否重复
        List<String> checkList = new ArrayList<>();
        codeList.forEach(item -> {
            if (checkList.contains(item.getValue())) {
                log.error("项目启动失败, 业务编码重复: 编码={}, 描述={}", item.getValue(), item.getDesc());
                throw new BusinessException("500", "业务编码重复:" + item.getValue());
            }
            checkList.add(item.getDesc());
        });

        AutomationStaticRepository.removeCodeList();
    }
}
