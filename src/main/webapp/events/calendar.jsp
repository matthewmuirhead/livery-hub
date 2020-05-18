<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../includes/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white">Upcoming Events - ${currentMonth.toString()}</h6>
		<small>Calendar View</small>
	</div>
	<div class="d-flex align-items-center new-item">
		<a href="/calendar?cmd=new" class="hvr-underline-from-left hvr-float">Add New</a>
	</div>
</div>
<jsp:include page="../includes/carousel.jsp" />

<section>
	<div class="container">
		<div class="row">
			<c:forEach items="${eventDetails}" var="eventDetail">
				<div class="col-md-4 mt-4">
					<a href="/events?cmd=view&id=${eventDetail.getEvent().getId()}">
						<div class="card profile-card">
							<div class="card-img-block">
								<img class="card-img-top"
									src="../img/tracks/logos/${eventDetail.getTrack().getLogo()}"
									alt="${eventDetail.getEvent().getName()} Logo">
							</div>
							<div class="card-body pt-0">
								<h5 class="card-title">
									${eventDetail.getEvent().getName()}
								</h5>
								<p class="card-text">${eventDetail.getEvent().getDescription()}</p>
	
							</div>
						</div>
					</a>
				</div>
			</c:forEach>
		</div>
	</div>
</section>

<jsp:include page="../includes/footer.jsp" />