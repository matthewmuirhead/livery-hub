<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty eventDetails}">
		<c:set var="event" value="${eventDetails.getEvent()}" />
	</c:if>
<jsp:include page="../includes/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow">
	<div class="lh-100">
		<h3 class="mb-0">${event.getName()}</h3>
	</div>
</div>

<section>
	<div class="container">
		<jsp:include page="../includes/alertmessages.jsp" />
		<form class="well form-horizontal" id="eventForm" action="/events?cmd=save" method="POST"
			id="contact_form">
			<fieldset>
				<input type="hidden" name="eventId" value="${event.getId()}"/>
				<nav>
					<div class="nav nav-tabs" id="nav-tab" role="tablist">
						<a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">
							Settings
						</a>
						<a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">
							Sessions
						</a>
						<a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab" aria-controls="nav-contact" aria-selected="false">
							Teams
						</a>
					</div>
				</nav>
				<div class="tab-content" id="nav-tabContent">
					<div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
						<div class="col-md-10 offset-md-1">
							<div class="mt-4">
								<div class="col-sm-8 pb-3">
		                            <label for="name">Event Name</label>
		                            <input type="text" class="form-control" placeholder="Event Name" name="name" value="${event.getName()}">
		                        </div>
		                        <div class="col-sm-12 pb-3">
		                            <label for="eventDate">Date</label>
		                            <input type="datetime-local" class="form-control" name="eventDate" value="${event.getEventDate()}">
		                        </div>
		                        <div class="col-sm-12 pb-3">
		                            <label for="description">Description</label>
		                            <textarea class="form-control" name="description" rows=6>${event.getDescription()}</textarea>
		                        </div>
		                        <div class="col-sm-4 pb-3">
		                            <label for="host">Host</label>
		                            <select class="form-control" name="hostId">
		                                <c:forEach var="host" items="${hosts}">
											<option value="${host.getId()}" ${host.getId() == eventDetails.getHost().getId() ? 'selected' : ''}>${host.getName()}</option>
										</c:forEach>
		                            </select>
		                        </div>
		                        <div class="col-sm-4 pb-3">
		                            <label for="track">Track</label>
		                            <select class="form-control" name="trackId">
		                                <c:forEach var="track" items="${tracks}">
											<option value="${track.getId()}" ${track.getId() == eventDetails.getTrack().getId() ? 'selected' : ''}>${track.getName()} (${track.getSet()})</option>
										</c:forEach>
		                            </select>
		                        </div>
		                        
		                        <div class="col-sm-12 pb-3">
		                            <label for="regulations">Regulations</label>
		                            <textarea class="form-control" name="regulations" rows=6>${event.getRegulations()}</textarea>
		                        </div>
		                        
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
						<c:if test="${not empty eventDetails and not empty eventDetails.getSessions()}">
							<table class="table table-hover mt-3">
								<tbody>
									<tr>
										<th>
											Session Name
										</th>
										<th>
											Duration
										</th>
										<th>
											Actual Start
										</th>
										<th>
											In-game Start
										</th>
										<th>
											Time Progression
										</th>
										<th>
											Tire Sets
										</th>
										<th>
											Qualy Driver Only
										</th>
										<th>
										</th>
									</tr>
									<c:forEach var="session" items="${eventDetails.getSessions()}">
										<tr id="session_${session.getId()}">
											<td>
												${session.getName()}
											</td>
											<td>
												${session.getLengthHours()}:${session.getLengthMinutes()}
											</td>
											<td>
												${session.getActualStart().getHour()}:${session.getActualStart().getMinute()}
											</td>
											<td>
												${session.getInGameStart().getHour()}:${session.getInGameStart().getMinute()}
											</td>
											<td>
												${session.getTimeProgression()}
											</td>
											<td>
												${session.getTireSets()}
											</td>
											<td>
												${session.getQualyDriver()}
											</td>
											<td>
												<button class="btn btn-secondary">Edit</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						<button class="btn btn-secondary">
							Add New
						</button>
					</div>
					<div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
						Tab 3
					</div>
				</div>

				<jsp:include page="../includes/savebuttons.jsp">
					<jsp:param value="/events" name="servletUrl"/>
					<jsp:param value="eventForm" name="formId"/>
				</jsp:include>

			</fieldset>
		</form>
	</div>
</section>
<jsp:include page="../includes/footer.jsp" />