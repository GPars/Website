get "/", 			forward: "index.html", cache: 2.hours
get "/home", 		forward: "home.html", cache: 2.hours

get "/guide", 		forward: "/guide/index.html", cache: 2.hours
get "/qs",		    forward: "/quickstart/index.html", cache: 2.hours

get "/installer", 	 forward: "/gpars.groovy", cache: 2.minutes

get "/readme", 		forward: "/README.html", cache: 2.hours
get "/todo", 		forward: "/Todo.html", cache: 2.hours  
get "/stale", 		forward: "StaleURLs.html", cache: 2.hours

get "/jk",		    forward: "/JonKerridgeBook/index.html", cache: 2.hours
get "/structure", 	 forward: "WebsiteStructure/index.html", cache: 2.hours

// used as testbed for includes/header.gtpl & footer.gtpl
get "/datetime", 	forward: "/datetime.groovy", cache: 2.minutes

