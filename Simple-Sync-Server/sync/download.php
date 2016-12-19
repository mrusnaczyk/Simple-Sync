<?php
	include_once '../auth/login.php';
	$file = "../UserDir/testing".$_GET['f'];

	if (file_exists($file)) {
		if(!isset($_SERVER['PHP_AUTH_USER'])){
			header('WWW-Authenticate: Basic');
		}else{
			$username = $_SERVER['PHP_AUTH_USER'];
			$password = $_SERVER['PHP_AUTH_PW'];


			$auth = authenticate($username, $password);
			if($auth == "OK"){
				header('Content-Description: File Transfer');
				header('Content-Type: application/octet-stream');
				header('Content-Disposition: attachment; filename='.basename($file).'');
				header('Expires: 0');
				header('Cache-Control: must-revalidate');
				header('Pragma: public');
				header('Content-Length: ' . filesize($file));
				$file = "../UserDir/".$_SERVER['PHP_AUTH_USER']."/".$_GET['f'];
				readfile($file);
				exit;
			}
		}
	}else {
		echo "File '".$file."' not found!";
	}
?>
