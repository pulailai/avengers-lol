package com.yuapt.service.core.ribbon.predicate;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.yanglinkui.ab.dsl.Operation;
import com.yanglinkui.ab.dsl.service.ServiceLexer;
import com.yanglinkui.ab.dsl.service.ServiceParser;
import com.yanglinkui.ab.dsl.service.Statements;
import com.yuapt.service.core.hystrix.YuaptHystrixRequestContext;
import io.jmnarloch.spring.cloud.ribbon.api.RibbonFilterContext;
import io.jmnarloch.spring.cloud.ribbon.predicate.DiscoveryEnabledPredicate;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by jimmy on 17/1/5.
 *
 * @see DiscoveryEnabledPredicate
 */
public class MetadataCustomPredicate extends DiscoveryEnabledPredicate {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetadataCustomPredicate.class);

    @Override
    protected boolean apply(DiscoveryEnabledServer server) {

        //线程上下文信息
        final RibbonFilterContext context = RibbonFilterContextHolder.getCurrentContext();
        final HystrixRequestVariableDefault<String> hrvd = YuaptHystrixRequestContext.getInstance();
        final String serviceHeader = hrvd.get();

        //获取serviceHeader 假如表达式是 s1.version = [1, 2.1, 3], s2.ip != ['192.168.10.1'] 添加灰度发布逻辑
        try {
            if (null != serviceHeader && StringUtils.isNotEmpty(serviceHeader)) {
                //解析
                ServiceParser p = new ServiceParser(new ServiceLexer(serviceHeader));
                Statements s = p.statements();
                //获得操作
                Operation op = s.getOperation(server.getInstanceInfo().getAppName() + ".version");
//              op.interpret(new ServiceContext() {
//                @Override
//                public String getValue(Variable variable) {
//                    return null;
//                }
//              });

                if (op != null && StringUtils.isNotEmpty(String.valueOf(op.getValue()))) {
                    context.add("version", op.getValue().getValue().toString());
                }
            }

            LOGGER.info("The value of the server header is [{}]", serviceHeader);

        }
        catch (Exception e) {
            LOGGER.error("解析serviceHeader[{}]失败;异常信息[{}]", new Object[]{serviceHeader, e.getMessage()});
        }

        final Set<Map.Entry<String, String>> attributes = Collections.unmodifiableSet(context.getAttributes().entrySet());
        final Map<String, String> metadata = server.getInstanceInfo().getMetadata();
        LOGGER.info("==========="+JSON.toJSONString(metadata));
        return metadata.entrySet().containsAll(attributes);
    }
}
