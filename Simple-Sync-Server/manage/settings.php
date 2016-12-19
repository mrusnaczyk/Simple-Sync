<?php
  session_start();
  include_once './backend/getSettings.php';

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
			<h2 class="menuItem" onClick="window.location='./'">Dashboard</h2>
			<h2 class="menuItem" onClick="window.location='files.php'">Files</h2>
			<h2 class="menuItem" onClick="window.location='clients.php'">Clients</h2>
			<h2 class="menuItem" style="background-color: #585858">Settings</h2>
      <h2 class="menuItem" onClick="window.location='auth/logout.php'">Logout</h2>
		</div>
		<h1 id="title">Dashboard</h1>
		<div class="wrapper">
      <div style="margin:0 auto; text-align:center;">
        <button id="Save"style="padding:2px 15px" onClick="saveSettings()">Save Settings</button>
      </div>
			<div class="flexContainer" id="dumpStats">
        <div class="flexItem">
          <h2 class="flexItemHeading">Client Auto Sync</h2>
          <?php
          if($AutoSync == 1){?>
            <input id="AutoSync" type="checkbox" checked="true">Auto Sync</input>
          <?php
          } else{ ?>
            <input id="AutoSync" type="checkbox">Auto Sync</input>
          <?php
          }?>
        </div>
				  <div class="flexItem">
				    <h2 class="flexItemHeading">Client Sync Interval</h2>
            <div style="margin:0 auto; width:60%; max-height:21px ">
              <input id="SyncInterval"style="width:21px; height:21px; float:left; padding:2px; text-align:center;" type="textbox" value="<?php echo $SyncInterval; ?>"></input>Seconds
            </div>
				  </div>
				  <div class="flexItem">
				    <h2 class="flexItemHeading">Wipe Local Directory</h2>
            <input id="WipeClients"type="checkbox">Wipe Clients</div>
				  </div>
			</div>
		</div>
	</body>
</html>
