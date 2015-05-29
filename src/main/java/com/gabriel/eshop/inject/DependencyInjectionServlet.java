package com.gabriel.eshop.inject;

import org.springframework.context.ApplicationContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Common ancestor for all servlets that want to use @Inject
 */
public class DependencyInjectionServlet extends HttpServlet {

    /**
     * web/xml <context-param><param-value>
     */
    private final static String APP_CTX_PATH = "contextConfigLocation";

    /**
     * Will be executed before creating the Servlet instance.
     * And installs the required dependency injection (DI) in a field marked with @Inject.
     * @throws ServletException
     */
    @Override
    public final void init() throws ServletException {
        String appCtxPath = this.getServletContext().getInitParameter(APP_CTX_PATH);
        System.out.println("load " + APP_CTX_PATH + " -> " + appCtxPath);

        if(appCtxPath == null) {
            System.err.println("I need init param " + APP_CTX_PATH);
            throw new ServletException(APP_CTX_PATH + " init param == null");
        }

        try{
            //Load AppContext
            ApplicationContext appCtx = ApplicationContextHolder.getClassXmlApplicationContext(appCtxPath);
            // than inject from AppContext to all marked by @Inject fields
            List<Field> allFields = FieldReflector.collectUpTo(this.getClass(), DependencyInjectionServlet.class);
            // get all inject fields
            List<Field> injectFields = FieldReflector.filterInject(allFields);

            for(Field field : injectFields) {
                field.setAccessible(true);
                Inject annotation = field.getAnnotation(Inject.class);
                System.out.println("I find method marked by @Inject: " + field);
                // id required bean
                String beanName = annotation.value();
                System.out.println("I must instantiate and inject '" + beanName + "'");
                // get a bean from the file specified in APP_CTX_PATH by id
                Object bean = appCtx.getBean(beanName);
                System.out.println("Instantiation - OK: '" + beanName + "'");
                if(bean == null) {
                    throw new ServletException("There isn't bean with name '" + beanName + "'");
                }
                // set in the field inherited from this class the bean
                field.set(this, bean);
            }
        } catch (Exception e) {
            throw new ServletException("Can't inject from " + APP_CTX_PATH);
        }
    }
}
