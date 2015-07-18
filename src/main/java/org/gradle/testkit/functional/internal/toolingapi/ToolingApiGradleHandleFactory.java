package org.gradle.testkit.functional.internal.toolingapi;

import groovy.lang.Closure;
import org.gradle.testkit.functional.internal.GradleHandle;
import org.gradle.testkit.functional.internal.GradleHandleFactory;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;
import java.util.List;

public class ToolingApiGradleHandleFactory implements GradleHandleFactory {

    private final Closure connectorConfigureAction;

    private ProjectConnection connection;

    public ToolingApiGradleHandleFactory() {
        this(null);
    }

    public ToolingApiGradleHandleFactory(Closure connectorConfigureAction) {
        this.connectorConfigureAction = connectorConfigureAction;
    }

    public GradleHandle start(File directory, List<String> arguments) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(directory);
        configureConnector(connector);
        connection = connector.connect();
        BuildLauncher launcher = connection.newBuild();
        String[] argumentArray = new String[arguments.size()];
        arguments.toArray(argumentArray);
        launcher.withArguments(argumentArray);
        return new BuildLauncherBackedGradleHandle(launcher);
    }

    @Override
    public void close() {
        if (connection != null) {
            connection.close();
        }
    }

    private void configureConnector(GradleConnector connector) {
        if (connectorConfigureAction != null) {
            connectorConfigureAction.setDelegate(connector);
            connectorConfigureAction.call(connector);
        }
    }
}
