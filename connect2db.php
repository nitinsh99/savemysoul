#!/usr/local/bin/php

<?php

$conn  = mysql_connect('mysql.cise.ufl.edu', 'rrohit', 'cps2014spring') or 
   die ('Could not connect:' . mysql_error());

mysql_select_db('maindb') or die('Could not select database');



?>
