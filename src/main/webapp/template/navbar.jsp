<%@ page import="com.codemaven.events.manager.enums.NavBarZone" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="zones" value="<%=NavBarZone.values()%>" />

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
				<a class="nav-link" href="/manager/">Dashboard</a>
			</li>
			<li
				class="mx-3 nav-item ${zones[3].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/manager/events">Events</a>
			</li>
			<li
				class="mx-3 nav-item ${zones[5].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/manager/teams">Teams</a>
			</li>
			<li
				class="mx-3 nav-item ${zones[4].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/manager/hosts">Hosts</a>
			</li>
			<li
				class="mx-3 nav-item ${zones[1].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/manager/tracks">Tracks</a>
			</li>
			<li
				class="mx-3 nav-item ${zones[2].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/manager/cars">Cars</a>
			</li>
		</ul>
		<span class="navbar-text pt-0 pb-0">
			<c:set var="user" value="${sessionScope.Session_User}"></c:set>
			<c:choose>
				<c:when test="${not empty user}">
					<ul class="navbar-nav mr-auto">
						<li class="nav-item">
							<a class="nav-link" href="/user">
								Driver Home
							</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="/user?cmd=logout">
								Log	Out
							</a>
						</li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul class="navbar-nav mr-auto">
						<li class="nav-item">
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