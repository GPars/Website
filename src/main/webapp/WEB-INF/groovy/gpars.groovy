import com.jim.GParsDownloader;

// http://stackoverflow.com/questions/4555128/how-to-correctly-read-url-content-with-utf8-chars
// http://www.leveluplunch.com/groovy/examples/parse-rss-xml-feed-with-xmlsluper/
// http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests?lq=1  <- better example
log.info "Setting GPars attribute "
def flag=true;
def rss = null;

// build JQuery accordion string
String blogtext = """<div id="accordion">
""".toString();

// define the url, change it to whatever rss url you like; this one is an xml feed
try{
    def url = "https://groups.google.com/forum/feed/gpars-users/msgs/rss.xml?num=30" 
    // catch HttpURLConnection#getResponseCode()==500 here ?? 
    rss = new XmlSlurper().parse(url) 
}
catch(Exception x){
    // or try  HttpURLConnection#getErrorStream() to see actual failure
    def tx = (x.message.size() > 200)? x.message.substring(0,200) : x.message;
    println "failed:"+tx;
    flag=false;
    blogtext += "<h3>Failed to get Blog</h3>"
    blogtext+="<p>"+tx+"</p><br />";
} // end of catch


//println rss.channel.title
// construct a jquery  accordion entry (h3+div) for each item in the rss feed if true flag indicates no error detected
if (flag)
{
  // walk each xml entry 
  rss.channel.item.each {
    blogtext += "<h3>${it.title}</h3>"
    blogtext += "<div>"
    blogtext += "<p>"
    def pd = it.pubDate.toString();
    int i = pd.indexOf("+");
    if (i>-1) {pd = pd.substring(0,i)}
    
    blogtext +=  "Dated ${pd}"
    blogtext += "</p>"

    def desc = it.description.toString();
    desc = desc.replaceAll(~/’/, "&apos;")
    desc = desc.replaceAll(~/'/, "&apos;")
    blogtext +=  "<p>"+desc+"<br />";
    blogtext +=  "<a href=${it.link} target=\"_blank\">Read more ...</a></p>";
    blogtext +=  "</div>\n"
  }
}
blogtext +=  "</div>\n"

request.setAttribute 'users', blogtext.toString()


// =======================================
blogtext = """<div id="accordion1">
""".toString();

// now, we  do the same thing again but for the developer posts
// define the url, change it to whatever rss url you like; this one is an xml feed
url = "https://groups.google.com/forum/feed/gpars-developers/msgs/rss.xml?num=30" 
rss = new XmlSlurper().parse(url) 
int ct = 0;

// build a jquery acordion for developer posts
rss.channel.item.each {

  if (ct++ < 13)
  {
    def pd = it.pubDate.toString();
    int i = pd.indexOf("+");
    if (i>-1) {pd = pd.substring(0,i)}
    i = pd.indexOf(", ");
    if (i>-1) {pd = pd.substring(i+1)}
    i = pd.indexOf(" 201");
    if (i>-1) {pd = pd.substring(0, i+5)}
    pd+=" - " 
    blogtext += "<h3>${pd} ${it.title}</h3>"
    
    blogtext += "<div>"
    blogtext += "<p>"

    def author = it.creator.toString();

    pd = it.pubDate.toString();
    i = pd.indexOf("+");
    if (i>-1) {pd = pd.substring(0,i)}

    blogtext +=  "<span class=\"date\">Published ${pd}"
    if (author.size() > 0) {blogtext +=  " by ${author}"}
    blogtext +=  "</span>"
    blogtext += "</p>"

    def desc = it.description.toString();
    if (flag){flag=false;}

    desc = desc.replaceAll(~/’/, "&apos;")
    //desc = desc.replaceAll(~/<br />/, " ")
    blogtext +=  desc.trim();
    blogtext +=  "<p><a href=${it.link} class=\"more\" target=\"_blank\">Read more . .</a></p>"
    
    blogtext +=  "</div>\n"
  } // end of if
} // end of each

blogtext +=  "</div><br />\n"

// plug this app's results back into the http request
request.setAttribute 'developers', blogtext.toString()

// plug this app's download results back into the http request
GParsDownloader obj = new GParsDownloader();
request.setAttribute 'payload', obj.getAccordion().toString()


// call the html structure around both of these feeds
log.info "Forwarding to the gpars.gtpl"
forward '/WEB-INF/pages/gpars.gtpl'