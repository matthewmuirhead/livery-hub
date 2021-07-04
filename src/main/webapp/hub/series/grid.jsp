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
		<c:set var="seriesList" value="${activeSeries}" scope="request"/>
		<jsp:include page="includes/gridItem.jsp">
			<jsp:param value="Active Series" name="title"/>
			<jsp:param value="true" name="firstItem"/>
		</jsp:include>
		<c:set var="seriesList" value="${upcomingSeries}" scope="request"/>
		<jsp:include page="includes/gridItem.jsp">
			<jsp:param value="Upcoming Series" name="title"/>
		</jsp:include>
		<c:set var="seriesList" value="${finishedSeries}" scope="request"/>
		<jsp:include page="includes/gridItem.jsp">
			<jsp:param value="Finished Series" name="title"/>
		</jsp:include>
	</div>
</section>

<jsp:include page="../../template/footer.jsp" />