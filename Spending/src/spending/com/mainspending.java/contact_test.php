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
			
					
			<table width="100%" border="0">
			  <tr>
			    <td align="left" valign="top"><table width="100%" border="0" cellspacing="4" cellpadding="0">
				<tr>
				<td bgcolor="#E7E7E7"><table width="100%" border="0" cellpadding="1">
                    <tr>
                      <td> </td>
                    </tr>
					
                    <tr>
                      <td><div align="center" class="style3">1380 W Howard Street - Elk Grove Village, IL 60007</div></td>
                    </tr>
					
                    <tr>
                      <td><div align="center" class="style3">USA</div></td>
                    </tr>
					


                    <tr>
                      <td><div align="center" class="style3">
                        Phone: 847-439-4150    Fax: 847-439-4135    </div></td>
                    </tr>
                    <tr>
                      <td><div align="center"><a href="mailto:kaleszczyk@wrighttechnologiesinc.com"><span class="style3">Email: </span>kaleszczyk@wrighttechnologiesinc.com</a></div></td>
                    </tr>
                   <tr>
                      <td> </td>
                    </tr>
					
                    <tr>
                      <td> <div align="center">Click here for <a href="http://maps.yahoo.com/map#mvt=m&lat=42.0162&lon=-87.965973&zoom=16&q1=1380%2520Howard%2520St%252C%2520Elk%2520Grove%2520Village%252C%2520IL%252C%252060007&gid1=31130677" target="_blank">driving directions</a></div></td>
                    </tr>
					<tr>
                      <td><div align="center">Click here for <a onMouseOver="window.status='Click me!'; return true" href="javascript:mapCompany()">map</a></div></td>
                    </tr>
					<tr>
                      <td> </td>
                    </tr>
					
                  </table></td></tr>
                    <tr class="border_color">
					
                      <td align="left" valign="top" class="border_color"><table width="100%" border="0" cellpadding="5">
                          <tr bgcolor="#E7E7E7">
                            <td align="center" valign="top">

	 	
		<div align="center">
			<form action="contactUs.html" method="post" onSubmit="return checkValue();" enctype="multipart/form-data">
			  <table width="550" border="0">
				<tr>
				  <td width="180"><span class="style5">Your Name: </span></td>
				  <td width="370"><label></label>
				    <table width="393"  border="0" cellpadding="3">
                      <tr>
                        <td width="218"><input name="txtName" type="text" id="txtName" size="30"  value=""/></td>
                        <td width="10">&nbsp;</td>
                        <td width="112"><span id="err_msg1" style="color: red;">*</span></td>
                      </tr>
                    </table>
				    </td>
				    
				</tr>
				<tr>
				  <td><span class="style5">Company Name:</span></td>
				  <td><label>
					<input name="txtCompanyName" type="text" id="txtCompanyName" size="30" value="'.$company.'"/>
				  </label></td>
				  
				</tr>
				<tr>
				  <td><span class="style5">Fax Number:</span></td>
				  <td><label>
					<input name="txtFax" type="text" id="txtFax" value="'.$fax.'"/>
				  </label></td>
				  
				</tr>
				<tr>
				  <td><span class="style5">Phone Number:</span></td>
				  <td><label></label>
				    <table width="100%" border="0" cellpadding="3">
                      <tr>
                        <td width="45%"><input name="txtPhone" type="text" id="txtPhone" value="'.$phone.'"/></td>
                        <td width="5%">&nbsp;</td>
                        <td width="50%"><span id="err_msg2" style="color: red;">*</span></td>
                      </tr>
                    </table></td>
				    
				</tr>
				<tr>
				  <td><span class="style5">E-mail Address:</span></td>
				  <td><table width="100%" border="0" cellpadding="3">
                      <tr>
                        <td width="52%"><input name="txtEmail" type="text" id="txtEmail" size="30" value=""/></td>
                        <td width="3%">&nbsp;</td>
                        <td width="45%"><span id="err_msg3" style="color: red;">  </span></td>
                      </tr>
                    </table></td>
				    
				</tr>
				<tr>
				  <td><span class="style5">Address:</span></td>
				  <td><label>
					<textarea name="txtAddress" cols="45" rows="1" id="txtAddress" ></textarea>
				  </label></td>
				</tr>	
				<tr>
				  <td><span class="style5">Code</span></td>
				  <td ><table width="423" border="0">
                      <tr>
                        <td width="50"><input name="txtCode" type="text" id="txtCode" size="7" /></td>
                        <td width="110"><label><img src="imageCode.html" width="110" height="30"/></label></td>
						<td width="250" style="color: red;"></td>
                      </tr>
                    </table></td>
				</tr>
				<tr>
				  <td><span class="style5">Content:</span></td>
			<td ><textarea name="txtContent" cols="45" rows="4" id="txtContent">'.$content.'</textarea></td>
				</tr>
				<tr>
				  <td><span class="style5">Attachment:</span></td>
				  <td><input name="uploaded" style="width: 350px;" type="file"> </td>
				    <td></td>
				</tr>
				<tr>
				  <td> </td>
				  <td >
					<input type="submit" name="submit" value="Submit" />
				  <input type="reset" name="Reset" value="Reset" /></td>
				  <td></td>
				</tr>
				<tr>
				  <td> </td>
				</tr>
			  </table>
			</form>
		</div>
		
		echo $strForm;
	}						
							
ob_flush();
	?>
							</td>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td align="left" valign="top"><div align="center"><img src="image_layout/Quality.jpg" alt="Quality is our business" width="180" height="20" /></div></td>
                    </tr>
                </table></td>
			    </tr>
              <tr>
                <td> </td>
              </tr>
            </table>
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
