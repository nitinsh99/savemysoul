
<?php

include_once("connect2db.php");
?>

<!DOCTYPE html>
<html>


<head>

<META HTTP-EQUIV="refresh" CONTENT="20">

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
$query1 = "select latitude from location";
$result=mysql_query($query1,$handler);
while($row = mysql_fetch_array($result)) {
    // Append to the array
    $latArray[] = $row[0];   
}

?>

<script type="text/javascript">

  latArr= <?php echo json_encode($latArray ); ?>;
 </script>


<?php
$longArray = array();
$query2 = "select longitude from location";
$result=mysql_query($query2,$handler);
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
var marker = new google.maps.Marker({
      position: new google.maps.LatLng(parseFloat(meanLat),parseFloat(meanLong)), 
      map: map, 
      title:"Hello World!"
    }); 
}

google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>

<body>

<div id="googleMap" style="width:1200px;height:600px;"></div>

</body>
</html>
