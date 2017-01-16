package com.yuapt.service.core.ribbon.predicate;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.yanglinkui.ab.dsl.Operation;
import com.yanglinkui.ab.dsl.Variable;
import com.yanglinkui.ab.dsl.service.ServiceContext;
import com.yuapt.service.core.hystrix.AvengersHystrixRequestContext;
import com.yuapt.service.core.utils.AvengersHttpHeader;
import io.jmnarloch.spring.cloud.ribbon.predicate.DiscoveryEnabledPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

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
        final HystrixRequestVariableDefault<AvengersHttpHeader> hrvd = AvengersHystrixRequestContext.getInstance();
        final AvengersHttpHeader avengersHttpHeader = hrvd.get();

        //获取serviceHeader 假如表达式是 s1.version >= 1, s2.ip != ['192.168.10.1'] 添加灰度发布逻辑
        try {

            if (avengersHttpHeader != null && avengersHttpHeader.getS() != null) {
                //获取server 元数据
                final Map<String, String> metadata = server.getInstanceInfo().getMetadata();
                for (Map.Entry<String, String> entry : metadata.entrySet()) {
                    String key = server.getInstanceInfo().getAppName() + "." + entry.getKey();
                    Operation op = avengersHttpHeader.getS().getOperation(key);
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
                LOGGER.info("The value of the server header is [{}]", avengersHttpHeader.getS().toString());
            }

            return false;
        } catch (Exception e) {
            LOGGER.error("解析serviceHeader[{}]失败;异常信息[{}]", new Object[]{avengersHttpHeader.getS().toString(), e.getMessage()});
            return false;
        }
    }
}
