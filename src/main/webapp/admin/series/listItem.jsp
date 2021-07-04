<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
	<div class="col-md-12 pt-3">
		<input class="w-100 form-control" id="search" type="text" placeholder="${param.searchPlaceholder}" onkeyup="search(this, '${param.tableId}')"/>
	</div>
	<div class="col-md-12 pb-4">
		<div class="d-flex flex-column align-items-end">
			<table class="table table-hover mt-3 mb-0">
				<thead>
					<tr>
						<th class="w-30">${languageFieldsList.getTranslation('Name')}</th>
						<th class="w-25">${languageFieldsList.getTranslation('Host')}</th>
						<th class="w-15">${languageFieldsList.getTranslation('Start')}</th>
						<th class="w-15">${languageFieldsList.getTranslation('End')}</th>
						<th class="w-15"></th>
					</tr>
				</thead>
				<tbody id="${param.tableId}">
					<c:forEach var="series" items="${seriesList}">
						<tr id="series_${series.getId()}">
							<td>${series.getName()}</td>
							<td>${series.getHost()}</td>
							<td>${localise.formatDate(series.getStart())}</td>
							<td>${localise.formatDate(series.getEnd())}</td>
							<td class="pr-1 text-right">
								<a href="/admin/series?id=${series.getId()}" class="btn btn-primary">Edit</a>
								<button id="series_remove_${series.getId()}" class="btn btn-danger" onclick="removeTeam(this)">Remove</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>