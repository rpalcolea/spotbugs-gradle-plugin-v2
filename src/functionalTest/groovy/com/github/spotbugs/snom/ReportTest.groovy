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
package com.github.spotbugs.snom
import org.gradle.internal.impldep.com.google.common.io.Files
import org.junit.jupiter.api.BeforeEach
import spock.lang.Specification

import org.gradle.testkit.runner.GradleRunner

import java.nio.file.Paths

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import static org.junit.jupiter.api.Assertions.assertTrue

class ReportTest extends Specification {
    File rootDir
    File buildFile

    @BeforeEach
    def setup() {
        rootDir = Files.createTempDir()
        buildFile = new File(rootDir, 'build.gradle')
        buildFile << """
plugins {
    id 'java'
    id 'jp.skypencil.spotbugs.snom'
}

version = 1.0

repositories {
    mavenCentral()
}
        """
        File sourceDir = rootDir.toPath().resolve("src").resolve("main").resolve("java").toFile()
        sourceDir.mkdirs()
        File sourceFile = new File(sourceDir, "Foo.java")
        sourceFile << """
public class Foo {
    public static void main(String... args) {
        System.out.println("Hello, SpotBugs!");
    }
}
"""
    }

    def "can generate spotbugs.html"() {
        buildFile << """
tasks.withType(com.github.spotbugs.snom.SpotBugsTask) {
    reports {
        html.enabled = true
    }
}"""
        when:
        def result = GradleRunner.create()
                .withProjectDir(rootDir)
                .withArguments('spotbugsMain')
                .withPluginClasspath()
                .build()

        then:
        result.task(":spotbugsMain").outcome == SUCCESS
        File report = rootDir.toPath().resolve("build").resolve("reports").resolve("spotbugs").resolve("main").resolve("spotbugs.html").toFile()
        assertTrue(report.isFile())
    }

    def "can generate spotbugs.xml"() {
        buildFile << """
tasks.withType(com.github.spotbugs.snom.SpotBugsTask) {
    reports {
        xml.enabled = true
    }
}"""
        when:
        def result = GradleRunner.create()
                .withProjectDir(rootDir)
                .withArguments('spotbugsMain')
                .withPluginClasspath()
                .build()

        then:
        result.task(":spotbugsMain").outcome == SUCCESS
        File report = rootDir.toPath().resolve("build").resolve("reports").resolve("spotbugs").resolve("main").resolve("spotbugs.xml").toFile()
        assertTrue(report.isFile())
    }
}