<%-- 
    Document   : Login
    Created on : Oct 13, 2016, 5:19:04 PM
    Author     : Guy Matus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Web Gridler</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="lib/jquery/jquery-3.1.1.js" type="text/javascript"></script>
        <link href="lib/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="lib/bootstrap/css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>
        <script src="lib/bootstrap/js/bootstrap.js" type="text/javascript"></script>

    </head>
    <body>
        <div class="col-lg-offset-2 col-lg-8">

            <h1>Shchor & Ptor</h1>

            <h2>Login Page</h2>

            <% if (request.getAttribute("error") != null) {%>
            <div class="alert alert-danger">
                <%= request.getAttribute("error")%>
            </div>
            <% }%>

            <div> 
                <form action="LoginController" method="post" >
                    <br>
                    <table class="col-lg-2 table table-striped">
                        <tr>             
                            <td>User:</td> 
                            <td><input type="text" name="user" class="form-control" /></td> 
                        </tr>

                        <tr>
                            <td>User Type:</td>
                            <td>
                                <div class="radio">
                                    <label class="col-lg-2"><input type="radio" name="type" value="computer">Computer</label> 
                                    <label class="col-lg-2"><input type="radio" name="type" value="human">Human</label>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                            </td>
                            <td>
                                <input type="submit" name="myButton" value="Login" class="btn btn-success" />
                            </td>
                        </tr>
                    </table>
                </form>

            </div>
        </div>
    </body>
</html>

