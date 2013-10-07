//
// updateWithURI
// Compute user information and file uri as a JSON message and send
// to server
// to download file
// Input: None
// Output: None
//
function updateWithURI() {
	var lJSONData=
	'{'+
	'"mURI":"' + $('#txtUploadFileURI').val() + '"' +
	'}';
	
	// AJAX call
	$.ajax({
		url : "rest/file/uri",
		type : "POST",
		data : lJSONData,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success : function(pData) {
			// Print out status and message
				$("#divLoginStatus")
				.html(
						'<div class="alert alert-success" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Opeartion Success</h4> Your opeartion just finished successfully</div>');;
			getFileList();
		} // success: function(pData)
		,
		error : function(xhr, status, thrown) {
			$('#result').html(xhr.responseText + " " + status + " " + thrown);
		} // error : function (xhr, status, thrown)
	}); // $.ajax
} // function updateWithURI

//
// getFileList
// Retrieve file list
// Compute user information as JSON object
// Input: None
// Output: None
function getFileList() 
{
	$
			.ajax({
				url : "rest/file/list",
				type : "GET",
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				success : function(pData) {
					$('#result').html('');
					$('#result').append('<p>' + pData.status + '</p>');

					// Clear the file list to ensure newest list is updated
					$('#filelist').html('');

					// Get the file list length
					var lFileListLength = pData.length;

					// Only need to go through file list if there is one or
					// more,
					// else print the message to show no file and end the
					// function
					if (lFileListLength == 0) 
					{
						var lTableHTMLString = '<table class="table table-striped">';
						// Table Header
						lTableHTMLString = lTableHTMLString + '<thead>';
						lTableHTMLString = lTableHTMLString + '<tr>';
						lTableHTMLString = lTableHTMLString + '<th>#</td>';
						lTableHTMLString = lTableHTMLString + '<th>File Name</td>';
						lTableHTMLString = lTableHTMLString + '<th></td>';
						lTableHTMLString = lTableHTMLString + '</tr>';
						lTableHTMLString = lTableHTMLString + '</thead>';
						lTableHTMLString = lTableHTMLString + '</table>';
						
						// Add the table into the file list div
						$('#filelist').append(lTableHTMLString);
						
						return;
					} // if

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

					// Loop through the file list and concate file into
					// table row
					for ( var lFileIndex = 0; lFileIndex < lFileListLength; lFileIndex++) {
						lTableHTMLString = lTableHTMLString + '<tr>';
						// File Name
						lTableHTMLString = lTableHTMLString + '<td>'
								+ (lFileIndex + 1) + '</td>';
						lTableHTMLString = lTableHTMLString
								+ '<td><a href="service/rest/file/'
								+ pData.statusmessage[lFileIndex] + '">'
								+ pData.statusmessage[lFileIndex] + '</a></td>';
						// Delete button for the particular file
						lTableHTMLString = lTableHTMLString
								+ '<td><input class="btn" type="button" onclick="deleteFile(\''
								+ pData.statusmessage[lFileIndex]
								+ '\');" value="Delete"/></td>';
						lTableHTMLString = lTableHTMLString + '</tr>';
					} // for

					lTableHTMLString = lTableHTMLString + '</table>';

					// Add the table into the file list div
					$('#filelist').append(lTableHTMLString);
				} // success: function(pData)
				,
				error : function(xhr, status, thrown) {
					fileOperationErrorStatus(xhr);
				} // error : function (xhr, status, thrown)
			}); // $.ajax
} // function getFileList

//
// deleteFile
// Compute the JSON message to send to server to delete the file
// for the particular user
// Input: pFileName the file to be deleted
// OUtput: None
//
function deleteFile(pFileName) {
	// Perform AJAX Call
	$.ajax({
		url : "rest/file/" + pFileName,
		type : "DELETE",
		data : lJSONData,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success : function(pData) {
			// Print out status and message
			$("#divLoginStatus")
			.html(
					'<div class="alert alert-success" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Opeartion Success</h4> Your opeartion just finished successfully</div>');;

			getFileList();
		} // success: function(pData)
		,
		error : function(xhr, status, thrown) 
		{
			fileOperationErrorStatus(xhr);
		} // error : function (xhr, status, thrown)
	}); // $.ajax
} // function getFileList

//
// refresh
// Refresh function to auto refresh current settings
// Input: None
// Output: Noe
function refresh()
{
	// Only if the user is login, perform the refresh
	if($("#txtModalLoginUser").val() != '')
	{
		getFileList(); 
	}  // if
}  // if

//
// fileOperationErrorStatus
// Generate error based on inputted xhr object
// Input: xhr Response Header
//
//
function fileOperationErrorStatus(xhr)
{

	if (xhr.status == 401) 
	{
		// Since the session expire, require the user to login again
		$("#divLoginStatus")
		.html(
				'<div class="alert alert-error" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Session Expire</h4> Your session just expires, please sign in again</div>');

		clearUserCredentials();
	} // if
	else {
		$("#divUploadStatus")
				.html(
						'<div class="alert alert-error" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Server Error</h4> Server is under maintence, please come back later</div>');
	} // else
}  // fileOperationErrorStatus