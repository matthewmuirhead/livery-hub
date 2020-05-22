<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../includes/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow">
	<div class="lh-100">
		<h6 class="mb-0 text-white">Tracks</h6>
		<small>Available Track List</small>
	</div>
</div>
<jsp:include page="../includes/carousel.jsp" />

<div class="my-3 mx-0 p-3 bg-white rounded box-shadow row">
	<h6 class="border-bottom border-gray pb-2 mb-0 col-lg-12">
		Intercontinental GT Challenge
	</h6>
	<div class="col-lg-12">
		<c:forEach items="${igtc}" var="map">
			<c:set var="track" value="${map.key}"/>
			<c:set var="location" value="${map.value}"/>
			<div class="media text-muted pt-3">
				<img
					data-src="holder.js/32x32?theme=thumb&amp;bg=007bff&amp;fg=007bff&amp;size=1"
					alt="32x32" class="mr-2 rounded" style="width: 32px; height: 32px;"
					src="../img/flags/${location.getFlagUrl()}"
					data-holder-rendered="true">
				<p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
					<strong class="d-block text-gray-dark">${track.getName()}</strong>
					${location.getCountry()}
				</p>
			</div>
		</c:forEach>
	</div>
	<div class="col-lg-12 pt-3">
		<jsp:include page="../includes/locationmap/opentag.jsp" />
		<c:forEach items="${igtc}" var="track">
			<div class="world__location" style="display: block; left: ${track.key.getMapLeft()}%; top: ${track.key.getMapTop()}%; opacity: 0.684062;">
				<div class="world__location-ring"></div>
				<div class="world__location-dot"></div>
			</div>
		</c:forEach>
		<jsp:include page="../includes/locationmap/closetag.jsp">
			<jsp:param value="/img/worlddotmap.png" name="map"/>
		</jsp:include>
	</div>
</div>


<div class="my-3 mx-0 p-3 bg-white rounded box-shadow row">
	<h6 class="border-bottom border-gray pb-2 mb-0 col-lg-12">
		GT World Challenge 2019
	</h6>
	<div class="col-lg-6">
		<c:forEach items="${gtwc2019}" var="map">
			<c:set var="track" value="${map.key}"/>
			<c:set var="location" value="${map.value}"/>
			<div class="media text-muted pt-3">
				<img
					data-src="holder.js/32x32?theme=thumb&amp;bg=007bff&amp;fg=007bff&amp;size=1"
					alt="32x32" class="mr-2 rounded" style="width: 32px; height: 32px;"
					src="../img/flags/${location.getFlagUrl()}"
					data-holder-rendered="true">
				<p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
					<strong class="d-block text-gray-dark">${track.getName()}</strong>
					${location.getCountry()}
				</p>
			</div>
		</c:forEach>
	</div>
	<div class="col-lg-6">
		<jsp:include page="../includes/locationmap/opentag.jsp" />
		<c:forEach items="${gtwc2019}" var="track">
			<div class="world__location" style="display: block; left: ${track.key.getMapLeft()}%; top: ${track.key.getMapTop()}%; opacity: 0.684062;">
				<div class="world__location-ring"></div>
				<div class="world__location-dot"></div>
			</div>
		</c:forEach>
		<jsp:include page="../includes/locationmap/closetag.jsp">
			<jsp:param value="/img/europedotmap.png" name="map"/>
		</jsp:include>
	</div>
</div>

<div class="my-3 mx-0 p-3 bg-white rounded box-shadow row">
	<h6 class="border-bottom border-gray pb-2 mb-0 col-lg-12">
		GT World Challenge 2018
	</h6>
	<div class="col-lg-6">
		<c:forEach items="${gtwc2018}" var="map">
			<c:set var="track" value="${map.key}"/>
			<c:set var="location" value="${map.value}"/>
			<div class="media text-muted pt-3">
				<img
					data-src="holder.js/32x32?theme=thumb&amp;bg=007bff&amp;fg=007bff&amp;size=1"
					alt="32x32" class="mr-2 rounded" style="width: 32px; height: 32px;"
					src="../img/flags/${location.getFlagUrl()}"
					data-holder-rendered="true">
				<p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
					<strong class="d-block text-gray-dark">${track.getName()}</strong>
					${location.getCountry()}
				</p>
			</div>
		</c:forEach>
	</div>
	<div class="col-lg-6">
		<jsp:include page="../includes/locationmap/opentag.jsp" />
		<c:forEach items="${gtwc2019}" var="track">
			<div class="world__location" style="display: block; left: ${track.key.getMapLeft()}%; top: ${track.key.getMapTop()}%; opacity: 0.684062;">
				<div class="world__location-ring"></div>
				<div class="world__location-dot"></div>
			</div>
		</c:forEach>
		<jsp:include page="../includes/locationmap/closetag.jsp">
			<jsp:param value="/img/europedotmap.png" name="map"/>
		</jsp:include>
	</div>
</div>

<jsp:include page="../includes/footer.jsp" />