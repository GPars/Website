// this sample wont work without OAuth user token and https:
import groovy.json.JsonSlurper;

def q="https://twitter.com/search?q=%40jnorthr"

def q2 = "https://api.twitter.com/1.1/search/tweets.json?q=%40jnorthr"

def feed = new JsonSlurper().parse(q2.toURL());


println "--- the end ---"