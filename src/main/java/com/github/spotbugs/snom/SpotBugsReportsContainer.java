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

import com.github.spotbugs.snom.internal.AbstractSingleFileReport;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Optional;
import org.gradle.api.reporting.CustomizableHtmlReport;
import org.gradle.api.reporting.SingleFileReport;
import org.gradle.api.tasks.Internal;

public interface SpotBugsReportsContainer
// TODO consider to extends ReportContainer<ConfigurableReport> that needs internal dependencies
{
  @Internal
  @NonNull
  SingleFileReport getText();

  @Internal
  @NonNull
  SingleFileReport getXml();

  @Internal
  @NonNull
  CustomizableHtmlReport getHtml();

  /** @return Get the enabled report. */
  @Internal
  @NonNull
  Optional<AbstractSingleFileReport> getFirstEnabled();
}
