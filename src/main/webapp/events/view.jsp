<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../includes/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h3 class="mb-0">${eventDetails.getEvent().getName()}</h3>
	</div>
	<div class="d-flex align-items-center new-item">
		<a href="/events?cmd=edit&id=${eventDetails.getEvent().getId()}" class="hvr-underline-from-left hvr-float">Edit</a>
	</div>
</div>

<section>
	<!-- Content Wrapper -->
	<div id="content-wrapper" class="d-flex flex-column">
		<!-- Begin Page Content -->
		<div class="container-fluid">
			
			<c:if test="${not empty eventDetails.getEvent().getDescription()}">
				<div class="row">
					<!-- Description -->
					<div class="col-lg-12">
						<div class="card shadow mb-4">
							<!-- Card Header -->
							<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
								<h6 class="m-0 font-weight-bold text-danger">Description</h6>
							</div>
							<!-- Card Body -->
							<div class="card-body">
								${eventDetails.getEvent().getDescription()}
							</div>
						</div>
					</div>
				</div>
			</c:if>
			
			<div class="row">
				<!-- Sessions -->
				<div class="col-lg-12">
					<div class="card shadow mb-4">
						<!-- Card Header -->
						<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
							<h6 class="m-0 font-weight-bold text-danger">Sessions</h6>
						</div>
						<!-- Card Body -->
						<div class="card-body pt-0">
							<c:forEach items="${eventDetails.getSessions()}" var="session">
								<div class="col-lg-12 border-bottom border-gray mx-0 px-3">
									<div class="media text-muted">
										<p class="media-body py-3 mb-0 small lh-125">
											<strong class="d-block text-gray-dark">${session.getName()}</strong>
											Duration: ${session.getLengthHours()}:${session.getLengthMinutes()}
												<br/>
											Actual Start: ${session.getActualStart().getHour()}:${session.getActualStart().getMinute() < 10 ? '0' : ''}${session.getActualStart().getMinute()} /
											 In Game Start: ${session.getInGameStart().getHour()}:${session.getInGameStart().getMinute() < 10 ? '0' : ''}${session.getInGameStart().getMinute()}
											 (Time Progression: x${session.getTimeProgression()})
											<br/>
											Number of Tire Sets: ${session.getTireSets()}
											<br/>
											Qualifying Driver Only: ${session.getQualyDriver()}
											<br/>
										</p>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>

			<!-- Content Row -->
			<div class="row">
				<!-- Track Location -->
				<div class="col-xl-8 col-lg-7 mb-4">
					<div class="card shadow h-100">
						<!-- Card Header -->
						<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
							<h6 class="m-0 font-weight-bold text-danger">Track Location</h6>
							</div>
						<!-- Card Body -->
						<div class="card-body">
							<c:set var="track" value="${eventDetails.getTrack()}"/>
							<div class="col-lg-12 border-bottom border-gray">
								<div class="media text-muted">
									<img
										data-src="holder.js/32x32?theme=thumb&amp;bg=007bff&amp;fg=007bff&amp;size=1"
										alt="32x32" class="mr-2 rounded" style="width: 32px; height: 32px;"
										src="../img/flags/${eventDetails.getLocation().getFlagUrl()}"
										data-holder-rendered="true">
										<p class="media-body pb-3 mb-0 small lh-125">
										<strong class="d-block text-gray-dark">${track.getName()}</strong>
										${eventDetails.getLocation().getCountry()}
									</p>
								</div>
							</div>
							<div class="row mx-0">
								<c:if test="${!isIGTC}">
									<div class="col-lg-3">
									</div>
								</c:if>
								<div class="${isIGTC ? 'col-lg-12 pt-2' : 'col-lg-6'}">
									<jsp:include page="../includes/locationmap/opentag.jsp" />
									<div class="world__location" style="display: block; left: ${track.getMapLeft()}%; top: ${track.getMapTop()}%; opacity: 0.684062;">
										<div class="world__location-ring"></div>
										<div class="world__location-dot"></div>
									</div>
									<c:choose>
										<c:when test="${isIGTC}">
											<c:set var="map" value="/img/worlddotmap.png"/>
										</c:when>
										<c:otherwise>
											<c:set var="map" value="/img/europedotmap.png"/>
										</c:otherwise>
									</c:choose>
									<jsp:include page="../includes/locationmap/closetag.jsp">
										<jsp:param value="${map}" name="map"/>
									</jsp:include>
								</div>
								<c:if test="${!isIGTC}">
									<div class="col-lg-3">
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				
				<!-- Host -->
				<div class="col-xl-4 col-lg-5 mb-4">
					<div class="card shadow h-100">
						<!-- Card Header -->
						<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
							<h6 class="m-0 font-weight-bold text-danger">Host</h6>
						</div>
						<!-- Card Body -->
						<div class="card-body">
							<div class="col-lg-12 d-flex flex-column">
								<div class="d-flex justify-content-center">
									<img src="../img/hosts/${eventDetails.getHost().getLogo()}">
									</div>
								<div class="media text-muted pt-3">
									<p class="media-body mb-0 small lh-125">
										<strong class="d-block text-gray-dark">${eventDetails.getHost().getName()}</strong>
										<c:if test="${not empty eventDetails.getHost().getDiscord()}">
											Discord: <a class="link-dark" href="${eventDetails.getHost().getDiscord()}">${eventDetails.getHost().getDiscord()}</a>
											<br />
										</c:if>
										<c:if test="${not empty eventDetails.getHost().getUrl()}">
											Website: <a class="link-dark" href="${eventDetails.getHost().getUrl()}">${eventDetails.getHost().getUrl()}</a>
										</c:if>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- Teams Widget -->
			<c:if test="${not empty eventDetails.getTeams()}">
				<div class="row">
					<c:forEach var="teamDetails" items="${eventDetails.getTeams()}">
						<!-- Total Race Starts -->
						<a class="col-xl-3 col-md-6 mb-4" href="/teams?cmd=manager&teamId=${teamDetails.getTeamId()}">
							<div class="card border-left-primary shadow h-100 py-2 border ${teamDetails.getStatusStyle()}">
								<div class="card-body pt-3">
									<div class="row no-gutters align-items-center">
										<div class="col mr-2">
											<div class="text-xs font-weight-bold text-danger text-uppercase mb-1">${teamDetails.getTeam().getName()}</div>
											<div class="h6 mb-0 font-weight-bold text-secondary">${teamDetails.getCar().getFullNameAndYear()}</div>
											<c:forEach var="driver" items="${teamDetails.getDrivers()}">
												<div class="h6 mb-0 text-secondary">${driver.getName()}</div>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
						</a>
					</c:forEach>
				</div>
			</c:if>
			
			
			<c:if test="${not empty eventDetails.getEvent().getRegulations()}">
				<div class="row">
					<!-- Regulations -->
					<div class="col-lg-12">
						<div class="card shadow mb-4">
							<!-- Card Header -->
							<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
								<h6 class="m-0 font-weight-bold text-danger">Regulations</h6>
							</div>
							<!-- Card Body -->
							<div class="card-body">
								${eventDetails.getEvent().getRegulations()}
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</div>
		<!-- /.container-fluid -->
	</div>
	<!-- End of Main Content -->
</section>

<jsp:include page="../includes/footer.jsp" />