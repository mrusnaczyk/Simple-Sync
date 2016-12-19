<?php
  require_once '../auth/conn_config.php';

  $queryClients = $db -> prepare("SELECT `client_name`, `ip_addr`, `last_seen`,`clientid` FROM `Clients` WHERE `userid` = ?");
  $queryClients -> execute(array("testing"));
  $clientsCount = $queryClients->rowCount();
  $clientResult = $queryClients->fetchAll();

  date_default_timezone_set("America/Chicago");

  for($i = 0; $i < $clientsCount; $i++){
    $diff = strtotime(date("Y-m-d H:i:s")) - strtotime($clientResult[$i][2]);
    //echo $diff;
    if($diff >= 120){
      $queryRemove = $db->prepare("DELETE FROM `Clients` WHERE `clientid` = ?");
      $queryRemove->execute(array($clientResult[$i][3]));
    }
    ?>
    <tr class="fileItem">
      <td><?php echo $clientResult[$i][0];?> </td>
      <td> <?php echo $clientResult[$i][1]?> </td>
      <td> <?php echo $clientResult[$i][2];?> </td>
    </tr>
<?php } ?>
