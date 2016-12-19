<?php
	ini_set('display_errors',1);
	error_reporting(E_ALL);
	session_start();
	include_once './backend/getStats.php';

	if(!isset($_SESSION['auth'])){
		header("Location: login.php");
	}
?>
<!DOCTYPE HTML>
<html lang="en"><head>
	<title>Admin Console</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width">
	<meta name="theme-color" content="#195B8A">
	<link href="https://fonts.googleapis.com/css?family=Oswald:400,300" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="admincss.css">
    <script src="js/nav.js"></script>
</head>
	<body>
		<div id="menu" class="nav_hide">
			<div id="toggleBtn" onclick="navHide()" onHover="" style="margin:0; position:fixed; left: 200px; top:-3px;">
				<p style="margin:0;color:white; font-size:30px; font-weight:bolder; line-height:0.3">_<br>_<br>_</p>
			</div>
			<br>
			<br>
			<h2 class="menuItem" style="background-color: #585858">Dashboard</h2>
			<h2 class="menuItem" onClick="window.location='files.php'">Files</h2>
			<h2 class="menuItem" onClick="window.location='clients.php'">Clients</h2>
			<h2 class="menuItem" onClick="window.location='settings.php'">Settings</h2>
			<h2 class="menuItem" onClick="window.location='auth/logout.php'">Logout</h2>
		</div>
		<h1 id="title">Dashboard</h1>
		<div class="wrapper">
			<div class="flexContainer" id="dumpStats">
				<!-- <script> getStats("backend/getStats.php", "dumpStats"); </script> -->
				  <div class="flexItem">
				    <h2 class="flexItemHeading">Total Storage Used:</h2>
				    <?php echo number_format(getDirSize("../UserDir/")/1000000,2); ?> Megabytes
				  </div>
				  <div class="flexItem">
				    <h2 class="flexItemHeading">Number of Clients:</h2>
				    <?php echo getNumClients(); ?> Clients
				  </div>
			</div>
		</div>
	</body>
</html>
