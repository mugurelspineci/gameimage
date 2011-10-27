<?php 
ob_start();
session_start(); ?>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Wright Technologies </title>

<link rel="icon" href="image_layout/logoR.ico" type="image/x-icon"> 
<link rel="shortcut icon" href="image_layout/logoR.ico" type="image/x-icon">
<link href="style.css" type="text/css"  rel="stylesheet"/>
<link href="layout.css" type="text/css"  rel="stylesheet"/>
<script language="javascript" type="text/javascript">
function mapCompany() {
        window.open('map_company.html','Mtttt','toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=592,height=595');
}

 function checkValue()
		{
	
			var name = document.getElementById("txtName");
			var phone = document.getElementById("txtPhone");			
			var email = document.getElementById("txtEmail");
			if( name.value==""){
                document.getElementById("err_msg1").innerHTML="Please, enter your name";				
				name.focus();
				return false;
			}
			else
				document.getElementById("err_msg1").innerHTML="*";
			if(phone.value=="" || phone.value.length<8){
				document.getElementById("err_msg2").innerHTML="Please, enter your phone";								
				phone.focus();
				return false;
			}				
			else
				document.getElementById("err_msg2").innerHTML="*";		
            emailRegExp = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.([a-z]){2,4})$/;
			if(email.value!="")
			{
				if(!emailRegExp.test(email.value)){                	
                	document.getElementById("err_msg3").innerHTML="Please, enter your email";
					email.focus();
					return false;
				}					
				else
					document.getElementById("err_msg3").innerHTML="";	
			}
			else		
				document.getElementById("err_msg3").innerHTML="";	
			return true;
        }
</script>
</head>

<body>
<div class="mainAll" align="center">
  <div class="mainA">
	<div class="qHeader">
		<?php include("pageHeader.php") ?>
	</div>
	
	<div class="pBody">
		 <div  class="pLeft">
		 		<?php include("pageLeft.php") ?> 	
		</div>
		
		<div class="pCenter">	
			<div align="left" class="bgBrown">
				<div style="margin-top:10px">
				 <div align="center">
				  	<span style="color:#000066;">1380 W Howard Street - Elk Grove Village, IL 60007<br/>USA<br/>
				  Phone: 847-439-4150<br/>
				  <span style="color:#000000">	Email: <a href="mailto:sales@wrighttechnologiesinc.com"><span>sales@wrighttechnologiesinc.com</span></a><br/>
				  	Click here for <a href="http://maps.yahoo.com/map#mvt=m&lat=42.0162&lon=-87.965973&zoom=16&q1=1380%2520Howard%2520St%252C%2520Elk%2520Grove%2520Village%252C%2520IL%252C%252060007&gid1=31130677" target="_blank"><span>driving directions</span></a><br/>
				  	Click here for <a onMouseOver="window.status='Click me!'; return true" href="javascript:mapCompany()"><span>map</span></a></span>
				  </div>
				  
