  <script type="text/javascript">
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
        lJSONData = lJSONData + "\"user\":\"admin\"";
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
                alert('fail');
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
        lJSONData = lJSONData + "\"user\":\"admin\"";
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
                	lTableHTMLString = lTableHTMLString + '<td><a href="rest/getfile?user=admin&filename=' + pData.statusmessage [lFileIndex] + '">' + pData.statusmessage [lFileIndex] + '</a></td>';
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
                alert('fail');
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
        lJSONData = lJSONData + "\"user\":\"admin\"";
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
                alert('fail');
                $('#result').html(xhr.responseText + " " + status + " " + thrown);
            }  // error : function (xhr, status, thrown)
        });  // $.ajax
    }  // function getFileList 
</script>