function navHide(){
	var menu = document.getElementById('menu');
	var menuBtn = document.getElementById("toggleBtn");

	if(menu.className === "nav_hide"){
		menu.className = "nav_show";
	}else {
		menu.className = "nav_hide";
	}
}

function getDir(url, placement, varName, dir)
{
	var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById(placement).innerHTML = " ";
			document.getElementById(placement).innerHTML = this.responseText;
			document.getElementById("dirPath").innerHTML = " ";
			document.getElementById("dirPath").innerHTML = dir.trim();
    }
  };
  xhttp.open("GET", url + "?" + varName + "=" + dir, true);
  xhttp.send();
}

function getClients(url, placement)
{
	var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById(placement).innerHTML = " ";
			document.getElementById(placement).innerHTML = this.responseText;
    }
  };
  xhttp.open("GET", url, true);
  xhttp.send();
}

function saveSettings(){
	 var AutoSync = document.getElementById("AutoSync").checked;
	 var SyncInterval = document.getElementById("SyncInterval").value;
	 var WipeClients = document.getElementById("WipeClients").checked;

	 var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				document.getElementById("Save").innerHTML = " ";
			 document.getElementById("Save").innerHTML = this.responseText;
			}
		};
		xhttp.open("GET", "backend/updateSettings.php?AutoSync=" + AutoSync +"&SyncInterval=" + SyncInterval + "&WipeClients=" + WipeClients, true);
		xhttp.send();
}

function upDir(){
	var dir = document.getElementById("dirPath").innerHTML;
	var newDir = document.getElementById("dirPath").innerHTML.substring(0, dir.lastIndexOf("/"));
	getDir("backend/listFiles.php", "fileDisplay", "fileDir", newDir);
}
