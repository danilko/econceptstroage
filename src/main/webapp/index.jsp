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
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" media="screen" href="css/bootstrap.min.css">
<link rel="stylesheet" media="screen" href="css/bootstrap-theme.min.css">
<style>
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>
<link rel="stylesheet" href="css/main.css">

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
</head>
<body>
	<!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->

	<!-- This code is taken from http://twitter.github.com/bootstrap/examples/hero.html -->

	<div class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					</button>
				<a class="navbar-brand" href="#">Econcept Storage</a>
			</div>
			<div id="divLoginServices" style="display: none">
			<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="#" onClick="$('#services').show();$('#account').hide();">Services</a></li>
				<li><a href="#" onClick="$('#services').hide();$('#account').show();">Account</a></li>
			</ul>
				<form class="navbar-right">
					<ul class="nav navbar-nav">
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown"> <b><i
									class="glyphicon glyphicon-user"></i></b> <b id="bAccountFullName"
								name="bAccountFullName"></b> <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<!-- <li><a href="#" onClick="$('#divUserModal').modal('show');"><span
										class="glyphicon glyphicon-user"></span> User Information</a></li>
								<li class="divider"></li> -->
								<li><a href="#" onClick="clearUserCredentials();"><span
										class="glyphicon glyphicon-flag"></span> Sign Out</a></li>
							</ul></li>
					</ul>
				</form>
			</div>
			</div>
			<!--/.nav-collapse -->

		</div>
	</div>
	<div class="container">
		<div id="login" class="row">
			<div>
				<blockquote>
					<h2>AGREEMENT</h2>
					<p>
						<strong> THIS IS AN EXPERIMENT, PLEASE DO NOT UPLOAD ANY
							SENSITIVE INFORMATION </strong> <br /> <br /> THE SOFTWARE IS PROVIDED
						"AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
						INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
						FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
						SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
						DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
						OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
						SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. <br /> <br />
						<strong> BY USING THE SERVICE, YOU AGREE TO THIS
							AGREEMENT </strong>
					</p>
					<br />
					<p class="text-right">Econcept Storage Team</p>
				</blockquote>
			</div>
			<div class="col-md-6 col-md-offset-3" >
				<fieldset>
					<legend>Existing users login</legend>
					<div id="divLoginStatus"></div>
					<form class="form-horizontal" role="form">
					<div class ="form-group">
					<!-- divLoginStatus -->
					<label class="col-lg-2 control-label" for="txtLoginUser">Username</label>
					<div class="col-lg-10 input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-user"></i></span> <input type="text"
							class="form-control" placeholder="Type your username"
							id="txtLoginUser">
					</div>
					</div>
					<div class ="form-group">
					 <label class="col-lg-2 control-label" for="txtLoginPassword">Password</label>
					<div class="col-lg-10 input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-lock"></i></span> <input type="password"
							class="form-control" placeholder="Type your password"
							id="txtLoginPassword">
					</div>
					</div>
					<button class="btn btn-lg btn-primary btn-block" type="button"
						id="btnLogin" onclick="confirmUserCredentials();">Sign In</button>
					
					</form>
				</fieldset>
			</div>
		</div>

		<div id="services" style="display: none;" class="row">
			<div id="divUploadStatus"></div>
			<ul id="ulSeviceTabs" class="nav nav-tabs" data-tabs="tabs">
				<li class="active"><a href="#divFiles" data-toggle="tab">Files</a></li>
				<li><a href="#divUpload" data-toggle="tab">Upload</a></li>
			</ul>
			<div id="divServiceTabContents" class="tab-content">
				<div id="divFiles" class="tab-pane active">
					<br />
					<div id="divFilelist"></div>
				</div>
				<div id="divUpload" class="tab-pane">
					<br />
					<div id="divFormUpload" class="panel panel-primary">
						<!-- Default panel contents -->
						<div id="divFormUploadHeader" class="panel-heading">Upload
							with Web Based Form</div>
						<div id="divFormUploadBody" class="panel-body">
							<p>Choose file and upload it!</p>
							<a class="btn" id="btnFileBrowse"
								onClick="$('#fileUploadFile').click();">Browse</a> <label
								id="lblFileBrowseName" for="txtFileUpload">No File is
								Selected</label>
							<div id="divUploadFileProgress" class="progress">
								<div id="divUploadFileProgressBar" class="progress-bar"
									role="progressbar" aria-valuenow="0" aria-valuemin="0"
									aria-valuemax="100" style="width: 0%;">
									<span id="spanUploadFileProgressBarSROnly" class="sr-only">0%
										Complete</span>
								</div>
							</div>
							<br />
							<form id="file_upload_form" action="rest/file/upload"
								method="post" enctype="multipart/form-data">
								<input class="btn" type="file" name="fileUploadFile"
									id="fileUploadFile" style="display: none"
									onChange="$('#lblFileBrowseName').html($('#fileUploadFile').val())" />
								<input class="btn" type="submit" value="Upload with Form" />
							</form>
						</div>
						<!-- divFormUploadBody -->
					</div>
					<!-- divFormUploadHeader -->
					<div id="divURIUpload" class="panel panel-primary">
						<!-- Default panel contents -->
						<div id="divURIUploadHeader" class="panel-heading">Upload
							with URI</div>
						<div id="divURIUploadBody" class="panel-body">
							<p>Input an URI and let Server download it for you!</p>
							<label class="control-label" for="txtLoginPassword">URI</label>
							<div class="input-group">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-link"></i></span> <input type="text"
									class="form-control" placeholder="Type your URI"
									id="txtUploadFileURI" />
							</div>
							<br /> <input class="btn" type="button" value="Update With URI"
								id="btnUpdateWithURI" onclick="updateWithURI();" />
						</div>
						<!-- divURIUploadBody -->
					</div>
					<!-- divURIUploadHeader -->
				</div>
			</div>

		</div>
