<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../template/header.jsp" />
<div class="d-flex align-items-center p-3 my-3 text-white bg-red-fade rounded box-shadow justify-content-between">
	<div class="lh-100">
		<h6 class="mb-0 text-white" id="title">Upcoming Events - ${currentMonth.toString()}</h6>
		<small>Calendar View</small>
	</div>
	<div class="d-flex align-items-center new-item">
		<a href="/events?cmd=new" class="hvr-underline-from-left hvr-float">Add New</a>
	</div>
</div>
<jsp:include page="../../includes/carousel.jsp" />

<form class="d-none" id="backMonthForm">
	<input type="hidden" id="backMonthId" value="${currentMonth.getValue()-1 > 0 ? currentMonth.getValue()-1 : 12}">
	<input type="hidden" id="backYear" value="${currentMonth.getValue()-1 == 0 ? currentYear-1 : currentYear}">
</form>
<form class="d-none" id="forwardMonthForm">
	<input type="hidden" id="forwardMonthId" value="${currentMonth.getValue()+1 < 13 ? currentMonth.getValue()+1 : 1}">
	<input type="hidden" id="forwardYear" value="${currentMonth.getValue()+1 == 13 ? currentYear+1 : currentYear}">
</form>

<section>
	<div class="row">
		<div class="col-md-12 pb-3 d-flex align-items-center justify-content-between">
			<ul class="pagination my-0">
				<li class="page-item"><a class="page-link" id="backMonthLink">Previous Month</a></li>
				<li class="page-item"><a class="page-link" id="forwardMonthLink">Next Month</a></li>
				
			</ul>
			<a href="events?cmd=list" class="float-right"><button class="btn btn-danger bg-red-fade">List View</button></a>
		</div>
		<div class="col-md-12 pb-3">
			<div class="calendar-container">
				<div class="calendar-header">
					<h1 id="currentMonthDisplay">
						${currentMonth}
					</h1>
					<p id="currentYearDisplay">${currentYear}</p>
				</div>
				<div class="calendar" id="calendar">
					<span class="day-name">Mon</span>
					<span class="day-name">Tue</span>
					<span class="day-name">Wed</span>
					<span class="day-name">Thu</span>
					<span class="day-name">Fri</span>
					<span class="day-name">Sat</span>
					<span class="day-name">Sun</span>
					<c:if test="${prevMonthDays > 0}">
						<c:forEach var="day" begin="${prevMonthStart}" end="${prevMonthEnd}">
							<div class="day day--disabled">${day}</div>
						</c:forEach>
					</c:if>
					<c:forEach var="day" begin="1" end="${currentMonthDays}">
						<div class="day">${day}</div>
					</c:forEach>
					<c:if test="${nextMonthDays > 0}">
						<c:forEach var="day" begin="${nextMonthStart}" end="${nextMonthEnd}">
							<div class="day day--disabled">${day}</div>
						</c:forEach>
					</c:if>
					
					<c:forEach var="eventDetails" items="${thisMonthEventDetails}">
						<c:set var="eventDate" value="${eventDetails.getEvent().getEventDate()}" />
						<a class="task task--danger" ${eventDetails.getCalendarDisplay()} href="/events?cmd=view&id=${eventDetails.getEvent().getId()}">
							${eventDetails.getEvent().getName()}
							<div class="task__detail">
								<h2><i class="fa fa-clock-o" aria-hidden="true"></i> ${eventDate.getHour()}:${eventDate.getMinute()}</h2>
								<p><i class="fa fa-location-arrow" aria-hidden="true"></i> ${eventDetails.getTrack().getName()}</p>
							</div>
						</a>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</section>

<jsp:include page="../../template/footer.jsp" />

<script>

$(document).ready(function()
{
	$('#forwardMonthLink').on('click', fetchNextMonth);
	$('#backMonthLink').on('click', fetchPreviousMonth);
});

function fetchPreviousMonth()
{
    $.ajax({
        type: "POST",
        url: '?cmd=updateCalendar',
        data: "monthId="+$("#backMonthId").val()+"&year="+$("#backYear").val(),
        success: handleAjaxReply,
    	error: function()
    	{
        	alert("An error occured fetching events, please try again.");
    	}
	});
}

function fetchNextMonth()
{
    $.ajax({
        type: "POST",
        url: '?cmd=updateCalendar',
        data: "monthId="+$("#forwardMonthId").val()+"&year="+$("#forwardYear").val(),
        success: handleAjaxReply,
    	error: function()
    	{
        	alert("An error occured fetching events, please try again.");
    	}
	});
}

// gets called when we get a reply from an ajax save
function handleAjaxReply(data)
{
	// Remove Current Calendar
	$('#calendar').children('.day').remove();
	$('#calendar').children('.task').remove();
	
	// Update month displays
	$('#title').text("Upcoming Events - "+data.currentMonth);
	$('#currentMonthDisplay').text(data.currentMonth);
	$('#currentYearDisplay').text(data.currentYear);
	
	// Set values for next and prev month buttons
	var currMonth = parseInt(data.currentMonthValue);
	var currYear = parseInt(data.currentYear);
	$("#backMonthId").val(currMonth-1 > 0 ? currMonth-1 : 12);
	$("#backYear").val(currMonth-1 > 0 ? currYear : currYear-1);
	$("#forwardMonthId").val(currMonth+1 <= 12 ? currMonth+1 : 1);
	$("#forwardYear").val(currMonth+1 <= 12 ? currYear : currYear+1);
	
	// Add in days for month
	if (data.prevMonthDays > 0)
	{
		for (var i = data.prevMonthStart; i<=data.prevMonthEnd; i++)
		{
			$('#calendar').append('<div class="day day--disabled">'+i.toString()+'</div>');
		}
	}
	
	for (var i = 1; i<=data.currentMonthDays; i++)
	{
		$('#calendar').append('<div class="day">'+i.toString()+'</div>');
	}
	
	if (data.nextMonthDays > 0)
	{
		for (var i = data.nextMonthStart; i<=data.nextMonthEnd; i++)
		{
			$('#calendar').append('<div class="day day--disabled">'+i.toString()+'</div>');
		}
	}
	
	// Add Events
	data.events.forEach(setEvent);
}

function setEvent(item, index)
{
	var link = "<a class='task task--danger' "+item.display+" href='/events?cmd=view&id="+item.id+"'>";
	var title = item.name;
	var detail = "<div class='task__detail'>";
	var time = "<h2><i class='fa fa-clock-o' aria-hidden='true'></i> "+item.time+"</h2>";
	var location = "<p><i class='fa fa-location-arrow' aria-hidden='true'></i> "+item.location+"</p>";
	var detailClose = "</div>";
	var linkClose = "</a>";
	$('#calendar').append(link+title+detail+time+location+detailClose+linkClose);
}

</script>

