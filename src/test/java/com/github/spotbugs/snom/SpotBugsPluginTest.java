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

import static org.junit.jupiter.api.Assertions.*;

import org.gradle.util.GradleVersion;
import org.junit.jupiter.api.Test;

class SpotBugsPluginTest {

  @Test
  void testLoadToolVersion() {
    assertNotNull(new SpotBugsPlugin().loadProperties().getProperty("spotbugs-version"));
    assertNotNull(new SpotBugsPlugin().loadProperties().getProperty("slf4j-version"));
  }

  @Test
  void testVerifyGradleVersion() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          new SpotBugsPlugin().verifyGradleVersion(GradleVersion.version("3.9"));
        });
    new SpotBugsPlugin().verifyGradleVersion(GradleVersion.version("4.0"));
  }
}