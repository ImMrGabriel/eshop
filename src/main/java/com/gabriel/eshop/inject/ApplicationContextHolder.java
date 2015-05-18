package com.gabriel.eshop.inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Синглетонное хранилище ApplicationContext-ов
 * Необходимо для того, чтобы каждый из потомков DependencyInjectionServlet
 * не создавал свой appCtx, а использовал разделяемый с другими сервлетами.
 * Хотя может хранить множество контекстов - по одному для каждого xml-ля,
 * но в нашей системе будет один на все сервлеты.
 */
public class ApplicationContextHolder {
    private static final Map<String, ApplicationContext> pathToContextRepository = new HashMap<>();

    static synchronized ApplicationContext getClassXmlApplicationContext(String path) {
        if(!pathToContextRepository.containsKey(path)) {
            pathToContextRepository.put(path, new ClassPathXmlApplicationContext(path));
        }
        return pathToContextRepository.get(path);
    }
}
