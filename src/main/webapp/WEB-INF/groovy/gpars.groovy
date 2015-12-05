// http://stackoverflow.com/questions/4555128/how-to-correctly-read-url-content-with-utf8-chars
// http://www.leveluplunch.com/groovy/examples/parse-rss-xml-feed-with-xmlsluper/
log.info "Setting blog attribute "
def flag=true;
def rss = null;

String blogtext = """<div id="accordion">
""".toString();

// define the url, change it to whatever rss url you like; this one is an xml feed
try{
    def url = "https://groups.google.com/forum/feed/gpars-users/msgs/rss.xml?num=30" 
	rss = new XmlSlurper().parse(url) 
}
catch(Exception x){
    def tx = (x.message.size()>200)?x.message.substring(0,200):x.message;
    println "failed:"+tx;
    flag=false;
    blogtext += "<h3>Failed to get Blog</h3>"
	blogtext+="<p>"+tx+"</p><br />";
}

//println rss.channel.title
// construct a jquery  accordion entry (h3+div) for each item in the rss feed
if (flag)
{
  rss.channel.item.each {
    blogtext += "<h3><a href=${it.link} target=\"_blank\">${it.title}</a></h3>"
    blogtext += "<div>"
    blogtext += "<p>"
    def pd = it.pubDate.toString();
    int i = pd.indexOf("+");
    if (i>-1) {pd = pd.substring(0,i)}
    
    def author = it.creator.toString();
    blogtext +=  "<span class=\"date\">Published ${pd} by ${author}</span>"
    blogtext += "</p>"

    def desc = it.description.toString()
    desc = desc.replaceAll(~/’/, "&apos;")
    blogtext +=  "<p>"+desc+"</p><br />";
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
    blogtext += "<h3><a href=${it.link} target=\"_blank\">${pd}${it.title}</a></h3>"
    
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
    if (flag){flag=false;log.info desc;}

    desc = desc.replaceAll(~/’/, "&apos;")
    //desc = desc.replaceAll(~/<br />/, " ")
    blogtext +=  desc.trim();
    blogtext +=  "<br /><a href=${it.link} class=\"more\" target=\"_blank\">Read more . .</a>"
    
    blogtext +=  "</div>\n"
  } // end of if
} // end of each

blogtext +=  "</div><br />\n"

// plug this app's results back into the http request
request.setAttribute 'developers', blogtext.toString()

log.info "Forwarding to the gpars.gtpl"

// call the html structure around both of these feeds
forward '/WEB-INF/pages/gpars.gtpl'
