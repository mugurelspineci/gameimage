var flag = true;
var arrCustomer = new Array(10);
var checkBoxAll;
function intTable()
{		
	var oRowTable;
	eleTable= document.getElementById("tblCustomer");
	if(eleTable){		
		checkBoxAll = document.getElementById("selectAll");
		eleTable.SelectAllRows = checkBoxAll; 
		eleAllCheckBox = document.getElementsByName("arrCustomer[]");

		eleTable.CheckBoxs=eleAllCheckBox;
		oRowTable=eleTable.tBodies[0].rows;
		for(var i=0; i<oRowTable.length; i++){
			eleAllCheckBox[i].onclick= tableCheckBox_Click;
		}
		eleTable.SelectAllRows.onclick=checkAll;
		/*eleTable.SelectAllRows.onclick= function(){
			if(typeof(eleTable) =='undefined' || !eleTable)	return;
			var tblCheckBoxs = eleTable.CheckBoxs;
			var bChecked = eleTable.SelectAllRows.checked;
			var oRows = eleTable.tBodies[0].rows;
			if(bChecked)
				for(var i=0; i<oRows.length; i++){
					tblCheckBoxs[i].checked = bChecked;
					oRows[i].style.backgroundColor="#666666";
				}
			else
				for(var j=0; j<oRows.length; j++){
					tblCheckBoxs[j].checked = bChecked;
					oRows[j].style.backgroundColor="";
				}
			
		};*/
	}
	else alert('nothing');
}

function checkAll(){
	//alert('check : ' + eleAllCheckBox.length)
			if(typeof(eleTable) =='undefined' || !eleTable)	return;
			var tblCheckBoxs = eleTable.CheckBoxs;
			var bChecked = eleTable.SelectAllRows.checked;
			var oRows = eleTable.tBodies[0].rows;
			
			if(bChecked){
				//alert(oRows.length);
				for(var i=0; i<oRows.length; i++){
					tblCheckBoxs[i].checked = bChecked;
					oRows[i].style.backgroundColor="#666666";
				}
			}
			else{
				for(var j=0; j<oRows.length; j++){
					tblCheckBoxs[j].checked = bChecked;
					oRows[j].style.backgroundColor="";
				}
			}
}

function tableCheckBox_Click()
{
	var eleTr= this.parentNode.parentNode;
	if(this.checked){ 
		eleTr.style.backgroundColor='#666666';		
	}
	else{
		eleTr.style.backgroundColor='';
		checkBoxAll.checked= false;	
	}
}

function row_MouseOver(x)
{ 
	if(navigator.appName.indexOf("Microsoft") != - 1)
	{
		if(x.style.backgroundColor=='white' || x.style.backgroundColor=='' 
		   || x.style.backgroundColor!='#666666'){
			x.style.backgroundColor = 'lightblue';	
		}
	}
	else{
		if(x.style.backgroundColor=='white' || x.style.backgroundColor==''
		   || x.style.backgroundColor!='rgb(102, 102, 102)'){
			x.style.backgroundColor = 'lightblue';
		}
	}
}

function row_MouseOut(x)
{ 
	if(navigator.appName.indexOf("Microsoft") != - 1)
	{
		if(x.style.backgroundColor!='#666666'){
			x.style.backgroundColor = 'white';
		}
	}
	else{
		if(x.style.backgroundColor!='rgb(102, 102, 102)'){
			x.style.backgroundColor = 'white';
		}
	}
	
}
