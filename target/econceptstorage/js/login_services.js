var login_csrf_token = null;

// confirmUserCredentials
// Confirm the user login credentials
// Input: None
// OUtput: None
//
function getCXRFToken() {
	var lHeader = window.btoa($("#txtLoginUser").val() + ":"
			+ $("#txtLoginPassword").val());

	clearLogin();

	// Clear login status
	$("#divLoginStatus").html('');

} // function getCXRFToken

// confirmUserCredentials
// Confirm the user login credentials
// Input: None
// OUtput: None
//
function confirmUserCredentials() {
	var lHeader = window.btoa($("#txtLoginUser").val() + ":"
			+ $("#txtLoginPassword").val());

	getCXRFToken();

	// Clear login status
	$("#divLoginStatus").html('');

	// Perform AJAX Call
	$
			.ajax({
				url : "rest/user",
				type : "GET",
				dataType : "json",
				beforeSend : function(pXHR) 
				{
					pXHR.setRequestHeader("Authorization",
							"Basic EMPTY");
				}, // beforeSend
				contentType : "application/json; charset=utf-8",
				error : function(pXHR, pStatus, pThrown) {
					
					login_csrf_token = pXHR.getResponseHeader('X-CSRF-TOKEN');
					// Perform AJAX Call
					$
							.ajax({
								url : "rest/user/authenticate",
								type : "POST",
								beforeSend : function(pXHR) 
								{
									pXHR.setRequestHeader("Authorization",
											"Basic " + lHeader);
									pXHR.setRequestHeader("X-CSRF-Token",
											login_csrf_token);
								}, // beforeSend
								dataType : "json",
								contentType : "application/json; charset=utf-8",
								success : function(pData, pTextStatus, pRequest) {
									login_csrf_token = pXHR
											.getResponseHeader('X-CSRF-TOKEN');

									// Set user credential
									$("#fileUploadFile").val('');

									setUserCredentials(pData);

									// Refresh file list
									getFileList();

									$("#login").slideUp();

									$("#divLoginServices").show();
									$("#services").show();
								} // success: function(pData)
								,
								error : function(xhr, status, thrown) 
								{
									login_csrf_token = null;
									
									if (xhr.status == 401) {
										$("#divLoginStatus")
												.html(
														'<div class="alert alert-danger" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Login Error</h4> Incorrect Username or Password</div>');
									} // if
									else {
										$("#divLoginStatus")
												.html(
														'<div class="alert alert-danger" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Login Error</h4> Server is under maintence, please come back later</div>');
									} // else
								} // error : function (xhr, status, thrown)
							}); // $.ajax
				} // error : function (xhr, status, thrown)
			}); // $.ajax
} // function confirmUserCredentials

// getUserCredentials
// Confirm the user login credentials
// Input: pUserData JSON User object
// OUtput: None
//
function setUserCredentials(pUserData) 
{
	$("#divLoginStatus").html('');
	$("#divUploadStatus").html('');
	
	$("#bAccountFullName").html(pUserData.firstName + ' ' + pUserData.lastName);

	$("#txtModalLoginUser").val(pUserData.username);
	$("#txtModalLoginFirstName").val(pUserData.firstName);
	$("#txtModalLoginLastName").val(pUserData.lastName);
	$("#txtModalLoginEmail").val(pUserData.email);

} // function confirmUserCredentials

// clearLogin
// Clear login
// Input: None
// OUtput: None
//
function clearLogin() 
{
	$("#divLoginStatus").html('');
	$("#divUploadStatus").html('');
	
	$("#txtLoginUser").val('');
	$("#txtLoginPassword").val('');
	$("#lblFileBrowseName").html('No File is Selected');
	
	// Clear user credentials
	$("#txtAccount_ID").val('');
	$("#divAccountMessage").html('');
	$("#fileUploadFile").val('');
	$("#divFilelist").html('');
	$("#txtUploadFileURI").val('');

	$("#txtModalLoginUser").val('');
	$("#txtModalLoginFirstName").val('');
	$("#txtModalLoginLastName").val('');
	$("#txtModalLoginEamil").val('');

	login_csrf_token = null;
	upload_csrf_token = null;
} // clearLogin()

// confirmUserCredentials
// Confirm the user login credentials
// Input: None
// OUtput: None
//
function clearUserCredentials() {
	$("#services").hide();
	$('#account').hide();
	$("#divLoginServices").hide();
	$("#login").slideDown();

	// Perform AJAX Call
	$
			.ajax({
				url : document.URL + "rest/user",
				type : "POST",
				beforeSend : function(pXHR) {
					pXHR.setRequestHeader("Authorization", "Basic Empty");
					pXHR.setRequestHeader('X-CSRF-Token', login_csrf_token);
				}, // beforeSend
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				error : function(xhr, status, thrown) {
					$("#txtLoginAccountPassword").val('');
					if (xhr.status == 401) {
						$("#divLoginStatus")
								.html(
										'<div class="alert alert-success" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Log out</h4>You are now sign out</div>');
						location.reload(true);
					} // if
				} // error : function (xhr, status, thrown)
			}); // $.ajax
	clearLogin();
} // function clearUserCredentials