---
layout: master
title: Developer Zone
---

# Developer Zone

### Information for GPars developers

## Build info

The continuous integration build can be found under:

Build Server | Link | Note
---|---|---
Travis-CI | <https://travis-ci.org/GPars/GPars> | ![Travis-CI status master](https://travis-ci.org/GPars/GPars.svg?branch=master)
Snap-CI | <https://snap-ci.com/GPars/GPars/branch/master> | ![Snap-CI status master](https://snap-ci.com/GPars/GPars/branch/master/build_image)
JetBrains TeamCity | <http://teamcity.jetbrains.com/project.html?projectId=GPars> | needs registration
Codehaus Bamboo | <http://bamboo.ci.codehaus.org/browse/GPARS-DEF> |

## Issue Tracker

The JIRA issue tracker: <http://jira.codehaus.org/browse/GPARS>

## Source Repository

The Git repository held at GitHub is the official mainline:

To work on the codebase please fork the repository on GitHub in the usual
GitHub workflow way. Keeping the master branch as a mirror of the mainline,
working on a feature branch and then sending in pull requests based on that
feature branch seems to be the best way of working. Please refer to the Git
and GitHub documentation for any further details on using Git and GitHub.

## Mirror Repository

A repository that is a mirror of the GitHub repository is maintained at
Codehaus in order to continue integration with various continuous integration
servers (over time more of this will migrate to GitHub). Also this Codehaus
project remains the location of the issue tracker and is the route for
artefacts to get into the Maven repository.

You should never need to clone this repository, but for completeness, the
command:

creates a clone of the repository in the subdirectory GPars. The above URL
gives read-only access to the repository. Those people with write access to
the repository should use the URL:

## Personal clones

Project commiters and contributors typically keep their personal clones of the
main repository for feature branches:

Vaclav Pech

Russel Winder

## Building the project

The _gradlew_ script will download and setup gradle for the project and
execute the build.

## IDE integration

Create an IDEA or Eclipse project files through _gradlew idea_ or _gradlew
eclipse_ commands and you are ready to go.

### IntelliJ IDEA

Upon start or right before building the project IDEA will prompt you for the
JDK to use. Once you specify that on the project level, you should be good to
go.

### The default IntelliJ IDEA project file

GPars holds a default IDEA project file in the root of the project and is
named _GPars_IDEAX.ipr_. This project serves as a master copy for the
generated project files (see above) and also to configure our Continuous
Integraion. If you for some reason decide to use the default project file, you
need to go through a few configuraion steps first.

The first time you open the project you will be prompted to enter a
PROJECT_JDK_NAME and a _MAVEN_REPOSITORY_ variable. These are IDEA variables
and not system variables. The result is stored in your home directory.
Each developer can have a unique value. For example, your _MAVEN_REPOSITORY_
may be a path on your disk, and _PROJECT_JDK_NAME_ can be any string value,
e.g. "1.6". This is the name of the Global JDK defined defined within IDEA.
You can setup a global JDK in IDEA under File->Project Structure->SDKs. There
is a little text box to fill in where you give the JDK a name. Whatever you
typed into this textbox needs is what needs to be typed into the IDEA
Environment Variable screen for PROJECT_JDK_NAME.

The _MAVEN_REPOSITORY_ variable should point to your local maven repository,
which IDEA will be using to keep downloadable artifacts of third-party
libraries that are needed to build and run _GPars_. You need to populate the
repo first for IDEA to find the necessary artifacts. The best way to do so is
to open the _/java-demo/pom.xml_ file (provided Maven support is enabled in
your IDEA installation) and ask IDEA to _Import_ _changes_. Alternatively you
can use the _Refresh_ button in the Maven tool window.
Future IDEA environment variables can be declared within the .ipr and .iml
with the syntax $VAR_NAME$. Anything undefined at project startup will prompt
the user for entry.

## Code style

If you plan to contribute code to the project, please check out our brief
[code style guide](Code+Style) to make sure your contribution fits seamlessly
with the rest of the code base.

## VCS workflow

1. People clone the main GitHub repository
1. People create feature branches in their personal cloned repository
1. People publish their work to possibly cooperate with others on the feature and when ready for review
   announce the branch asking for people to review. (_git push [mirrorRepo] myFeature_)
1. People reviewing the feature branch fetch the changesets from the public mirror and review running tests
   (_[git remote add mirrorRepo mirrorRepoUrl;] git fetch [mirrorRepo] myFeature_)
1. If there are no worries about the proposed changes then people say so, where there are issues start a
   debate on the email list.
