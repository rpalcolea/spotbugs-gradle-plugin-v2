/**
 * Copyright 2019 SpotBugs team
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.spotbugs.snom;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import groovy.lang.Closure;
import java.io.File;
import java.util.Optional;
import javax.annotation.Nullable;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.reporting.CustomizableHtmlReport;
import org.gradle.api.reporting.Report;
import org.gradle.api.reporting.SingleFileReport;
import org.gradle.api.resources.TextResource;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputFile;
import org.gradle.util.ConfigureUtil;

public abstract class SpotBugsReport
    implements SingleFileReport,
        CustomizableHtmlReport // to expose CustomizableHtmlReport#setStylesheet to build script
{
  private final Property<File> destination;
  private final Property<Boolean> isEnabled;
  private final SpotBugsTask task;

  public SpotBugsReport(ObjectFactory objects, SpotBugsTask task) {
    this.destination = objects.property(File.class);
    this.isEnabled = objects.property(Boolean.class);
    this.task = task;
  }

  @NonNull
  @Internal("This property returns always same value")
  public abstract Optional<String> toCommandLineOption();

  @Override
  @OutputFile
  public File getDestination() {
    return destination.get();
  }

  @Override
  @Internal("This property returns always same value")
  public OutputType getOutputType() {
    return OutputType.FILE;
  }

  @Override
  @Input
  public boolean isEnabled() {
    return isEnabled.getOrElse(Boolean.FALSE);
  }

  @Override
  public void setEnabled(boolean b) {
    isEnabled.set(b);
  }

  @Override
  public void setEnabled(Provider<Boolean> provider) {
    isEnabled.set(provider);
  }

  @Override
  public void setDestination(File file) {
    destination.set(file);
  }

  @Override
  public void setDestination(Provider<File> provider) {
    destination.set(provider);
  }

  @Override
  public Report configure(Closure closure) {
    ConfigureUtil.configureSelf(closure, this);
    return this;
  }

  @Override
  @Internal("This property provides only a human readable name.")
  public String getDisplayName() {
    return String.format("%s type report generated by the task %s", getName(), getTask().getPath());
  }

  @CheckForNull
  @Override
  // TODO adding an @Input triggers 'cannot be serialized' exception
  public TextResource getStylesheet() {
    return null;
  }

  @Override
  public void setStylesheet(@Nullable TextResource textResource) {
    throw new UnsupportedOperationException(
        String.format("stylesheet property is not available in the %s type report", getName()));
  }

  @NonNull
  @Internal
  protected final SpotBugsTask getTask() {
    return task;
  }
}