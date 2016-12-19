<?php
  function getDirSize($directory) {
    $size = 0;
    foreach (new RecursiveIteratorIterator(new RecursiveDirectoryIterator($directory)) as $file) {
        $size += $file->getSize();
    }
    return $size;
  }

  function getNumClients(){
    require_once './auth/conn_config.php';

    $query = $db -> prepare("SELECT COUNT(*) FROM `Clients` WHERE `userid` = ?");
    $query -> execute(array("testing"));
    $result = $query -> fetchAll();
    return $result[0][0];
  }
?>
