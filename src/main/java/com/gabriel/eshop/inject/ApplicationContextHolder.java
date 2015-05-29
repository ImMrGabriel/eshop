package com.gabriel.eshop.inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton storage ApplicationContexts
 * There is a need to ensure that each of the descendants DependencyInjectionServlet
 * did not create your appCtx, and used shared with other servlets.
 * Although you can store a variety of contexts, one for each xml,
 * but in our system will be one for all the servlets.
 *
 * Синглетонное хранилище ApplicationContext-ов
 * Необходимо для того, чтобы каждый из потомков DependencyInjectionServlet
 * не создавали свой appCtx, а использовал разделяемый с другими сервлетами.
 * Хотя может хранить множество контекстов - по одному для каждого xml-ля,
 * но в нашей системе будет один на все сервлеты.
 */
public class ApplicationContextHolder {
    private static final Map<String, ApplicationContext> pathToContextRepository = new HashMap<>();

    /**
     * Returns the ApplicationContext from the xml file specified in the path,
     * if the ApplicationContext for the path already exists then returns it,
     * otherwise creates a new ApplicationContext and added to the pathToContextRepository.
     * @param path resource location
     * @return ApplicationContext
     */
    static synchronized ApplicationContext getClassXmlApplicationContext(String path) {
        if(!pathToContextRepository.containsKey(path)) {
            pathToContextRepository.put(path, new ClassPathXmlApplicationContext(path));
        }
        return pathToContextRepository.get(path);
    }
}
