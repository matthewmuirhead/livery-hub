<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<input type="hidden" name="seriesId" value="${selectedSeries.getId()}" />
				<div class="col-md-10 offset-md-1">
					<div class="mt-4 mb-2">
						<div class="col-sm-12 pb-3">
							<label for="name">Series Name</label>
							<input type="text" class="form-control" placeholder="Series Name" name="name" value="${selectedSeries.getName()}">
						</div>
						<div class="col-sm-12 pb-3">
							<label for="host">Host</label>
							<input type="text" class="form-control" placeholder="Host Name" name="host" value="${selectedSeries.getHost()}">
						</div>
						<div class="col-sm-6 pb-3">
							<label for="startDate">Start Date</label>
							<input type="datetime-local" class="form-control" name="startDate" value="${selectedSeries.getStart()}">
						</div>
						<div class="col-sm-6 pb-3">
							<label for="endDate">End Date</label>
							<input type="datetime-local" class="form-control" name="endDate" value="${selectedSeries.getEnd()}">
						</div>
						
						<div class="col-lg-12">
							<label for="header">Header Image</label>
							<!-- Upload image input-->
							<div class="input-group mb-3 px-2 py-2 rounded bg-white shadow-sm">
								<input id="header" type="file" name="header" class="upload form-control border-0">
								<label id="header-label" for="header" class="upload-label text-muted">Choose file</label>
								<div class="input-group-append">
									<label for="header" class="btn btn-light m-0 rounded px-4"> <i class="fa fa-cloud-upload mr-2 text-muted"></i><small class="text-uppercase font-weight-bold text-muted">Choose file</small></label>
								</div>
							</div>
							<!-- Uploaded image area-->
							<div class="image-area mt-4 d-flex flex-column justify-content-center">
								<span id="headerResultPlaceholder" class="font-italic align-self-center">No Header Image</span>
								<img id="headerResult" src="#" alt="" class="img-fluid rounded shadow-sm mx-auto d-block">
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

/*  ==========================================
    SHOW UPLOADED IMAGE
* ========================================== */

$('#header').on('change', function () {
	readURL(this, '#headerResultPlaceholder', '#headerResult');
});

function readURL(element, resultPlaceholderId, resultId) {
	var input = $(element)[0];
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function (e) {
			$(resultPlaceholderId).hide();
			$(resultId).addClass('header-img');
			$(resultId).attr('src', e.target.result);
		};
		reader.readAsDataURL(input.files[0]);
	}
}

/*  ==========================================
    SHOW UPLOADED IMAGE NAME
* ========================================== */

$('#header').on('change', function () {
	showFileName(this, '#header-label');
});

function showFileName(element, labelId) {
	var fileName = $(element)[0].files[0].name;
	$(labelId).html('File name: ' + fileName);
}

</script>