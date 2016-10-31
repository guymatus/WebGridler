<%-- 
    Document   : Dashboard
    Created on : Oct 13, 2016, 5:30:29 PM
    Author     : Guy Matus
--%>

<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="gridler.Utilities.Utilities"%>
<%@page import="gridler.types.UserType"%>
<%@page import="Logic.Player"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Web Gridler Dashboard</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- jquery -->
        <script src="lib/jquery/jquery-3.1.1.js" type="text/javascript"></script>

        <!-- bootstrap -->
        <link href="lib/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="lib/bootstrap/css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>
        <script src="lib/bootstrap/js/bootstrap.js" type="text/javascript"></script>
        <script src="lib/bootstrap-filestyle-1.2.1/src/bootstrap-filestyle.min.js" type="text/javascript"></script>

        <!-- jquery file upload -->
        <link href="css/jquery.fileupload.css" rel="stylesheet" type="text/css"/>
        <script>
            var roomsTolock = [];
            function updateStatus()
            {
                $.getJSON("StatusController",
                        function (data) {
                            // users table update
                            $("#userstable").empty();
                            $.each(data.currentUsers, function (key, value) {
                                if (value == "Computer") {
                                    $("<tr><td>" + key + "</td><td>" + '<img src="images/computerIcon.png" />' + "</td></tr>").appendTo($("#userstable"));
                                } else if (value == "Human") {
                                    $("<tr><td>" + key + "</td><td>" + '<img src="images/humanIcon.png" />' + "</td></tr>").appendTo($("#userstable"));
                                }
                            });

                            // games table update
                            $("#gamestable").empty();
                            $.each(data.runningGames, function (index, value) {
                                $("<tr class='clickable-row' data-id='" + index + "'><td>" + value.m_gameTitle + "</td><td>" + value.m_organizer + "</td><td>" + value.m_activePlayers + "/"
                                        + value.m_playerCount + "</td><td>"
                                        + value.m_numberOfRounds + "</td><td>" +
                                        value.boardCols + "x" + value.boardRows + "</td></tr>").appendTo($("#gamestable"));
                                if (value.m_activePlayers == value.m_playerCount)
                                {
                                    roomsTolock[index] = true;
                                }
                            });

                        });
            }
            function setupOfferNewGameButton()
            {
                $("#offerNewGameButton").change(function () {
                    var file_data = $("#offerNewGameButton").prop('files')[0];
                    var form_data = new FormData();
                    form_data.append('file', file_data);
                    $.ajax({
                        url: 'LoadXmlController',
                        dataType: 'text',
                        cache: false,
                        contentType: false,
                        processData: false,
                        data: form_data,
                        type: 'post',
                        success: function (data) {
                            if (data === "success")
                            {
                                // lets not wait for refresh.
                                updateStatus();
                            } else
                            {
                                // show the exception
                                alert(data);
                            }
                        }
                    });
                });
            }

            $(function () {
                setInterval(updateStatus, 2000);
                setupOfferNewGameButton();
                var PlayerName = "<label>" + '<%= request.getSession().getAttribute("username")%>' + "</label>";
                $("#playerName").append("hello " + PlayerName + ", welcome to gridler");

                $('#gamestable').on('click', '.clickable-row', function () {
                    var gameID = $(this).data('id');
                    if (roomsTolock[gameID] === true)
                    {
                        return;
                    }
                    var addPlayerToGameData = {
                        gameIndex: gameID,
                        playerName: '<%= request.getSession().getAttribute("username")%>'
                    }
                    $.post("AddPlayerToGameController", addPlayerToGameData, function (data) {
                        if (data === "success") {
                            window.location.href = "GameRoomController?id=" + gameID;
                        } else {
                            alert(data);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>



        <h1 class="text-success">Shchor & Ptor Dashboard</h1>
        <div class="col-lg-4">
            <h2 class="text-primary">Logged in users:</h2>
            <a href="LogoutController" class="btn btn-warning">Logout</a>
            <br />
            <div id="playerName">

            </div>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Player Name</th>
                        <th>Player Type</th>
                    </tr>
                </thead>
                <tbody id="userstable">
                    <% for (Map.Entry<String, UserType> user : Utilities.getUsersList(getServletContext()).entrySet()) {
                            String userName = user.getKey();
                            UserType userType = user.getValue();%>
                    <tr>
                        <td><%= userName%></td>
                        <td>
                            <% if (userType == UserType.Human) { %>
                            <img src="images/humanIcon.png" />
                            <% } else if (userType == UserType.Computer) { %>
                            <img src="images/computerIcon.png" />
                            <% } %>
                        </td>
                    </tr>
                    <% }%>


                </tbody>
            </table>
        </div>
        <div class="col-lg-8">
            <h2 class="text-primary">Available games:</h2>

            <input type="file" id="offerNewGameButton" class="filestyle" data-buttonText="Offer New Game" data-buttonBefore="true" data-icon="false" data-buttonName="btn-primary"  data-input="false" data-badge="false" />           

            <table class="table table-hover">
                <thead> 
                    <tr>
                        <th>Title</th>
                        <th>Organizer</th>
                        <th>Player Count</th>
                        <th>Total Moves</th>
                        <th>Board Size</th>
                    </tr>
                </thead>
                <tbody id="gamestable">
                </tbody>
            </table>

        </div>
    </body>
</html>
