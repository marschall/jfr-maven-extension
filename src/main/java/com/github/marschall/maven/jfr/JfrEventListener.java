package com.github.marschall.maven.jfr;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.maven.execution.AbstractExecutionListener;
import org.apache.maven.execution.ExecutionEvent;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.project.MavenProject;

import com.github.marschall.maven.jfr.JfrEventListener.MojoEvent;
import com.github.marschall.maven.jfr.JfrEventListener.ProjectEvent;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.StackTrace;

final class JfrEventListener extends AbstractExecutionListener {

  private final ConcurrentMap<MavenProject, ProjectEvent> projectEvents;
  private final ConcurrentMap<MojoExecution, MojoEvent> mojoEvents;

  JfrEventListener() {
    this.projectEvents = new ConcurrentHashMap<>();
    this.mojoEvents = new ConcurrentHashMap<>();
  }

  @Override
  public void projectStarted(ExecutionEvent event) {

    MavenProject mavenProject = event.getProject();

    var projectEvent = new ProjectEvent();
    projectEvent.setGroupId(mavenProject.getGroupId());
    projectEvent.setArtifactId(mavenProject.getArtifactId());
    projectEvent.setVersion(mavenProject.getVersion());

    this.projectEvents.put(mavenProject, projectEvent);

    projectEvent.begin();
  }

  private void stopProject(MavenProject mavenProject) {
    var projectEvent = this.projectEvents.remove(mavenProject);
    if (projectEvent != null) {
      projectEvent.end();
      projectEvent.commit();
    }
  }

  @Override
  public void projectSucceeded(ExecutionEvent event) {
    this.stopProject(event.getProject());
  }

  @Override
  public void projectFailed(ExecutionEvent event) {
    this.stopProject(event.getProject());
  }

  @Override
  public void mojoStarted(ExecutionEvent event) {
    var mojoExecution = event.getMojoExecution();
    var mojoEvent = newMojoEvent(mojoExecution);
    mojoEvent.setForked(false);

    this.mojoEvents.put(mojoExecution, mojoEvent);

    mojoEvent.begin();
  }

  private void stopMojo(MojoExecution mojoExecution) {
    var mojoEvent = this.mojoEvents.remove(mojoExecution);
    if (mojoEvent != null) {
      mojoEvent.end();
      mojoEvent.commit();
    }
  }

  @Override
  public void mojoSucceeded(ExecutionEvent event) {
    this.stopMojo(event.getMojoExecution());
  }

  @Override
  public void mojoFailed(ExecutionEvent event) {
    this.stopMojo(event.getMojoExecution());
  }
  
  @Override
  public void forkStarted(ExecutionEvent event) {
    var mojoExecution = event.getMojoExecution();
    var mojoEvent = newMojoEvent(mojoExecution);
    mojoEvent.setForked(true);

    this.mojoEvents.put(mojoExecution, mojoEvent);

    mojoEvent.begin();
  }
  
  @Override
  public void forkedProjectSucceeded(ExecutionEvent event) {
    this.stopMojo(event.getMojoExecution());
  }
  
  @Override
  public void forkFailed(ExecutionEvent event) {
    this.stopMojo(event.getMojoExecution());
  }

  private static MojoEvent newMojoEvent(MojoExecution mojoExecution) {
    var mojoEvent = new MojoEvent();
    mojoEvent.setGoal(mojoExecution.getGoal());
    mojoEvent.setPhase(mojoExecution.getLifecyclePhase());
    mojoEvent.setGroupId(mojoExecution.getPlugin().getGroupId());
    mojoEvent.setArtifactId(mojoExecution.getPlugin().getArtifactId());
    mojoEvent.setExecutionId(mojoExecution.getExecutionId());
    return mojoEvent;
  }

  @Category("Maven")
  @Label("Project")
  @Description("build of a project")
  @StackTrace(false)
  static final class ProjectEvent extends Event {


    @Label("groupId")
    @Description("The groupId of the executed project")
    private String groupId;

    @Label("artifactId")
    @Description("The artifactId of the executed project")
    private String artifactId;

    @Label("version")
    @Description("The version of the executed project")
    private String version;

    String getGroupId() {
      return this.groupId;
    }

    void setGroupId(String groupId) {
      this.groupId = groupId;
    }

    String getArtifactId() {
      return this.artifactId;
    }

    void setArtifactId(String artifactId) {
      this.artifactId = artifactId;
    }

    String getVersion() {
      return this.version;
    }

    void setVersion(String version) {
      this.version = version;
    }

  }

  @Category("Maven")
  @Label("Mojo")
  @Description("execution of a Mojo")
  @StackTrace(false)
  static final class MojoEvent extends Event {

    private boolean forked;

    private String goal;

    private String phase;

    private String groupId;

    private String artifactId;

    private String executionId;

    boolean isForked() {
      return forked;
    }

    void setForked(boolean forked) {
      this.forked = forked;
    }

    String getGoal() {
      return this.goal;
    }

    void setGoal(String goal) {
      this.goal = goal;
    }

    String getPhase() {
      return this.phase;
    }

    void setPhase(String phase) {
      this.phase = phase;
    }

    String getGroupId() {
      return groupId;
    }

    void setGroupId(String groupId) {
      this.groupId = groupId;
    }

    String getArtifactId() {
      return this.artifactId;
    }

    void setArtifactId(String artifactId) {
      this.artifactId = artifactId;
    }

    String getExecutionId() {
      return this.executionId;
    }

    void setExecutionId(String executionId) {
      this.executionId = executionId;
    }

  }

}
