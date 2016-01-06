get "/", 			forward: "index.html", cache: 2.hours
get "/home", 		forward: "home.html", cache: 2.hours
get "/stale", 		forward: "StaleURLs.html", cache: 2.hours

get "/guide", 		forward: "/guide/index.html", cache: 2.hours
get "/qs",		    forward: "/quickstart/index.html", cache: 2.hours

get "/gpars", 	 	forward: "/gpars.groovy", cache: 2.hours
get "/readme", 		forward: "/Readme.html", cache: 2.hours
get "/todo", 		forward: "/Todo.html", cache: 2.hours  

get "/datetime", 	forward: "/datetime.groovy", cache: 2.minutes

get "/jk",		    forward: "/JonKerridgeBook/index.html", cache: 2.hours

