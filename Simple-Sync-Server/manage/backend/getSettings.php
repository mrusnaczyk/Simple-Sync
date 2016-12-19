<?php
  require_once './auth/conn_config.php';

  //Fetch user's userid
  $queryUserID = $db->prepare("SELECT `id` FROM `Users` WHERE `username` = ? LIMIT 1");
  $queryUserID->execute(array("testing"));
  $resultUserID = $queryUserID->fetchAll();

  //Fetch settings
  $querySettings = $db -> prepare("SELECT `AutoSync`, `SyncInterval` FROM `Settings` WHERE `userid` = ?");
  $querySettings -> execute(array($resultUserID[0][0]));
  $settingsCount = $querySettings->rowCount();
  $settingsResult = $querySettings->fetchAll();

  $AutoSync = $settingsResult[0][0];
  $SyncInterval = $settingsResult[0][1];
?>
