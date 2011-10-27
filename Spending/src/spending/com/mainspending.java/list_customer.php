<?php 
ob_start();
session_start(); ?>
<html>
<head>
<title>Wright Technologies</title>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <link rel="shortcut icon" href="image_layout/logoR.ico" type="image/x-icon">
 <link media="all" href="style.css" type="text/css" rel="stylesheet">
  
<script language="javascript" src="table.js"></script>

<style type="text/css">
<!--
#Layer1 {
	height:450px;
	width:100%;
	OVERFLOW: scroll;
}
-->
</style>
</head>
<body>
<?php 
if($_SESSION['login']!='logged_in'){
	header("Location:login.html");
	exit();
}		

//time out
// 1 mins in seconds				
$inactive = 15*60; //15 mins
//session_cache_expire( 20 );

if(isset($_SESSION['start']) ) {
	$session_life = time() - $_SESSION['start'];
	if($session_life > $inactive){	
		header("Location: logout.php");
	}
}
else
	$_SESSION['start'] = time();

$chkCustomers = $_POST['arrCustomer'];	
if(isset($chkCustomers)){
	if(file_exists('contactUs.xml')){
		$xml = simplexml_load_file('contactUs.xml');
	 }
	 else{
		echo 'File xml not found';
		return;
	 }	 

if(is_null($chkCustomers)) {
	echo 'nothing';
}
else{
	 foreach($chkCustomers as $chkCustomer) {	 
		 foreach($xml->customer as $customer){
 			$attrCustomer=$customer->attributes();
			if($attrCustomer['id'] == $chkCustomer){
				$dom=dom_import_simplexml($customer);
				$dom->parentNode->removeChild($dom);
				if(!is_file('upload/'.$customer->filename))
					echo "Unable to open file {$filename}";
				else 
					unlink('upload/'.$customer->filename); 
			}
		 }		
	}
	
	file_put_contents('contactUs.xml', $xml->asXml());
 }	
}
ob_flush();
?>

 <form action="list_customer.php"  name="frm" method="post" >
  <table width="100%" border="0" style="table-layout:fixed">
  <tr>
    <td><?php include("pageFooter.php") ?></td>
  </tr>
  <tr>
    <td> 
      <table width="100%" border="0" >
        <tr>
          <td width="80%"><div align="left">		 
            <input name="Delete" type="submit" id="Delete" value="Delete">		
          </div></td>
  		  <td width="10%"><div align="right"><a href="changePass.php" >change password</a> </div></td>
		  <td width="10"></td>
          <td width="10%"><div align="left"><a href="logout.php">sign out</a> </div></td>

        </tr>
      </table>
	  
	  </td>
  </tr>
  <tr>
    <td valign="top" align="center">
  <div id="Layer1">
 <table border="1" cellpadding="0" cellspacing="0"  style="width: 1700px" id="tblCustomer">
<thead>
      <tr style=" font-weight: bold; color: #ffffff; background-color: #660000;">
       <td style="width: 15px; height: 21px;" >
	   	<input type="checkbox" id="selectAll" onClick="checkAll()"/></td>
      <td style="width: 40px; height: 21px;" >STT</td>
        <td style="width: 80px; height: 21px;" > Date</td>
         <td style="width: 180px; height: 21px;" >Company</td>
		 <td style="width: 200px; height: 21px;" >Name</td>
		 <td style="width: 100px; height: 21px;" >Fax</td>
           <td style="width: 100px; height: 21px;" >phone</td>
          <td style="width: 180px; height: 21px;"> email</td>
           <td style="width: 260px; height: 21px;" > address</td>
           <td style="width: 300px; height: 21px;" > content</td> 
		   <td style="width: 200px; height: 21px;" > File name</td>             
		   </tr>
    </thead>
	<tbody>

<?php


 if(file_exists('contactUs.xml')){
		$xml = simplexml_load_file('contactUs.xml');
	 }
	 else{
		echo 'File xml not found';
		return;
	 }
	 

$catList = '';
$tableBook='';

$tableCustomer.='';
	foreach($xml->customer as $customer){
		$attrCustomer=$customer->attributes();
		$tableCustomer.= bodyTable($attrCustomer['id'], $customer->dateT,  $customer->company, $customer->name, $customer->fax,$customer->phone,  $customer->email, $customer->address, $customer->content, $customer->filename);
	}
	
echo $tableCustomer;

function bodyTable($id, $dateT, $company, $name, $fax, $phone, $email, $address, $content, $filename)
{
	global $i;
	$i=$i+1;
	$tableBook.='<tr onmouseover="row_MouseOver(this)" onmouseout="row_MouseOut(this)">';
	$tableBook.=' <td><input type="checkbox" name="arrCustomer[]" value="'.$id.'"/></td>';	

	$tableBook.=' <td bgcolor="#666600">';
	$tableBook.=$i;
	$tableBook.='</td>';
	
	$tableBook.=' <td>';
	$tableBook.=$dateT.'&nbsp;';
	$tableBook.='</td>';
	
	$tableBook.=' <td>';
	$tableBook.=$company.'&nbsp;';
	$tableBook.='</td>';
	
	$tableBook.='<td>';
	$tableBook.=$name.'&nbsp;';
	$tableBook.='</td>';
	
	$tableBook.=' <td>';	
	$tableBook.=$fax.'&nbsp;';
	$tableBook.='</td>';
	
	$tableBook.='<td>';
	$tableBook.=$phone.'&nbsp;';
	$tableBook.='</td>';
	
	$tableBook.='<td> ';		     
	$tableBook.='<a href="mailto:'.$email.'">'.$email.'</a>&nbsp;';
	$tableBook.='</td>';
	
	$tableBook.=' <td>';
	$tableBook.=$address.'&nbsp;';
	$tableBook.='</td>';
	
	$tableBook.=' <td>';
	$tableBook.=$content.'&nbsp;';
	$tableBook.='</td>';	
	
	$tableBook.='<td>';
	$tableBook.='<a href="upload/'.$filename.'" target="_blank">'.$filename.'</a>&nbsp;';
	$tableBook.='</td>';	
	$tableBook.='</tr>';		
	return $tableBook;
}
//$tableAll=$table . $tableBook . '</table>';

?>
</tbody>
</table>
</div></td>
  </tr>
</table>
<script type="text/javascript">
	intTable();
</script>

</form>
</body>
</html>