<?php
	require '../auth/login.php';
  require '../manage/auth/conn_config.php';

  define('masterKey','WfZ4nLWscKIxxsUFazYQSaJ94FQnsu8AoyPjXNI');

  if(!isset($_SERVER['PHP_AUTH_USER'])){
    header('WWW-Authenticate: Basic');
  }else{
    $username = $_SERVER['PHP_AUTH_USER'];
    $password = $_SERVER['PHP_AUTH_PW'];

    $auth = authenticate($username, $password);
    if($auth == "OK"){
      if(isset($_GET['key'])){
        if($_GET['key'] == constant('masterKey')){
          date_default_timezone_set("America/Chicago");

          $clientID = uniqid().uniqid();
          $ipAddr = $_SERVER['REMOTE_ADDR'];
          $clientName = $_GET['clientName'];

          $queryAddClient = $db->prepare("INSERT INTO `Clients` VALUES(?,?,?,?,?,?,?)");
          $queryAddClient->execute(array($clientID,"123",$username,$ipAddr,$clientName,date("Y-m-d H:i:s"),0));

          setcookie("X-SimpleSync-ClientID", $clientID, time() + (120), "/");
          echo "OK";
        }
      }
    }else{
      echo "Uh Oh!";
    }
  }

?>
