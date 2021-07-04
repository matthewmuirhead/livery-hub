<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
	<div class="col-md-12 pb-3 d-flex align-items-center justify-content-between">
		<p class="h2">${param.title}</p>
		<c:if test="${not empty param.firstItem}">
			<div class="d-flex align-items-center">
				<a href="series" class="float-right pr-2"><button class="btn btn-danger bg-red-fade">${languageFieldsList.getTranslation('Grid View')}</button></a>
				<a href="series?view=list" class="float-right"><button class="btn btn-danger bg-blue-fade active">${languageFieldsList.getTranslation('List View')}</button></a>
			</div>
		</c:if>
	</div>
</div>
<div class="row">
	<div class="col-md-12 pb-4">
		<c:choose>
			<c:when test="${empty seriesList}">
				<div class="row row-striped row-striped-disabled" href="/series?id=${series.getId()}">
					<div class="col-2 text-right">
						<div class="w-100"></div>
					</div>
					<div class="col-10">
						<h3 class="text-uppercase"><strong>No ${param.title}</strong></h3>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<c:forEach items="${seriesList}" var="series">
					<a class="row row-striped" href="/series?id=${series.getId()}">
						<div class="col-2 text-right">
							<c:choose>
								<c:when test="${series.getHasLogo()}">
									<img src="/img/series/${series.getId()}/logo.jpg" class="row-profile-img"/>
								</c:when>
								<c:otherwise>
									<div class="row-profile-img"></div>
								</c:otherwise>
							</c:choose>
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
			</c:otherwise>
		</c:choose>
	</div>
</div>