<?php
		$strCode='';
		$strForm='';	
		$strMsg='';	
	 if(isset($_POST['submit'])){
	 	try{
			$company=$_POST["txtCompanyName"];
			$name=$_POST["txtName"];
			$fax=$_POST["txtFax"];
			$phone=$_POST["txtPhone"];
			$email=$_POST["txtEmail"];
			$address=$_POST["txtAddress"];
			$content=$_POST["txtContent"];
			$b_upload='0';
			if($_SESSION['security_code']==$_POST["txtCode"] && $_POST["txtCode"] !=''){
				$_SESSION['security_code']='';	
				$strCode='';		
				$dateT= date('Y').'/'.date('m').'/'.date('d');
			
////////////////////////////////////////////////// Upload File ///////////////////				
				$upload_dir = "upload/";
				//the file size in bytes.
				$size_bytes =51200; //51200 bytes = 50KB.
				$limitedext = array(".gif",".jpg",".jpeg",".png",".txt",".nfo",".doc",".xls",".csv",".rtf",".htm",".dmg",".zip",".rar",".gz");
				
				//define variables to hold the values.
				$new_file = $_FILES['uploaded'];
				$file_name = $new_file['name'];
				$file_tmp = $new_file['tmp_name'];
				$file_size = $new_file['size'];

				if(!empty($file_name)) {
					$file_name = str_replace(' ', '_', $file_name);
					$file_name =date('Y').date('m').date('d').'__'.str_replace(' ', '_', $file_name);
					if ($file_size > $size_bytes){
						$strMsg= "File : ($file_name) Faild to upload. File must be <b>". $size_bytes / 1024 ."</b> KB. <br>";
						$b_upload='1';
					}
					$ext = strrchr($file_name,'.'); 
					if(!in_array(strtolower($ext), $limitedext)) {
						$strMsg= '<p align="center">File: ($file_name) Wrong file extension. </p>';
						$b_upload='2';
					}
					if($b_upload=='0'){
						if(file_exists($upload_dir.$file_name)){
							$strMsg= "<p align='center'>File: ($file_name) already exists.</p>";						
						}
						else{
							if(!move_uploaded_file($file_tmp, $upload_dir.$file_name)) {								
								$strMsg= '<p align="center">File: Faild to upload.</p>';
							}
						}
					}					
				}
		
		/////////////////////////// End Upload File ///////////////////////
				
				
				 if(file_exists('contactUs.xml')){
					$xml = simplexml_load_file('contactUs.xml');
				 }
				 else{
					echo 'Error';
					return;
				 }
				  
				  $newCustomer = $xml->addChild("customer");
				  $newCustomer["id"]= date('Y').date('m').date('d').date('H').date('i').date('s');
				  $newCustomer->addChild("dateT", $dateT);
				  $newCustomer->addChild("company", $company);
				  $newCustomer->addChild("name", $name);
				  $newCustomer->addChild("fax", $fax);
				  $newCustomer->addChild("phone", $phone);  	  
				  $newCustomer->addChild("email", $email);
				  $newCustomer->addChild("address", $address);
				  $newCustomer->addChild("content", $content);
  				  $newCustomer->addChild("filename", $file_name);
				
				  file_put_contents('contactUs.xml', $xml->asXml());
				  chmod($upload_dir.$file_name, 0777);
				  $strMsg.= '<p align="center"><b>Thank for submit</b></p>';
				  echo $strMsg;
				  //header("Location: seccessfull.html");
			 }
			 else{				
				 	$strCode="Letter isn't correct";				 
			 }
		  }
		  catch(Exception $e){
			echo 'Error';
		  }
	 
	 }
	 if(!isset($_POST['submit']) || $strCode!=''){
	  	$strForm='';
		
	$strForm.=' <div style="height:350px; color:#000000">';
	$strForm.='<form action="contactUs.php" method="post" onsubmit="return checkValue();" enctype="multipart/form-data">';
	$strForm.='<div class="tForm">';
	$strForm.='	<div class="tName">Your name:</div> ';
	$strForm.='	<input name="txtName" type="text" id="txtName" size="30" value="'.$name.'" class="tInput"/>';
	$strForm.='	<span id="err_msg1" style="color: red;">*</span> </div>';
	$strForm.='<div class="tForm">';
	$strForm.='	<div class="tName">Company name:</div>';
	$strForm.='	<input name="txtCompanyName" type="text" id="txtCompanyName" size="30" value="'.$company.'" class="tInput"/>';
	$strForm.='</div>';
	$strForm.='<div class="tForm">';
	$strForm.='	<div class="tName">Fax Number: </div>';
	$strForm.='	<input name="txtFax" type="text" id="txtFax" value="'.$fax.'" class="tInput"/>';
	$strForm.='</div>';
	$strForm.='<div class="tForm">';
	$strForm.='	<div class="tName">Phone Number:</div> ';
	$strForm.='	<input name="txtPhone" type="text" id="txtPhone" value="'.$phone.'" class="tInput" />';
	$strForm.='	<span id="err_msg2" style="color: red;float:left">*</span>';
	$strForm.='</div>';
	$strForm.='<div class="tForm">';
	$strForm.='	<div class="tName">E-mail Address:</div> ';
	$strForm.='	<input name="txtEmail" type="text" id="txtEmail" value="'.$email.'" size="30" class="tInput" /><span id="err_msg3" style="color: red;"></span>';
	$strForm.='</div>';
	$strForm.='<div class="tForm">';
	$strForm.='	<div class="tName">Address:</div> ';
	$strForm.='	<textarea name="txtAddress" cols="38" rows="1" id="txtAddress"  style="float:left">'.$address.'</textarea>';
	$strForm.='</div>';
	$strForm.='<div class="tForm" style="height:32px; vertical-align:middle">';
	$strForm.='	<div class="tName" >Code</div> ';
	$strForm.='	<input name="txtCode" type="text" id="txtCode" size="7" class="tInput" style="margin-top:4px" /><img src="imageCode.php" width="105" height="30" class="tInput"/>';
	$strForm.='	<span style="color: red; float:left; margin-top:5px">'.$strCode.'</span>';
	$strForm.='</div>	';
	$strForm.='<div class="tForm">';
	$strForm.='	<div class="tName">Content:</div> ';
	$strForm.='	<textarea name="txtContent" cols="38" rows="4" id="txtContent" style="float:left">'.$content.'</textarea>';
	$strForm.='</div>	';
	$strForm.='<div class="tForm" style="margin-top:10px">';
	$strForm.='	<div class="tName">Attachment: </div>';
	$strForm.='	<input name="uploaded" style="width: 380px;" type="file" class="tInput">';
	$strForm.='</div>';
	$strForm.='<div class="tForm" style="margin-top:10px">';
	$strForm.='	<div class="tName">&nbsp;</div> ';
	$strForm.='	<input type="submit" name="submit" value="Submit" />';
	$strForm.='	<input type="reset" name="Reset" value="Reset" />'; 
	$strForm.='</div>';				
	$strForm.='</form>';
	$strForm.=' </div>';
				
	echo $strForm;
	}						
							
ob_flush();
?>
				  
				</div>
			</div>
			<div align="center">
		  		<img src="image_layout/Quality.jpg" alt="Quality is our business" width="180" height="20" />
			</div>
	
  		</div>
 <!-- end center -->
 </div>
 <!-- end body -->
  <div class="pFooter">
	<div>
			<?php include("pageFooter.php") ?>
	  </div>
	</div> 
  </div>  
</div>
</body>
</html>
