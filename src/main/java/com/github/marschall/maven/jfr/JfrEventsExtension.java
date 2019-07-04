package com.github.marschall.maven.jfr;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.ExecutionListener;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.component.annotations.Component;

/**
 * An extension that generates Flight Recorder events.
 */
@Component(role = AbstractMavenLifecycleParticipant.class, hint = "jfrevents")
public class JfrEventsExtension extends AbstractMavenLifecycleParticipant {

  @Override
  public void afterProjectsRead(MavenSession session) throws MavenExecutionException {
    MavenExecutionRequest mavenExecutionRequest = session.getRequest();

    ExecutionListener originalListener = mavenExecutionRequest.getExecutionListener();
    var jfrEventListener = new JfrEventListener();
    if (originalListener != null) {
      var compositeExecutionListener = new CompositeExecutionListener(jfrEventListener, originalListener);
      mavenExecutionRequest.setExecutionListener(compositeExecutionListener);
    } else {
      mavenExecutionRequest.setExecutionListener(jfrEventListener);
    }

  }

}
