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
		<div class="row">
			<div class="col-md-12 pb-3">
				<input class="w-100 form-control" id="search" type="text" placeholder="${languageFieldsList.getTranslation('Search')}" onkeyup="search(this)"/>
			</div>
			<div class="col-md-12 pb-3">
				<div class="d-flex flex-column align-items-end">
					<table class="table table-hover mt-3 mb-0">
						<thead>
							<tr>
								<th></th>
								<th>${languageFieldsList.getTranslation('Language')}</th>
								<th>${languageFieldsList.getTranslation('Code')}</th>
								<th></th>
							</tr>
						</thead>
						<tbody id="languages-table">
							<c:forEach var="language" items="${languages}">
								<tr id="language_${language.getId()}">
									<td><img src="/img/flags/${language.getFlag()}" /></td>
									<td>${language.getName()}</td>
									<td>${language.getCode()}</td>
									<td class="pr-1 text-right">
										<a href="/admin/languages?cmd=translations&languageId=${language.getId()}" class="btn btn-primary">View Translations</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</section>

<jsp:include page="../../template/footer.jsp" />

<script>
function search(element)
{
	var value = element.value.toLowerCase();
	$("#languages-table tr").filter(function() {
		$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	});
}
</script>