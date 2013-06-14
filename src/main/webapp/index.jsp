<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <style>
            body {
                padding-top: 60px;
                padding-bottom: 40px;
            }
        </style>
        <link rel="stylesheet" href="css/bootstrap-responsive.min.css">
        <link rel="stylesheet" href="css/main.css">

        <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <script src="js/vendor/jquery-1.9.1.min.js"></script>
        <script src="js/vendor/jquery.form.js"></script>
        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>

        <script src="js/vendor/bootstrap.min.js"></script>
        <script>
            var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
            (function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
            g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
            s.parentNode.insertBefore(g,s)}(document,'script'));
        </script>
        
        <!-- REST Services Codes -->
  		<script>
    	  //
    	  // document ready
    	  // Initialize functions once document loading is complete
    	  //
    	    $(document).ready(function () 
    	    	{
    	    	// Retreive file list
    	    	getFileList();
    	    	
    	    	// Get progress div
    	        var lProgress = $('#progress');
    	        
    	    	// Initilaze AJAX upload form
    	        $('#file_upload_form').ajaxForm({
    	        	// Initialize status
    	            beforeSend: function () {
    	                var lPercentValue = '0%';
    	                lProgress.html(lPercentValue);
    	            },  // beforeSend: function
    	            // Update progress
    	            uploadProgress: function (pEvent, pPosition, pTotal, pPercentComplete) {
    	                // Update percentage complete
    	            	var lPercentValue = pPercentComplete + '%';
    	                lProgress.html(lPercentValue);
    	            },  // beforeSend: function
    	            complete: function (pXHR) 
    	            {
    	            	// Analysis message as a JSON object from responseText
    	            	var lJSONObject = jQuery.parseJSON(pXHR.responseText)
    	                $('#result').append('<p>' + lJSONObject.status + '</p>');
    	                $('#result').append('<p>' + lJSONObject.statusmessage + '</p>');
    	                
    	                // Update progress and file list once update completes
    	                if(lJSONObject.status=='success')
    	                {
    	                	lProgress.html('100% Complete');
    	                	getFileList();
    	                }  // if
    	                
    	            	$('#result').html('');
    	            }  // complete: function
    	        });  // $('form').ajaxForm
    	    });  // $(document).ready(function(){})
    	    
    	    //
    	    // updateWithURI
    	    // Compute user information and file uri as a JSON message and send to server
    	    // to download file
    	    // Input: None
    	    // Output: None
    	    //
    	    function updateWithURI() {
    	    	// Compute JSON data
    	        var lJSONData = "{";
    	        lJSONData = lJSONData + "\"parameter\":\"" + $("#txtUploadFileURI").val() + "\",";
    	        lJSONData = lJSONData + "\"account_id\":\"" + $("#txtAccount_ID").val() + "\"";
    	        lJSONData = lJSONData + "}";

    	        // AJAX call
    	        $.ajax({
    	            url: "rest/uploadwithuri",
    	            type: "POST",
    	            data: lJSONData,
    	            dataType: "json",
    	            contentType: "application/json; charset=utf-8",
    	            success: function (pData) 
    	            {
    	            	// Print out status and message
    	            	$('#result').html('');
    	                $('#result').append('<p>' + pData.status + '</p>');
    	                $('#result').append('<p>' + pData.statusmessage + '</p>');
    	                getFileList();
    	            }  // success: function(pData)
    	            ,
    	            error: function (xhr, status, thrown) {
    	                $('#result').html(xhr.responseText + " " + status + " " + thrown);
    	            }  // error : function (xhr, status, thrown)
    	        });  // $.ajax
    	    }  // function updateWithURI
    	    
    	    //
    	    // getFileList
    	    // Retrieve file list
    	    // Compute user information as JSON object
    	    // Input: None
    	    // Output: None
    	    function getFileList() {
    	    	// Compute JSON information
    	        var lJSONData = "{";
    	        lJSONData = lJSONData + "\"account_id\":\"" + $("#txtAccount_ID").val() + "\"";
    	        lJSONData = lJSONData + "}";

    	        $.ajax({
    	            url: "rest/getfilelist",
    	            type: "POST",
    	            data: lJSONData,
    	            dataType: "json",
    	            contentType: "application/json; charset=utf-8",
    	            success: function (pData) {
    	            	$('#result').html('');
    	                $('#result').append('<p>' + pData.status + '</p>');
    	                
    	                // If the operation status is error, display it and end the
    	                // current function
    	                if(pData.status == 'error')
    	                {
    	                    $('#result').append('<p>' + pData.statusmessage + '</p>');
    	                    return;
    	                }  // if
    	                
    	                // Clear the file list to ensure newest list is updated
    	                $('#filelist').html('');
    	                
    	                // Get the file list length
    	                var lFileListLength = pData.statusmessage.length;
    	                
    	                // Only need to go through file list if there is one or more,
    	                // else print the message to show no file and end the function
    	                if(lFileListLength == 0)
    	                	{
    	                	 $('#filelist').append('There is no file exist for this user. Upload some file through URI or Web based upload.');
    	                	 return;
    	                	}  // if
    	                	
    	                // Prepare table to display file list
    					var lTableHTMLString = '<table class="table table-striped">';
    					// Table Header
    					lTableHTMLString = lTableHTMLString + '<thead>';
    					lTableHTMLString = lTableHTMLString + '<tr>';
    	            	lTableHTMLString = lTableHTMLString + '<th>#</td>';
    	            	lTableHTMLString = lTableHTMLString + '<th>File Name</td>';
    	            	lTableHTMLString = lTableHTMLString + '<th></td>';
    	    			lTableHTMLString = lTableHTMLString + '</tr>';
    	    			lTableHTMLString = lTableHTMLString + '</thead>';
    					
    	    			// Loop through the file list and concate file into table row
    	                for(var lFileIndex = 0; lFileIndex < lFileListLength; lFileIndex++)
    	                {
    	                	lTableHTMLString = lTableHTMLString + '<tr>';
    	                	// File Name
    	                	lTableHTMLString = lTableHTMLString + '<td>' +  (lFileIndex + 1) + '</td>';
    	                	lTableHTMLString = lTableHTMLString + '<td><a href="rest/getfile?account_id=' + $("#txtAccount_ID").val() + '&filename=' + pData.statusmessage [lFileIndex] + '">' + pData.statusmessage [lFileIndex] + '</a></td>';
    	                	// Delete button for the particular file
    	                	lTableHTMLString = lTableHTMLString + '<td><input class="btn" type="button" onclick="deleteFile(\'' + pData.statusmessage [lFileIndex]  + '\');" value="Delete"/></td>';
    	        			lTableHTMLString = lTableHTMLString + '</tr>';
    	                }  // for
    	                
    	                lTableHTMLString = lTableHTMLString + '</table>';
    	                
    	            	// Add the table into the file list div
    	                $('#filelist').append(lTableHTMLString);
    	            }  // success: function(pData)
    	            ,
    	            error: function (xhr, status, thrown) {
    	                $('#result').html(xhr.responseText + " " + status + " " + thrown);
    	            }  // error : function (xhr, status, thrown)
    	        });  // $.ajax
    	    }  // function getFileList
    	    
    	     //
    	     // deleteFile
    	     // Compute the JSON message to send to server to delete the file
    	     // for the particular user
    	     // Input: pFileName the file to be deleted
    	     // OUtput: None
    	     //
    	     function deleteFile(pFileName) {
    	         // Compute JSON data
    	    	 var lJSONData = "{";
    	        lJSONData = lJSONData + "\"parameter\":\"" + pFileName + "\",";
    	        lJSONData = lJSONData + "\"account_id\":\"" + $("#txtAccount_ID").val() + "\"";;
    	        lJSONData = lJSONData + "}";

    	        // Perform AJAX Call
    	        $.ajax({
    	            url: "rest/deletefile",
    	            type: "POST",
    	            data: lJSONData,
    	            dataType: "json",
    	            contentType: "application/json; charset=utf-8",
    	            success: function (pData) {
    	            	$('#result').html('');
    	                $('#result').append('<p>' + pData.status + '</p>');
    	                $('#result').append('<p>' + pData.statusmessage + '</p>');
    	                getFileList();
    	            }  // success: function(pData)
    	            ,
    	            error: function (xhr, status, thrown) {
    	                $('#result').html(xhr.responseText + " " + status + " " + thrown);
    	            }  // error : function (xhr, status, thrown)
    	        });  // $.ajax
    	    }  // function getFileList 
  		
  	     // confirmUserCredentials
  	     // Confirm the user login credentials
  	     // Input: None
  	     // OUtput: None
  	     //
  	     function confirmUserCredentials() {
  	         // Compute JSON data
  	    	 var lJSONData = "{";
  	        lJSONData = lJSONData + "\"account_id\":\"" + $("#txtLoginAccountID").val() + "\",";
  	        lJSONData = lJSONData + "\"account_password\":\"" + $("#txtLoginAccountPassword").val() + "\",";
  	        lJSONData = lJSONData + "\"operation\":\"CONFIRM\"";
  	        lJSONData = lJSONData + "}";

  	        // Clear login status
  	      	$("#divLoginStatus").html('');
  	        
  	        // Perform AJAX Call
  	        $.ajax({
  	            url: "rest/account/account",
  	            type: "POST",
  	            data: lJSONData,
  	            dataType: "json",
  	            contentType: "application/json; charset=utf-8",
  	            success: function (pData) 
  	            {
  	            	if(pData.statusmessage == "CONFIRM")
  	            	{
  	            		// Set user credential
  	            		$("#txtAccount_ID").val($("#txtLoginAccountID").val());
  	            		$("#divAccountMessage").html($("#txtLoginAccountID").val());
  	            		$("#fileUploadFile").val('');
  	            		
  	            		// Refresh file list
  	            		getFileList();
  	            		
  	            		$("#login").slideUp();
  	            		
  	            		$("#ulLogout").show();
  	            		$("#services").show();
  	            		
  	            		$("#txtLoginAccountID").val('');
  	            		$("#txtLoginAccountPassword").val('');
  	            	}  // if
  	            	else
  	            	{
  	            		$("#txtLoginAccountPassword").val('');
  	            		
  	            		$("#divLoginStatus").html('<div class="alert alert-error" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Login Error</h4> Incorrect Username or Password</div>');
  	            	}  // else if
  	            }  // success: function(pData)
  	            ,
  	            error: function (xhr, status, thrown) {
  	                $('#result').html(xhr.responseText + " " + status + " " + thrown);
  	            }  // error : function (xhr, status, thrown)
  	        });  // $.ajax
  	    }  // function confirmUserCredentials
  	    
  	  // confirmUserCredentials
 	     // Confirm the user login credentials
 	     // Input: None
 	     // OUtput: None
 	     //
 	     function clearUserCredentials() {

 	    	 // TODO Need to implement a more secure algorithm
 	    	 
 	    	$("#txtLoginAccountID").val('');
  	        $("#txtLoginAccountPassword").val('');
  	      	
 			$("#services").hide();
 			$("#ulLogout").hide();
     		$("#login").slideDown();
     		
      		// Clear user credentials
      		$("#txtAccount_ID").val('');
      		$("#divAccountMessage").html('');
      		$("#fileUploadFile").val('');
     		$("#filelist").html('');
     		$("txtUploadFileURI").val('');
 	    }  // function clearUserCredentials
  		</script>
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->

        <!-- This code is taken from http://twitter.github.com/bootstrap/examples/hero.html -->

        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="#">EConcept Storage</a>
                     <div class="nav-collapse collapse">

                        <form id="ulLogout" class="navbar-form pull-right" style="display:none;">
                     	<ul class="nav" >
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown"><div id="divAccountMessage"></div><b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Account Information</a></li>
                                    <li class="divider"></li>
                                    <li><a href="#" onClick="clearUserCredentials();">Logout</a></li>
                                </ul>
                            </li>
                        </ul>
                        </form>
                    </div><!--/.nav-collapse -->

                </div>
            </div>
        </div>
        <div class="container">
            <div id="login">
            <!-- Example row of columns -->
            <div class="row">
                <div class="span6">
                <h1>THIS IS AN EXPERIMENT SITE</h1>
                <p>AGREEMENT:</p>
                <p>THIS IS AN EXPERMINTE SITE, PLEASE DO NOT UPLOAD ANY SENSITIVE INFORMATION</p>
                <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
					IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
					FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
					AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
					LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
					OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
					THE SOFTWARE.</p>
				<p>BY USING THE SERVICE, YOU AGREE TO THIS AGREEMENT</p>
                </div>
                <div class="span6">
                <div class = "well">
                	<legend>Sign in</legend>
                	<div id="divLoginStatus"></div> <!-- divLoginStatus -->
                    <input class="span3" placeholder="ID" type="text" id="txtLoginAccountID" />
                    <input class="span3" placeholder="Password" type="password" id="txtLoginAccountPassword" />
                    <label class = "checkbox">
                    	<input type="checkbox" name="remember" value = "1"> Remember Me
                    </label>
                    <input class="btn-info btn" type="button" value="Login" id="btnLogin" onclick="confirmUserCredentials();"/>
				</div>
				</div>
             </div>
			</div> <!-- /login  -->
			 <div id= "services" style="display: none;">
			 <div class="hero-unit">
                <div id = "message"></div>
                <p>Current Uploaded Files:</p>
                <div id = "filelist"></div>
                <p><input class="btn btn-primary btn-large" type="button" value="RefreshFileList" id="btnRefreshFileList" onclick="getFileList();"/></p>
            </div>

            <!-- Example row of columns -->
            <div class="row">
                <div class="span4">
                    <h2>Upload with Web Based Form</h2>
                    <p>Choose file and upload it!</p>
                    <form id="file_upload_form" action="rest/upload" method="post" enctype="multipart/form-data">
					  <input class="btn" type = "file" name ="fileUploadFile" id="fileUploadFile"/>
					  <input class="btn" type = "text" name ="txtAccount_ID"  id="txtAccount_ID" style="display:none;"/>
					  <br/></br>
					  <input class="btn" type="submit" value="Upload"/>
					</form>
					<div id = "progress"></div>
                </div>
                <div class="span4">
                    <h2>Upload with URI</h2>
                    <p>Input an URI and let Server download it for you!</p>
                    <p><input type="text" id="txtUploadFileURI" /></p>
                    <p><input class="btn" type="button" value="Update With URI" id="btnUpdateWithURI" onclick="updateWithURI();"/></p>
               </div>
                <div class="span4">
                    <h2>Update Staus</h2>
                    <p>File Upload Status</p>
                    <p id = "result"></p>
                </div>
                <div class="span4">
                </div>
            </div>
			 </div> <!-- /backend  -->
            <hr>

            <footer>
                <p>ECONCEPT STORAGE 2013</p>
            </footer>

        </div> <!-- /container -->
    </body>
</html>

