<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>${title}</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<a class="navbar-brand" href="/"> <img src="../img/cm-banner.png"
			width="30" height="30" class="d-inline-block align-top" alt="">
		</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarText" aria-controls="navbarText"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarText">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="/">Dashboard
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="/Calendar">Calendar</a></li>
				<li class="nav-item"><a class="nav-link" href="/Drivers">Drivers</a>
				</li>
			</ul>
		</div>
	</nav>