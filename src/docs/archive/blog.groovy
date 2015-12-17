
//log.info "Setting blog attribute "

String blogtext = """<div id="main" class="container clear">  
  <div class="span-16"> 
  <div id="left_content"> 
    <h2>Latest Posts</h2>
""";

// define the url, change it to whatever rss url you like; this one is an xml feed
def url = "https://groups.google.com/forum/feed/gpars-users/msgs/rss.xml?num=15" 
def rss = new XmlSlurper().parse(url) 

//println rss.channel.title
rss.channel.item.each {
    blogtext += "<div class=\"news_item\">"
    blogtext += "<h3><a href=${it.link} target=\"_blank\">${it.title}</a></h3>"
    
    def pd = it.pubDate.toString();
    int i = pd.indexOf("+");
    if (i>-1) {pd = pd.substring(0,i)}
    
    def author = it.creator.toString();
    blogtext +=  "<span class=\"date\">Published ${pd} by ${author}</span>"
    def desc = it.description.toString()
    desc = desc.replaceAll(~/'/, "&apos;")
    blogtext +=  "<p>"+desc+"</p>";
    blogtext +=  "<br /></div>\n"
}

blogtext +=  "</div></div></div><br />\n"
//println blogtext.toString()

request.setAttribute 'payload', blogtext.toString()
forward '/WEB-INF/pages/blog.gtpl'