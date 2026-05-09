<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Tetris MVC</title>
<%--  <link rel="stylesheet" href="css/style.css">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/tetris-mvc/css/style.css">
</head>
<body>

<h1>TETRIS</h1>

<div class="game-wrapper">

  <!-- LEFT PANEL -->
  <div class="side-panel">
    <div class="panel-box">
      <div class="panel-label">Score</div>
      <div class="panel-value" id="score-display">0</div>
    </div>
    <div class="panel-box">
      <div class="panel-label">Lines</div>
      <div class="panel-value" id="lines-display">0</div>
    </div>
    <div class="panel-box">
      <div class="panel-label">Level</div>
      <div class="panel-value" id="level-badge">1</div>
    </div>
    <div class="panel-box">
      <div class="panel-label">High Score</div>
      <div class="panel-value" id="hi-display">0</div>
    </div>
  </div>

  <!-- BOARD -->
  <div class="board-container">
    <canvas id="board-canvas" width="280" height="560"></canvas>

    <div class="overlay" id="start-screen">
      <h2>TETRIS</h2>
      <p>MVC ARCHITECTURE</p>
      <button class="btn" id="start-btn">START GAME</button>
    </div>

    <div class="overlay hidden" id="pause-screen">
      <h2>PAUSED</h2>
      <button class="btn" id="resume-btn">RESUME</button>
    </div>

    <div class="overlay hidden" id="gameover-screen">
      <h2>GAME OVER</h2>
      <p id="final-score-text"></p>
      <button class="btn" id="restart-btn">PLAY AGAIN</button>
      <button class="btn" id="home-btn">HOME</button>
    </div>
  </div>

  <!-- RIGHT PANEL -->
  <div class="side-panel">
    <div class="panel-box">
      <div class="panel-label">Next</div>
      <canvas id="next-canvas" width="112" height="80"></canvas>
    </div>
  </div>

</div>

<a id="logout-btn"
   href="${pageContext.request.contextPath}/logout">
    Logout
</a>

<script>
    window.CONTEXT_PATH =
        "${pageContext.request.contextPath}";
</script>
<script type="module" src="${pageContext.request.contextPath}/tetris-mvc/js/main.js">
</script>
</body>
</html>