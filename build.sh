#!/bin/sh
# Exit on failure
set -e
if [ "$TRAVIS_BRANCH" = "master" ] && [ "$TRAVIS_PULL_REQUEST" = "false" ]; then
    mvn clean compile
elif [ "$TRAVIS_PULL_REQUEST" != "false" ]; then
	mvn clean install
fi
