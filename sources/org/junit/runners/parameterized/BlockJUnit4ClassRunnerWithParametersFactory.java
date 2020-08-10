package org.junit.runners.parameterized;

import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;

public class BlockJUnit4ClassRunnerWithParametersFactory implements ParametersRunnerFactory {
    public Runner createRunnerForTestWithParameters(TestWithParameters testWithParameters) throws InitializationError {
        return new BlockJUnit4ClassRunnerWithParameters(testWithParameters);
    }
}
