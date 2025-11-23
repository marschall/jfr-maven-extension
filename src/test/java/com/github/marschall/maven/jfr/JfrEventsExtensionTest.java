package com.github.marschall.maven.jfr;

import java.io.File;

import org.junit.jupiter.api.extension.RegisterExtension;

import io.takari.maven.testing.TestResources5;
import io.takari.maven.testing.executor.MavenExecution;
import io.takari.maven.testing.executor.MavenExecutionResult;
import io.takari.maven.testing.executor.MavenRuntime;
import io.takari.maven.testing.executor.MavenRuntime.MavenRuntimeBuilder;
import io.takari.maven.testing.executor.MavenVersions;
import io.takari.maven.testing.executor.junit.MavenPluginTest;


@MavenVersions("3.9.11")
class JfrEventsExtensionTest {

  @RegisterExtension
  final TestResources5 resources = new TestResources5();

  private final MavenRuntime mavenRuntime;

  JfrEventsExtensionTest(MavenRuntimeBuilder builder) throws Exception {
    this.mavenRuntime = builder.build();
  }

  @MavenPluginTest
  void testBasic() throws Exception {
    File basedir = this.resources.getBasedir("project-to-test");
    MavenExecution execution = this.mavenRuntime.forProject(basedir);

    MavenExecutionResult result = execution.execute("clean", "package");
    result.assertErrorFreeLog();
  }

}
