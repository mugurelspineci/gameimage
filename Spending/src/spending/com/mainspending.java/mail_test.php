<?php
$host = "ssl://mail.wrighttechnologiesinc.com";
$port = 465;
IsSMTP(); // send via SMTP
$mail->SMTPAuth = true; // turn on SMTP authentication
$mail->Username = "root@wrighttechnologiesinc.com"; // SMTP username
$mail->Password = "Gb65sa45"; // SMTP password
$webmaster_email = "http://mail.wrighttechnologiesinc.com"; //Reply to this email ID
$email="daohuynh6@yahoo.com"; // Recipients email ID
$name="name"; // Recipient's name
$mail->From = $webmaster_email;
$mail->FromName = "Webmaster";
$mail->AddAddress($email,$name);
$mail->AddReplyTo($webmaster_email,"Webmaster");
$mail->WordWrap = 50; // set word wrap
$mail->IsHTML(true); // send as HTML
$mail->Subject = "This is the subject";
$mail->Body = "Hi,This is the HTML BODY "; //HTML Body
$mail->AltBody = "This is the body when user views in plain text format"; //Text Body
if(!$mail->Send())
{
  echo "Mailer Error: " . $mail->ErrorInfo;
}
else
{
  echo "Message has been sent";
}

?>
