<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../includes/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h3 class="mb-0">${eventDetails.getEvent().getName()}</h3>
	</div>
	<div class="d-flex align-items-center new-item">
		<a href="/calendar?cmd=new" class="hvr-underline-from-left hvr-float">Add New</a>
	</div>
</div>

<section>

</section>

<jsp:include page="../includes/footer.jsp" />