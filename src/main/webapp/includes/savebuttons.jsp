<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Button -->
<div class="btn-bar">
	<a id="saveButton" class="btn btn-success disabled">Save</a>
	<a id="saveCloseButton" class="btn btn-primary">Save and Close</a>
	<a id="cancel" href="${not empty param.cancelServletUrl ? param.cancelServletUrl : param.servletUrl}${param.customCancel}" class="btn btn-danger">Cancel</a>
</div>

<c:set var="ajaxSaveCmd" value="${not empty param.customCmd ? param.customCmd : 'ajaxSave'}"/>

<script>


// call back functions array
var allAjaxSaveCallbackFunctions = [];
function addAjaxSaveCallbackFunction(func)
{
	allAjaxSaveCallbackFunctions.push(func);
}

// page initialisation
$(document).ready(function()
{
	enableListeners();
	
	$("#saveCloseButton").click(function(){        
        $("#${param.formId}").submit(); // Submit the form
    });
});

// enable the green save button, then disable all event listeners that enable it so it
// doesn't get enabled multiple times
function enableSaveButton()
{
	$('#saveButton').removeClass('disabled').text('Save').on('click', ajaxSave);
	
	$('form input').off('keyup', enableSaveButton).off('change', enableSaveButton);
	$('form select').off('change', enableSaveButton);
	$('form textarea').off('keyup', enableSaveButton).off('change', enableSaveButton);		
	$('form a').off('click', enableSaveButton);
	
	$('#success_message').hide();
	$('#failure_message').hide();
}

// put event listeners on form elements so that the green save button gets enabled 
// if something gets changed
function enableListeners()
{
	$('form input').one('keyup change', enableSaveButton);
	$('form select').change(enableSaveButton);
	$('form textarea').one('keyup change', enableSaveButton);
	$('form a').not('#saveButton, #saveAndCloseButton, #returnToListButton, #savePublishButton').on('click', enableSaveButton);
}

// we've pressed the green button...
function ajaxSave()
{
	if (typeof beforeFormSubmission == 'function' && !beforeFormSubmission())
	{
		return;
	}
	
	$('#saveButton').text('Saving...').addClass('disabled').off('click', ajaxSave);
	
	enableListeners();
    
    $.ajax({
        type: "POST",
        url: '${param.servletUrl}?cmd=${ajaxSaveCmd}',
        data: $("#${param.formId}").serialize(),
        success: handleAjaxReply,
    	error: function()
    	{
        	$('#saveButton').text('Save');
        	$('#failure_message').show();
    	}
	});
}

// gets called when we get a reply from an ajax save
function handleAjaxReply(data)
{
	$('#saveButton').text('Saved');
	$.each(data.NewHtmlElementValues, function(elementId, newValue) 
	{
	  	$('#' + elementId).val(newValue);
	});
	
	var success = true;
	
	$.each(data.ErrorMessages, function(errorElementId, newValue) 
	{
	  	if (errorElementId === "successMessage")
	  	{
	  		$('#success_message_content').text(newValue);
	  	}
	  	else if (errorElementId === "generalError")
	  	{
	  		$('#failure_message_content').text(newValue);
	  		success = false;
	  	}
	  	else
	  	{
	  		$('#' + errorElementId).text(newValue);
	  		success = false;
	  	}
	});
	
	if (success)
	{
		$('#success_message').show();
		$('#saveButton').text('Saved');
	}
	else
	{
		$('#failure_message').show();
		$('#saveButton').text('Save');
	}
	
	for (var i = 0; i < allAjaxSaveCallbackFunctions.length; i++)
	{
		allAjaxSaveCallbackFunctions[i](data);
	}
}

</script>