<?php
  require_once '../auth/conn_config.php';

  if($_GET['AutoSync'] == "true"){
    $AutoSync = 1;
  }else{
    $AutoSync = 0;
  }

  if($_GET['WipeClients'] == "true"){
    $WipeClients = 1;
  }else{
    $WipeClients = 0;
  }

  $SyncInterval = $_GET['SyncInterval'];

  //Fetch user's userid
  $queryUserID = $db->prepare("SELECT `id` FROM `Users` WHERE `username` = ? LIMIT 1");
  $queryUserID->execute(array("testing"));
  $resultUserID = $queryUserID->fetchAll();

  //Update settings
  $queryUpdate = $db->prepare("UPDATE `Settings` SET `AutoSync`=? ,`SyncInterval`=? WHERE `userid`=?");
  $queryUpdate->execute(array($AutoSync,$SyncInterval, $resultUserID[0][0]));

  $queryAutoWipe = $db->prepare("UPDATE `Clients` SET `WipeLocal` = ? WHERE `userid` = ?");
  $queryAutoWipe->execute(array($WipeClients, "testing"));

  echo "Settings Saved";

 ?>
