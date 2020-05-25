<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../includes/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow">
	<h3 class="mb-0">${not empty team.getName() ? team.getName() : 'New Team'}</h3>
</div>
<section>
	<div class="container">
		<jsp:include page="../includes/alertmessages.jsp" />
		<form class="well form-horizontal" id="teamForm" action="/teams?cmd=save" method="POST"
			id="contact_form">
			<fieldset style="margin-top:-20px">
				<input type="hidden" name="teamId" value="${team.getId()}"/>
				<input type="hidden" name="eventId" value="${not empty team.getEventId() ? team.getEventId() : eventId}"/>

				<div class="col-md-10 offset-md-1">
					<div class="form-row mt-4">
						
						<div class="col-sm-12 pb-3">
                            <label for="name">Team Name</label>
                            <input type="text" class="form-control" name="name" value="${team.getName()}" />
                        </div>
                        <div class="col-sm-8 pb-3">
                            <label for="carId">Car</label>
                            <select class="form-control" name="carId">
                                <c:forEach var="car" items="${cars}">
									<option value="${car.getId()}" ${car.getId() == team.getCarId() ? 'selected' : ''}>${car.getFullNameAndYear()}</option>
								</c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-4 pb-3">
                            <label for="number">Number</label>
                            <input type="number" class="form-control" name="number" value="${team.getNumber()}" />
                        </div>
                        <div class="col-sm-12 pb-3">
                            <label for="driver1">Driver 1</label>
                            <select class="form-control" name="driver1">
                            	<option value="0">No Driver</option>
                                <c:forEach var="driver" items="${drivers}">
									<option value="${driver.getId()}" ${driver.getId() == team.getDriver_1() ? 'selected' : ''}>${driver.getName()}</option>
								</c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-12 pb-3">
                            <label for="driver2">Driver 2</label>
                            <select class="form-control" name="driver2">
                            	<option value="0">No Driver</option>
                                <c:forEach var="driver" items="${drivers}">
									<option value="${driver.getId()}" ${driver.getId() == team.getDriver_2() ? 'selected' : ''}>${driver.getName()}</option>
								</c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-12 pb-3">
                            <label for="driver3">Driver 3</label>
                            <select class="form-control" name="driver3">
                            	<option value="0">No Driver</option>
                                <c:forEach var="driver" items="${drivers}">
									<option value="${driver.getId()}" ${driver.getId() == team.getDriver_3() ? 'selected' : ''}>${driver.getName()}</option>
								</c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-12 pb-3">
                            <label for="driver4">Driver 4</label>
                            <select class="form-control" name="driver4">
                            	<option value="0">No Driver</option>
                                <c:forEach var="driver" items="${drivers}">
									<option value="${driver.getId()}" ${driver.getId() == team.getDriver_4() ? 'selected' : ''}>${driver.getName()}</option>
								</c:forEach>
                            </select>
                        </div>
                        
                        <div class="col-sm-4 pb-3">
                            <label for="category">Category</label>
                            <select class="form-control" name="category">
                                <option value="PRO" ${'PRO' == team.getCategory() ? 'selected' : ''}>PRO</option>
                                <option value="PRO-AM" ${'PRO-AM' == team.getCategory() ? 'selected' : ''}>PRO-AM</option>
                                <option value="SILVER" ${'SILVER' == team.getCategory() ? 'selected' : ''}>SILVER</option>
                                <option value="AM" ${'AM' == team.getCategory() ? 'selected' : ''}>AM</option>
                            </select>
                        </div>
                        <div class="col-sm-4 pb-3">
                            <label for="status">Status</label>
                            <select class="form-control" name="status">
                                <option value="Unconfirmed" ${'Unconfirmed' == team.getStatus() ? 'selected' : ''}>Unconfirmed</option>
                                <option value="Provisional" ${'Provisional' == team.getStatus() ? 'selected' : ''}>Provisional</option>
                                <option value="Registered" ${'Registered' == team.getStatus() ? 'selected' : ''}>Registered</option>
                            </select>
                        </div>
					</div>
				</div>

				<jsp:include page="../includes/savebuttons.jsp">
					<jsp:param value="/teams" name="servletUrl"/>
					<jsp:param value="/events" name="cancelServletUrl"/>
					<jsp:param value="?cmd=edit&id=${not empty team.getEventId() ? team.getEventId() : eventId}" name="customCancel"/>
					<jsp:param value="teamForm" name="formId"/>
				</jsp:include>

			</fieldset>
		</form>
	</div>
</section>
<jsp:include page="../includes/footer.jsp" />