<div id="account" style="display: none;" class="row">
			<div>
				<div>
				<fieldset>
					<legend>Account Information</legend>
					<br/> 
					<label class="control-label" for="txtModalLoginUser">User
						Name</label>
					<div class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-user"></i></span> <input type="text"
							class="form-control" id="txtModalLoginUser"
							placeholder="Type your username">
					</div>
					<br />
					<label class="control-label" for="txtModalLoginPassword">Password</label>
					<div class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-lock"></i></span> <input type="password"
							class="form-control" id="txtModalLoginPassword"
							placeholder="Type your password">
					</div>
					<br />
					<label class="control-label" for="txtModalLoginLastName">Last
						Name</label> <input type="text" class="form-control"
						id="txtModalLoginLastName" placeholder="Type your last name">
					<br />
					<label class="control-label" for="txtModalLoginFirstName">First
						Name</label> <input type="text" class="form-control"
						id="txtModalLoginFirstName" placeholder="Type your first name">
					<br />
					<label class="control-label" for="txtModalLoginEmail">Email</label>
					<div class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-envelope"></i></span> <input type="text"
							class="form-control" id="txtModalLoginEmail" type="text"
							placeholder="Type your email">
					</div>
					</fieldset>
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


	<!--  <div class="modal fade" id="divUserModal" role="dialog"
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
					<br /> <label class="control-label" for="txtModalLoginUser">User
						Name</label>
					<div class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-user"></i></span> <input type="text"
							class="form-control" id="txtModalLoginUser"
							placeholder="Type your username">
					</div>
					<br />
					<label class="control-label" for="txtModalLoginPassword">Password</label>
					<div class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-lock"></i></span> <input type="password"
							class="form-control" id="txtModalLoginPassword"
							placeholder="Type your password">
					</div>
					<br />
					<label class="control-label" for="txtModalLoginLastName">Last
						Name</label> <input type="text" class="form-control"
						id="txtModalLoginLastName" placeholder="Type your last name">
					<br />
					<label class="control-label" for="txtModalLoginFirstName">First
						Name</label> <input type="text" class="form-control"
						id="txtModalLoginFirstName" placeholder="Type your first name">
					<br />
					<label class="control-label" for="txtModalLoginEmail">Email</label>
					<div class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-envelope"></i></span> <input type="text"
							class="form-control" id="txtModalLoginEmail" type="text"
							placeholder="Type your email">
					</div>
					
				</div>
				
			</div>
			<
		</div>
		<
	</div>-->
	
	
</body>
</html>

