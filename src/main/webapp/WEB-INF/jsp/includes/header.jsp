<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
      <meta charset="UTF-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Fantasy Football Draft</title>
      <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
      <link rel="preconnect" href="https://fonts.googleapis.com">
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
      <link
        href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300&family=Open+Sans:wght@400;700&family=Staatliches&display=swap"
        rel="stylesheet">
      <link href="../../pub/css/global.css" rel="stylesheet">
    </head>

    <body>
      <nav class="navbar navbar-expand-lg">
        <div class="container-fluid">


          <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="/index">Home</a>
              </li>
              <sec:authorize access="!isAuthenticated()">
                <li class="nav-item">
                  <a class="nav-link" href="/signup">Signup</a>
                </li>
              </sec:authorize>
              <li class="nav-item">
                <a class="nav-link" href="/team/create">Create Team</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="/draftboard">Draftboard</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="/team/viewAll">View Teams</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="/player/search">Player Search</a>
              </li>
              <sec:authorize access="isAuthenticated()">
                <li class="nav-item">
                  <a class="nav-link" href="/login/logout">Logout</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="">
                    <sec:authentication property="principal.username" />
                  </a>
                </li>
              </sec:authorize>
              <sec:authorize access="!isAuthenticated()">
                <li class="nav-item">
                  <a class="nav-link" href="/login/login">Login</a>
                </li>
              </sec:authorize>


              <sec:authorize access="hasAnyAuthority('ADMIN')">
                <li class="nav-item dropdown">
                  <a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuLink" role="button"
                    data-bs-toggle="dropdown" aria-expanded="false">Admin Link</a>
                  <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <li><a class="dropdown-item" href="/team/search">Find Team</a></li>
                    <li><a class="dropdown-item" href="/player/create">Create Player</a></li>
                </li>
              </sec:authorize>

            </ul>
          </div>
        </div>
      </nav>