package com.github.marschall.maven.jfr;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.ExecutionListener;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.component.annotations.Component;

@Component(role = AbstractMavenLifecycleParticipant.class, hint = "jfrevents")
public class JfrEventsExtension extends AbstractMavenLifecycleParticipant {

  @Override
  public void afterProjectsRead(MavenSession session) throws MavenExecutionException {
    MavenExecutionRequest request = session.getRequest();

    ExecutionListener originalListener = request.getExecutionListener();
    var jfrEventListener = new JfrEventListener();
    if (originalListener != null) {
      var compositeExecutionListener = new CompositeExecutionListener(jfrEventListener, originalListener);
      request.setExecutionListener(compositeExecutionListener);
    } else {
      request.setExecutionListener(jfrEventListener);
    }

  }

}
