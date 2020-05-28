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
				<a class="nav-link" href="/">Dashboard</a>
			</li>
			<li class="mx-3 nav-item ${zones[3].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/events">Events</a>
			</li>
			<li class="mx-3 nav-item ${zones[5].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/teams">Teams</a>
			</li>
			<li class="mx-3 nav-item ${zones[4].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/hosts">Hosts</a>
			</li>
			<li class="mx-3 nav-item ${zones[1].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/tracks">Tracks</a>
			</li>
			<li class="mx-3 nav-item ${zones[2].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/cars">Cars</a>
			</li>
		</ul>
	</div>
</nav>