<?php
	include_once '../auth/login.php';
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
			echo json_encode(ReadFolderDirectory('../UserDir/'.$username), JSON_FORCE_OBJECT);
		}else{
			echo "Uh Oh!";
		}
	}

	function ReadFolderDirectory($dir = "root_dir/here") {
	  $listDir = array();
	  if($handler = opendir($dir)) {
	    while (($sub = readdir($handler)) !== FALSE) {
	      if ($sub != "." && $sub != ".." && $sub != "Thumb.db") {
	        if(is_file($dir."/".$sub)) {
						array_push($listDir, array('Type' => 'FILE', 'FileName' => $sub,'LastModified' => strval(filemtime($dir . "/" .$sub))));
	        }elseif(is_dir($dir."/".$sub)){
						array_push($listDir, array('Type' => 'DIR','FileName' => $sub, 'Files' => ReadFolderDirectory($dir."/".$sub)));
	        }
	      }
	    }
	    closedir($handler);
	  }

	  return $listDir;
	}

?>
