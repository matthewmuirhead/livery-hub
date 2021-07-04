<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
	<div class="col-md-12 d-flex align-items-center justify-content-between">
		<p class="h2">${param.title}</p>
		<c:if test="${not empty param.firstItem}">
			<div class="d-flex align-items-center">
				<a href="series" class="float-right pr-2"><button class="btn btn-danger bg-blue-fade active">${languageFieldsList.getTranslation('Grid View')}</button></a>
				<a href="series?view=list" class="float-right"><button class="btn btn-danger bg-red-fade">${languageFieldsList.getTranslation('List View')}</button></a>
			</div>
		</c:if>
	</div>
</div>
<div class="row">
	<div class="col-md-12 pb-4">
		<c:choose>
			<c:when test="${empty seriesList}">
				<div class="col-md-4">
					<div class="card profile-card">
						<div class="card-body">
							<h5 class="card-title">
								No ${param.title}
							</h5>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<c:forEach items="${seriesList}" var="series">
					<div class="col-md-4 mt-4">
						<a href="/series?id=${series.getId()}">
							<div class="card profile-card">
								<div class="card-img-block">
									<c:choose>
										<c:when test="${series.getHasLogo()}">
											<img class="card-img-top card-profile-img"
												src="/img/series/${series.getId()}/logo.jpg"
												alt="${series.getName()} Logo">
										</c:when>
										<c:otherwise>
											<div class="card-img-top card-profile-img"></div>
										</c:otherwise>
									</c:choose>
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
			</c:otherwise>
		</c:choose>
	</div>
</div>