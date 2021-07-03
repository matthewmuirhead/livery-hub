<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../template/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white">Upcoming Events</h6>
		<small>List View</small>
	</div>
</div>
<jsp:include page="../../includes/carousel.jsp" />

<section>
	<div class="container">
		<div class="row">
			<div class="col-md-12 pb-3">
				<div class="float-right">
					<a href="series?view=list" class="float-right"><button class="btn btn-danger bg-blue-fade active">${languageFieldsList.getTranslation('List View')}</button></a>
					<a href="series" class="float-right pr-1"><button class="btn btn-danger bg-red-fade">${languageFieldsList.getTranslation('Grid View')}</button></a>
				</div>
			</div>
			<div class="col-md-12">
				<c:forEach items="${seriesList}" var="series">
					<a class="row row-striped" href="/series?cmd=view&id=${series.getId()}">
						<div class="col-2 text-right">
							<img src="/img/series/${series.getId()}/logo.jpg" style="width:100%"/>
						</div>
						<div class="col-10">
							<h3 class="text-uppercase"><strong>${series.getName()}</strong></h3>
							<ul class="list-inline">
							    <li class="list-inline-item"><i class="fa fa-calendar" aria-hidden="true"></i> ${languageFieldsList.getTranslation('Duration: {start} - {end}', localise.formatDate(series.getStart()), localise.formatDate(series.getEnd()))}</li>
								<li class="list-inline-item"><i class="fa fa-user" aria-hidden="true"></i> ${languageFieldsList.getTranslation('Hosted by: {host}', series.getHost())}</li>
							</ul>
							<br/>
						</div>
					</a>
				</c:forEach>			
			</div>
		</div>
	</div>
</section>

<jsp:include page="../../template/footer.jsp" />