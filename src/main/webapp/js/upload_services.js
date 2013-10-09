var upload_csrf_token = null;

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
	'"uri":"' + $('#txtUploadFileURI').val() + '"' +
	'}';
	
	// AJAX call
	$.ajax({
		url : "rest/file/uri",
		type : "POST",
		data : lJSONData,
		dataType : "json",
		beforeSend : function(pXHR) 
		{
			pXHR.setRequestHeader('X-CSRF-Token', upload_csrf_token);
		}, // beforeSend
		contentType : "application/json; charset=utf-8",
		success : function(pData) {
			// Print out status and message
			$("#divUploadStatus")
				.html(
						'<div class="alert alert-success" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Opeartion Success</h4> File is uploaded successfully</div>');;
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
				success : function(pData, pTextStatus, pRequest) 
				{
					upload_csrf_token = pRequest.getResponseHeader('X-CSRF-TOKEN');
					
					// Clear the file list to ensure newest list is updated
					$('#divFilelist').html('');

					// Get the file list length
					var lFileListLength = pData.length;

					// Prepare table to display file list
					var lTableHTMLString = '<table class="table table-striped">';
					// Table Header
					lTableHTMLString = lTableHTMLString + '<thead>';
					lTableHTMLString = lTableHTMLString + '<tr>';
					lTableHTMLString = lTableHTMLString + '<th>#</th>';
					lTableHTMLString = lTableHTMLString + '<th>File Name</th>';
					lTableHTMLString = lTableHTMLString + '<th>File Size</th>';
					lTableHTMLString = lTableHTMLString + '<th>Last Modified</th>';
					lTableHTMLString = lTableHTMLString + '<th>Operations</th>';
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
								+ '<td><a href="rest/file/'
								+ pData[lFileIndex].fileName + '">'
								+ pData[lFileIndex].fileName + '</a></td>';
						// File Size
						lTableHTMLString = lTableHTMLString
								+ '<td>' + pData[lFileIndex].fileSize + '</td>';
				
						// Last Modified
						lTableHTMLString = lTableHTMLString
								+ '<td>' + pData[lFileIndex].lastModified + '</td>';
				
						// Delete button for the particular file
						lTableHTMLString = lTableHTMLString
								+ '<td><a href="#" class="icon-trash" onclick="deleteFile(\''
								+ pData[lFileIndex].fileName
								+ '\');"/></a></td>';
						lTableHTMLString = lTableHTMLString + '</tr>';
					} // for

					lTableHTMLString = lTableHTMLString + '</table>';

					// Add the table into the file list div
					$('#divFilelist').append(lTableHTMLString);
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
		dataType : "json",
		beforeSend : function(pXHR) 
		{
			pXHR.setRequestHeader('X-CSRF-Token', upload_csrf_token);
		}, // beforeSend
		contentType : "application/json; charset=utf-8",
		success : function(pData) {
			// Print out status and message
			$("#divUploadStatus")
			.html(
					'<div class="alert alert-success" ><a class="close" data-dismiss="alert" href="#">x</a><h4 class="alert-heading">Opeartion Success</h4> File is deleted successfully</div>');;
			getFileList(); 
		}, // success: function(pData)
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
	if(login_csrf_token != null)
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