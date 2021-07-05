<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="hasLogo" value="${selectedSeries.getHasLogo()}" />
<c:set var="hasHeader" value="${selectedSeries.getHasLogo()}" />
<c:set var="seriesId" value="${selectedSeries.getId()}" />

<jsp:include page="../../template/header.jsp" />
<div
	class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow">
	<div class="lh-100">
		<h3 class="mb-0">${not empty selectedSeries.getName() ? selectedSeries.getName() : languageFieldsList.getTranslation('New Series')}</h3>
	</div>
</div>

<section>
	<div class="container">
		<jsp:include page="../../includes/alertmessages.jsp" />
		<form class="well form-horizontal" id="seriesForm" action="/admin/series?cmd=save" method="POST" id="series_form">
			<fieldset>
				<input type="hidden" name="seriesId" value="${seriesId}" />
				<div class="col-md-10 offset-md-1">
					<div class="mt-4 mb-2">
						<div class="col-sm-12 pb-3">
							<label for="name">Series Name</label>
							<div class="rounded bg-white shadow-sm p-2">
								<input type="text" class="form-control border-0" placeholder="Series Name" name="name" value="${selectedSeries.getName()}">
							</div>
						</div>
						<div class="col-sm-12 pb-3">
							<label for="host">Host</label>
							<div class="rounded bg-white shadow-sm p-2">
								<input type="text" class="form-control border-0" placeholder="Host Name" name="host" value="${selectedSeries.getHost()}">
							</div>
						</div>
						<div class="col-sm-6 pb-3">
							<label for="startDate">Start Date</label>
							<div class="rounded bg-white shadow-sm p-2">
								<input type="datetime-local" class="form-control border-0" name="startDate" value="${selectedSeries.getStart()}">
							</div>
						</div>
						<div class="col-sm-6 pb-3">
							<label for="endDate">End Date</label>
							<div class="rounded bg-white shadow-sm p-2">
								<input type="datetime-local" class="form-control border-0" name="endDate" value="${selectedSeries.getEnd()}">
							</div>
						</div>
						
						<div class="col-lg-12 pb-3">
							<label for="header">Header Image</label>
							<!-- Upload image input-->
							<div class="input-group mb-3 p-2 rounded bg-white shadow-sm">
								<input id="header" type="file" name="header" class="upload form-control border-0">
								<label id="header-label" for="header" class="upload-label text-muted ml-1">Choose file</label>
								<div class="input-group-append">
									<label for="header" class="btn btn-light m-0 rounded px-4"> <i class="fa fa-cloud-upload mr-2 text-muted"></i><small class="text-uppercase font-weight-bold text-muted">Choose file</small></label>
								</div>
							</div>
							<!-- Uploaded image area-->
							<div class="image-area mt-4 d-flex flex-column justify-content-center">
								<span id="headerResultPlaceholder" class="font-italic align-self-center" ${hasHeader ? 'style="display:none;"' : ''}>No Header Image</span>
								<c:set var="headerUrl" value="/img/series/${seriesId}/header.jpg"/>
								<img id="headerResult" src="${hasHeader ? headerUrl : '#'}" alt="" class="img-fluid rounded shadow-sm mx-auto d-block${hasHeader ? ' header-img' : ''}">
							</div>
						</div>
						
						<div class="col-lg-12">
							<label for="header">Logo Image</label>
							<!-- Upload image input-->
							<div class="input-group mb-3 p-2 rounded bg-white shadow-sm">
								<input id="logo" type="file" name="header" class="upload form-control border-0">
								<label id="logo-label" for="logo" class="upload-label text-muted ml-1">Choose file</label>
								<div class="input-group-append">
									<label for="logo" class="btn btn-light m-0 rounded px-4"> <i class="fa fa-cloud-upload mr-2 text-muted"></i><small class="text-uppercase font-weight-bold text-muted">Choose file</small></label>
								</div>
							</div>
							<!-- Uploaded image area-->
							<div class="image-area mt-4 d-flex flex-column justify-content-center">
								<span id="logoResultPlaceholder" class="font-italic align-self-center" ${hasLogo ? 'style="display:none;"' : ''}>No Logo Image</span>
								<c:set var="logoUrl" value="/img/series/${seriesId}/logo.jpg"/>
								<img id="logoResult" src="${hasLogo ? logoUrl : '#'}" alt="" class="img-fluid rounded shadow-sm mx-auto d-block${hasLogo ? ' card-profile-img' : ''}" style="width:298px">
							</div>
						</div>
					</div>
				</div>
				<c:set var="customCancel" value="?cmd=view&id=${eventDetails.getEventId()}" />
				<jsp:include page="../../includes/savebuttons.jsp">
					<jsp:param value="/admin/series" name="servletUrl" />
					<jsp:param value="${not empty eventDetails.getEventId() ? customCancel : ''}" name="customCancel"/>
					<jsp:param value="eventForm" name="formId" />
				</jsp:include>
			</fieldset>
		</form>
	</div>
</section>

<jsp:include page="../../template/footer.jsp" />

<script>
$('#header').on('change', function () {
	readURL(this, '#headerResultPlaceholder', '#headerResult', 'header-img');
	showFileName(this, '#header-label');
});

$('#logo').on('change', function () {
	readURL(this, '#logoResultPlaceholder', '#logoResult', 'card-profile-img');
	showFileName(this, '#logo-label');
});

/*  ==========================================
    SHOW UPLOADED IMAGE
* ========================================== */

function readURL(element, resultPlaceholderId, resultId, resultClass) {
	var input = $(element)[0];
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function (e) {
			$(resultPlaceholderId).hide();
			$(resultId).addClass(resultClass);
			$(resultId).attr('src', e.target.result);
		};
		reader.readAsDataURL(input.files[0]);
	}
}

/*  ==========================================
    SHOW UPLOADED IMAGE NAME
* ========================================== */

function showFileName(element, labelId) {
	var fileName = $(element)[0].files[0].name;
	$(labelId).html('File name: ' + fileName);
}
</script>