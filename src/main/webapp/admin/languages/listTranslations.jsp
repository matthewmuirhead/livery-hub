<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="HTMLUtil" class= "com.codemaven.liveries.util.HTMLUtil"/>

<jsp:include page="../../template/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white">${languageFieldsList.getTranslation('Edit {language} Translations', selectedLanguage.getName())}</h6>
		<small>${languageFieldsList.getTranslation('All translations for {language} in the system', selectedLanguage.getName())}</small>
	</div>
</div>
<jsp:include page="../../includes/carousel.jsp" />

<section>
	<div class="container">
		<div class="row">
			<div class="col-md-12 pb-3">
				<input class="w-100 form-control" id="search" type="text" placeholder="${languageFieldsList.getTranslation('Search')}" onkeyup="search(this)"/>
			</div>
			<div class="col-md-12 pb-3 alert alert-success" role="alert" id="successMessage" style="display:none">
				<svg class="bi bi-check-circle alert-icon" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
					<path fill-rule="evenodd" d="M15.354 2.646a.5.5 0 010 .708l-7 7a.5.5 0 01-.708 0l-3-3a.5.5 0 11.708-.708L8 9.293l6.646-6.647a.5.5 0 01.708 0z" clip-rule="evenodd" />
					<path fill-rule="evenodd" d="M8 2.5A5.5 5.5 0 1013.5 8a.5.5 0 011 0 6.5 6.5 0 11-3.25-5.63.5.5 0 11-.5.865A5.472 5.472 0 008 2.5z" clip-rule="evenodd" />
				</svg>
				<span style="padding-left: 5px;" id="failure_message_content">
					${languageFieldsList.getTranslation('Translation Saved')}
				</span>
			</div>
			<div class="col-md-12 pb-3">
				<div class="d-flex flex-column align-items-end">
					<table class="table table-hover mt-3 mb-0">
						<thead>
							<tr>
								<th>Key</th>
								<th>Translation</th>
							</tr>
						</thead>
						<tbody id="translations-table">
							<c:forEach var="entry" items="${languageTranslations}">
								<tr data-key="${entry.key}" data-key-attribute="translation_${HTMLUtil.validAttribute(entry.key)}">
									<td>${entry.key}</td>
									<td id="translation_${HTMLUtil.validAttribute(entry.key)}">${entry.value}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</section>

<div class="modal fade" id="editTranslationModal" tabindex="-1" role="dialog" aria-labelledby="editTranslationModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Edit Translation</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<div class="alert alert-danger" role="alert" id="failureMessage" style="display:none">
			<svg class="bi bi-x-circle alert-icon" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
				<path fill-rule="evenodd" d="M8 15A7 7 0 108 1a7 7 0 000 14zm0 1A8 8 0 108 0a8 8 0 000 16z" clip-rule="evenodd"/>
			  	<path fill-rule="evenodd" d="M11.854 4.146a.5.5 0 010 .708l-7 7a.5.5 0 01-.708-.708l7-7a.5.5 0 01.708 0z" clip-rule="evenodd"/>
			 	<path fill-rule="evenodd" d="M4.146 4.146a.5.5 0 000 .708l7 7a.5.5 0 00.708-.708l-7-7a.5.5 0 00-.708 0z" clip-rule="evenodd"/>
			</svg>
			<span style="padding-left: 5px;" id="failure_message_content">
				${languageFieldsList.getTranslation('Failed to saved translation')}
			</span>
		</div>
        <form>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Key:</label>
            <input type="text" class="form-control" id="key" disabled />
          </div>
          <div class="form-group">
            <label for="message-text" class="col-form-label">Translation:</label>
            <input class="form-control" id="translation" />
          </div>
          <input type="hidden" id="languageId" value="${selectedLanguage.getId()}" />
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="submit">Save</button>
      </div>
    </div>
  </div>
</div>

<jsp:include page="../../template/footer.jsp" />

<script>
function search(element) {
	var value = element.value.toLowerCase();
	$("#translations-table tr").filter(function() {
		$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	});
}

$('#translations-table tr').on('click', function () {
	var key = $(this).data('key');
	var keyAttribute = $(this).data('key-attribute');
	var translation = $('#'+keyAttribute).html();
	$('#failureMessage').hide();
	$('#successMessage').hide();
	$('#editTranslationModal').modal('show');

	var modal = $('#editTranslationModal');
	modal.find('#key').val(key);
	modal.find('#translation').val(translation);
});

$('#submit').on('click', function () {
	$.ajax({
		url:'./languages',
		method:"POST",
		data:{
			"cmd":"saveTranslation",
			"key":$('#key').val(),
			"translation":$('#translation').val(),
			"languageId":$('#languageId').val(),
		}
	})
	.done(function (data) {
		$('#translation_'+data.key).html(data.translation);
		$('#successMessage').show();
		$('#editTranslationModal').modal('hide');
	})
	.fail(function (data) {
		$('#failureMessage').show();
	});
});
</script>