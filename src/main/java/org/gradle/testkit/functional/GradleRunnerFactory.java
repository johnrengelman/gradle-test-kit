package org.gradle.testkit.functional;

import groovy.lang.Closure;
import org.gradle.testkit.functional.internal.DefaultGradleRunner;
import org.gradle.testkit.functional.internal.GradleHandleFactory;
import org.gradle.testkit.functional.internal.classpath.ClasspathInjectingGradleHandleFactory;
import org.gradle.testkit.functional.internal.toolingapi.ToolingApiGradleHandleFactory;

public class GradleRunnerFactory {

    public static GradleRunner create(Closure connectorConfigureAction) {
        GradleHandleFactory toolingApiHandleFactory = new ToolingApiGradleHandleFactory(connectorConfigureAction);

        // TODO: Which class would be attached to the right classloader? Is using something from the test kit right?
        ClassLoader sourceClassLoader = GradleRunnerFactory.class.getClassLoader();
        GradleHandleFactory classpathInjectingHandleFactory = new ClasspathInjectingGradleHandleFactory(sourceClassLoader, toolingApiHandleFactory);

        return new DefaultGradleRunner(classpathInjectingHandleFactory);
    }

    public static GradleRunner create() {
        return create(null);
    }

}
