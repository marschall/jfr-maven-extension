package com.github.marschall.maven.jfr;

import java.util.Objects;

import org.apache.maven.execution.ExecutionEvent;
import org.apache.maven.execution.ExecutionListener;

final class CompositeExecutionListener implements ExecutionListener {

  private final ExecutionListener jfrListener;
  private final ExecutionListener originalListener;

  CompositeExecutionListener(ExecutionListener jfrListener, ExecutionListener originalListener) {
    Objects.requireNonNull(jfrListener, "jfrListener");
    Objects.requireNonNull(originalListener, "originalListener");
    this.jfrListener = jfrListener;
    this.originalListener = originalListener;
  }

  @Override
  public void projectDiscoveryStarted(ExecutionEvent event) {
    this.jfrListener.projectDiscoveryStarted(event);
    this.originalListener.projectDiscoveryStarted(event);
  }

  @Override
  public void sessionStarted(ExecutionEvent event) {
    this.jfrListener.sessionStarted(event);
    this.originalListener.sessionStarted(event);
  }

  @Override
  public void sessionEnded(ExecutionEvent event) {
    this.jfrListener.sessionEnded(event);
    this.originalListener.sessionEnded(event);
  }

  @Override
  public void projectSkipped(ExecutionEvent event) {
    this.jfrListener.projectSkipped(event);
    this.originalListener.projectSkipped(event);
  }

  @Override
  public void projectStarted(ExecutionEvent event) {
    this.jfrListener.projectStarted(event);
    this.originalListener.projectStarted(event);
  }

  @Override
  public void projectSucceeded(ExecutionEvent event) {
    this.jfrListener.projectSucceeded(event);
    this.originalListener.projectSucceeded(event);
  }

  @Override
  public void projectFailed(ExecutionEvent event) {
    this.jfrListener.projectFailed(event);
    this.originalListener.projectFailed(event);
  }

  @Override
  public void mojoSkipped(ExecutionEvent event) {
    this.jfrListener.mojoSkipped(event);
    this.originalListener.mojoSkipped(event);
  }

  @Override
  public void mojoStarted(ExecutionEvent event) {
    this.jfrListener.mojoStarted(event);
    this.originalListener.mojoStarted(event);
  }

  @Override
  public void mojoSucceeded(ExecutionEvent event) {
    this.jfrListener.mojoSucceeded(event);
    this.originalListener.mojoSucceeded(event);
  }

  @Override
  public void mojoFailed(ExecutionEvent event) {
    this.jfrListener.mojoFailed(event);
    this.originalListener.mojoFailed(event);
  }

  @Override
  public void forkStarted(ExecutionEvent event) {
    this.jfrListener.forkStarted(event);
    this.originalListener.forkStarted(event);
  }

  @Override
  public void forkSucceeded(ExecutionEvent event) {
    this.jfrListener.forkSucceeded(event);
    this.originalListener.forkStarted(event);
  }

  @Override
  public void forkFailed(ExecutionEvent event) {
    this.jfrListener.forkFailed(event);
    this.originalListener.forkFailed(event);
  }

  @Override
  public void forkedProjectStarted(ExecutionEvent event) {
    this.jfrListener.forkedProjectStarted(event);
    this.originalListener.forkedProjectStarted(event);
  }

  @Override
  public void forkedProjectSucceeded(ExecutionEvent event) {
    this.jfrListener.forkedProjectSucceeded(event);
    this.originalListener.forkedProjectSucceeded(event);
  }

  @Override
  public void forkedProjectFailed(ExecutionEvent event) {
    this.jfrListener.forkedProjectFailed(event);
    this.originalListener.forkedProjectFailed(event);
  }

}
