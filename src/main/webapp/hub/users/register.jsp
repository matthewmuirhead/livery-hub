<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../template/header.jsp" />
<img class="mt-3 w-100 rounded" src="../img/carousel/gtr-tail-pits.png"
	alt="${languageFieldsList.getTranslation('Register Header Image')}">
<div
	class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow">
	<h3 class="mb-0">${languageFieldsList.getTranslation('Register')}</h3>
</div>
<section>
	<div class="container">
		<jsp:include page="../../includes/alertmessages.jsp" />
		<form class="well form-horizontal" id="registerForm" method="POST" onsubmit="validate(this)">
			<fieldset style="margin-top: -20px">
				<div class="col-md-10 offset-md-1">
					<div class="form-row mt-4">
						<input type="hidden" name="submitted" value="true" />
						<div class="col-sm-12 pb-3">
							<label for="username">${languageFieldsList.getTranslation('Username')}</label>
							<input type="text" class="form-control" id="username" name="username" value="${username}" />
							<div id="usernameError" class="invalid-feedback">${languageFieldsList.getTranslation('Username cannot be empty')}</div>
						</div>

						<div class="col-sm-12 pb-3">
							<label for="password">${languageFieldsList.getTranslation('Password')}</label>
							<input type="password" class="form-control" id="password" name="password"
								value="${password}" />
							<div id="passwordError" class="invalid-feedback">${languageFieldsList.getTranslation('Password cannot be empty')}</div>
						</div>
						<div class="col-sm-12 pb-3">
							<label for="password">${languageFieldsList.getTranslation('Confirm Password')}</label>
							<input type="password" class="form-control" id="confirmPassword"
								name="confirmPassword" value="${confirmPassword}" />
							<div id="confirmPasswordError" class="invalid-feedback">${languageFieldsList.getTranslation('Passwords do not match')}</div>
						</div>

						<div class="col-sm-12 pb-3">
							<input type="submit" class="form-control"
								value="${languageFieldsList.getTranslation('Submit')}" />
						</div>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</section>
<jsp:include page="../../template/footer.jsp" />