1. When changes have been reviewed and agreed, one of the committing authors is agreed to merge the branch
   into their master and pushes to the GitHub main repository (and their public mirror repository of course)
   (_git checkout master;git pull; git merge --no-ff myFeature;git push_)

Notice the **\--no-ff** flag when merging.
Note that this workflow is applicable to all people whether they are
committing authors or not.  It's just that non-committing authors have to
convince a committing author to do the commit.  A consequence is that people
should not be advised to submit patches on JIRA issues, but instead to specify
where their feature branch is so it can be pulled. Obviously patches work as
well but the whole point is for everyone to publish their feature branches so
others can review them in a VCS context.

### Simplified workflow

Trivial spelling error fixes, extra tests that don't necessitate a change of
code but just extend the test coverage, and very simple (non-controversial)
bug fixes with their tests are currently exempt from having a review process.
Discretion on the part of committing developers is required here. (_git pull;
fix; commit; git push_) or (_git pull; git checkout -b myFix; fix; commit; git
checkout master; git pull; git merge --no-ff myFix;git push_)

## Upgrading Gradle

1. Install Gradle from an up to date Gradle Trunk.
1. Edit the build.gradle file to change the number of the wrapper to the new one.
1. Run "gradle wrapper"
1. If the wrapper is a snapshot the edit wrapper/gradle-wrapper.properties to add back in the missing
   snapshots from the repository URL
1. Check the result with "git diff".
1. Check the results with "gradlew clean test".
1. If on Linux check that the Bamboo build should work with "env -i ./bambooBuild"
1. If everything is successful commit the result "git commit -m ' . . .' -a"
1. Push to the mainline "git push"
1. Push to the personal mirror "git push --mirror . . . "
1. Wait expectantly to see if Bamboo works or not . . .

## The release plan

### 1\. Set version

In _build.gradle_ and in _doc.properties_ set the version property

Also update the ReleaseNotes.txt file.

### 2.Write what's new

Update the "What's new" section of the user guide as well as the
ReleaseNotes.txt file

### 3\. Tag the sources

After a **proper** release create a tag in the VCS with sources that were used
to make the release. Label the tab using the _release-x.x_ pattern.

### 4\. Build the project

Issue a full rebuild either for a snapshot

or a **proper** release

Make sure all demos work

### 5\. Upload the artifacts

Run the Release build plan on Bamboo, which will make all the artifacts
available for download.

### 6\. Update the maven repository

Make sure your codehaus credentials are in
_$USER_HOME/.gradle/gradle.properties_

or specify your credentials directly in the _uploadArchives_ task in
_build.gradle_ and add _uploadArchive_ task to the desired build task:

Check out that the artifacts have been successfully uploaded either at
<https://dav.codehaus.org/snapshots.repository/gpars/> for snapshots or at
<https://dav.codehaus.org/repository/gpars/> for **proper** releases. Within a
couple of hours the new **proper** release should be propagated into the maven
central repository at
<http://repo1.maven.org/maven2/org/codehaus/gpars/gpars/>.

### 7\. Clean up the snapshot repository

After a **proper** release the older snapshot artifacts should be removed
manually from the snapshot repository at
<https://dav.codehaus.org/snapshots.repository/gpars/>. Any webdav client,
like e.g. AnyClient (<http://www.anyclient.com/download.html>) should be
capable to do so.

### 8\. Upload the User Guide and docs

The generated User Guide at _/build/docs/manual_ should be uploaded to
<http://www.gpars.org/guide/> .

The javadoc and groovydoc folders should be copied to
<http://gpars.org/javadoc/index.html> and
[http://gpars.org/groovydoc/index.html
respectively](http://gpars.org/groovydoc/index.html%20respectively).

### 9\. Update the version

After a **proper** release the version in the build file has to be changed to
the next version.

### 10\. Update JIRA

**Proper** releases should be also closed in JIRA.

### 11\. Tell the world

People are impatiently waiting for the new GPars features so now it is the
highest time to tell them. New **proper** releases should be announced at the
following mailing lists and sites:

* announce@gpars.codehaus.org
* user@gpars.codehaus.org
* dev@gpars.codehaus.org
* user@groovy.codehaus.org
* <http://docs.codehaus.org/pages/createblogpost.action?spaceKey=GPARS>
* Any other relevant channel

## Xircles Project Page

<http://xircles.codehaus.org/projects/gpars>

After a **proper** release create a tag in the VCS with sources that were used
to make the release. Label the tab using the _release-x.x_ pattern.
