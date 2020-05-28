<%@ page import="com.codemaven.events.manager.enums.NavBarZone" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="zones" value="<%=NavBarZone.values()%>" />
<c:set var="user" value="${sessionScope.Session_User}"></c:set>

<nav class="main-header navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
	<a class="navbar-brand my-2 ml-3" href="/"> <img src="../img/cm-shield.png"
		width="30" height="30" class="d-inline-block align-top" alt="">
	</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarText" aria-controls="navbarText"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarText">
		<ul class="navbar-nav mr-auto">
			<li class="mx-3 nav-item ${zones[0].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/">Dashboard</a>
			</li>
			<li class="mx-3 nav-item ${zones[3].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/leagues">Leagues</a>
			</li>
			<li class="mx-3 nav-item ${zones[5].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/events">Individual Events</a>
			</li>
			
			<c:if test="${not empty user && user.getManager()}">
				<li class="mx-3 nav-item dropdown">
					<a class="mx-3 nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Manager
					</a>
					<div class="dropdown-menu bg-dark" aria-labelledby="navbarDropdown">
						<a class="nav-link drop-link ${zones[3].name() == navbarzone.name() ? 'active' : ''}" href="/manager/events">Events</a>
						<a class="nav-link drop-link ${zones[5].name() == navbarzone.name() ? 'active' : ''}" href="/manager/teams">Teams</a>
						<c:if test="${not user.getManagerViewOnly()}">
							<div class="dropdown-divider"></div>
							<a class="nav-link drop-link ${zones[4].name() == navbarzone.name() ? 'active' : ''}" href="/manager/hosts">Hosts</a>
							<a class="nav-link drop-link ${zones[1].name() == navbarzone.name() ? 'active' : ''}" href="/manager/tracks">Tracks</a>
							<a class="nav-link drop-link ${zones[2].name() == navbarzone.name() ? 'active' : ''}" href="/manager/cars">Cars</a>
						</c:if>
					</div>
				</li>
			</c:if>
			<c:if test="${not empty user && user.getAdmin()}">
				<li class="mx-3 nav-item dropdown">
					<a class="mx-3 nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Admin
					</a>
					<div class="dropdown-menu bg-dark" aria-labelledby="navbarDropdown">
						<a class="nav-link drop-link ${zones[3].name() == navbarzone.name() ? 'active' : ''}" href="/admin/leagues">Leagues</a>
						<a class="nav-link drop-link ${zones[3].name() == navbarzone.name() ? 'active' : ''}" href="/admin/events">Events</a>
						<a class="nav-link drop-link ${zones[5].name() == navbarzone.name() ? 'active' : ''}" href="/admin/users">Teams</a>
					</div>
				</li>
			</c:if>
		</ul>
		<span class="navbar-text pt-0 pb-0">
			
			<c:choose>
				<c:when test="${not empty user}">
					<ul class="navbar-nav mr-auto">
						<li class="mx-3 nav-item ${zones[6].name() == navbarzone.name() ? 'active' : ''}">
							<a class="nav-link" href="/user">
								Driver Home
							</a>
						</li>
						<li class="mx-3 nav-item">
							<a class="nav-link" href="/user?cmd=logout">
								Log	Out
							</a>
						</li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul class="navbar-nav mr-auto">
						<li class="mx-3 nav-item">
							<a class="nav-link" href="/user?cmd=new">
								Register
							</a>
						</li>
						<li class="mx-3 nav-item">
							<a class="nav-link" href="/user">
								Login
							</a>
						</li>
					</ul>
				</c:otherwise>
			</c:choose>
		</span>
	</div>
</nav>