<?php 
ob_start();
session_start(); ?>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Wright Technologies</title>
<link rel="shortcut icon" href="image_layout/logoR.ico" type="image/x-icon">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.font_red {color: #FF0000}
-->
</style>
</head>

<body>

<table width="100%" height="553" border="0">
  <tr>
    <td align="center" valign="middle">
	<form id="form2" name="form2" method="post" action="changePass.php">
	  <table width="353" border="0">
        <tr>
          <td height="24" colspan="2">
		  <span class="font_red">
<?php 
try{
		 if(isset($_POST['submit'])){
			if(file_exists('Acount.xml')){
				$xml = simplexml_load_file('Acount.xml') or die('File error');
			 }
			 else{
				echo 'File xml not found';
				return;
			 }
			 $username= $_POST['username'];
			 $password=$_POST['oldpass'];
			 $newpass=$_POST['newpass'];
			 if($username==$xml->username && md5($password)==$xml->password){		
			 	$xml->password = md5($newpass);
				file_put_contents('Acount.xml', $xml->asXml()) or die('change error');
				unset($_SESSION['login']);
				unset($_SESSION['start']);
				header("Location: changed.php");
				exit(); 
			 }
			 else 
				echo 'Authentication failed. Please try again.';
		}
}
catch(Exception $e){
			echo 'Exception';
}
		  		ob_flush();
?>
		  </span>
		   </td>
          </tr>
        <tr>
          <td width="131" height="24">UserName</td>
          <td width="212"><label>
            <input type="text" name="username" id="username" />
          </label></td>
        </tr>
        <tr>
          <td>Old password</td>
          <td><label>
            <input type="password" name="oldpass" id="oldpass" />
          </label></td>
        </tr>
		<tr>
          <td>New password</td>
          <td><label>
            <input type="password" name="newpass" id="newpass" />
          </label></td>
        </tr>
		<tr>
          <td>Confirm password</td>
          <td><label>
            <input type="password" name="cfmpass" id="cfmpass" />
          </label></td>
        </tr>
        <tr>
          <td height="26">&nbsp;</td>
          <td><label>
            <input type="submit" name="submit" value="login" />
          </label></td>
        </tr>
      </table>
	</form>    </td>
  </tr>
</table>
</body>

</html>