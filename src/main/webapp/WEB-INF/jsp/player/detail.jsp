<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <jsp:include page="../includes/header.jsp" />


    <main>
        <h1 class="text-center mt-3" id="playerName" value="${player.name}">${player.name}</h1>

        <div class="text-center">
            <iframe src="" height="360" width="640"></iframe>
        </div>
        <div class="text-center">
            <button type="button" class="btn btn-primary" id="highlight">Play Highlights</button>
        </div>
        <div class="mx-5 mt-3">
            <table class="table table-striped table-bordered">

                <tbody>
                    <tr>
                        <th scope="row">Id</th>
                        <td>${player.id}</td>
                    </tr>
                    <tr>
                        <th scope="row">Name</th>
                        <td>${player.name}</td>
                    </tr>
                    <tr>
                        <th scope="row">Position</th>
                        <td>${player.position}</td>
                    </tr>
                    <tr>
                        <th scope="row">Team</th>
                        <td>${player.team}</td>
                    </tr>
                    <tr>
                        <th scope="row">Bye</th>
                        <td>${player.bye}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
        integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="../../../pub/js/highlight.js"></script>

    <jsp:include page="../includes/footer.jsp" />