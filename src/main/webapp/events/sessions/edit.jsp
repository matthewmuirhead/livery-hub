<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../includes/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow">
	<h3 class="mb-0">${not empty session.getName() ? session.getName() : 'New Session'}</h3>
</div>
<section>
	<div class="container">
		<jsp:include page="../../includes/alertmessages.jsp" />
		<form class="well form-horizontal" id="sessionForm" action="/events?cmd=saveSession" method="POST"
			id="contact_form">
			<fieldset style="margin-top:-20px">
				<input type="hidden" name="sessionId" value="${session.getId()}"/>
				<input type="hidden" name="eventId" value="${not empty session.getEventId() ? session.getEventId() : eventId}"/>

				<div class="col-md-10 offset-md-1">
					<div class="form-row mt-4">
						
						<div class="col-sm-8 pb-3">
                            <label for="name">Session Name</label>
                            <select class="form-control" name="name">
                            	<option value="Free Practice" ${'Free Practice' == session.getName() ? 'selected' : ''}>Free Practice</option>
                            	<option value="Qualifying" ${'Qualifying' == session.getName() ? 'selected' : ''}>Qualifying</option>
                            	<option value="Race" ${'Race' == session.getName() ? 'selected' : ''}>Race</option>
                            </select>
                        </div>
                        <div class="col-sm-12 pb-3">
                            <label for="actualStart">Actual Start Time</label>
                            <input type="time" class="form-control" name="actualStart" value="${session.getActualStart()}" />
                        </div>
                        <div class="col-sm-12 pb-3">
                            <label for="inGameStart">In Game Start Time</label>
                            <input type="time" class="form-control" name="inGameStart" value="${session.getInGameStart()}" />
                        </div>
                        <div class="col-sm-6 pb-3">
                            <label for="hours">Session Length Hours</label>
                            <input type="number" class="form-control" placeholder="0" name="hours" value="${session.getLengthHours()}" />
                        </div>
                        <div class="col-sm-6 pb-3">
                            <label for="minutes">Session Length Minutes</label>
                            <input type="number" class="form-control" placeholder="0" name="minutes" value="${session.getLengthMinutes()}" />
                        </div>
                        <div class="col-sm-4 pb-3">
                            <label for="timeProgression">Time Progression</label>
                            <select class="form-control" name="timeProgression">
                                <c:forEach var="progression" begin="1" end="24">
									<option value="${progression}" ${progression == session.getTimeProgression() ? 'selected' : ''}>${progression}</option>
								</c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-4 pb-3">
                            <label for="tireSets">Available Tire Sets</label>
                            <select class="form-control" name="tireSets">
                                <c:forEach var="tires" begin="1" end="50">
									<option value="${tires}" ${tires == session.getTireSets() ? 'selected' : ''}>${tires}</option>
								</c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-4 pb-3">
                            <label for="qualyDriver">Qualifying Driver Only</label>
                            <input type="checkbox" class="form-control" name="qualyDriver" ${session.getQualyDriver() ? 'checked' : ''} />
                        </div>
					</div>
				</div>

				<jsp:include page="../../includes/savebuttons.jsp">
					<jsp:param value="/events" name="servletUrl"/>
					<jsp:param value="ajaxSaveSession" name="customCmd"/>
					<jsp:param value="?cmd=edit&id=${not empty session.getEventId() ? session.getEventId() : eventId}" name="customCancel"/>
					<jsp:param value="sessionForm" name="formId"/>
				</jsp:include>

			</fieldset>
		</form>
	</div>
</section>
<jsp:include page="../../includes/footer.jsp" />
