<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="d-flex flex-column align-items-end">
	<table class="table table-hover mt-3 mb-0">
		<tbody id="teams-table">
			<tr>
				<th>Team Name</th>
				<th>Car</th>
				<th>Number</th>
				<th>Category</th>
				<th>Status</th>
				<th></th>
			</tr>
			<c:forEach var="teamDetails" items="${eventDetails.getTeams()}">
				<tr id="team_${teamDetails.getTeamId()}">
					<td>${teamDetails.getTeam().getName()}</td>
					<td>${teamDetails.getCar().getFullNameAndYear()}</td>
					<td>${teamDetails.getTeam().getNumber()}</td>
					<td>${teamDetails.getTeam().getCategory()}</td>
					<td>${teamDetails.getTeam().getStatus()}</td>
					<td class="pr-0 text-right">
						<a href="/teams?cmd=edit&teamId=${teamDetails.getTeamId()}" class="btn btn-secondary">Edit</a>
						<button id="team_remove_${teamDetails.getTeamId()}" class="btn btn-danger" onclick="removeTeam(this)">Remove</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="/teams?cmd=new&eventId=${eventDetails.getEventId()}" class="btn btn-info mb-3" style="width:100px;">Add New</a>
</div>