package com.yuapt.service.core.ribbon.rule;

import com.yuapt.service.core.ribbon.predicate.MetadataCustomPredicate;
import io.jmnarloch.spring.cloud.ribbon.predicate.DiscoveryEnabledPredicate;
import io.jmnarloch.spring.cloud.ribbon.rule.DiscoveryEnabledRule;

/**
 * Created by jimmy on 17/1/5.
 *
 * @see DiscoveryEnabledRule
 * @see MetadataCustomPredicate
 */
public class MetadataCustomRule extends DiscoveryEnabledRule {


    /**
     * Creates new instance of {@link MetadataCustomRule}.
     */
    public MetadataCustomRule() {
        this(new MetadataCustomPredicate());
    }

    /**
     * Creates new instance of {@link MetadataCustomRule} with specific predicate.
     *
     * @param predicate the predicate, can't be {@code null}
     * @throws IllegalArgumentException if predicate is {@code null}
     */
    public MetadataCustomRule(DiscoveryEnabledPredicate predicate) {
        super(predicate);
    }
}
