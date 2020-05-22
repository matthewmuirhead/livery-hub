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
	<div class="row bg-white mx-0 rounded">
		<c:if test="${!isIGTC}">
			<div class="col-lg-3 pt-3">
			</div>
		</c:if>
		<div class="${isIGTC ? 'col-lg-12' : 'col-lg-6'} pt-3">
			<jsp:include page="../includes/locationmap/opentag.jsp" />
			<div class="world__location" style="display: block; left: ${eventDetails.getTrack().getMapLeft()}%; top: ${eventDetails.getTrack().getMapTop()}%; opacity: 0.684062;">
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
			<div class="col-lg-3 pt-3">
			</div>
		</c:if>
	</div>
</section>

<jsp:include page="../includes/footer.jsp" />