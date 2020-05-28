<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../template/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white">Teams</h6>
		<small>All teams</small>
	</div>
</div>
<jsp:include page="../../includes/carousel.jsp" />

<section>
	<div class="container">
		<div class="row">
			<div class="col-md-12 pb-3">
				<input class="w-100 form-control" id="search" type="text" placeholder="Search" onkeyup="search(this)"/>
			</div>
			<div class="col-md-12 pb-3">
				<div class="d-flex flex-column align-items-end">
					<table class="table table-hover mt-3 mb-0">
						<thead>
							<tr>
								<th>Event</th>
								<th>Team Name</th>
								<th>Car</th>
								<th>Number</th>
								<th></th>
							</tr>
						</thead>
						<tbody id="teams-table">
							
							<c:forEach var="teamDetails" items="${teamDetailsList}">
								<tr id="team_${teamDetails.getTeamId()}">
									<td>${teamDetails.getEvent().getName()}</td>
									<td>${teamDetails.getTeam().getName()}</td>
									<td>${teamDetails.getCar().getFullNameAndYear()}</td>
									<td>${teamDetails.getTeam().getNumber()}</td>
									<td class="pr-0 text-right">
										<a href="/teams?cmd=manager&teamId=${teamDetails.getTeamId()}" class="btn btn-primary">Manager</a>
										<a href="/teams?cmd=edit&teamId=${teamDetails.getTeamId()}" class="btn btn-secondary">Edit</a>
										<button id="team_remove_${teamDetails.getTeamId()}" class="btn btn-danger" onclick="removeTeam(this)">Remove</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</section>

<jsp:include page="../../template/footer.jsp" />

<script>

function search(element)
{
	var value = element.value.toLowerCase();
	$("#teams-table tr").filter(function() {
		$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
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