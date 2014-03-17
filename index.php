
<?php

include_once("connect2db.php");
 $id= $_POST["id"];
  $lat =$_POST["latitude"]; 
  $long = $_POST["longitude"];
echo "your message is recieved";

//$query = "select * from location where id = '$id'";

$query="select * from location where id='$id'";
$result=mysql_query($query,$handler);
if(mysql_num_rows($result)==0)
{
//echo "no location from correcponding id\n";
$query="insert into location(id,latitude,longitude)  values('$id','$lat','$long')";
mysql_query($query,$handler);
//echo "entry stored \n";
}

else
{
$query = "update location set latitude='$lat',longitude='$long' where id='$id'";
mysql_query($query,$handler);
//echo "entry updated\n";
}
header("location: server.php"); 

?>

