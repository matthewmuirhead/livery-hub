<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../template/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white">${languageFieldsList.getTranslation('Languages')}</h6>
		<small>${languageFieldsList.getTranslation('All languages in the system')}</small>
	</div>
</div>
<jsp:include page="../../includes/carousel.jsp" />

<section>
	<div class="container">
		<nav>
			<div class="nav nav-tabs" id="nav-tab" role="tablist">
				<a class="nav-item nav-link" id="nav-upcoming-tab" data-toggle="tab" href="#nav-upcoming" role="tab" aria-controls="nav-upcoming" aria-selected="false">
					Upcoming
				</a>
				<a class="nav-item nav-link active" id="nav-active-tab" data-toggle="tab" href="#nav-active" role="tab" aria-controls="nav-active" aria-selected="true">
					Active
				</a>
				<a class="nav-item nav-link" id="nav-finished-tab" data-toggle="tab" href="#nav-finished" role="tab" aria-controls="nav-finished" aria-selected="false">
					Finished
				</a>
			</div>
		</nav>
		<div class="tab-content" id="nav-tabContent">
			<div class="tab-content" id="nav-tabContent">
				<div class="tab-pane fade" id="nav-upcoming" role="tabpanel" aria-labelledby="nav-upcoming-tab">
					<c:set var="seriesList" value="${upcomingSeries}" scope="request"/>
					<jsp:include page="listItem.jsp">
						<jsp:param value="upcoming-series-table" name="tableId"/>
						<jsp:param value="${languageFieldsList.getTranslation('Search Upcoming Series')}" name="searchPlaceholder"/>
					</jsp:include>
				</div>
				<div class="tab-pane fade show active" id="nav-active" role="tabpanel" aria-labelledby="nav-active-tab">
					<c:set var="seriesList" value="${activeSeries}" scope="request"/>
					<jsp:include page="listItem.jsp">
						<jsp:param value="active-series-table" name="tableId"/>
						<jsp:param value="${languageFieldsList.getTranslation('Search Active Series')}" name="searchPlaceholder"/>
					</jsp:include>
				</div>
				<div class="tab-pane fade" id="nav-finished" role="tabpanel" aria-labelledby="nav-finished-tab">
					<c:set var="seriesList" value="${finishedSeries}" scope="request"/>
					<jsp:include page="listItem.jsp">
						<jsp:param value="finished-series-table" name="tableId"/>
						<jsp:param value="${languageFieldsList.getTranslation('Search Finished Series')}" name="searchPlaceholder"/>
					</jsp:include>
				</div>
			</div>
		</div>
	</div>
</section>

<jsp:include page="../../template/footer.jsp" />

<script>
function search(element, tableId)
{
	var value = element.value.toLowerCase();
	$("#"+tableId+" tr").filter(function() {
		$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	});
}
</script>