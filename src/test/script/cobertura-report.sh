#! /bin/sh

# If we're not in the root of the Maven project, then find it using
# this script's name:
if ! [ -r pom.xml ]; then
    cd "$(dirname "$0")"/../../../
fi

# "mvn cobertura:cobertura" is clever: it runs the complete lifecycle,
# tests, ... before generating the report. But sometimes, we want to
# just run a few commands and regenerate the report quickly, and the
# maven plugin doesn't do that. This script calls cobertura manually,
# relying on mvn only to compute the classpath.
#
# When http://jira.codehaus.org/browse/MCOBERTURA-124 is resolved, we
# can get rid of this script.
#
# Alternative : use the fork of the plugin available there:
# http://code.google.com/p/cobertura-it-maven-plugin/wiki/HowToUse

defaultargs="--datafile ./target/cobertura/cobertura.ser"
defaultargs="$defaultargs --destination target/site/cobertura/"
defaultargs="$defaultargs --source src/main/java"
defaultargs="$defaultargs --source target/generated-sources/antlr4"
if [ "$#" -gt 0 ]; then
    args="$defaultargs $*"
else
    args="$defaultargs"
fi

# Cobertura runtime is in classpath only with scope=test.
mvn -q exec:java -Dexec.classpathScope=test \
    -Dexec.mainClass=net.sourceforge.cobertura.reporting.Main -Dexec.args="$args"
