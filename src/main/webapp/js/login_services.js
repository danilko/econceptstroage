  <script type="text/javascript">
     // login
     // Confirm the user login credentials
     // Input: None
     // OUtput: None
     //
     function confirmUserCredentials(pAccountID, pAccountPassword) {
         // Compute JSON data
    	 var lJSONData = "{";
        lJSONData = lJSONData + "\"account_id\":\"" + pAccountID + "\",";
        lJSONData = lJSONData + "\"account_password\":\"" + pAccountPassword + "\",";
        lJSONData = lJSONData + "\"operation\":\"CONFIRM\"";
        lJSONData = lJSONData + "}";

        // Perform AJAX Call
        $.ajax({
            url: "rest/account",
            type: "POST",
            data: lJSONData,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (pData) 
            {
            	if(pData.statusmessage == "CONFIRM")
            	{
            		alert("success");
            	}  // if
            }  // success: function(pData)
            ,
            error: function (xhr, status, thrown) {
                $('#result').html(xhr.responseText + " " + status + " " + thrown);
            }  // error : function (xhr, status, thrown)
        });  // $.ajax
    }  // function getFileList 
</script>