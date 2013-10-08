<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
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
<script src="js/vendor/jquery-1.10.2.min.js"></script>
<script src="js/vendor/jquery.form.js"></script>
<script>
	window.jQuery
			|| document
					.write('<script src="' + document.URL + 'js/vendor/jquery-1.10.2.min.js"><\/script>')
</script>

<script src="js/vendor/bootstrap.min.js"></script>
<script>
	var _gaq = [ [ '_setAccount', 'UA-XXXXX-X' ], [ '_trackPageview' ] ];
	(function(d, t) {
		var g = d.createElement(t), s = d.getElementsByTagName(t)[0];
		g.src = ('https:' == location.protocol ? '//ssl' : '//www')
				+ '.google-analytics.com/ga.js';
		s.parentNode.insertBefore(g, s)
	}(document, 'script'));
</script>

<script src="js/login_services.js"></script>
<script src="js/upload_services.js"></script>

<!-- REST Services Codes -->
<script>
	//
	// document ready
	// Initialize functions once document loading is complete
	//
	$(document).ready(
			function() 
			{
				// Auto refresh list if sign in for every 30 seconds
				var intVar=setInterval(function(){refresh()},30000);
				
				// Retreive file list
				//getFileList();

				// Get progress div
				var lProgress = $('#progress');

				// Initilaze AJAX upload form
				$('#file_upload_form').ajaxForm(
						{
							// Initialize status
							beforeSend : function(pXHR) 
							{
								pXHR.setRequestHeader('X-CSRF-Token', upload_csrf_token);
								var lPercentValue = '0%';
								lProgress.html(lPercentValue);
							}, // beforeSend
							// Update progress
							uploadProgress : function(pEvent, pPosition,
									pTotal, pPercentComplete) {
								// Update percentage complete
								var lPercentValue = pPercentComplete + '%';
								lProgress.html(lPercentValue);
							}, // beforeSend: function
							complete : function(pXHR) {
								if(pXHR.status == 200)
								{
								// Print out status and message
									$("#divLoginStatus")
									.html(
										'<div class="alert alert-success" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Opeartion Success</h4> Your opeartion just finished successfully</div>');;
									// Update progress and file list once update completes
									if (lJSONObject.status == 'success') {
											lProgress.html('100% Complete');
											getFileList();
									} // if
								}  // if
								else
								{
									fileOperationErrorStatus(pXHR);
								}  // else
							} // complete: function
						}); // $('form').ajaxForm
			}); // $(document).ready(function(){})
