<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../template/header.jsp" />
<img class="mt-3 w-100 rounded" src="../img/carousel/audi-tail.png" alt="${languageFieldsList.getTranslation('Login Header Image')}">
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow">
	<h3 class="mb-0">${languageFieldsList.getTranslation('Login')}</h3>
</div>
<section>
	<div class="container">
		<jsp:include page="../../includes/alertmessages.jsp" />
		<form class="well form-horizontal" id="loginForm" method="POST"
			id="contact_form">
			<fieldset style="margin-top:-20px">
				<div class="col-md-10 offset-md-1">
					<div class="form-row mt-4">
						<input type="hidden" name="submitted" value="true"/>
						<div class="col-sm-12 pb-3">
                            <label for="password">${languageFieldsList.getTranslation('Username')}</label>
                            <input type="text" class="form-control" name="username" value="${username}" />
                        </div>
                        
                        <div class="col-sm-12 pb-3">
                            <label for="password">${languageFieldsList.getTranslation('Password')}</label>
                            <input type="password" class="form-control" name="password" value="${password}" />
                        </div>
                        
                         <div class="col-sm-12 pb-3">
                            <input type="submit" class="form-control" value="${languageFieldsList.getTranslation('Submit')}" />
                        </div>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</section>
<jsp:include page="../../template/footer.jsp" />

<script>

</script>