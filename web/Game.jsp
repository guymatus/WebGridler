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

        <style>
            .btn-clicked {
                border-color: red;
                background-color: gainsboro;
            }
            .square {
                width: 32px;
                height: 32px;
            }
            .square-blacked {
                background-color:black;
            }
            .square-empty {
                background-color: white;
            }
        </style>

        <script>

            // this is set to true, while executing a move.
            var inAMove = false;
            var moveList = [];
            var moveState;
            var IsPlayable = false;
            var numberOfPlayers = 0;
            var startGame = false;
            var currentPlayerUsername = "";

            function updateButtons() {
                if (moveList.length === 0)
                {
                    $("#moveButtons :radio").prop("disabled", true).prop("checked", false);
                    $("#doneButton").prop("disabled", true);
                }

            }

            function findIndexToRemove(val) {
                for (var i = 0; i < moveList.length; i++)
                {
                    if (moveList[i][0] === val[0] && moveList[i][1] === val[1])
                    {
                        return i;
                    }
                }
            }
            function getBoardFromServer() {
                $("#Board").empty();
                var data = {
                    game_id: <%= request.getParameter("id")%>,
                    username: "<%= request.getSession().getAttribute("username")%>"
                }
                $.post("UpdateBoardController", data, function (ret) {

                    if (ret !== "error")
                    {
                        $(ret).appendTo($("#Board"));
                    } else
                    {
                        alert("error");
                    }
                });
            }


            function updateStatus() {
                $.get("UpdateGameRoomUsersList", {game_id: <%= request.getParameter("id")%>},
                        function (data) {
                            // users table update
                            data = data.split(",");
                            $("#MyName").empty();
                            $("<h1>" + '<%= request.getSession().getAttribute("username")%>' + "</h1>").appendTo("#MyName");
                            for (var i = 0; i < data.length; i++)
                            {
                                data[i] = data[i].replace("{", "");
                                data[i] = data[i].replace("}", "");
                                data[i] = data[i].split(",");
                            }
                            for (var j = 0; j < data.length; j++)
                            {

                                data[j][0] = data[j][0].split("=");
                            }

                            if (data.length === (<%=(Integer.parseInt(((IGameManager) request.getAttribute("currentGame")).getPlayersCount()))%> * 2))
                            {
                                $("#waitForStart").hide();

                                checkIfPlayersTurn();


                            } else
                            {
                                waitForGameStart();
                            }
                            $("#userstable").empty();
                            for (var i = 0; i < data.length; i++)
                            {
                                var users = data;
                                if (users[i][0][1] === "Computer") {
                                    alert("goldenEgg");
                                } else if (users[i][0][1] == "Human") {
                                    $("<tr><td>" + (i + 1) + "</td><td>" + users[i][0][0] + "</td><td>" + '<img src="images/humanIcon.png" />' + "</td><td>" + users[i + ((data.length) / 2)][0][1] + "</td></tr>").appendTo($("#userstable"));
                                }

                            }

                        });
                $.get("CurrentTurnController", {game_id: <%= request.getParameter("id")%>}, function (data) {
                    if (data === "<%= request.getSession().getAttribute("username")%>") {
                        // this is our turn now                                     
                        $("#commands .btn").prop("disabled", false);
                        $("#Board .btn").prop("disabled", false);
                        $("#waitUntilYourTurn").hide();
                        if (data !== currentPlayerUsername) {
                            getBoardFromServer();
                        }
                    } else
                    {
                        $("#waitUntilYourTurn").show();
                        $("#commands .btn").prop("disabled", true);
                        $("#Board .btn").prop("disabled", true);
                    }
                    currentPlayerUsername = data;
                });
            }

            $(function () {
                getBoardFromServer();
                setInterval(updateStatus, 2000);

                $("#Board").on('click', '.btn', function () {

                    if (!$(this).hasClass('btn-clicked'))
                    {
                        //$(this).removeClass('btn btn-default');
                        $(this).addClass('btn-clicked');
                        $("#moveButtons :radio").prop("disabled", false);
                        moveList[moveList.length] = [$(this).data('row'), $(this).data('col')];
                    } else
                    {
                        $(this).removeClass('btn-clicked');
                        //$(this).addClass('btn btn-default');
                        var valToRemove = [$(this).data('row'), $(this).data('col')];
                        var indexToRemove = findIndexToRemove(valToRemove);
                        moveList.splice(indexToRemove, 1);
                    }
                    updateButtons();
                });
                $("#moveButtons :radio").on('change', function () {
                    moveState = $(this).data('state');
                    $("#doneButton").prop("disabled", false);
                });
                $("#makeAMoveButton").click(function () {
                    moveList.splice(0, moveList.length);
                    // uncheck everything in the board
                    //$("#Board .btn").removeClass('btn-clicked').addClass('btn-default');
                    $("#Board .btn").removeClass('btn-clicked');
                    if (!inAMove) {
                        inAMove = true;
                        $(this).addClass("active");
                        $("#moveButtons").fadeIn();
                        updateButtons();
                        $("#doneButton").prop("disabled", false);
                    } else {
                        inAMove = false;
                        $(this).removeClass("active");
                        $("#moveButtons").fadeOut();
                    }
                });
                $("#doneButton").click(function () {
                    var data = {
                        game_id: <%= request.getParameter("id")%>,
                        moveList: JSON.stringify(moveList),
                        moveState: moveState
                    }
                    $.post("MoveController", data, function (ret) {
                        if (ret === "success") {
                            $("#makeAMoveButton").removeClass("active");
                            $("#moveButtons").hide();
                            $("#commands .btn").prop("disabled", true);
                            $("#Board .btn").prop("disabled", true);
                            $("#waitUntilYourTurn").show();
                            getBoardFromServer();
                        } else {
                            alert("could not execute move. error = " + ret);
                        }
                    });
                });
                $("#undoButton").click(function () {
                    var data = {
                        game_id: <%= request.getParameter("id")%>,
                    }
                    $.post("UndoController", data, function (ret) {
                        if (ret === "success") {
                            getBoardFromServer();
                        } else {
                            alert(ret);
                        }
                    });
                    updateMovesHistory();
                });
                $("#movesHistoryButton").click(function () {
                    updateMovesHistory();
                });
            });
            function waitForGameStart() {
                var waitForStart = "<h3>" + 'Wait while room gets full to start Game' + "</h3>";
                $("#waitForStart").empty();
                $(waitForStart).appendTo($("#waitForStart"));
                if (startGame === true)
                {
                    $("#waitForStart").hide();
                    return;
                }


                if (!startGame)
                {
                    if (<%=(Integer.parseInt(((IGameManager) request.getAttribute("currentGame")).getPlayersCount()))%> === <%=((IGameManager) request.getAttribute("currentGame")).getLstPlayers().size()%>)
                    {
                        startGame = true;
                        $("#waitForStart").hide();
                        checkIfPlayersTurn();

                        return;
                    } else
                    {
                        $("#waitForStart").show();
                        // $("#Board :btn").prop("disabled", true);
                        $("#commands :button").prop("disabled", true);
                    }

                }
            }
            function checkIfPlayersTurn() {
                $.get("CurrentTurnController", {game_id: <%= request.getParameter("id")%>}, function (data) {
                    $("#currentPlayer").empty();
                    $("<h3 class='text-info'> player turn: " + data + "</h3>").appendTo($("#currentPlayer"));
                    $("#currentPlayer").show();
                    if (data === "<%= request.getSession().getAttribute("username")%>") {
                        // this is our turn now
                        $("#commands .btn").prop("disabled", false);
                        $("#Board .btn").prop("disabled", false);
                        $("#waitUntilYourTurn").hide();
                        $("#waitForStart").hide();
                        // getBoardFromServer();
                    } else
                    {
                        $("#waitUntilYourTurn").show();
                        $("#commands .btn").prop("disabled", false);
                        $("#Board .btn").prop("disabled", false);

                    }
                });
            }
            function updateMovesHistory() {
                var data = {
                    game_id: <%= request.getParameter("id")%>,
                }
                $.get("HistoryController", data, function (ret) {
                    $("#MovesHistory").empty();
                    var history = "<label>" + "Moves History: " + ret + "</label>";
                    $(history).appendTo($("#MovesHistory"));
                });
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


                        <td id="playerCount"> </td>


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
            <div id="commands">
                <button class="btn btn-info" id="makeAMoveButton">Make a Move</button>
                <button class="btn btn-info" id="undoButton">Undo a move</button>
                <button class="btn btn-primary">Show Statistics</button>
                <button class="btn btn-info" id="movesHistoryButton">Moves History</button>
                <button class="btn btn-success" id="doneButton" disabled>Done & Pass Turn</button>
                <a class="btn btn-danger" href="LeaveGameController?game_id=<%= request.getParameter("id")%>">Leave Game</a>
            </div>
            <div id="waitForStart"> </div>

            <div id="moveButtons" style="display:none" class="row">
                <label class="col-lg-2"><input type="radio" name="moveType" value="blacked" data-state="BLACKED" id="blackRadioButton" disabled>Blacked</label> 
                <label class="col-lg-2"><input type="radio" name="moveType" value="empty" data-state="EMPTY" id="emptyRadioButton" disabled>Empty</label>
                <label><input type="radio" name="moveType" value="undefined" data-state="UNDEFINED" id="undefinedRadioButton" disabled>Undefined</label>

                <div id="guide" style="display: none"><label class="text-info">select squares, choose the new state for them and click done button to submit you move</label></div>
            </div>
            <div class="row">
                <h2 class="col-lg-3">Board</h2>
                <h2 class="col-lg-3">Moves:  <%= ((IGameManager) request.getAttribute("currentGame")).getCurrentGameRound()%> /
                    <%= ((IGameManager) request.getAttribute("currentGame")).getGameRounds()%></h2>
            </div>
            <div id="waitUntilYourTurn" style="display:none">
                <h2>Waiting for your turn. please wait patiently.</h2>

            </div>
            <div id="Board" class="row">
            </div>
            <div id="currentPlayer">

            </div>
            <div id="MyName">

            </div>
        </div>

    </body>
</html>
