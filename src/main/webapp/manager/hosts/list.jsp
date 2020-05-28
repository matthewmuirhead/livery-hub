<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../template/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white">Hosts</h6>
		<small>All Available Event Hosts</small>
	</div>
	<div class="d-flex align-items-center new-item">
		<a href="/manager/hosts?cmd=new" class="hvr-underline-from-left hvr-float">Add New</a>
	</div>
</div>
<jsp:include page="../../includes/carousel.jsp" />

<section>
	<div class="container">
		<div class="row">
			<c:forEach items="${hosts}" var="host">
				<div class="col-md-4 mt-4">
					<a href="/manager/hosts?cmd=view&id=${host.getId()}">
						<div class="card profile-card">
							<div class="card-img-block">
								<img class="card-img-top"
									src="../img/cars/manufacturers/${host.getLogo()}"
									alt="${host.getName()} Logo">
							</div>
							<div class="card-body pt-0">
								<h5 class="card-title">
									${host.getName()}
								</h5>
								<p class="card-text">${not empty host.getUrl() ? host.getUrl() : host.getDiscord()}</p>
							</div>
						</div>
					</a>
				</div>
			</c:forEach>
		</div>
	</div>
</section>

<jsp:include page="../../template/footer.jsp" />