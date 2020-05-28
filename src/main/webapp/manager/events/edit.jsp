<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty eventDetails}">
	<c:set var="event" value="${eventDetails.getEvent()}" />
</c:if>
<jsp:include page="../../template/header.jsp" />
<div
	class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow">
	<div class="lh-100">
		<h3 class="mb-0">${not empty event.getName() ? event.getName() : 'New Event'}</h3>
	</div>
</div>

<section>
	<div class="container">
		<jsp:include page="../../includes/alertmessages.jsp" />

		<nav>
			<div class="nav nav-tabs" id="nav-tab" role="tablist">
				<a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">
					Settings
				</a>
				<a class="nav-item nav-link" id="nav-sessions-tab" data-toggle="tab" href="#nav-sessions" role="tab" aria-controls="nav-sessions" aria-selected="false">
					Sessions
				</a>
				<a class="nav-item nav-link" id="nav-teams-tab" data-toggle="tab" href="#nav-teams" role="tab" aria-controls="nav-teams" aria-selected="false">
					Teams
				</a>
			</div>
		</nav>
		<div class="tab-content" id="nav-tabContent">
			<div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
				<form class="well form-horizontal" id="eventForm" action="/events?cmd=save" method="POST" id="contact_form">
					<fieldset>
						<input type="hidden" name="eventId" value="${event.getId()}" />
						<div class="col-md-10 offset-md-1">
							<div class="mt-4">
								<div class="col-sm-8 pb-3">
									<label for="name">Event Name</label>
									<input type="text" class="form-control" placeholder="Event Name" name="name" value="${event.getName()}">
								</div>
								<div class="col-sm-12 pb-3">
									<label for="eventDate">Date</label> <input type="datetime-local" class="form-control" name="eventDate" value="${event.getEventDate()}">
								</div>
								<div class="col-sm-12 pb-3">
									<label for="description">Description</label>
									<textarea class="form-control" name="description" rows=6>${event.getDescription()}</textarea>
								</div>
								<div class="col-sm-4 pb-3">
									<label for="host">Host</label>
									<select class="form-control" name="hostId">
										<c:forEach var="host" items="${hosts}">
											<option value="${host.getId()}"
												${host.getId() == eventDetails.getHost().getId() ? 'selected' : ''}>${host.getName()}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-4 pb-3">
									<label for="track">Track</label>
									<select class="form-control" name="trackId">
										<c:forEach var="track" items="${tracks}">
											<option value="${track.getId()}"
												${track.getId() == eventDetails.getTrack().getId() ? 'selected' : ''}>${track.getName()}
												(${track.getSet()})</option>
										</c:forEach>
									</select>
								</div>

								<div class="col-sm-12 pb-3">
									<label for="regulations">Regulations</label>
									<textarea class="form-control" name="regulations" rows=6>${event.getRegulations()}</textarea>
								</div>

							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="tab-pane fade" id="nav-sessions" role="tabpanel" aria-labelledby="nav-sessions-tab">
				<jsp:include page="includes/sessions.jsp" />
			</div>
			<div class="tab-pane fade" id="nav-teams" role="tabpanel" aria-labelledby="nav-teams-tab">
				<jsp:include page="includes/teams.jsp" />
			</div>
		</div>
		<c:set var="customCancel" value="?cmd=view&id=${eventDetails.getEventId()}" />
		<jsp:include page="../../includes/savebuttons.jsp">
			<jsp:param value="/events" name="servletUrl" />
			<jsp:param value="${not empty eventDetails.getEventId() ? customCancel : ''}" name="customCancel"/>
			<jsp:param value="eventForm" name="formId" />
		</jsp:include>

	</div>
</section>
<jsp:include page="../../template/footer.jsp" />

<script>

function removeSession(target)
{
	var id = target.id.substring(15);
	
	$.ajax({
        type: "POST",
        url: '/events?cmd=ajaxDeleteSession',
        data: "sessionId="+id,
        success: function(data)
        {
        	handleAjaxReply(data, '#session_'+data.removedId);
        },
    	error: function()
    	{
        	alert("An error occured deleting the team, please try again.");
    	}
	});
}

function removeTeam(target)
{
	var id = target.id.substring(12);
	
	$.ajax({
        type: "POST",
        url: '/teams?cmd=ajaxDelete',
        data: "teamId="+id,
        success: function(data)
        {
        	handleAjaxReply(data, '#team_'+data.removedId);
        },
    	error: function()
    	{
        	alert("An error occured deleting the team, please try again.");
    	}
	});
}

function handleAjaxReply(data, elementId)
{
	var success = false;
	$.each(data, function(elementId, newValue) 
	{
		if (elementId === "successMessage")
		{
			$('#success_message_content').text(newValue);
			success = true;
		}
		else if (elementId === "errorMessage")
		{
			$('#failure_message_content').text(newValue);
		}
	});
			
	if (success)
	{
		$(elementId).remove();
		$('#success_message').show();
	}
	else
	{
		$('#failure_message').show();
	}
}

</script>