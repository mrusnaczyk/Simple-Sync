<?php
	session_start();
	include '../auth/login.php';

	$error = false;

	if(isset($_POST['username']) && isset($_POST['password'])){
		$loginResponse = authenticate($_POST['username'], $_POST['password']);

		if($loginResponse == "OK")
		{
			//echo $loginResponse;
			$_SESSION["auth"] = "true";
			//echo $_SESSION["auth"];
			header("Location: index.php");
		}else {
			$error = "true";
		}

	}

?>

<!DOCTYPE HTML>
<html lang="en">
<head>
    <title>Website Administration</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width" />
    <link href='https://fonts.googleapis.com/css?family=Oswald:300,400' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="admincss.css">
</head>
<body>
		<div class="wrapper">
				<div id="login_div">
					<div style="background-color:#11334B; width:100%; padding-top: 5px; padding-bottom:5px;margin-bottom:15px;">
						<h1 id="login">Login Required</h1>
					</div>
					<div style="text-align:center; font-family:sans-serif;"><?php if($error == "true"){echo $loginResponse;}?></div>
					<form class="login" method="POST" action="login.php">
			  			<input name="username" placeholder="Username" class="textbox_reg" type="text">
						<br>
			  			<input name="password" placeholder="Password" class="textbox_reg" type="password">
						<br>
						<input value="Login" id="submit" name="submit" type="submit">
						<br>
					</form>
			</div>
		</div>
</body>
</html>
