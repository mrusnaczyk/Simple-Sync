<?php
  session_start();
  if(!isset($_SESSION['auth'])){
    header("Location: login.php");
  }
?>
<!DOCTYPE HTML>
<html lang="en">
  <head>
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
			<h2 class="menuItem" onClick="window.location='./'">Dashboard</h2>
			<h2 class="menuItem" onClick="window.location='files.php'">Files</h2>
			<h2 class="menuItem" style="background-color: #585858">Clients</h2>
			<h2 class="menuItem" onClick="window.location='settings.php'">Settings</h2>
      <h2 class="menuItem" onClick="window.location='auth/logout.php'">Logout</h2>
		</div>
    <h1 id="title">Connected Clients</h1>
		<div class="wrapper">
      <br>
      <table>
        <tr><th>Client Name</th><th>IP Address</th><th>Last Seen</th></tr>
        <tbody id="fileDisplay">
          <script> getClients("backend/getClientList.php", "fileDisplay"); </script>
        </tbody>
      </table>
		</div>
	</body>
</html>
