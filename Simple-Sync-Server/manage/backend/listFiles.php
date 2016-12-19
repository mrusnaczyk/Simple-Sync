<?php
  function ReadFolderDirectory($rootDir = "root_dir/here"){
    $files = array();
    $folders = array();
    $dir = "../../UserDir/testing/" . $rootDir;
    if($handler = opendir($dir)) {
      while (($sub = readdir($handler)) !== FALSE) {
        if ($sub != "." && $sub != ".." && $sub != "Thumb.db") {
          if(is_file($dir."/".$sub)) {
            array_push($files, $sub);
          }elseif(is_dir($dir."/".$sub)){
            array_push($folders, $sub);
          }
        }
      }
      closedir($handler);
    }

    foreach ($folders as $fold) { ?>
      <div class="fileItem" onClick='getDir("backend/listFiles.php", "fileDisplay", "fileDir", "<?php echo $rootDir."/".$fold ?>")'>
        <img src="img/folder.png" height="20px" width="20px" style="float:left; padding-right: 5px;">
        <?php echo $fold?>
      </div>
      <?php
    }

    foreach ($files as $file) {
      ?> <div class="fileItem"> <?php echo $file?> </div><?php
    }

  }

  if(isset($_GET['fileDir'])){
    ReadFolderDirectory($_GET['fileDir']);
  }


?>
