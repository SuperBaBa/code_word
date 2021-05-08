package org.edwin.selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Predicate;

/**
 * @author marcus
 * @date 2020/8/12-17:33
 */
public class AspectSelector implements ImportSelector {
    Logger logger = LoggerFactory.getLogger(AspectSelector.class);
    private final String[] DEFAULT_REFERENCE = new String[]{"org.edwin.config.EnableAspectLogAutoConfiguration"};

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableAspectLog.class.getName());
//        if (attributes == null) {
//            return new String[0];
//        }
//        //获取package属性的value
//        String[] packages = (String[]) attributes.get("packages");
//        if (packages == null || packages.length <= 0 || StringUtils.isEmpty(packages[0])) {
//            return new String[0];
//        }
//        logger.info("加载该包所有类到spring容器中的包名为：" + Arrays.toString(packages));
//        Set<String> classNames = new HashSet<>();
//
//        for (String packageName : packages) {
//            classNames.addAll(ClassUtils.getClassName(packageName, true));
//        }
//        //将类打印到日志中
//        for (String className : classNames) {
//            logger.info(className + "加载到spring容器中");
//        }
//        String[] returnClassNames = new String[classNames.size()];
//        returnClassNames = classNames.toArray(returnClassNames);
//        return returnClassNames;
        return DEFAULT_REFERENCE;
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return null;
    }
}
