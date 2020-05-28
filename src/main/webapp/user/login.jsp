<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../template/header.jsp" />

<section>
	<div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

        <div class="col-xl-10 col-lg-12 col-md-9">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
                                </div>
                                <jsp:include page="../includes/alertmessages.jsp" />
                                <form class="user" action="/user" method="POST">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <div class="input-group-text">@</div>
                                            </div>
                                            <input name="username" class="form-control form-control-user"
                                                type="text" placeholder="Username" required />
                                        </div>
                                        <small id="username" class="form-text text-muted">Please enter your username used to create your profile.</small>
                                    </div>
                                    <div class="form-group">
                                        <input name="password" class="form-control form-control-user" type="password"
                                            placeholder="Password" length="17" required />
                                        <small id="passwordHelp" class="form-text text-muted">The password you supplied
                                            when you created your profile.</small>
                                    </div>
                                    <input class="btn btn-primary btn-user btn-block" type="submit" value="Login"
                                        name="submitted" />
                                </form>
                                <hr>
                                <div class="text-center">
                                    <a class="small" href="/forgot-password">Forgot Password?</a>
                                </div>
                                <div class="text-center">
                                    <a class="small" href="./driverreq">Create an Account!</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</section>

<jsp:include page="../template/footer.jsp" />