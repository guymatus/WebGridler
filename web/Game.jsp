<%-- 
    Document   : Game
    Created on : 19/10/2016, 20:37:18
    Author     : Danny
--%>

<%@page import="Logic.Player"%>
<%@page import="Logic.IGameManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Web Gridler Game Room</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- jquery -->
        <script src="lib/jquery/jquery-3.1.1.js" type="text/javascript"></script>

        <!-- bootstrap -->
        <link href="lib/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="lib/bootstrap/css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>
        <script src="lib/bootstrap/js/bootstrap.js" type="text/javascript"></script>
    </head>
    <body>
        <h1 class="text-success">Game: <%= ((IGameManager) request.getAttribute("currentGame")).getGameTitle()%></h1>
        <div class="col-lg-4">
            <h2 class="text-primary">Players</h2>
            <table class="table table-hover table-bordered">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Player Name</th>
                        <th>Player Type</th>
                        <th>Score</th>
                    </tr>
                </thead>
                <tbody id="userstable">
                    <% for (Player player : ((IGameManager) request.getAttribute("currentGame")).getLstPlayers()) {%>
                    <tr>
                        <td>0</td>
                        <td><%= player.getName()%></td>
                        <td>
                            <% if (player.isHuman()) { %>
                            <img src="images/humanIcon.png" />
                            <% } else { %>
                            <img src="images/computerIcon.png" />
                            <% }%>
                        </td>
                        <td><%= player.calculateScore()%></td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>
        <div class="col-lg-8">
            <h2 class="text-primary">Commands</h2>
            <a class="btn btn-info">Make a Move</a>
            <a class="btn btn-info">Undo a move</a>
            <a class="btn btn-primary">Client Bla bla</a>
            <a class="btn btn-info">Moves History</a>
            <a class="btn btn-success">Done</a>
            <a class="btn btn-danger">Leave Game</a>
        </div>
    </body>
</html>
