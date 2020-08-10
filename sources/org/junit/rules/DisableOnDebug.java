package org.junit.rules;

import java.lang.management.ManagementFactory;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class DisableOnDebug implements TestRule {
    private final boolean debugging;
    private final TestRule rule;

    public DisableOnDebug(TestRule testRule) {
        this(testRule, ManagementFactory.getRuntimeMXBean().getInputArguments());
    }

    DisableOnDebug(TestRule testRule, List<String> list) {
        this.rule = testRule;
        this.debugging = isDebugging(list);
    }

    public Statement apply(Statement statement, Description description) {
        if (this.debugging) {
            return statement;
        }
        return this.rule.apply(statement, description);
    }

    private static boolean isDebugging(List<String> list) {
        for (String str : list) {
            if ("-Xdebug".equals(str)) {
                return true;
            }
            if (str.startsWith("-agentlib:jdwp")) {
                return true;
            }
        }
        return false;
    }

    public boolean isDebugging() {
        return this.debugging;
    }
}
