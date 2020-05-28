<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="d-flex flex-column align-items-end">
	<table class="table table-hover mt-3 mb-0">
		<tbody id="sessions-table">
			<tr>
				<th>Session Name</th>
				<th>Duration</th>
				<th>Actual Start</th>
				<th>In-game Start</th>
				<th>Time Progression</th>
				<th>Tire Sets</th>
				<th>Qualy Driver Only</th>
				<th></th>
			</tr>
			<c:forEach var="session" items="${eventDetails.getSessions()}">
				<tr id="session_${session.getId()}">
					<td>${session.getName()}</td>
					<td>
						${session.getLengthHours()}:${session.getLengthMinutes() < 10 ? '0' : ''}${session.getLengthMinutes()}
					</td>
					<td>
						${session.getActualStart().getHour() < 10 ? '0' : ''}${session.getActualStart().getHour()}:${session.getActualStart().getMinute() < 10 ? '0' : ''}${session.getActualStart().getMinute()}
					</td>
					<td>
						${session.getInGameStart().getHour() < 10 ? '0' : ''}${session.getInGameStart().getHour()}:${session.getInGameStart().getMinute() < 10 ? '0' : ''}${session.getInGameStart().getMinute()}
					</td>
					<td>${session.getTimeProgression()}</td>
					<td>${session.getTireSets()}</td>
					<td>${session.getQualyDriver()}</td>
					<td class="pr-0 text-right">
						<a href="/manager/events?cmd=editSession&sessionId=${session.getId()}" class="btn btn-secondary">Edit</a>
						<button id="session_remove_${session.getId()}" class="btn btn-danger" onclick="removeSession(this)">Remove</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="/manager/events?cmd=newSession&eventId=${eventDetails.getEventId()}" class="btn btn-info mb-3" style="width:100px;">Add New</a>
</div>