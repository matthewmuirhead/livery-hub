<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../template/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white">Cars</h6>
		<small>Available Car List</small>
	</div>
	<div class="d-flex align-items-center new-item">
		<a href="/manager/cars?cmd=new" class="hvr-underline-from-left hvr-float">Add New</a>
	</div>
</div>
<jsp:include page="../../includes/carousel.jsp" />

<section>
	<div class="container">
		<div class="row">
			<c:forEach items="${cars}" var="car">
				<div class="col-md-4 mt-4">
					<a href="/manager/cars?cmd=view&id=${car.getId()}">
						<div class="card profile-card">
							<div class="card-img-block">
								<img class="card-img-top"
									src="../img/cars/manufacturers/${car.getLogo()}"
									alt="${car.getManufacturer()} Logo">
							</div>
							<div class="card-body pt-0">
								<h5 class="card-title">
									${car.getFullName()}
								</h5>
								<p class="card-text">${car.getYear()}</p>
	
							</div>
						</div>
					</a>
				</div>
			</c:forEach>
		</div>
	</div>
</section>

<jsp:include page="../../template/footer.jsp" />