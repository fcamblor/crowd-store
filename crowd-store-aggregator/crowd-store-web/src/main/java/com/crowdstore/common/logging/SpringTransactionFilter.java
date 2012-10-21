package com.crowdstore.common.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.crowdstore.common.tx.Transactions;
import org.slf4j.MDC;

/**
 * @author fcamblor
 */
public class SpringTransactionFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        MDC.put("transaction", Transactions.getTransactionStatus(true));
        return FilterReply.NEUTRAL;
    }
}
