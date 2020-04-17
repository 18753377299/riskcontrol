package org.easy.excel.util;


import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {
    private static ApplicationContext context;  
    
    public static void setApplicationContext(ApplicationContext applicationContext) {  
        context = applicationContext;  
    }  
    public static ApplicationContext getApplicationContext() {  
        return context;  
    } 
}
