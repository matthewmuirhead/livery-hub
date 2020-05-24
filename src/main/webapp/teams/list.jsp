<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../includes/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white">Cars</h6>
		<small>Available Car List</small>
	</div>
	<div class="d-flex align-items-center new-item">
		<a href="/cars?cmd=new" class="hvr-underline-from-left hvr-float">Add New</a>
	</div>
</div>
<jsp:include page="../includes/carousel.jsp" />

<section>
	<div class="container">
		<div class="row">
			<div class="col-md-12 pb-3">
				<input class="w-100 form-control" id="search" type="text" placeholder="Search"/>
			</div>
			<div class="col-md-12 pb-3">
				<div class="d-flex flex-column align-items-end">
					<table class="table table-hover mt-3 mb-0">
						<thead>
							<tr>
								<th>Event</th>
								<th>Team Name</th>
								<th>Car</th>
								<th>Number</th>
								<th></th>
							</tr>
						</thead>
						<tbody id="teams-table">
							
							<c:forEach var="teamDetails" items="${teamDetailsList}">
								<tr id="team_${teamDetails.getTeamId()}">
									<td>${teamDetails.getEvent().getName()}</td>
									<td>${teamDetails.getTeam().getName()}</td>
									<td>${teamDetails.getCar().getFullNameAndYear()}</td>
									<td>${teamDetails.getTeam().getNumber()}</td>
									<td class="pr-0 text-right">
										<a href="/teams?cmd=manager&teamId=${teamDetails.getTeamId()}" class="btn btn-primary">Manager</a>
										<a href="/teams?cmd=edit&teamId=${teamDetails.getTeamId()}" class="btn btn-secondary">Edit</a>
										<button id="team_remove_${teamDetails.getTeamId()}" class="btn btn-danger" onclick="removeTeam(this)">Remove</button>
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

<jsp:include page="../includes/footer.jsp" />

<script>

$(document).ready(function(){
	  $("#search").on("keyup", function() {
	    var value = $(this).val().toLowerCase();
	    $("#teams-table tr").filter(function() {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});


</script>