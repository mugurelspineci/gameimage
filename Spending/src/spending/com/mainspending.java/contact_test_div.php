<?php 
ob_start();
session_start(); ?>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Wright Technologies </title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link rel="icon" href="image_layout/logoR.ico" type="image/x-icon"> 
<link rel="shortcut icon" href="image_layout/logoR.ico" type="image/x-icon">
<link href="style.css" type="text/css"  rel="stylesheet"/>
<script language="javascript" type="text/javascript">
function mapCompany() {
        window.open('map_company.html','Mtttt','toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=no,copyhistory=no,scrollbars=no,width=520,height=520');
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#E7E7E7" >
  <tr>
    <th align="center" valign="top" scope="row">
	<table width="800px" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="border_color" >
	<tr><td>
	<!-- header-->	  
		<?php include("pageHeader.html") ?>
	  
	  </td></tr>
      <tr>
        <td>
			<table width="800px" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="200px" scope="row" valign="top">
			 <!-- PageLeft -->
			 		<?php include("pageLeft.html") ?>   
			 <!-- end Page Left -->
			 
			</td>
            <td width="600px" valign="top">
<!-- Center -->
<div>
<div id="addressCtc"></div>
<div>
		

<!--End Center -->
			</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td>

		</td>
      </tr>
    </table></th>
  </tr>
</table>

</body>
</html>
