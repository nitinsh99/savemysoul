<?php 
$handler=mysql_connect("localhost","root") or die("could not connect to mysql");
print($handler);
if($handler!=NUll)
mysql_select_db("mobile_project",$handler);
print("connected to database");
?>