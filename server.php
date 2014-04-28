#!/usr/local/bin/php
<?php

include_once("connect2db.php");
?>

<!DOCTYPE html>
<html>


<head>

<META HTTP-EQUIV="refresh" CONTENT="20">
<script type="text/javascript" src="Distance.js"></script>

<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyC3wzMp-2Ss2iijSy7mBBKAcDZXOdz-FYg&sensor=false">
</script>


<script>
var counter = "<?php $result =mysql_query("select count(id) from location"); echo mysql_result($result,0); ?>";
document.write(counter);
var latArr = new Array();
var longArr = new Array();
</script>



<?php
$latArray = array();
$query1 = "select `lat` from location";
$result=mysql_query($query1);
while($row = mysql_fetch_array($result)) {
    // Append to the array
    $latArray[] = $row[0];   
}

?>

<script type="text/javascript">

  latArr= <?php echo json_encode($latArray ); ?>;
 </script>



<?php
$idArray = array();
$query4 = "select `id` from location";
$result4=mysql_query($query4);
while($row = mysql_fetch_array($result4)) {
    // Append to the array
    $idArray[] = $row[0];   
}

?>

<script type="text/javascript">

  idArr= <?php echo json_encode($idArray ); ?>;
 </script>

<?php
$flagArray = array();
$query3 = "select `flag` from location";
$result3=mysql_query($query3);
while($row = mysql_fetch_array($result3)) {
    // Append to the array
    $flagArray[] = $row[0];   
	if($row[0]==1)
{

$result =mysql_query("select `id` from location where `flag`=$row[0]");
 echo mysql_result($result,0);
 }
}

?>

<script type="text/javascript">

  flagArr= <?php echo json_encode($flagArray ); ?>;
 </script>


<?php
$longArray = array();
$query2 = "select `long` from location";
$result=mysql_query($query2);
while($row = mysql_fetch_array($result)) {
    // Append to the array
    $longArray[] = $row[0];   
}

?>

<script type="text/javascript">

    longArr= <?php echo json_encode($longArray ); ?>;
 </script>

<script>


var latSum=0;
var longSum=0;

for (var i=0;i<counter;i++)
{
latSum = latSum + parseFloat(latArr[i]);
}
for (var i=0;i<counter;i++)
{
longSum = longSum + parseFloat(longArr[i]);
}
var meanLat = latSum/counter;
var meanLong = longSum/counter;



document.write(parseFloat(meanLat)+'\n');
document.write(parseFloat(meanLong));
</script>


<script>
function initialize()
{

var mapProp = {
  center:new google.maps.LatLng(parseFloat(meanLat),parseFloat(meanLong)),
  zoom:19,
  mapTypeId:google.maps.MapTypeId.SATELLITE
  };
var map=new google.maps.Map(document.getElementById("googleMap")
  ,mapProp);

for (var i=0;i<counter;i++)
{




var marker = new google.maps.Marker({
      position: new google.maps.LatLng(parseFloat(latArr[i]),parseFloat(longArr[i])), 
      map: map, 
      title:"Victim"

    }); 
}

}

google.maps.event.addDomListener(window, 'load', initialize);
</script>


</head>

<body>

<div id="googleMap" style="width:1200px;height:600px;"></div>

</body>
</html>
