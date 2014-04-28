#!/usr/local/bin/php

<?php
include_once("connect2db.php");


 

$query="insert into location(`id`,`lat`,`long`,`flag`) values('nitin','sharma','guna','indore')";

mysql_query($query);
?>
