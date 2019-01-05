# State Machines [![Coverage Status](https://coveralls.io/repos/github/MrShoenel/java-state-machines/badge.svg?branch=master)](https://coveralls.io/github/MrShoenel/java-state-machines?branch=master)
A `Java` library packaged for _maven_ to build state(-ful) machines, such as DFAs or NFAs. Supports transitions, custom arguments for transitions and validation in each state(-machine) of available actions.

Comes unit-tested and contains some examples.

## Add to your project
Open up your `pom.xml` and add this:
<pre>
  &lt;dependency&gt;
    &lt;groupId&gt;io.github.mrshoenel&lt;/groupId&gt;
    &lt;artifactId&gt;stateMachines&lt;/artifactId&gt;
    &lt;version&gt;1.0.0-SNAPSHOT&lt;/version&gt;
  &lt;/dependency&gt;
</pre>

## Coverage reporting
This is mostly a note to myself, run following command for all tests, generating jacoco agent and report and submit to coveralls.io:
<pre>
mvn clean test jacoco:prepare-agent jacoco:report coveralls:report
</pre>
