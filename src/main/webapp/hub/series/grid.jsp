<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../template/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white">${languageFieldsList.getTranslation('Series')}</h6>
		<small>${languageFieldsList.getTranslation('All Available Series')}</small>
	</div>
</div>
<jsp:include page="../../includes/carousel.jsp" />

<section>
	<div class="container">
		<div class="row">
			<div class="col-md-12 pb-3">
				<div class="float-right">
					<a href="series?view=list" class="float-right"><button class="btn btn-danger bg-red-fade">${languageFieldsList.getTranslation('List View')}</button></a>
					<a href="series" class="float-right pr-1"><button class="btn btn-danger bg-blue-fade active">${languageFieldsList.getTranslation('Grid View')}</button></a>
				</div>
			</div>
			<c:forEach items="${seriesList}" var="series">
				<div class="col-md-4 mt-4">
					<a href="/series?id=${series.getId()}">
						<div class="card profile-card">
							<div class="card-img-block">
								<img class="card-img-top"
									src="/img/series/${series.getId()}/logo.jpg"
									alt="${series.getName()} Logo">
							</div>
							<div class="card-body pt-0">
								<h5 class="card-title">
									${series.getName()}
								</h5>
								<ul class="list-unstyled">
								    <li><i class="far fa-calendar" aria-hidden="true"></i> ${languageFieldsList.getTranslation('Duration: {start} - {end}', localise.formatDate(series.getStart()), localise.formatDate(series.getEnd()))}</li>
									<li><i class="far fa-user" aria-hidden="true"></i> ${languageFieldsList.getTranslation('Hosted by: {host}', series.getHost())}</li>
								</ul>
							</div>
						</div>
					</a>
				</div>
			</c:forEach>
		</div>
	</div>
</section>

<jsp:include page="../../template/footer.jsp" />