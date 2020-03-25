<?php if (!defined('THINK_PATH')) exit(); /*a:1:{s:71:"D:\WAMP\wamp64\www\tp5\public/../application/index\view\index\test.html";i:1560570105;}*/ ?>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"> 
	<title>菜鸟教程(runoob.com)</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">  
	<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
	<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	
<div class="container">
	<h2>韩服账号</h2>                                   
	<table class="table table-striped table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>id</th>
				<th>index</th>
				<th>title</th>
				<th>url</th>
				<th>price</th>
			</tr>
		</thead>
		<a href=""></a>
		<tbody>
			<?php
				foreach($arr as $value){
					echo '<tr>';
					echo '<td>'.$value['id'].'</td>';
					echo '<td>'.$value['index'].'</td>';
					echo '<td>'.$value['title'].'</td>';
					echo '<td>
							<a href="'.$value['href'].'">'.$value['href'].'</a>
						  </td>';
					echo '<td>'.$value['price'].'</td>';
					echo '</tr>';
				}
			?>
		</tbody>
	</table>
</div>

</body>
</html>