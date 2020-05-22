<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../includes/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white">Upcoming Events</h6>
		<small>List View</small>
	</div>
	<div class="d-flex align-items-center new-item">
		<a href="/events?cmd=new" class="hvr-underline-from-left hvr-float">Add New</a>
	</div>
</div>
<jsp:include page="../includes/carousel.jsp" />

<section>
	<div class="container">
		<div class="row">
			<div class="col-md-12 pb-3">
				<a href="events?cmd=calendar" class="float-right"><button class="btn btn-danger bg-red-fade">Calendar View</button></a>
			</div>
			<div class="col-md-12">
				<c:forEach items="${eventDetails}" var="eventDetail">
					<c:set var="event" value="${eventDetail.getEvent()}" />
					<c:set var="eventDate" value="${event.getEventDate()}" />
					<a href="/events?cmd=view&id=${eventDetail.getEvent().getId()}">
						<div class="row row-striped">
							<div class="col-2 text-right">
								<h1 class="display-4"><span class="badge badge-secondary">${eventDate.getDayOfMonth()}</span></h1>
								<h2>${eventDate.getMonth()}</h2>
							</div>
							<div class="col-10">
								<h3 class="text-uppercase"><strong>${event.getName()}</strong></h3>
								<ul class="list-inline">
								    <li class="list-inline-item"><i class="fa fa-calendar-o" aria-hidden="true"></i> ${eventDate.getDayOfWeek()}</li>
									<li class="list-inline-item"><i class="fa fa-clock-o" aria-hidden="true"></i> ${eventDate.getHour()}:${eventDate.getMinute()}</li>
									<li class="list-inline-item"><i class="fa fa-location-arrow" aria-hidden="true"></i> ${eventDetail.getTrack().getName()}</li>
								</ul>
								<p>${event.getDescription()}</p>
							</div>
						</div>
					</a>
				</c:forEach>				
			</div>
		</div>
	</div>
</section>

<jsp:include page="../includes/footer.jsp" />