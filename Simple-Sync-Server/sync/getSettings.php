<?php
	require '../auth/login.php';
  require '../manage/auth/conn_config.php';
  date_default_timezone_set("America/Chicago");

  if(!isset($_SERVER['PHP_AUTH_USER'])){
    header('WWW-Authenticate: Basic');
  }else{
    $username = $_SERVER['PHP_AUTH_USER'];
    $password = $_SERVER['PHP_AUTH_PW'];

    header('Content-Type: application/json');
    header('Expires: 0');
    header('Cache-Control: must-revalidate');

    $auth = authenticate($username, $password);
    if($auth == "OK"){
			$clientID = $_COOKIE['X-SimpleSync-ClientID'];

      //Fetch user's userid
      $queryUserID = $db->prepare("SELECT `id` FROM `Users` WHERE `username` = ? LIMIT 1");
      $queryUserID->execute(array($username));
      $resultUserID = $queryUserID->fetchAll();

      //Fetch user's settings
      $querySettings = $db->prepare("SELECT `AutoSync`, `SyncInterval` FROM `Settings` WHERE `userid` = ?");
      $querySettings->execute(array($resultUserID[0][0]));
      $resultSettings = $querySettings->fetchAll();

			//Check if client should wipe local folder
			$queryWipe = $db->prepare("SELECT `WipeLocal` FROM `Clients` WHERE `clientid` = ?");
			$queryWipe->execute(array($clientID));
			$resultWipe = $queryWipe->fetchAll();

      //Echo settings as a JSON document
      $settings = array("AutoSync" => $resultSettings[0][0], "SyncInterval" => $resultSettings[0][1], "WipeLocal" => $resultWipe[0][0]);
      echo json_encode($settings, JSON_FORCE_OBJECT);

      //Change WipeLocal back to 0 and update LastSeen after the client retrieves settings
      $resetWipe = $db->prepare("UPDATE `Clients` SET `WipeLocal` = ?, `last_seen` = ? WHERE `clientid` = ?");
      $resetWipe->execute(array(0, date("Y-m-d H:i:s"), $clientID));
			setcookie($cookie_name, $cookie_value, time() + (120), "/");

    }else{
      echo "Uh Oh!";
    }
  }

?>
