package zzz.weixbot.common;

import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

public class ResourceName {
    /**
     * 获取azure 资源参数
     */
    public static String getResourceName(String resourceName){
        Properties properties = null;
        try {properties = new Properties();
            ClassPathResource resource = new ClassPathResource("azure-openai.properties");
            properties.load(resource.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(resourceName);
    }
}
