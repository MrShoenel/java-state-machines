# State Machines [![Coverage Status](https://coveralls.io/repos/github/MrShoenel/java-state-machines/badge.svg?branch=master)](https://coveralls.io/github/MrShoenel/java-state-machines?branch=master)
A `Java` library packaged for _maven_ to build state(-ful) machines, such as DFAs or NFAs. Supports transitions, custom arguments for transitions and validation in each state(-machine) of available actions.

Comes unit-tested and contains some examples.

## Add to your project [![Maven Central](https://img.shields.io/maven-central/v/io.github.mrshoenel/stateMachines.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.mrshoenel%22%20AND%20a:%22stateMachines%22)
Open up your `pom.xml` and add this (the releases are synced now to maven central):
<pre>
  &lt;dependency&gt;
    &lt;groupId&gt;io.github.mrshoenel&lt;/groupId&gt;
    &lt;artifactId&gt;stateMachines&lt;/artifactId&gt;
    &lt;version&gt;1.2.3/version&gt; &lt;!-- check latest version in pom.xml --&gt;
  &lt;/dependency&gt;
</pre>

## Coverage reporting
This is mostly a note to myself, run following command for all tests, generating jacoco agent and report and submit to coveralls.io:
<pre>
mvn clean test jacoco:prepare-agent jacoco:report coveralls:report -DrepoToken=&lt;token&gt;
</pre>

## Release to Maven central
Again, a note to myself for staging the repo at OSSRH Sonatype and then releasing to central. We have auto-release set to true, so this command is sufficient:
<pre>
mvn clean deploy
</pre>
Otherwise, we would need to call
<pre>
mvn nexus-staging:release
</pre>
afterwards (c.f. https://central.sonatype.org/pages/apache-maven.html).