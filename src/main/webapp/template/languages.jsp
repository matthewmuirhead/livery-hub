<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<li class="mx-3 nav-item dropdown">
	<img alt="${sessionScope.Current_Language.getCode()}"
			class="nav-link dropdown-toggle" style="width: 45px; height: 45px;"
			src="/img/flags/${sessionScope.Current_Language.getFlag()}"
			data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
			id="navbarDropdown" />
	<div class="dropdown-menu dropdown-menu-right bg-dark language-menu" aria-labelledby="navbarDropdown">
		<c:forEach items="${languages}" var="language">
			<a class="nav-link drop-link py-0 mx-2" data-lang-code="${language.getCode()}" onclick="changeLanguage(this)">
				<img alt="${language.getCode()}"
						class="nav-link dropdown-toggle mr-1" style="width: 45px; height: 45px;"
						src="/img/flags/${language.getFlag()}" />
				${language.getCode()}
			</a>
		</c:forEach>
	</div>
</li>

<script>
function changeLanguage(link) {
	var langCode = $(link).data('lang-code');
	
	var url = $(location).attr('href');
	var splitUrl = url.split('?');
	
	var newParams = "";
	var paramJoiner = "?";
	
	if (splitUrl.length > 1) {
		url = splitUrl[0];
		var queryParams = splitUrl[1].split('&');
		
		for (var i=0; i<queryParams.length; i++) {
			var param = queryParams[i].split('=');
			
			var key = param[0];
			var value = param[1];
			
			if (key == "lang") {
				value = langCode;
			}
			
			newParams = newParams + paramJoiner + key + "=" + value;
			paramJoiner = "&";
		}
		if (!newParams.includes("lang=")) {
			newParams = newParams + paramJoiner + "lang=" + langCode;
		}
	} else {
		newParams = "?lang="+langCode;
	}
	
	$(location).attr('href', url+newParams);
}
</script>