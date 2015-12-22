<!DOCTYPE html> 
<html> 
<head> 
<title>Installer for GPars.org</title> 
<meta charset="utf-8" /> 
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">

<link rel="stylesheet" href="./css/screen.css" type="text/css" media="screen, projection" /> 
<link rel="stylesheet" href="./css/core.css" type="text/css" media="screen, projection, print" />
 
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/redmond/jquery-ui.css">
  

<script>
  \$(function() {
    \$( "#accordion" ).accordion({
      active: false,
      heightStyle: 'content', 
      collapsible: true
    });

    \$( "#accordion1" ).accordion({
      active: false,
      heightStyle: 'content', 
      collapsible: true
    });

    \$( "#accordion2" ).accordion({
      active: false,
      heightStyle: 'content', 
      collapsible: true
    });


  });
</script>

</head> 

<body> 
<div class="red">  &nbsp; </div>

		<div><a href="http://gpars.org" target="_blank"><img src="/images/gpars-rgb-big.png"  class='divider2' style='width:33%'></a>
        	</div>

<div id="page"> 
  
  <div id="header" class="container"> 
  
      <div class="span-4 prepend-4"><a href="http://gpars.org/"  target="_blank" title="To Homepage"><img src="/images/gparsinstaller.jpg" style="width:195px;margin-top:22px;margin-left:30px;"></a></div>     	
  </div>


  <div id="graphic" class="container clear"> 
	<div class="span-9"> <img id="gembox" src="./images/ipad.png" width="99%"  style="margin-top:5px;" alt="Box of gems pic" /> </div> 
  	<div class="span-13 prepend-2 last"> 
  		<h1>Ready to install our toolkit ?</h1>
		<p>This is a <strong>self-contained toolkit</strong> that includes <strong>Jars</strong>, samples, documents and more.</p>
 
	  <div> 
    	<div id="download_btn"> <a href="http://gpars.org/download/1.2.1/" target="_blank"><span>Download</span></a> </div> 
    	<div id="addon_btn"> <a href="http://www.groovy-lang.org/download.html" target="_blank"><span>Get Groovy</span></a> </div> 
  	  </div> 
	</div>
	 
  </div>

  <div id="main" class="container clear">  

    <div class="span-8 prepend-1">
        <div id="right_content" >

	    <h2>All Our Downloads</h2>
      	<%= request.getAttribute('payload') %>    
	  </div>
	</div>

	<div class="span-9  prepend-1"> 
    	<div id="left_content"> 
      	<h2>Latest User Posts</h2> 
      	<%= request.getAttribute('users') %>    
	    </div> 	    
    </div>     

    <div class="span-9 prepend-1">      	
    	<div id="middle_content"> 
        <h2>Latest Developer Posts</h2> 
      	<%= request.getAttribute('developers') %>    
	    </div> 	    
    </div> 

    <div class="span-1 last">&nbsp;	</div>

	<div id="fastbar" class="container clear"> 
		<div>
			<img src="/images/star.svg"  class='divider'>
        	</div>

		<div class="fastlink span-5"> 	
    		<p> &nbsp; </p> 
  		</div> 

		<div id="contribute" class="fastlink span-7"> 	
    		<h3><a href="https://github.com/gpars" target="_blank">Contribute</a></h3> 
    		<p>How can I help the project ?</p> 
  		</div> 

		<div id="mailing" class="fastlink span-7"> 
    		<h3> <a href="Privacy.html" target="_blank">Privacy</a></h3> 
    		<p>How do you keep data secure ?</p> 
  		</div>
  		 
		<div id="questions" class="fastlink span-7 last">  
    		<h3><a href="#" target="_blank">FAQ</a></h3> 
    		<p>Frequently Asked Questions.</p> 
  		</div> 
	</div> 
</div>

  <div id="bottom">
  <div id="footer" class="container clear">

	<div class="span-8"> 	
    	<p> &nbsp; </p> 
  	</div> 


  <div class="span-4">
    <ul>
      <li><strong>GPars Installer</strong></li>
      <li><a href="GPars_History.html" target="_blank">About</a></li>
      <li><a href="https://groups.google.com/forum/#!forum/gpars-users" target="_blank">Blog</a></li>
      <li><a href="http://gpars.org/download/1.2.1/" target="_blank">Download</a></li>
      <li><a href="Developer_Zone.html" target="_blank">Join Us</a></li>
    </ul>
  </div>
  <div class="span-4">
    <ul>
      <li><strong>Developers</strong></li>
      <li><a href="TheGParsTeam.html" target="_blank">Developer List</a></li>
      <li><a href="https://groups.google.com/forum/#!forum/gpars-developers" target="_blank">Developer Forums</a></li>
    </ul>
  </div>
  <div class="span-3"> 
    <ul> 
      <li><strong>Help</strong></li> 
      <li><a href="https://docs.google.com/forms/d/10dy1PpZXwJb9awpRkDrZuawbEKRiqzG8qDtmZwr9BF0/viewform?hl=en&formkey=dFdYb2U1dFo2am9OZ1NTQUFuY0lSdXc6MQ#gid=0" target="_blank">Opinions</a></li>
      <li><a href="https://groups.google.com/forum/#!forum/gpars-users" target="_blank">Forums</a></li>
      <li><a href="https://groups.google.com/forum/#!forum/gpars-users" target="_blank">Report a bug</a></li>
      <li><a href="https://groups.google.com/forum/#!forum/gpars-developers" target="_blank">Request a feature</a></li>
    </ul> 
  </div> 
  <div id="copyright" class="span-3">
    GPars Installer<br />Copyright &#169; 2015<br /><a href="TheGParsTeam.html" target="_blank">The Whole GPars Team</a>
  </div> 
  <div class="span-3 last"> 	
    		<p> &nbsp; </p> 
  </div> 


  </div>
   
 </div> 
</div> 

</body> 
</html> 
