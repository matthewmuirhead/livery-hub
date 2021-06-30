<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../template/header.jsp"/>

<div class="masthead d-flex h-100">
	<div class="container text-center my-auto">
		<h1 class="mb-1">${languageFieldsList.getTranslation('ACC Livery Hub')}</h1>
		<h3 class="mb-5">
			<em>${languageFieldsList.getTranslation('All your series liveries in one place')}</em>
		</h3>
		<a class="btn btn-danger bg-red-fade btn-xl" href="/events">${languageFieldsList.getTranslation('View Series')}</a>
	</div>
	<div class="overlay"></div>
</div>

<jsp:include page="../template/footer.jsp"/>