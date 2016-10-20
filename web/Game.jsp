<%-- 
    Document   : Game
    Created on : 19/10/2016, 20:37:18
    Author     : Danny
--%>

<%@page import="gridler.Utilities.Utilities"%>
<%@page import="Logic.Board"%>
<%@page import="Logic.Slice"%>
<%@page import="Logic.Player"%>
<%@page import="Logic.Engine"%>
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

        <script>

            function printBoard() {


                var table = '';
               // var rowBlocks= <%= request.getAttribute("rowBlocks")%>;
               
                var rows = <%=((IGameManager) request.getAttribute("currentGame")).getBoardRows()%>
                var cols = <%=((IGameManager) request.getAttribute("currentGame")).getBoardCols()%>

                table += "<tr><td>";
                for (var c = 1; c <= cols; c++)
                {
                    table += "<td>" + c + "</td>";
                }
                table += "</tr></td>";

                
                for (var r = 1; r <= rows; r++)
                {
                    table += "<tr><td>" + r + "</td>";
                    for (var j = 0; j < cols; j++)
                    {
                        
                        table += ' <td><a class= "btn btn-default" style="width: 32px; height: 32px;"></a></td> '
                    }
                     var rowBlocks = [<%=((String)request.getAttribute("rowBlocks"))%>];
                    table += "<td>" + rowBlocks[r-1] +"</td></tr>";    
                     
                }
                $("<table class='col-lg-2'>" + table + "</table>").appendTo($("#Board"));

            }


            function colBlockAsString(block)
            {
                var vv = block;
                if (vv.length > 1)
                {
                    var vv = block.split(",");
                    return vv[0] + "<br>" + vv[1];
                }
                return vv[0];
            }
            function rowBlockAsString(block)
            {
                var vv = block;
                if (vv.length > 1)
                {
                    var vv = block.split(",");
                    return vv[0] + " " + vv[1];
                }
                return vv[0];
            }



        </script>
    </head>

    <body>
        <h1 class="text-success">Game: <%= ((IGameManager) request.getAttribute("currentGame")).getGameTitle()%></h1>
        <div class="col-lg-3">
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
        <div class="col-lg-9">
            <h2 class="text-primary">Commands</h2>
            <a class="btn btn-info">Make a Move</a>
            <a class="btn btn-info">Undo a move</a>
            <a class="btn btn-primary">Client Bla bla</a>
            '

            <a class="btn btn-info">Moves History</a>
            <a class="btn btn-success">Done</a>
            <a class="btn btn-danger">Leave Game</a>

            <div class="row">
                <h2 class="col-xs-3">Board</h2>
                <h2 class="col-xs-3">Moves:  <%= ((IGameManager) request.getAttribute("currentGame")).getCurrentGameRound()%> /
                    <%= ((IGameManager) request.getAttribute("currentGame")).getGameRounds()%></h2>
            </div>
            <!--                     <div class="row">
                                <h2 class='text-success'>Board </h2>  <h2 class='text-primary' >      Moves:  </h2>
                                 </div>-->

            <div id ="Board">

                <script>
                    printBoard();
                </script>

            </div>



        </div>
    </body>
</html>
