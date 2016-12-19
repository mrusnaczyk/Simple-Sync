<?php
	define('dbUsername','****');
	define('dbPassword','****');

	try{
 		$db = new PDO("mysql:host=****;dbname=****", constant('dbUsername'), constant('dbPassword'));
 	}
 	catch(PDOException $e){
 	 echo 'Error: ' . $e->getMessage();
		die();
 	}


?>
