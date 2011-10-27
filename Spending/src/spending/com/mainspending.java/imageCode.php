<?php 
ob_start();
session_start();
// Set the content-type
header('Content-type: image/png');

// Create the image
$img = imagecreate(55, 20);

// Create some colors
$grey = imagecolorallocate($img, 128, 128, 128);
$black = imagecolorallocate($img, 0, 0, 0);
$green = imagecolorallocate($img, 0, 120, 65);
$pink = imagecolorallocate($img, 200, 30, 80);
$orange = imagecolorallocate($img, 255, 200, 50);

$textcolor = imagecolorallocate($img, 0, 0, 255);


 //Let's generate a totally random string using md5
$md5_hash = md5(rand(1,999).'tz');

$md5_hash = str_replace('0','',$md5_hash);
$md5_hash = str_replace('o','',$md5_hash);
$md5_hash = str_replace('O','',$md5_hash);

$security_code = substr($md5_hash, 15, 5);

//Set the session to store the security code
$_SESSION["security_code"] = $security_code;
imagefill($img, 0, 0, $grey);
imagestring($img, 5, 3, 3,  substr($security_code,0,1), $green);
imagestring($img, 5, 13, 4,  substr($security_code,1,1), $pink);
imagestring($img, 5, 23, 2,  substr($security_code,2,1), $orange);
imagestring($img, 5, 33, 5,  substr($security_code,3,1), $black);
imagestring($img, 5, 43, 4,  substr($security_code,4,1), $green);


imagepng($img);
imagedestroy($img);

?>