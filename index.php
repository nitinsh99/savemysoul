#!/usr/local/bin/php

<?php
include_once("connect2db.php");
 $id= $_POST["id"];
  $lat =$_POST["lat"]; 
  $long = $_POST["long"];
  $flag = $_POST["flag"];

$q = "select * from location where id='$id'";
$r = mysql_query($q);

if(mysql_num_rows($r)==0)
{
$query="insert into location(`id`,`lat`,`long`,`flag`) values('$id','$lat','$long','$flag')";

mysql_query($query);
}

else
{
$q1 = "update location set `lat`='$lat',`long`='$long',`flag`='$flag' where `id`='$id'";
mysql_query($q1);
}
$result =mysql_query("select `id` from location where `flag`='2'");
 echo mysql_result($result,0);
//header("location: server.php"); 

?>

