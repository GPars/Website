# Website
Source for the GPars website

4 Nov.2015 - JNorthr
Initial Commit of GPars Documentation reworked using asciidoctor markup.

There is much remaining to do, so just wanted to get this set of stuff into GitHub before it is lost.

There are three gradle build scripts included plus the gradle 2.8 wrapper. To build/deploy etc. do this:
1. open terminal
2. clone this repo
3. change directories into root folder of this repo.
4. ./gradlew check
<or> gradlew check
to confirm all dependencies have been downloaded 

Then to rebuild html from .adoc's do this:
5. ./gradlew asciidoctor

To build a deployable war file run script with no parms - this will run gradle with default tasks creating .war
6. ./gradlew

To test this war file on your own system before uploading, the gretty.gradle script starts a local jetty7 server so you can then open a browser to localhost:8080  to see how the website looks and make any tweeks first.

cd /Users/jim/Dropbox/GParsDocs/build/libs 
./gradlew -b gretty.gradle appRun

7. When ready to deploy you will need a client account on any CloudFoundry provider; the third gradle script is to deploy our docs to a cloud foundry instance. Typically:

cd /Users/jim/Dropbox/GParsDocs/
./gradlew -b deploy.gradle cfLogin cfDelete cfPush

should create a running web app at the URI declared within the cloudfoundry{} closure; include your own credentials before first run; this app lives in the anynines.com PaaS provider in Berlin. The 'target' and 'uri' values point to that provider. You can deploy this .war file other splaces if you join a different provider.

If you need a tut or how-to, read this: http://cloudnines.de.a9sapp.eu/
  
