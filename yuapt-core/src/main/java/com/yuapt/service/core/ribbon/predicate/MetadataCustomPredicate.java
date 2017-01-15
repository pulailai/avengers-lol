package com.yuapt.service.core.ribbon.predicate;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.yanglinkui.ab.dsl.Operation;
import com.yanglinkui.ab.dsl.Variable;
import com.yanglinkui.ab.dsl.service.ServiceContext;
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

import java.util.*;

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

        //获取serviceHeader 假如表达式是 s1.version >= 1, s2.ip != ['192.168.10.1'] 添加灰度发布逻辑
        try {
            if (null != serviceHeader && StringUtils.isNotEmpty(serviceHeader)) {
                //解析
                ServiceParser p = new ServiceParser(new ServiceLexer(serviceHeader));
                Statements s = p.statements();
                //获得操作
                final Map<String, String> metadata = server.getInstanceInfo().getMetadata();
                for (Map.Entry<String, String> entry : metadata.entrySet()) {
                    String key = server.getInstanceInfo().getAppName() + "." + entry.getKey();
                    Operation op = s.getOperation(key);
                    if (op != null) {
                        boolean ok = op.interpret(new ServiceContext() {
                            @Override
                            public String getValue(Variable variable) {
                                return entry.getValue();
                            }
                        });

                        if (ok) {
                            return true;
                        }
                    }

                }
            }

            LOGGER.info("The value of the server header is [{}]", serviceHeader);

            return false;
        }
        catch (Exception e) {
            LOGGER.error("解析serviceHeader[{}]失败;异常信息[{}]", new Object[]{serviceHeader, e.getMessage()});
            return  false;
        }

    }
}
