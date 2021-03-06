<?php
  function authenticate($user = "lol", $pass = "lol"){
      //Connnect to DB
      include '../manage/auth/conn_config.php';

      //Hash the password before sending to DB
      $userSalt = getSalt($user);
      $passwordHash = md5($userSalt.$pass);

      $queryUser = $db->prepare("SELECT * FROM `Users` WHERE `username` = ? AND `password` = ? LIMIT 1");
      $queryUser->execute(array($user, $passwordHash));
      $userCount = $queryUser->rowCount();
      if($userCount == 1){
        return "OK";
      }else {
        return "ERROR";
      }

  }

  function getSalt($user = "lol"){
    //Connect to DB
    include '../manage/auth/conn_config.php';

    $query = $db->prepare("SELECT `usersalt` FROM `Users` WHERE `username` = ?");
    $query->execute(array($user));
    $result = $query->fetchAll();

    return $result[0][0];
  }
?>
