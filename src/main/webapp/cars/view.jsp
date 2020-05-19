<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../includes/header.jsp" />
<c:set var="headerImg" value="<img class='mt-3 w-100 rounded' src='../img/cars/models/${car.getImage()}' alt='${car.getFullNameAndYear()} Header Image'>"/>
${not empty car.getImage() ? headerImg : ''}
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow">
	<h3 class="mb-0">${not empty car.getManufacturer() ? car.getFullNameAndYear() : 'New Car'}</h3>
</div>
<section>
	<div class="container">
		<jsp:include page="../includes/alertmessages.jsp" />
		<form class="well form-horizontal" id="carForm" action="/cars?cmd=save" method="POST"
			id="contact_form">
			<fieldset style="margin-top:-20px">
				<input type="hidden" name="carId" value="${car.getId()}"/>
	
				<div class="col-md-10 offset-md-1">
					<div class="form-row mt-4">
						<div class="col-sm-4 pb-3">
                            <label for="year">Year</label>
                            <select class="form-control" name="year">
                                <c:forEach var="year" begin="2012" end="2021">
									<option value="${year}" ${year == car.getYear() ? 'selected' : ''}>${year}</option>
								</c:forEach>
                            </select>
                        </div>
						<div class="col-sm-8 pb-3">
                            <label for="manufacturer">Manufacturer</label>
                            <input type="text" class="form-control" placeholder="Manufacturer" name="manufacturer" value="${car.getManufacturer()}">
                        </div>
                        <div class="col-sm-12 pb-3">
                            <label for="model">Model</label>
                            <input type="text" class="form-control" placeholder="Model" name="model" value="${car.getModel()}">
                        </div>
                        <div class="col-sm-12 pb-3">
                            <label for="logo">Logo URL</label>
                            <input type="text" class="form-control" placeholder="Logo" name="logo" value="${car.getLogo()}">
                        </div>
                        <div class="col-sm-12 pb-3">
                            <label for="image">Image URL</label>
                            <input type="text" class="form-control" placeholder="Header Image URL" name="image" value="${car.getImage()}">
                        </div>
					</div>
				</div>

				<jsp:include page="../includes/savebuttons.jsp">
					<jsp:param value="/cars" name="servletUrl"/>
					<jsp:param value="carForm" name="formId"/>
				</jsp:include>

			</fieldset>
		</form>
	</div>
</section>
<jsp:include page="../includes/footer.jsp" />