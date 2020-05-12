<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp"/>
<%@ taglib prefix = "ara" tagdir = "/WEB-INF/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/util" prefix="util"%>


    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Dashboard</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->

    <!-- /.row -->
    <div class="row">

        <div class="panel panel-default">
            <div class="panel-heading">
                Heros list
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="table-responsive">

                    <util:pagination thispage="${page}"></util:pagination>
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>id</th>
                            <th>Name</th>
                            <th>Power</th>
                            <th>Akcja</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.content}" var="hero">
                            <tr>
                                <td>${hero.id}</td>
                                <td>${hero.name}</td>
                                <td>${hero.superPower}</td>
                                <td><a class="confirm" href="/hero/remove/${hero.id}">delete</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- /.table-responsive -->
            </div>
            <!-- /.panel-body -->
        </div>

    </div>
    <!-- /.row -->

<!-- /#page-wrapper -->

<jsp:include page="../footer.jsp"/>
