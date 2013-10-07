// confirmUserCredentials
// Confirm the user login credentials
// Input: None
// OUtput: None
//
function confirmUserCredentials() {
	var lHeader = window.btoa($("#txtLoginUser").val() + ":" + $(
	"#txtLoginPassword").val());

	clearLogin();

	// Clear login status
	$("#divLoginStatus").html('');

	// Perform AJAX Call
	$
			.ajax({
				url : "rest/user",
				type : "GET",
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Authorization", "Basic " + lHeader);
				}, // beforeSend
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				success : function(pData) 
				{
					// Set user credential
					$("#fileUploadFile").val('');

					setUserCredentials(pData);
					
					// Refresh file list
					refresh();

					$("#login").slideUp();

					$("#ulLogout").show();
					$("#services").show();
				} // success: function(pData)
				,
				error : function(xhr, status, thrown) {
					if (xhr.status == 401) {
						$("#divLoginStatus")
								.html(
										'<div class="alert alert-error" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Login Error</h4> Incorrect Username or Password</div>');
					} // if
					else {
						$("#divLoginStatus")
								.html(
										'<div class="alert alert-error" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Login Error</h4> Server is under maintence, please come back later</div>');
					} // else
				} // error : function (xhr, status, thrown)
			}); // $.ajax
} // function confirmUserCredentials

//getUserCredentials
//Confirm the user login credentials
//Input: pUserData JSON User object
//OUtput: None
//
function setUserCredentials(pUserData) 
{
	$("#bAccountFullName").html(pUserData.firstName + ' ' + pUserData.lastName);
	
	$("#txtModalLoginUser").val(pUserData.username); 
	$("#txtModalLoginFirstName").val(pUserData.firstName); 
	$("#txtModalLoginLastName").val(pUserData.lastName); 
	$("#txtModalLoginEmail").val(pUserData.email); 
	
} // function confirmUserCredentials

// clearLogin
//Clear login
//Input: None
//OUtput: None
//
function clearLogin()
{
	$("#txtLoginUser").val(''); 
	$("#txtLoginPassword").val('');
	
	// Clear user credentials
	$("#txtAccount_ID").val('');
	$("#divAccountMessage").html('');
	$("#fileUploadFile").val('');
	$("#filelist").html('');
	$("#txtUploadFileURI").val('');

	$("#txtModalLoginUser").val(''); 
	$("#txtModalLoginFirstName").val(''); 
	$("#txtModalLoginLastName").val(''); 
	$("#txtModalLoginEamil").val(''); 
}  // clearLogin()

// confirmUserCredentials
// Confirm the user login credentials
// Input: None
// OUtput: None
//
function clearUserCredentials() 
{
	clearLogin();

	$("#services").hide();
	$("#ulLogout").hide();
	$("#login").slideDown();

	// Perform AJAX Call
	$
			.ajax({
				url : document.URL + "rest/user",
				type : "GET",
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Authorization", "Basic Empty");
				}, // beforeSend
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				error : function(xhr, status, thrown) {
					$("#txtLoginAccountPassword").val('');
					if (xhr.status == 401) 
					{
						$("#divLoginStatus")
								.html(
										'<div class="alert alert-success" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Log out</h4>You are now sign out</div>');
					} // if
				} // error : function (xhr, status, thrown)
			}); // $.ajax
} // function clearUserCredentials