<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty carousel}">
	<div id="carouselExampleIndicators" class="carousel slide my-4"
		data-ride="carousel">
		<ol class="carousel-indicators">
			<c:forEach var="c" begin="0" end="${carousel.size()-1}">
				<li data-target="#carouselExampleIndicators" data-slide-to="${c}" ${c eq 0 ? 'class="active"' : ''}></li>
			</c:forEach>
		</ol>
		<div class="carousel-inner" role="listbox">
			<c:set var="firstItem" value="true"/>
			<c:forEach items="${carousel}" var="displayItem">
				<div class="carousel-item ${firstItem ? 'active' : ''}">
					<img class="d-block img-fluid rounded" src="../img/carousel/${displayItem.getUrl()}" alt="${displayItem.getAltText()}">
				</div>
				<c:set var="firstItem" value="false"/>
			</c:forEach>
		</div>
		<a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span>
			<span class="sr-only">Previous</span>
		</a>
		<a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
			<span class="carousel-control-next-icon" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>
</c:if>