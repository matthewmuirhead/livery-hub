<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../template/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white">${selectedSeries.getName()}</h6>
	</div>
	<c:if test="${not empty sessionScope.Session_User}">
		<div class="d-flex align-items-center new-item">
			<a href="/series/team" class="hvr-underline-from-left hvr-float">New Team</a>
			<c:if test="${sessionScope.Session_User.getAdmin()}">
				<a href="/series?cmd=edit&id=${selectedSeries.getId()}" class="hvr-underline-from-left hvr-float ml-3">Edit Series</a>
			</c:if>
		</div>
	</c:if>
</div>

<section>
	<!-- Content Wrapper -->
	<div id="content-wrapper" class="d-flex flex-column">
		<!-- Begin Page Content -->
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12 mb-4">
					<img class="d-block img-fluid rounded header-img" src="/img/series/${selectedSeries.getId()}/header.jpg" alt="${displayItem.getAltText()}">
				</div>
			</div>
			
			<div class="row">
				<!-- Description -->
				<div class="col-lg-12">
					<div class="card shadow mb-4">
						<!-- Card Header -->
						<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
							<h6 class="m-0 font-weight-bold text-danger">Series Details</h6>
						</div>
						<!-- Card Body -->
						<div class="card-body p-4">
							<div class="col-lg-12 pb-4 border-bottom border-gray">
								<div class="d-flex flex-row align-items-center mb-2">
									<i class="fa fa-calendar mr-1" aria-hidden="true"></i>
									<div class="h6 mb-0 mr-1 font-weight-bold text-secondary">Host:</div>
									<div class="h6 mb-0 text-secondary">${selectedSeries.getHost()}</div>
								</div>
								<div class="d-flex flex-row align-items-center">
									<i class="fa fa-user mr-1" aria-hidden="true"></i>
									<div class="h6 mb-0 mr-1 font-weight-bold text-secondary">Duration:</div>
									<div class="h6 mb-0 text-secondary"> ${localise.formatDate(selectedSeries.getStart())} - ${localise.formatDate(selectedSeries.getEnd())}</div>
								</div>
							</div>
							<div class="col-lg-12 pt-4 d-flex flex-row justify-content-around">
								<button id="downloadAll" class="col-lg-5 btn btn-danger bg-red-fade">Download All Liveries</button>
								<button id="downloadSelected" class="col-lg-5 btn btn-danger bg-red-fade" disabled>Download 0 Selected Liveries</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- Teams Widget -->
			<div class="row">
				<c:forEach var="team" items="${teams}">
					<div class="col-xl-4 col-md-6 mb-4">
						<div class="card border-left-primary shadow h-100 py-2 border">
							<div class="card-body pt-3">
								<div class="row no-gutters align-items-center">
									<div class="col mr-2">
										<div class="text-xs font-weight-bold text-danger text-uppercase mb-1">${team.getName()}</div>
										<div class="h6 mb-1 font-weight-bold text-secondary">#${team.getCarNumber()} ${team.getCar()}</div>
										<img class="w-100 py-2" src="/img/series/${selectedSeries.getId()}/teams/${team.getName()}.jpg">
										<div class="col-lg-12 d-flex flex-column px-0">
											<div class="col-lg-12 px-0 my-1 d-flex flex-row justify-content-between">
												<button class="col-lg-${sessionScope.Session_User.canAccessTeam(selectedSeries.getId()) ? '7' : '12'} btn btn-danger bg-red-fade">Download Livery</button>
												<c:if test="${sessionScope.Session_User.canAccessTeam(selectedSeries.getId())}">
													<a class="col-lg-4 px-0" href="/series/team?id=${team.getId()}"><button class="btn btn-danger bg-red-fade w-100">Edit</button></a>
												</c:if>
											</div>
											<div class="col-lg-12 px-0 my-1 d-flex flex-row justify-content-between">
												<button class="col-lg-12 btn btn-danger bg-red-fade" data-team-id="${team.getId()}" onclick="selectLivery(this)">Select Livery</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
			
		</div>
		<!-- /.container-fluid -->
	</div>
	<!-- End of Main Content -->
</section>

<jsp:include page="../../template/footer.jsp" />

<script>
var selected = [];

function selectLivery(button) {
	var teamId = $(button).data('team-id');
	if (selected.includes(teamId)) {
		// Unselect
		position = $.inArray(teamId, selected);
		if ( ~position ) {
			selected.splice(position, 1);
			$(button).html('Select Livery');
			$(button).addClass('bg-red-fade');
			$(button).removeClass('bg-blue-fade');
			
			var downloadSelectedHtml = $('#downloadSelected').html();
			downloadSelectedHtml = downloadSelectedHtml.replace(selected.length+1, selected.length);
			$('#downloadSelected').html(downloadSelectedHtml);
		}
	} else {
		// Select
		$(button).html('Selected Livery');
		selected.push(teamId);
		$(button).addClass('bg-blue-fade');
		$(button).removeClass('bg-red-fade');

		var downloadSelectedHtml = $('#downloadSelected').html();
		downloadSelectedHtml = downloadSelectedHtml.replace(selected.length-1, selected.length);
		$('#downloadSelected').html(downloadSelectedHtml);
	}
	
	if (selected.length > 0) {
		$('#downloadSelected').removeAttr('disabled');
		$('#downloadSelected').addClass('bg-blue-fade');
		$('#downloadSelected').removeClass('bg-red-fade');
	} else {
		$('#downloadSelected').attr('disabled', 'true');
		$('#downloadSelected').addClass('bg-red-fade');
		$('#downloadSelected').removeClass('bg-blue-fade');
	}
}
</script>