</script>
</head>
<body>
	<!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->

	<!-- This code is taken from http://twitter.github.com/bootstrap/examples/hero.html -->

	<div class="navbar navbar-inverse navbar-fixed-top" tabindex="1">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#">EConcept Storage</a>
				<div class="nav-collapse collapse">

					<form id="ulLogout" class="navbar-form pull-right"
						style="display: none;">
						<ul class="nav">
							<li class="dropdown"><a class="dropdown-toggle"
								data-toggle="dropdown"> <b><i
										class="icon-user icon-white"></i></b> <b id="bAccountFullName"
									name="bAccountFullName"></b> <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="#"
										onClick="$('#divUserModal').modal('show');"><span
											class="icon-cog"></span> User Information</a></li>
									<li class="divider"></li>
									<li><a href="#" onClick="clearUserCredentials();"><span
											class="icon-flag"></span> Sign Out</a></li>
								</ul></li>
						</ul>
					</form>
				</div>
				<!--/.nav-collapse -->

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
					<p>THIS IS AN EXPERMINTE SITE, PLEASE DO NOT UPLOAD ANY
						SENSITIVE INFORMATION</p>
					<p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
						KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
						WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
						AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
						HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
						WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
						OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
						DEALINGS IN THE SOFTWARE.</p>
					<p>BY USING THE SERVICE, YOU AGREE TO THIS AGREEMENT</p>
				</div>
				<div class="span6">
					<div class="well">
						<legend>Existing users login</legend>
						<b class="divider"></b>
						<div id="divLoginStatus"></div>
						<!-- divLoginStatus -->
						<div class="control-group">
						<label class="control-label" for="txtLoginUser">User Name</label>
						<div class="input-prepend">
							<span class="add-on"><i class="icon-user"></i></span> <input
								class="span3" placeholder="Type your username" type="text"
								id="txtLoginUser" required=""/>
						</div>
						</div>
						<div class="control-group">
						<label class="control-label" for="txtLoginPassword">Password</label>
						<div class="input-prepend">
							<span class="add-on"><i class="icon-lock"></i></span> <input
								class="span3" placeholder="Type your password" type="password"
								id="txtLoginPassword" required=""/>
						</div>
						</div>
						<div>
							<input class="btn btn-primary" type="button" value="Sign In"
								id="btnLogin" onclick="confirmUserCredentials();"  />
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="services" style="display: none;">
				<div id="divUploadStatus"></div>
				<ul id="ulSeviceTabs" class="nav nav-tabs" data-tabs="tabs">
					<li class="active"><a href="#divFiles" data-toggle="tab">Files</a></li>
					<li><a href="#divUpload" data-toggle="tab">Upload</a></li>
				</ul>
				<div id="divServiceTabContents" class="tab-content">
					<div id="divFiles" class="tab-pane active">
						<div id="divFilelist"></div>
					</div>
					<div id="divUpload" class="tab-pane">
						<div>
							<h1>Upload with Web Based Form</h1>
							<p>Choose file and upload it!</p>
							<a class="btn" id="btnFileBrowse" onClick="$('#fileUploadFile').click();">Browse</a>
								<label id="lblFileBrowseName" for="txtFileUpload">No File is Selected</label>
								<br /></br> 
							<form id="file_upload_form" action="rest/file/upload"
								method="post" enctype="multipart/form-data">
								<input class="btn" type="file" name="fileUploadFile"
									id="fileUploadFile" style="display:none" onChange="$('#lblFileBrowseName').html($('#fileUploadFile').val())"/> 
								<input class="btn" type="submit" value="Upload with Form" />
							</form>
							<div id="progress"></div>
						</div>
						<legend></legend>
						<div>
							<h1>Upload with URI</h1>
							<p>Input an URI and let Server download it for you!</p>
							<p>
								<input type="text" id="txtUploadFileURI" />
							</p>
							<p>
								<input class="btn" type="button" value="Update With URI"
									id="btnUpdateWithURI" onclick="updateWithURI();" />
							</p>
						</div>
					</div>
				</div>
		</div>
		<!-- /backend  -->
		<hr>

		<footer>
			<p>ECONCEPT STORAGE 2013</p>
		</footer>

	</div>
	<!-- /container -->


	<div class="modal fade" id="divUserModal" role="dialog"
		aria-labelledby="divUserModalLabel" tabindex="-1"
		style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Account Info</h4>
				</div>
				<div class="modal-body">
					<!-- Text input-->
					<div class="control-group">
						<label class="control-label" for="txtModalLoginUser">User
							Name</label>
						<div class="controls">
							<div class="input-prepend">
								<span class="add-on"><i class="icon-user"></i></span> <input
									id="txtModalLoginUser" name="txtModalLoginUser" type="text"
									placeholder="Type your username" class="input-xlarge"
									required="">
							</div>
						</div>
					</div>

					<!-- Password input-->
					<div class="control-group">
						<label class="control-label" for="txtModalLoginPassword">Password</label>
						<div class="controls">
							<div class="input-prepend">
								<span class="add-on"><i class="icon-lock"></i></span> <input
									id="txtModalLoginPassword" name="txtModalLoginPassword"
									type="password" placeholder="Type your password"
									class="input-xlarge" required="">
								<p class="help-block">help</p>
							</div>
						</div>
					</div>

					<!-- Text input-->
					<div class="control-group">
						<label class="control-label" for="txtModalLoginLastName">Last
							Name</label>
						<div class="controls">
							<input id="txtModalLoginLastName" name="txtModalLoginLastName"
								type="text" placeholder="Type your last name"
								class="input-xlarge" required="">
							<p class="help-block">help</p>
						</div>
					</div>

					<!-- Text input-->
					<div class="control-group">
						<label class="control-label" for="txtModalLoginFirstName">First
							Name</label>
						<div class="controls">
							<input id="txtModalLoginFirstName" name="txtModalLoginFirstName"
								type="text" placeholder="Type your first name"
								class="input-xlarge" required="">
						</div>
					</div>

					<!-- Text input-->
					<div class="control-group">
						<label class="control-label" for="txtModalLoginEmail">Email</label>
						<div class="controls">
						<div class="input-prepend">
								<span class="add-on"><i class="icon-envelope"></i></span>
							<input id="txtModalLoginEmail" name="txtModalLoginEmail"
								type="text" placeholder="Type your email" class="input-xlarge"
								required="">
						</div>
						</div>
					</div>
					<div></div>
					<!--<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						  <button type="button" class="btn btn-primary">Save
							changes</button>
					</div>-->
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>

