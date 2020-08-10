package org.junit.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RuleChain implements TestRule {
    private static final RuleChain EMPTY_CHAIN = new RuleChain(Collections.emptyList());
    private List<TestRule> rulesStartingWithInnerMost;

    public static RuleChain emptyRuleChain() {
        return EMPTY_CHAIN;
    }

    public static RuleChain outerRule(TestRule testRule) {
        return emptyRuleChain().around(testRule);
    }

    private RuleChain(List<TestRule> list) {
        this.rulesStartingWithInnerMost = list;
    }

    public RuleChain around(TestRule testRule) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(testRule);
        arrayList.addAll(this.rulesStartingWithInnerMost);
        return new RuleChain(arrayList);
    }

    public Statement apply(Statement statement, Description description) {
        for (TestRule apply : this.rulesStartingWithInnerMost) {
            statement = apply.apply(statement, description);
        }
        return statement;
    }
}
