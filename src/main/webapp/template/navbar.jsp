<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<a class="nav-link" href="/">${languageFieldsList.getTranslation('Home')}</a>
			</li>
			<li class="mx-3 nav-item ${zones[1].name() == navbarzone.name() ? 'active' : ''}">
				<a class="nav-link" href="/series">${languageFieldsList.getTranslation('Series')}</a>
			</li>
			
			<c:if test="${not empty user && user.getAdmin()}">
				<li class="mx-3 nav-item dropdown">
					<a class="nav-link dropdown-toggle ${isAdminArea ? 'active' : ''}" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						${languageFieldsList.getTranslation('Admin')}
					</a>
					<div class="dropdown-menu bg-dark" aria-labelledby="navbarDropdown">
						<a class="nav-link drop-link ${zones[2].name() == navbarzone.name() ? 'active' : ''}" href="/admin/series">${languageFieldsList.getTranslation('Series')}</a>
						<a class="nav-link drop-link ${zones[3].name() == navbarzone.name() ? 'active' : ''}" href="/admin/users">${languageFieldsList.getTranslation('Users')}</a>
						<a class="nav-link drop-link ${zones[6].name() == navbarzone.name() ? 'active' : ''}" href="/admin/languages">${languageFieldsList.getTranslation('Languages')}</a>
					</div>
				</li>
			</c:if>
		</ul>
		<span class="navbar-text pt-0 pb-0">
			<c:choose>
				<c:when test="${not empty user}">
					<ul class="navbar-nav mr-auto">
						<li class="mx-3 nav-item">
							<span class="nav-link nav-link-no-hover">
								${languageFieldsList.getTranslation('Logged in as: {user}', user.getUsername())}
							</span>
						</li>
						<li class="mx-3 nav-item">
							<a class="nav-link" href="/logout">
								${languageFieldsList.getTranslation('Log Out')}
							</a>
						</li>
						<jsp:include page="languages.jsp"/>
					</ul>
				</c:when>
				<c:otherwise>
					<ul class="navbar-nav mr-auto">
						<li class="mx-3 nav-item">
							<a class="nav-link ${zones[5].name() == navbarzone.name() ? 'active' : ''}" href="/register">
								${languageFieldsList.getTranslation('Register')}
							</a>
						</li>
						<li class="mx-3 nav-item">
							<a class="nav-link ${zones[4].name() == navbarzone.name() ? 'active' : ''}" href="/login">
								${languageFieldsList.getTranslation('Login')}
							</a>
						</li>
						<jsp:include page="languages.jsp"/>
					</ul>
				</c:otherwise>
			</c:choose>
		</span>
	</div>
</nav>